package com.danpinciotti.mobile.hackernews.stories

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.BaseListAdapter
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.list_story_item.view.*
import javax.inject.Inject

class StoryListAdapter @Inject constructor(val context: Context) :
    BaseListAdapter<Story, StoryListAdapter.ViewHolder>(context) {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ViewGroup = itemView.container
        val title: TextView = itemView.story_title
        val author: TextView = itemView.author
        val upvoteCount: TextView = itemView.upvote_count
    }

    override fun getLayoutRes(viewType: Int) = R.layout.list_story_item

    override fun createViewHolder(itemView: View, viewType: Int) = ViewHolder(itemView)

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val story = getItem(position)
        val relativeDate = DateUtils.getRelativeDateTimeString(context, story.date.time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0)

        vh.title.text = story.title
        vh.author.text = context.getString(R.string.author_information, story.authorName, relativeDate)
        vh.upvoteCount.text = story.score.toString()

        vh.container.setOnClickListener {  }
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    fun setStories(newItems: List<Story>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldItems: List<Story>,
        private val newItems: List<Story>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areItemsTheSame(oldItemPosition, newItemPosition)
    }
}