package com.danpinciotti.mobile.hackernews.story

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.models.StoryWithComments

interface StoryView : MvpView<StoryWithComments> {

    fun showStoryDetails(storyWithComments: StoryWithComments)
}