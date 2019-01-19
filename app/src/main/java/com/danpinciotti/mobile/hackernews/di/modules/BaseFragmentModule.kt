package com.danpinciotti.mobile.hackernews.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.di.scopes.FragmentScope
import dagger.Module
import dagger.Provides


@Module
class BaseFragmentModule {

    @Provides @FragmentScope
    fun provideFragmentContext(fragment: Fragment): Context? = fragment.context
}