package com.danpinciotti.mobile.hackernews.story

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.StoryService
import javax.inject.Inject

class StoryPresenter @Inject constructor() :
    BaseMvpPresenter<Story, StoryView>() {

    @Inject lateinit var storyService: StoryService

    fun loadStory(storyId: Int) {
        subscribe(storyService.fetchStory(storyId))
    }

    override fun onNext(data: Story) {
        getView()?.showStoryDetails(data)
    }
}