package com.danpinciotti.mobile.hackernews.service

import com.danpinciotti.mobile.hackernews.models.Comment
import io.reactivex.Single

interface CommentService {

    fun fetchComments(storyId: Int): Single<List<Comment>>
}