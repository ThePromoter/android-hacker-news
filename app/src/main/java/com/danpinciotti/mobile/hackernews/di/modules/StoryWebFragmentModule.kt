package com.danpinciotti.mobile.hackernews.di.modules

import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.di.scopes.FragmentScope
import com.danpinciotti.mobile.hackernews.story.web.StoryWebViewFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [StoryWebFragmentModule.BindsModule::class, BaseFragmentModule::class])
class StoryWebFragmentModule {

    @Provides @FragmentScope
    fun provideWebViewClient(): WebViewClient = WebViewClient()

    @Module interface BindsModule {
        @Binds @FragmentScope
        fun bindFragment(fragment: StoryWebViewFragment): Fragment
    }
}