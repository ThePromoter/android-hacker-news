package com.danpinciotti.mobile.hackernews.core.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.core.ui.presenter.MvpPresenter
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<M, V : MvpView<M>, P : MvpPresenter<M, V>> :
    Fragment(),
    MvpView<M> {

    abstract fun presenter(): P
    @LayoutRes abstract fun getLayoutRes(): Int?

    private fun getMvpView(): V = this as V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (
                getLayoutRes()?.let { inflater.inflate(it, container, false) }
                    ?: super.onCreateView(inflater, container, savedInstanceState))
            .also {
                presenter().attachView(getMvpView())
            }
    }

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    private fun injectDependencies() = AndroidSupportInjection.inject(this)
}