package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "comments",
    indices = [
        Index("parentStoryId")
    ],
    foreignKeys = [
        ForeignKey(entity = Story::class,
                   parentColumns = ["id"],
                   childColumns = ["parentStoryId"])
    ]
)
data class Comment(
    @PrimaryKey val id: Int,
    val parentStoryId: Int,
    val parentCommentId: Int?,
    val authorName: String,
    val date: Date,
    val text: String
) : Parcelable, Comparable<Comment> {

    @IgnoredOnParcel @Ignore var level: Int = 0

    override fun compareTo(other: Comment) = other.date.compareTo(date)
}