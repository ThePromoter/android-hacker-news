package com.danpinciotti.mobile.hackernews.story.web

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Story
import javax.inject.Inject

class StoryWebPresenter @Inject constructor() : BaseMvpPresenter<Story, StoryWebView>() {

    fun loadStoryUrl(story: Story) {
        story.url?.run {
            getView()?.showUrl(this)
        }
    }
}