package com.danpinciotti.mobile.hackernews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story
import io.reactivex.Flowable
import io.reactivex.Single


abstract class HackerNewsDao {

    @Dao
    abstract class StoryDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insert(vararg stories: Story)

        @Query(value = "SELECT * FROM stories ORDER BY date DESC LIMIT :limit")
        abstract fun getStories(limit: Long): Flowable<List<Story>>

        @Query(value = "SELECT * FROM stories WHERE id = :storyId")
        abstract fun getStory(storyId: Int): Single<Story>
    }

    @Dao
    abstract class CommentDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insert(vararg comments: Comment)

        @Query(value = "SELECT * FROM comments WHERE parentStoryId = :parentStoryId AND parentCommentId IS NULL ORDER BY date DESC")
        abstract fun getParentCommentsForStory(parentStoryId: Int): Single<List<Comment>>

        @Query(value = "SELECT * FROM comments WHERE parentCommentId = :parentCommentId ORDER BY date DESC")
        abstract fun getChildComments(parentCommentId: Int): Single<List<Comment>>
    }
}