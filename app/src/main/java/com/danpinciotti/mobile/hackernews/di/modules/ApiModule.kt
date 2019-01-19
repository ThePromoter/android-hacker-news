package com.danpinciotti.mobile.hackernews.di.modules

import com.danpinciotti.mobile.hackernews.api.HackerNewsApi
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides @ApplicationScope
    fun providesApi(retrofit: Retrofit): HackerNewsApi =
        retrofit.create(HackerNewsApi::class.java)
}