package com.danpinciotti.mobile.hackernews.story.comments

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.view.BaseFragment
import com.danpinciotti.mobile.hackernews.core.utils.toHtml
import com.danpinciotti.mobile.hackernews.models.Comment
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.fragment_story_comments.*
import kotlinx.android.synthetic.main.merge_story_summary.*
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
        renderStoryHeader()
        renderStoryText()
    }

    private fun renderStoryHeader() {
        val relativeDate = DateUtils.getRelativeDateTimeString(context, story.date.time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0)

        story_title.text = story.title
        author.text = context?.getString(R.string.author_information, story.authorName, relativeDate)
        upvote_count.text = story.score.toString()
        story_details.text = if (story.url != null) {
            context?.getString(R.string.story_details, story.commentCount, story.urlDomain)
        } else {
            context?.getString(R.string.comment_count, story.commentCount)
        }
    }

    private fun renderStoryText() {
        story.text?.let {
            story_text.visibility = VISIBLE
            story_text.text = it.toHtml()
        } ?: run {
            story_text.visibility = GONE
        }
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