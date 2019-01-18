package com.danpinciotti.mobile.hackernews.di.modules

import com.danpinciotti.mobile.hackernews.di.qualifiers.IOScheduler
import com.danpinciotti.mobile.hackernews.di.qualifiers.UIScheduler
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulerModule {

    @Provides @ApplicationScope @UIScheduler
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides @ApplicationScope @IOScheduler
    fun provideIoScheduler() = Schedulers.io()
}