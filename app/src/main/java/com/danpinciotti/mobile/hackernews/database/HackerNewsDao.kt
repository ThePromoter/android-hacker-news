package com.danpinciotti.mobile.hackernews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story
import io.reactivex.Flowable


abstract class HackerNewsDao {

    @Dao
    abstract class StoryDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insert(vararg stories: Story)

        @Query(value = "SELECT * FROM stories ORDER BY date DESC LIMIT :limit")
        abstract fun getStories(limit: Long): Flowable<List<Story>>
    }

    @Dao
    abstract class CommentDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insert(vararg comments: Comment)

        @Query(value = "SELECT * FROM comments WHERE parentStoryId = :parentStoryId ORDER BY date DESC")
        abstract fun getComments(parentStoryId: Int): Flowable<List<Comment>>
    }
}