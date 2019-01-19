package com.danpinciotti.mobile.hackernews.models

import com.squareup.moshi.Json
import java.util.*

data class HackerNewsItem(
    val id: Int,
    val type: Type,
    @Json(name = "by") val authorName: String,
    @Json(name = "time") val date: Date,
    val text: String?,
    @Json(name = "parent") val parentId: Int?,
    @Json(name = "kids") val commentIds: List<Int>?,
    val url: String?,
    val score: Int = 0,
    val title: String?,
    @Json(name = "descendants") val commentCount: Int = 0) {

    enum class Type {
        JOB, STORY, COMMENT, POLL, POLLOPT
    }
}