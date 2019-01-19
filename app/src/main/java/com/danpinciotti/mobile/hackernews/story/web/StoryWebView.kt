package com.danpinciotti.mobile.hackernews.story.web

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.models.Story

interface StoryWebView : MvpView<Story> {

    fun showUrl(url: String)
}