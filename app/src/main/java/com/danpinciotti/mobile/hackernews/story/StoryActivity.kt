package com.danpinciotti.mobile.hackernews.story

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.story.comments.StoryCommentsFragment
import com.danpinciotti.mobile.hackernews.story.web.StoryWebViewFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_story.*
import javax.inject.Inject


class StoryActivity :
    BaseActivity<Story, StoryView, StoryPresenter>(),
    StoryView, HasSupportFragmentInjector {

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var presenter: StoryPresenter

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.activity_story

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupStoryActions()

        val storyId = intent.getIntExtra(KEY_STORY_ID, 0)
        presenter.loadStory(storyId)
    }

    private fun setupStoryActions() {
        browse_back.setOnClickListener {  }
        browse_forward.setOnClickListener {  }
        refresh.setOnClickListener {  }
        open_externally.setOnClickListener {  }
    }

    override fun showStoryDetails(story: Story) {
        supportFragmentManager.beginTransaction().apply {
            if (story.url != null) {
                replace(R.id.main_fragment_container, StoryWebViewFragment.newInstance(story))
            }
            replace(R.id.sliding_fragment_container, StoryCommentsFragment.newInstance(story))
            commit()
        }
    }

    override fun supportFragmentInjector() = fragmentInjector

    companion object {
        const val KEY_STORY_ID = "KEY_STORY_ID"
    }
}