package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class HackerNewsItem(
    val id: Int,
    val type: Type,
    @Json(name = "by") val authorName: String,
    @Json(name = "time") val date: Date,
    val text: String?,
    @Json(name = "parent") val parentId: Int?,
    @Json(name = "kids") val commentIds: List<Int>?,
    val url: String?,
    val score: Int,
    val title: String,
    @Json(name = "descendants") val commentCount: Int?
) : Parcelable {

    enum class Type {
        JOB, STORY, COMMENT, POLL, POLLOPT
    }
}