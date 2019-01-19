package com.danpinciotti.mobile.hackernews.story.comments

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danpinciotti.mobile.hackernews.R
import com.danpinciotti.mobile.hackernews.core.ui.BaseListAdapter
import com.danpinciotti.mobile.hackernews.core.utils.toHtml
import com.danpinciotti.mobile.hackernews.models.Comment
import kotlinx.android.synthetic.main.list_comment_item.view.*
import javax.inject.Inject

class CommentListAdapter @Inject constructor(
    private val context: Context
) : BaseListAdapter<Comment, CommentListAdapter.ViewHolder>(context) {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.author
        val date: TextView = itemView.date
        val comment: TextView = itemView.comment
        val indent: Guideline = itemView.indent
    }

    override fun getLayoutRes(viewType: Int) = R.layout.list_comment_item

    override fun createViewHolder(itemView: View, viewType: Int) = ViewHolder(itemView)

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val comment = getItem(position)
        val relativeDate = DateUtils.getRelativeDateTimeString(context, comment.date.time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0)

        vh.author.text = comment.authorName
        vh.date.text = relativeDate
        vh.comment.text = comment.text.toHtml()
        vh.indent.setGuidelineBegin(comment.level * context.resources.getDimensionPixelOffset(R.dimen.commentIndent))
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    fun setComments(newComments: List<Comment>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(items, newComments))
        items.clear()
        items.addAll(newComments)
        diff.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldItems: List<Comment>,
        private val newItems: List<Comment>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areItemsTheSame(oldItemPosition, newItemPosition)
    }
}