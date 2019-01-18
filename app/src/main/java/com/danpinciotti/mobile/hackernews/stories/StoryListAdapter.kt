package com.danpinciotti.mobile.hackernews.stories

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.BaseListAdapter
import com.danpinciotti.mobile.hackernews.models.Story
import kotlinx.android.synthetic.main.list_story_item.view.*
import javax.inject.Inject

class StoryListAdapter @Inject constructor(context: Context) :
    BaseListAdapter<Story, StoryListAdapter.ViewHolder>(context) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.story_title
    }

    override fun getLayoutRes(viewType: Int) = R.layout.list_story_item

    override fun createViewHolder(itemView: View, viewType: Int) = ViewHolder(itemView)

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val story = getItem(position)

        vh.title.text = story.title
    }
}