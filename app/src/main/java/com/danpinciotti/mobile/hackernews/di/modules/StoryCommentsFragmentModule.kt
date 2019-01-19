package com.danpinciotti.mobile.hackernews.di.modules

import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.di.scopes.FragmentScope
import com.danpinciotti.mobile.hackernews.story.web.StoryWebViewFragment
import dagger.Binds
import dagger.Module

@Module(includes = [StoryCommentsFragmentModule.BindsModule::class, BaseFragmentModule::class])
class StoryCommentsFragmentModule {

    @Module interface BindsModule {
        @Binds @FragmentScope
        fun bindFragment(fragment: StoryWebViewFragment): Fragment
    }
}