package com.danpinciotti.mobile.hackernews.core.networking.moshi

import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson

class HackerNewsItemTypeAdapter {

    @ToJson fun toJson(type: HackerNewsItem.Type): String {
        return type.toString().toLowerCase()
    }

    @FromJson fun fromJson(typeString: String): HackerNewsItem.Type {
        for (type in HackerNewsItem.Type.values()) {
            if (type.toString().toLowerCase() == typeString) return type
        }
        throw JsonDataException("unknown item type: $typeString")
    }
}