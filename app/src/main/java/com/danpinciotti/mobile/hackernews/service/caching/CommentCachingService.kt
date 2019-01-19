package com.danpinciotti.mobile.hackernews.service.caching

import com.danpinciotti.mobile.hackernews.api.HackerNewsApi
import com.danpinciotti.mobile.hackernews.core.service.BaseService
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.CommentDao
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.service.CommentService
import io.reactivex.Flowable
import javax.inject.Inject

class CommentCachingService @Inject constructor(
    private val api: HackerNewsApi,
    private val commentDao: CommentDao
) : BaseService(), CommentService {

    override fun fetchComments(storyId: Int): Flowable<List<Comment>> {
        val disk = commentDao.getComments(storyId)

        // Start by getting the parent story
        val network = api.getItem(storyId)
            // Stream the commentIds one at a time
            .flattenAsFlowable { story -> story.commentIds }
            // Get the comment for each id
            .concatMapEager { id -> api.getItem(id).toFlowable() }
            .map { it.toComment(storyId) }
            .toSortedList()
            // Persist the comments
            .doOnSuccess { commentDao.insert(*it.toTypedArray()) }
            .toFlowable()

        // Will return the disk stories immediately, but also kick off a request to get fresh stories.
        // When that call has returned, will emit those stories as well
        return allListFlowables(disk, network)
    }

    private fun HackerNewsItem.toComment(storyId: Int): Comment {
        return Comment(id = id, parentStoryId = storyId, authorName = authorName, date = date, text = text!!)
    }
}