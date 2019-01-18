package com.danpinciotti.mobile.hackernews.stories

import com.danpinciotti.mobile.hackernews.core.ui.presenter.BaseMvpPresenter
import com.danpinciotti.mobile.hackernews.models.Story
import javax.inject.Inject

class StoryListPresenter @Inject constructor() : BaseMvpPresenter<List<Story>, StoryListView>()