package com.danpinciotti.mobile.hackernews.di.modules

import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import com.danpinciotti.mobile.hackernews.service.CommentService
import com.danpinciotti.mobile.hackernews.service.StoryService
import com.danpinciotti.mobile.hackernews.service.caching.CommentCachingService
import com.danpinciotti.mobile.hackernews.service.caching.StoryCachingService
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {

    @Binds @ApplicationScope
    abstract fun bindStoryService(service: StoryCachingService): StoryService

    @Binds @ApplicationScope
    abstract fun bindCommentService(service: CommentCachingService): CommentService
}