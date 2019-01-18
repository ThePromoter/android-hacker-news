package com.danpinciotti.mobile.hackernews.service.caching

import com.danpinciotti.mobile.hackernews.api.HackerNewsApi
import com.danpinciotti.mobile.hackernews.core.service.BaseService
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.CommentDao
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.StoryDao
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem.Type.STORY
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.StoryService
import io.reactivex.Flowable
import javax.inject.Inject

class StoryCachingService @Inject constructor(
    private val api: HackerNewsApi,
    private val storyDao: StoryDao,
    private val commentDao: CommentDao
) : BaseService(), StoryService {

    override fun fetchTopStories(): Flowable<List<Story>> {
        val disk = storyDao.getStories()

        // Start by getting all of the top storyIds
        val network = api.getTopStoryIds()
            // Stream the results one at a time
            .flattenAsFlowable { it -> it }
            // Get the item for each id
            .flatMapSingle { id -> api.getItem(id) }
            // We only care about stories
            .filter { item -> item.type == STORY }
            .flatMap { storyItem ->
                // Start by persisting this story
                Flowable.fromCallable { storyDao.insert(storyItem.toStory()) }
                    // Next let's stream each of the commentIds for this story
                    .flatMapIterable { storyItem.commentIds }
                    // Get the item for each commentId
                    .flatMapSingle { commentId -> api.getItem(commentId) }
                    .map { commentItem -> commentItem.toComment(storyItem.id) }
                    // Persist the comment
                    .doOnNext { comment -> commentDao.insert(comment) }
                    // Finally, return the item as a story
                    .map { storyItem.toStory() }
            }
            // Group up all of the stories and sort them by date
            .toList()
            .toFlowable()

        // Will return the disk stories immediately, but also kick off a request to get fresh stories.
        // When that call has returned, will emit those stories as well
        return allListFlowables(disk, network)
    }

    /*
     * Functions for mapping API models to DAO entities
     */
    private fun HackerNewsItem.toStory(): Story {
        return Story(id = id, authorName = authorName, date = date, title = title!!, url = url, score = score)
    }

    private fun HackerNewsItem.toComment(storyId: Int): Comment {
        return Comment(id = id, parentStoryId = storyId, authorName = authorName, date = date, text = text!!)
    }
}