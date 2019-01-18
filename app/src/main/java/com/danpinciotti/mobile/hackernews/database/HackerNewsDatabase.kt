package com.danpinciotti.mobile.hackernews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.CommentDao
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.StoryDao
import com.danpinciotti.mobile.hackernews.database.converters.DateTypeConverter
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story

@Database(entities = [
    Story::class,
    Comment::class
], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class HackerNewsDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun commentDao(): CommentDao

    companion object {
        const val NAME = "hacker-news"
    }
}