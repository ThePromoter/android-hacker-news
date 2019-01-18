package com.danpinciotti.mobile.hackernews.stories

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.activity_story_list.*
import javax.inject.Inject

class StoryListActivity :
    BaseActivity<List<Story>, StoryListView, StoryListPresenter>(),
    StoryListView {

    @Inject lateinit var presenter: StoryListPresenter
    @Inject lateinit var adapter: StoryListAdapter
    @Inject lateinit var layoutManager: LayoutManager

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.activity_story_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        story_recycler_view.adapter = adapter
        story_recycler_view.layoutManager = layoutManager

        presenter.loadStories()
    }

    override fun setStories(stories: List<Story>) {
        adapter.addStories(stories)
    }
}
