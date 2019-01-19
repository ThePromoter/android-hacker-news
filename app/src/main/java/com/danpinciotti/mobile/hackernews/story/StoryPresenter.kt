package com.danpinciotti.mobile.hackernews.story

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.StoryWithComments
import com.danpinciotti.mobile.hackernews.service.CommentService
import com.danpinciotti.mobile.hackernews.service.StoryService
import javax.inject.Inject

class StoryPresenter @Inject constructor() :
    BaseMvpPresenter<StoryWithComments, StoryView>() {

    @Inject lateinit var storyService: StoryService
    @Inject lateinit var commentService: CommentService

    fun loadStory(storyId: Int) {
        subscribe(storyService.fetchStory(storyId)
                      .toFlowable()
                      .flatMap({ story -> commentService.fetchComments(story.id) },
                               { story, comments -> StoryWithComments(story, comments) }))
    }

    override fun onNext(data: StoryWithComments) {
        getView()?.showStoryDetails(data)
    }
}