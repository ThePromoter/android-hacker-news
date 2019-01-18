package com.danpinciotti.mobile.hackernews.core.ui.presenter

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import java.lang.ref.WeakReference


abstract class BaseMvpPresenter<M, V : MvpView<M>> : MvpPresenter<M, V> {

    private var viewRef: WeakReference<V>? = null

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun getView(): V? = viewRef?.get()

    override fun detachView() {
        viewRef?.clear()
        viewRef = null
    }
}