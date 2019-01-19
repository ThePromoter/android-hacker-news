package com.danpinciotti.mobile.hackernews.story.comments

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Comment
import javax.inject.Inject

class StoryCommentsPresenter @Inject constructor(

) : BaseMvpPresenter<List<Comment>, StoryCommentsView>()