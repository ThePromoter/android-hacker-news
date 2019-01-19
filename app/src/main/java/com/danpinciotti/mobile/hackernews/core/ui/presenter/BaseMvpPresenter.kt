package com.danpinciotti.mobile.hackernews.core.ui.presenter

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.di.qualifiers.IOScheduler
import com.danpinciotti.mobile.hackernews.di.qualifiers.UIScheduler
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.ResourceSubscriber
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject


abstract class BaseMvpPresenter<M, V : MvpView<M>> :
    MvpPresenter<M, V> {

    @Inject @field:UIScheduler lateinit var uiScheduler: Scheduler
    @Inject @field:IOScheduler lateinit var ioScheduler: Scheduler

    private var viewRef: WeakReference<V>? = null
    private var subscriptions: CompositeDisposable = CompositeDisposable()
        get() {
            if (field.isDisposed) field = CompositeDisposable()
            return field
        }

    protected fun subscribe(flowable: Flowable<M>) {
        val subscriber = object : ResourceSubscriber<M>() {
            override fun onComplete() {
                this@BaseMvpPresenter.onComplete()
            }

            override fun onNext(t: M) {
                this@BaseMvpPresenter.onNext(t)
            }

            override fun onError(t: Throwable?) {
                this@BaseMvpPresenter.onError(t)
            }
        }

        subscriptions.add(flowable.observeOn(uiScheduler)
                              .subscribeOn(ioScheduler)
                              .subscribeWith(subscriber))
    }

    protected fun subscribe(single: Single<M>) {
        val observer = object : DisposableSingleObserver<M>() {
            override fun onSuccess(t: M) {
                this@BaseMvpPresenter.onNext(t)
                this@BaseMvpPresenter.onComplete()
            }

            override fun onError(e: Throwable) {
                this@BaseMvpPresenter.onError(e)
            }
        }

        subscriptions.add(single.observeOn(uiScheduler)
                              .subscribeOn(ioScheduler)
                              .subscribeWith(observer))
    }

    open fun onComplete() {}
    open fun onNext(data: M) {}
    open fun onError(error: Throwable?) {
        Timber.e(error)
    }

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun getView(): V? = viewRef?.get()

    override fun detachView() {
        viewRef?.clear()
        viewRef = null
        subscriptions.dispose()
    }
}