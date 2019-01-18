package com.danpinciotti.mobile.hackernews.stories

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem.Type.STORY
import com.danpinciotti.mobile.hackernews.service.HackerNewsApi
import timber.log.Timber
import javax.inject.Inject

class StoryListPresenter @Inject constructor() :
    BaseMvpPresenter<List<HackerNewsItem>, StoryListView>() {

    @Inject lateinit var api: HackerNewsApi

    fun loadStories() {
        subscribe(api.getTopStoryIds()
                      .flattenAsFlowable { it -> it }
                      .flatMapSingle { storyId -> api.getItem(storyId) }
                      .filter { item -> item.type == STORY }
                      .subscribe { item ->
                          Timber.d("Item from ${item.authorName}")
                      })
    }
}