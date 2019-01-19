package com.danpinciotti.mobile.hackernews.di

import com.danpinciotti.mobile.hackernews.HackerNewsApplication
import com.danpinciotti.mobile.hackernews.di.binders.ActivityBuilder
import com.danpinciotti.mobile.hackernews.di.binders.FragmentBuilder
import com.danpinciotti.mobile.hackernews.di.modules.*
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [
    AndroidSupportInjectionModule::class,

    ActivityBuilder::class,
    FragmentBuilder::class,

    AppModule::class,
    ApiModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    SchedulerModule::class,
    ServiceModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: HackerNewsApplication): Builder

        fun build(): AppComponent
    }

    fun inject (app:HackerNewsApplication)
}