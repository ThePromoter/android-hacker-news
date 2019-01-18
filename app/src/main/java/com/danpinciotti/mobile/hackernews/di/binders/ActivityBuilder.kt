package com.danpinciotti.mobile.hackernews.di.binders

import com.danpinciotti.mobile.hackernews.di.modules.StoryListActivityModule
import com.danpinciotti.mobile.hackernews.di.scopes.ActivityScope
import com.danpinciotti.mobile.hackernews.stories.StoryListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope @ContributesAndroidInjector(modules = [StoryListActivityModule::class])
    abstract fun bindStoryListActivity(): StoryListActivity
}