package com.danpinciotti.mobile.hackernews.service.caching

import android.net.Uri
import com.danpinciotti.mobile.hackernews.api.HackerNewsApi
import com.danpinciotti.mobile.hackernews.core.service.BaseService
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.StoryDao
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem.Type.STORY
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.StoryService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class StoryCachingService @Inject constructor(
    private val api: HackerNewsApi,
    private val storyDao: StoryDao
) : BaseService(), StoryService {

    override fun fetchTopStories(): Flowable<List<Story>> {
        val disk = storyDao.getStories(PAGE_SIZE)

        // Start by getting all of the top storyIds
        val network = api.getTopStoryIds()
            // Stream the results one at a time
            .flattenAsFlowable { it -> it }
            // Get the item for each id
            .flatMapSingle { id -> api.getItem(id) }
            // We only care about stories
            .filter { it.type == STORY && !it.deleted }
            .take(PAGE_SIZE)
            .map { it.toStory() }
            .toSortedList()
            // Persist the stories
            .doOnSuccess { storyDao.insert(*it.toTypedArray()) }
            .toFlowable()

        // Will return the disk stories immediately, but also kick off a request to get fresh stories.
        // When that call has returned, will emit those stories as well
        return allListFlowables(disk, network)
    }

    override fun fetchStory(storyId: Int): Single<Story> {
        val disk = storyDao.getStory(storyId)
        val network = api.getItem(storyId)
            .filter { !it.deleted }
            .toSingle()
            .map { it.toStory() }
            .doOnSuccess { storyDao.insert(it) }

        return firstSingle(disk, network)
    }

    /*
     * Functions for mapping API models to DAO entities
     */
    private fun HackerNewsItem.toStory(): Story {
        val urlDomain = url?.let { Uri.parse(it).host }
        return Story(id = id, authorName = authorName!!, date = date, title = title!!, text = text, url = url, urlDomain = urlDomain, score = score, commentCount = commentCount)
    }

    companion object {
        const val PAGE_SIZE = 100L
    }
}