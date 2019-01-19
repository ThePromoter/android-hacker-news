package com.danpinciotti.mobile.hackernews.story.web

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseFragment
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.fragment_story_web_view.*
import javax.inject.Inject

class StoryWebViewFragment :
    BaseFragment<Story, StoryWebView, StoryWebPresenter>(),
    StoryWebView, BrowserControls {

    @Inject lateinit var presenter: StoryWebPresenter
    @Inject lateinit var webViewClient: WebViewClient

    private lateinit var story: Story

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.fragment_story_web_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        story = arguments?.getParcelable(STORY) ?: throw Exception("missing required story argument")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        web_view.webViewClient = webViewClient
        presenter.loadStoryUrl(story)
    }

    override fun showUrl(url: String) {
        web_view.loadUrl(url)
    }

    override fun refresh() {
        web_view.reload()
    }

    override fun goBack() {
        web_view.goBack()
    }

    override fun goForward() {
        web_view.goForward()
    }

    companion object {
        const val STORY = "STORY"

        fun newInstance(story: Story): StoryWebViewFragment {
            val fragment = StoryWebViewFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(STORY, story)
            }
            return fragment
        }
    }
}