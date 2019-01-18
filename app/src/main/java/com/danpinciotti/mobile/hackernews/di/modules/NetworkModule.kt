package com.danpinciotti.mobile.hackernews.di.modules

import com.danpinciotti.mobile.hackernews.core.networking.LoggingInterceptor
import com.danpinciotti.mobile.hackernews.core.networking.moshi.HackerNewsItemTypeAdapter
import com.danpinciotti.mobile.hackernews.core.networking.moshi.UnixDateAdapter
import com.danpinciotti.mobile.hackernews.di.qualifiers.BaseUrl
import com.danpinciotti.mobile.hackernews.di.qualifiers.IoScheduler
import com.danpinciotti.mobile.hackernews.di.scopes.ApplicationScope
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides @ApplicationScope @BaseUrl
    fun provideBaseUrl() = "https://hacker-news.firebaseio.com/v0/"

    @Provides @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

    @Provides @ApplicationScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(UnixDateAdapter())
            .add(HackerNewsItemTypeAdapter())
            .build()
    }

    @Provides @ApplicationScope
    fun provideRetrofit(httpClient: OkHttpClient,
                        @BaseUrl baseUrl: String,
                        moshi: Moshi,
                        @IoScheduler scheduler: Scheduler): Retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler))
            .build()
}