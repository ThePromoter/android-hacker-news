package com.danpinciotti.mobile.hackernews.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.danpinciotti.mobile.hackernews.di.scopes.FragmentScope
import com.danpinciotti.mobile.hackernews.story.comments.CommentListAdapter
import com.danpinciotti.mobile.hackernews.story.comments.StoryCommentsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [StoryCommentsFragmentModule.BindsModule::class, BaseFragmentModule::class])
class StoryCommentsFragmentModule {

    @Provides @FragmentScope
    fun provideAdapter(context: Context?) = CommentListAdapter(context!!)

    @Provides @FragmentScope
    fun provideLayoutManager(context: Context?): LayoutManager = LinearLayoutManager(context)

    @Module interface BindsModule {
        @Binds @FragmentScope
        fun bindFragment(fragment: StoryCommentsFragment): Fragment
    }
}