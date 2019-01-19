package com.danpinciotti.mobile.hackernews.story.comments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseFragment
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.fragment_story_comments.*
import javax.inject.Inject

class StoryCommentsFragment :
    BaseFragment<List<Comment>, StoryCommentsView, StoryCommentsPresenter>(),
    StoryCommentsView {

    @Inject lateinit var presenter: StoryCommentsPresenter
    @Inject lateinit var adapter: CommentListAdapter
    @Inject lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var story: Story

    override fun presenter() = presenter

    override fun getLayoutRes() = R.layout.fragment_story_comments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        story = arguments?.getParcelable(STORY) ?: throw Exception("missing required story argument")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comment_recycler_view.adapter = adapter
        comment_recycler_view.layoutManager = layoutManager

        presenter.loadAllNestedComments(story)
    }

    override fun renderComments(parentComments: List<Comment>) {
        adapter.setComments(parentComments)
    }

    companion object {
        const val STORY = "STORY"

        fun newInstance(story: Story): StoryCommentsFragment {
            val fragment = StoryCommentsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(STORY, story)
            }
            return fragment
        }
    }
}