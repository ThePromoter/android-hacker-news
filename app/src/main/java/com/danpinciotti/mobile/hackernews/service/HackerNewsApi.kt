package com.danpinciotti.mobile.hackernews.service

import com.danpinciotti.mobile.hackernews.models.HackerNewsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface HackerNewsApi {

    @GET("topstories.json")
    fun getTopStoryIds(): Single<List<Int>>

    @GET("item/{itemId}.json")
    fun getItem(@Path("itemId") itemId: Int): Single<HackerNewsItem>
}