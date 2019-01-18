package com.danpinciotti.mobile.hackernews.di.modules

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.danpinciotti.mobile.hackernews.di.scopes.ActivityScope
import dagger.Binds
import dagger.Module


@Module
abstract class BaseActivityModule {

    @Binds @ActivityScope
    abstract fun activity(activity: AppCompatActivity): Activity

    @Binds @ActivityScope
    abstract fun activityContext(activity: Activity): Context
}