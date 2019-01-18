package com.danpinciotti.mobile.hackernews.stories

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.BaseListAdapter
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.list_story_item.view.*
import javax.inject.Inject

class StoryListAdapter @Inject constructor(context: Context) :
    BaseListAdapter<Story, StoryListAdapter.ViewHolder>(context) {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.story_title
    }

    override fun getLayoutRes(viewType: Int) = R.layout.list_story_item

    override fun createViewHolder(itemView: View, viewType: Int) = ViewHolder(itemView)

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val story = getItem(position)

        vh.title.text = story.title
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    fun addStories(newItems: List<Story>) {
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