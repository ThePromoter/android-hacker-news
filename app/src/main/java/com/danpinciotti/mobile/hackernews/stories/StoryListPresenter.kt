package com.danpinciotti.mobile.hackernews.stories

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.danpinciotti.mobile.hackernews.service.StoryService
import timber.log.Timber
import javax.inject.Inject

class StoryListPresenter @Inject constructor() :
    BaseMvpPresenter<List<HackerNewsItem>, StoryListView>() {

    @Inject lateinit var service: StoryService

    fun loadStories() {
        subscribe(service.fetchTopStories()
                      .flatMapIterable { it -> it }
                      .observeOn(uiScheduler)
                      .subscribe { story ->
                          Timber.d("Story titled \"${story.title}\" from \"${story.authorName}\"")
                      })
    }
}