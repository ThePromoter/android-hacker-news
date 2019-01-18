package com.danpinciotti.mobile.hackernews.di

import com.danpinciotti.mobile.hackernews.HackerNewsApplication
import com.danpinciotti.mobile.hackernews.di.binders.ActivityBuilder
import com.danpinciotti.mobile.hackernews.di.binders.FragmentBuilder
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@ApplicationScope
@Component(modules = [
    AndroidInjectionModule::class,

    ActivityBuilder::class,
    FragmentBuilder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application:HackerNewsApplication): Builder
        fun build(): AppComponent
    }

    fun inject (app:HackerNewsApplication)
}