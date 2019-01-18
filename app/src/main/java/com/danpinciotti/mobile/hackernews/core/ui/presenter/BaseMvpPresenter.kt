package com.danpinciotti.mobile.hackernews.core.ui.presenter

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.di.qualifiers.UIScheduler
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject


abstract class BaseMvpPresenter<M, V : MvpView<M>> : MvpPresenter<M, V> {

    @Inject @field:UIScheduler lateinit var uiScheduler: Scheduler

    private var viewRef: WeakReference<V>? = null
    private var subscriptions: CompositeDisposable = CompositeDisposable()
        get() {
            if (field.isDisposed) field = CompositeDisposable()
            return field
        }

    protected fun subscribe(disposable: Disposable) {
        subscriptions.add(disposable)
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