package com.danpinciotti.mobile.hackernews.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView





abstract class BaseListAdapter<L, VH : RecyclerView.ViewHolder>(
    context: Context
) : RecyclerView.Adapter<VH>() {

    private val inflater = LayoutInflater.from(context)

    var items: MutableList<L> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @LayoutRes protected abstract fun getLayoutRes(viewType: Int): Int
    protected abstract fun createViewHolder(itemView: View, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = inflater.inflate(getLayoutRes(viewType), parent, false)
        return createViewHolder(itemView, viewType)
    }

    override fun getItemCount() = items.size

    fun getItem(position: Int) = items[position]
}