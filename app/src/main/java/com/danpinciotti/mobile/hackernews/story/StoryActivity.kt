package com.danpinciotti.mobile.hackernews.story

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseActivity
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.StoryWithComments
import kotlinx.android.synthetic.main.activity_story.*
import javax.inject.Inject


class StoryActivity :
    BaseActivity<StoryWithComments, StoryView, StoryPresenter>(),
    StoryView {

    @Inject lateinit var presenter: StoryPresenter
    @Inject lateinit var webViewClient: WebViewClient

    override fun presenter() = presenter

    override fun getLayoutRes() = com.danpinciotti.mobile.hackernews.R.layout.activity_story

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        web_view.webViewClient = webViewClient
        web_view.settings.javaScriptEnabled = true

        val storyId = intent.getIntExtra(KEY_STORY_ID, 0)
        presenter.loadStory(storyId)
    }

    override fun showUrl(url: String) {
        web_view.loadUrl(url)
    }

    override fun showComments(comments: List<Comment>) {

    }

    companion object {
        const val KEY_STORY_ID = "KEY_STORY_ID"
    }
}