package com.danpinciotti.mobile.hackernews.service

import com.danpinciotti.mobile.hackernews.models.Story
import io.reactivex.Flowable
import io.reactivex.Single

interface StoryService {

    fun fetchTopStories(): Flowable<List<Story>>
    fun fetchStory(storyId: Int): Single<Story>
}