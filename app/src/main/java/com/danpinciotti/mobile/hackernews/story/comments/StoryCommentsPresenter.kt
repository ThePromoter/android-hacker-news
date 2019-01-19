package com.danpinciotti.mobile.hackernews.story.comments

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.service.CommentService
import javax.inject.Inject

class StoryCommentsPresenter @Inject constructor(
    private val commentService: CommentService
) : BaseMvpPresenter<List<Comment>, StoryCommentsView>() {

    fun loadAllNestedComments(story: Story) {
        subscribe(commentService.fetchComments(story.id))
    }

    override fun onNext(data: List<Comment>) {
        getView()?.renderComments(data)
    }

    override fun onError(error: Throwable?) {

    }
}