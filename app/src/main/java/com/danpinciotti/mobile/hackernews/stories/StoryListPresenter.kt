package com.danpinciotti.mobile.hackernews.stories

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.StoryService
import javax.inject.Inject

class StoryListPresenter @Inject constructor() :
    BaseMvpPresenter<List<Story>, StoryListView>() {

    @Inject lateinit var service: StoryService

    fun loadStories() {
        subscribe(service.fetchTopStories())
    }

    override fun onNext(data: List<Story>) {
        getView()?.setStories(data)
    }
}