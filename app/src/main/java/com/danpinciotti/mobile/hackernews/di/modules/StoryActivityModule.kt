package com.danpinciotti.mobile.hackernews.di.modules

import androidx.appcompat.app.AppCompatActivity
import com.danpinciotti.mobile.hackernews.di.scopes.ActivityScope
import com.danpinciotti.mobile.hackernews.story.StoryActivity
import dagger.Binds
import dagger.Module

@Module(includes = [StoryActivityModule.BindsModule::class, BaseActivityModule::class])
class StoryActivityModule {

    @Module interface BindsModule {
        @Binds @ActivityScope
        fun bindActivity(activity: StoryActivity): AppCompatActivity
    }
}