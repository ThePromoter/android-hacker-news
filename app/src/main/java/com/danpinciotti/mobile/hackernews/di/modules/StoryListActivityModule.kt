package com.danpinciotti.mobile.hackernews.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.danpinciotti.mobile.hackernews.di.scopes.ActivityScope
import com.danpinciotti.mobile.hackernews.stories.StoryListActivity
import com.danpinciotti.mobile.hackernews.stories.StoryListAdapter
import com.danpinciotti.mobile.hackernews.stories.StoryListAdapter.StoryActionListener
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [StoryListActivityModule.BindsModule::class, BaseActivityModule::class])
class StoryListActivityModule {

    @Provides @ActivityScope
    fun provideAdapter(context: Context, listener: StoryActionListener) = StoryListAdapter(context, listener)

    @Provides @ActivityScope
    fun provideLayoutManager(context: Context): LayoutManager = LinearLayoutManager(context)

    @Module interface BindsModule {
        @Binds @ActivityScope
        fun bindActivity(activity: StoryListActivity): AppCompatActivity

        @Binds @ActivityScope
        fun bindListener(activity: StoryListActivity): StoryActionListener
    }
}