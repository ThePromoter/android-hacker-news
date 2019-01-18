package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Comment(
    val id: Int,
    val type: Type,
    val by: String,
    val time: Date,
    val text: String?,
    val url: String,
    val title: String
) : Parcelable {

    enum class Type {
        JOB, STORY, COMMENT, POLL, POLLOPT
    }
}