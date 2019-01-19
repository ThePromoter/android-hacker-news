package com.danpinciotti.mobile.hackernews.stories

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.stories.StoryListAdapter.StoryActionListener
import com.danpinciotti.mobile.hackernews.story.StoryActivity
import com.danpinciotti.mobile.hackernews.story.StoryActivity.Companion.KEY_STORY_ID
import kotlinx.android.synthetic.main.activity_story_list.*
import javax.inject.Inject

class StoryListActivity :
    BaseActivity<List<Story>, StoryListView, StoryListPresenter>(),
    StoryListView, StoryActionListener {

    @Inject lateinit var presenter: StoryListPresenter
    @Inject lateinit var adapter: StoryListAdapter
    @Inject lateinit var layoutManager: LayoutManager

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.activity_story_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        story_recycler_view.adapter = adapter
        story_recycler_view.layoutManager = layoutManager

        presenter.loadStories()
    }

    override fun setStories(stories: List<Story>) {
        adapter.setStories(stories)
    }

    override fun storyClicked(storyId: Int) {
        Intent(this, StoryActivity::class.java).run {
            putExtra(KEY_STORY_ID, storyId)
            startActivity(this)
        }
    }
}
