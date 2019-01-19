package com.danpinciotti.mobile.hackernews.di.modules

import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.danpinciotti.mobile.hackernews.di.scopes.ActivityScope
import com.danpinciotti.mobile.hackernews.story.StoryActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [StoryActivityModule.BindsModule::class, BaseActivityModule::class])
class StoryActivityModule {

    @Provides @ActivityScope
    fun provideWebViewClient(): WebViewClient = WebViewClient()

    @Module interface BindsModule {
        @Binds @ActivityScope
        fun bindActivity(activity: StoryActivity): AppCompatActivity
    }
}