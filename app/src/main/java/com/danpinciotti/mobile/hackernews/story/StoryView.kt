package com.danpinciotti.mobile.hackernews.story

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.models.Story

interface StoryView : MvpView<Story> {

    fun showStoryDetails(story: Story)
}