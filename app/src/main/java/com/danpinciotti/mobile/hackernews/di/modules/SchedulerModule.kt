package com.danpinciotti.mobile.hackernews.di.modules

import com.danpinciotti.mobile.hackernews.di.qualifiers.IoScheduler
import com.danpinciotti.mobile.hackernews.di.qualifiers.UiScheduler
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulerModule {

    @Provides @ApplicationScope @UiScheduler
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides @ApplicationScope @IoScheduler
    fun provideIoScheduler() = Schedulers.io()
}