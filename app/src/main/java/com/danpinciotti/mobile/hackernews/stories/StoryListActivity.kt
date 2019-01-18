package com.danpinciotti.mobile.hackernews.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class StoryListActivity :
    BaseActivity<List<HackerNewsItem>, StoryListView, StoryListPresenter>(),
    StoryListView, HasSupportFragmentInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var presenter: StoryListPresenter

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.activity_story_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.loadStories()
    }

    override fun supportFragmentInjector() = injector
}
