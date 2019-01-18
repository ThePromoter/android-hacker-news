package com.danpinciotti.mobile.hackernews.core.networking.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UnixDateAdapter {

    @ToJson fun toJson(date: Date): Long {
        return date.time / 1000L
    }

    @FromJson fun fromJson(unixDate: Long): Date {
        return Date(unixDate * 1000L)
    }
}