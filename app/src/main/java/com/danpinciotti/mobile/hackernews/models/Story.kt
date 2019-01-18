package com.danpinciotti.mobile.hackernews.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "stories")
data class Story(
    @PrimaryKey val id: Int,
    val authorName: String,
    val date: Date,
    val title: String,
    val url: String?,
    val score: Int
) : Parcelable, Comparable<Story> {

    override fun compareTo(other: Story) = other.date.compareTo(date)
}