package com.danpinciotti.mobile.hackernews.service.caching

import com.danpinciotti.mobile.hackernews.api.HackerNewsApi
import com.danpinciotti.mobile.hackernews.core.service.BaseService
import com.danpinciotti.mobile.hackernews.database.HackerNewsDao.StoryDao
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem.Type.STORY
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.StoryService
import io.reactivex.Flowable
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
            .concatMapEager { id -> api.getItem(id).toFlowable() }
            // We only care about stories
            .filter { it.type == STORY }
            .take(PAGE_SIZE)
            .map { it.toStory() }
            .sorted()
            .doOnNext { storyDao.insert(it) }
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

    companion object {
        const val PAGE_SIZE = 100L
    }
}