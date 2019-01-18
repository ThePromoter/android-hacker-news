package com.danpinciotti.mobile.hackernews

import android.app.Activity
import android.app.Application
import com.danpinciotti.mobile.hackernews.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class HackerNewsApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() = DaggerAppComponent.builder().application(this).build().inject(this)

    override fun activityInjector() = activityInjector
}