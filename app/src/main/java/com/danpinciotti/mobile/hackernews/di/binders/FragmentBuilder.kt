package com.danpinciotti.mobile.hackernews.di.binders

import com.danpinciotti.mobile.hackernews.di.modules.StoryCommentsFragmentModule
import com.danpinciotti.mobile.hackernews.di.modules.StoryWebViewFragmentModule
import com.danpinciotti.mobile.hackernews.di.scopes.FragmentScope
import com.danpinciotti.mobile.hackernews.story.comments.StoryCommentsFragment
import com.danpinciotti.mobile.hackernews.story.web.StoryWebViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @FragmentScope @ContributesAndroidInjector(modules = [StoryWebViewFragmentModule::class])
    abstract fun bindStoryWebViewFragment(): StoryWebViewFragment

    @FragmentScope @ContributesAndroidInjector(modules = [StoryCommentsFragmentModule::class])
    abstract fun bindStoryCommentsFragment(): StoryCommentsFragment
}