package com.danpinciotti.mobile.hackernews.core.ui.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.danpinciotti.mobile.hackernews.core.ui.presenter.MvpPresenter
import dagger.android.AndroidInjection

abstract class BaseActivity<M, V : MvpView<M>, P : MvpPresenter<M, V>> :
    AppCompatActivity(),
    MvpView<M> {

    abstract fun presenter(): P
    @LayoutRes abstract fun getLayoutRes(): Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        getLayoutRes()?.let {
            setContentView(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter().detachView()
    }

    private fun injectDependencies() = AndroidInjection.inject(this)
}