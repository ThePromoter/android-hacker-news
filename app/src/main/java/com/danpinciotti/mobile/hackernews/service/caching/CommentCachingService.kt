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
        val disk = commentDao.getParentCommentsForStory(storyId)
            .flattenAsFlowable { it }
            .concatMap { getCommentsFromDao(it) }
            .toList()
            .toFlowable()

        // Start by getting the parent story
        val network = api.getItem(storyId)
            .filter { !it.deleted }
            .flattenAsFlowable { it.commentIds }
            .concatMapEager { api.getItem(it).toFlowable() }
            .sorted()
            .filter { !it.deleted }
            // Stream the commentIds one at a time
            .concatMap { getCommentsFromApi(it, storyId) }
            .toList()
            // Persist the comments
            .doOnSuccess { commentDao.insert(*it.toTypedArray()) }
            .toFlowable()

        // Will return the disk stories immediately, but also kick off a request to get fresh stories.
        // When that call has returned, will emit those stories as well
        return allListFlowables(disk, network)
    }

    private fun getCommentsFromDao(parentComment: Comment, level: Int = 0): Flowable<Comment> {
        return Flowable.merge(Flowable.just(parentComment.also { it.level = level }),
                              commentDao.getChildComments(parentComment.id)
                                  .flattenAsFlowable { it }
                                  .concatMap { getCommentsFromDao(it, level + 1) })
    }

    private fun getCommentsFromApi(parentItem: HackerNewsItem, storyId: Int, level: Int = 0): Flowable<Comment> {
        return if (parentItem.commentIds.isNullOrEmpty()) {
            Flowable.just(parentItem.toComment(storyId).also { it.level = level })
        } else {
            Flowable.merge(Flowable.just(parentItem)
                               .map { it.toComment(storyId).also { comment -> comment.level = level } },
                           Flowable.fromIterable(parentItem.commentIds)
                               .concatMapEager { api.getItem(it).toFlowable() }
                               .filter { !it.deleted }
                               .sorted()
                               .concatMap { getCommentsFromApi(it, storyId, level + 1) })
        }
    }

    private fun HackerNewsItem.toComment(storyId: Int): Comment {
        val parentCommentId = if (parentId != storyId) parentId else null
        return Comment(id = id, parentStoryId = storyId, parentCommentId = parentCommentId, authorName = authorName!!, date = date, text = text!!)
    }
}