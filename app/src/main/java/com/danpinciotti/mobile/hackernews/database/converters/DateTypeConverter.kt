package com.danpinciotti.mobile.hackernews.database.converters

import androidx.room.TypeConverter
import java.util.*


class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @TypeConverter
    fun toLong(value: Date?) = value?.time ?: 0
}