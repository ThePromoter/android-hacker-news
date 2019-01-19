package com.danpinciotti.mobile.hackernews.di.modules

import android.app.Application
import com.danpinciotti.mobile.hackernews.HackerNewsApplication
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds @ApplicationScope
    fun bindApplication(application: HackerNewsApplication): Application
}