package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryWithComments(
    val story: Story,
    val comments: List<Comment> = ArrayList()
) : Parcelable