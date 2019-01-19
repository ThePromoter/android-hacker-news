package com.danpinciotti.mobile.hackernews.story.comments

import com.danpinciotti.mobile.hackernews.core.ui.view.MvpView
import com.danpinciotti.mobile.hackernews.models.Comment

interface StoryCommentsView : MvpView<List<Comment>> {

    fun renderComments(parentComments: List<Comment>)
}