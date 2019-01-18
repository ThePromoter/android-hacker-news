package com.danpinciotti.mobile.hackernews.service

import com.danpinciotti.mobile.hackernews.models.Story
import io.reactivex.Flowable

interface StoryService {

    fun fetchTopStories(): Flowable<List<Story>>
}