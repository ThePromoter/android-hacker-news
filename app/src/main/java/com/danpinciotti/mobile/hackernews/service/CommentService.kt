package com.danpinciotti.mobile.hackernews.service

import com.danpinciotti.mobile.hackernews.models.Comment
import io.reactivex.Flowable

interface CommentService {

    fun fetchComments(storyId: Int): Flowable<List<Comment>>
}