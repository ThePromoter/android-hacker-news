package com.danpinciotti.mobile.hackernews.di.modules

import androidx.room.Room
import com.danpinciotti.mobile.hackernews.HackerNewsApplication
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.CommentDao
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.StoryDao
import com.danpinciotti.mobile.hackernews.database.HackerNewsDatabase
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides @ApplicationScope
    fun provideDatabase(application: HackerNewsApplication): HackerNewsDatabase =
        Room.databaseBuilder(application, HackerNewsDatabase::class.java, HackerNewsDatabase.NAME)
            .build()

    @Provides @ApplicationScope
    fun provideStoryDao(database: HackerNewsDatabase): StoryDao = database.storyDao()

    @Provides @ApplicationScope
    fun provideCommentDao(database: HackerNewsDatabase): CommentDao = database.commentDao()
}