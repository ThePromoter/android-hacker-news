package com.danpinciotti.mobile.hackernews.story

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.Story
import com.danpinciotti.mobile.hackernews.story.comments.StoryCommentsFragment
import com.danpinciotti.mobile.hackernews.story.web.BrowserControls
import com.danpinciotti.mobile.hackernews.story.web.StoryWebViewFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_story.*
import javax.inject.Inject


class StoryActivity :
    BaseActivity<Story, StoryView, StoryPresenter>(),
    StoryView, HasSupportFragmentInjector, PanelSlideListener {

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var presenter: StoryPresenter

    override fun presenter() = presenter

    override fun getLayoutRes() = com.danpinciotti.mobile.hackernews.R.layout.activity_story

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sliding_panel_layout.addPanelSlideListener(this)
        sliding_panel_layout.anchorPoint = 1f
        sliding_panel_layout.panelState = EXPANDED

        val storyId = intent.getIntExtra(KEY_STORY_ID, 0)
        presenter.loadStory(storyId)
    }

    private fun setupStoryActions(story: Story) {
        browse_back.setOnClickListener { supportFragmentManager.sendCommand(BrowserControls::goBack) }
        browse_forward.setOnClickListener { supportFragmentManager.sendCommand(BrowserControls::goForward) }
        refresh.setOnClickListener { supportFragmentManager.sendCommand(BrowserControls::refresh) }
        navigate_back.setOnClickListener { onBackPressed() }
        like.setOnClickListener { Toast.makeText(this, "Story liked", Toast.LENGTH_SHORT).show() }
        add_comment.setOnClickListener { Toast.makeText(this, "Add comment", Toast.LENGTH_SHORT).show() }
        open_externally_collapsed.setOnClickListener { story.url?.openURLExternally() }
        open_externally_expanded.setOnClickListener { story.url?.openURLExternally() }
    }

    override fun showStoryDetails(story: Story) {
        supportFragmentManager.beginTransaction().apply {
            if (story.url != null) {
                replace(com.danpinciotti.mobile.hackernews.R.id.main_fragment_container, StoryWebViewFragment.newInstance(story))
                Handler().postDelayed({ sliding_panel_layout.panelState = PanelState.COLLAPSED }, 500)
            } else {
                sliding_panel_layout.isTouchEnabled = false
            }
            replace(com.danpinciotti.mobile.hackernews.R.id.sliding_fragment_container, StoryCommentsFragment.newInstance(story))
            commit()
        }
        setupStoryActions(story)
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {}

    override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {
        when (newState) {
            EXPANDED -> {
                expanded_action_group.visibility = VISIBLE
                collapsed_action_group.visibility = GONE
            }
            COLLAPSED -> {
                expanded_action_group.visibility = GONE
                collapsed_action_group.visibility = VISIBLE
            }
        }
    }

    private fun String.openURLExternally() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
        startActivity(browserIntent)
    }

    private inline fun <reified T> FragmentManager.sendCommand(callback: (T) -> Unit) {
        for (fragment in fragments) {
            if (fragment is T) {
                callback(fragment)
            }
        }
    }

    override fun supportFragmentInjector() = fragmentInjector

    companion object {
        const val KEY_STORY_ID = "KEY_STORY_ID"
    }
}