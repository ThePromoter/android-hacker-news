package com.danpinciotti.mobile.hackernews.core.ui.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.core.ui.presenter.MvpPresenter
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<M, V : MvpView<M>, P : MvpPresenter<M, V>> :
    Fragment(),
    MvpView<M> {

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    private fun injectDependencies() = AndroidSupportInjection.inject(this)
}