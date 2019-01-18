package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
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
    val authorName: String,
    val date: Date,
    val text: String
) : Parcelable