package com.danpinciotti.mobile.hackernews.core.ui.presenter

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView

interface MvpPresenter<M, V : MvpView<M>> {

    /**
     * Set or attach the view to this presenter
     */
    fun attachView(view: V)

    /**
     * Checks if a view is attached to this presenter
     */
    fun isViewAttached() = getView() != null

    /**
     * Called when the view has been destroyed. Typically this method will be invoked from
     * <code>Activity#detachView()</code> or <code>Fragment#onDestroyView()</code>
     */
    fun detachView()

    /**
     * Get the attached view
     */
    fun getView(): V?
}