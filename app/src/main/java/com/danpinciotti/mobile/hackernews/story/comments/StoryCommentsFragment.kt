package com.danpinciotti.mobile.hackernews.story.comments

import android.os.Bundle
import android.view.View
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseFragment
import com.danpinciotti.mobile.hackernews.models.Comment
import javax.inject.Inject

class StoryCommentsFragment :
    BaseFragment<List<Comment>, StoryCommentsView, StoryCommentsPresenter>(),
    StoryCommentsView {

    @Inject lateinit var presenter: StoryCommentsPresenter

    private lateinit var comments: List<Comment>

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.fragment_story_comments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comments = (arguments?.getParcelableArray(COMMENTS) as? Array<Comment>)?.toList() ?: throw Exception("missing required story argument")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val COMMENTS = "COMMENTS"

        fun newInstance(comments: List<Comment>): StoryCommentsFragment {
            val fragment = StoryCommentsFragment()
            fragment.arguments = Bundle().apply {
                putParcelableArray(COMMENTS, comments.toTypedArray())
            }
            return fragment
        }
    }
}