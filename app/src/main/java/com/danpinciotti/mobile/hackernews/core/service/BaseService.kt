package com.danpinciotti.mobile.hackernews.core.service

import android.os.Parcelable
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import java.util.*




abstract class BaseService {

    protected fun <T : List<Parcelable>> allListFlowables(vararg sources: Flowable<T>): Flowable<T> {
        // Build a list of observables to call asynchronously
        val modifiedSources = LinkedList<Flowable<T>>()
        for (i in sources.indices) {
            val source = sources[i]
            // If the observable is the last one, allow it to emit an error
            if (i == sources.size - 1) {
                modifiedSources.add(source)
                continue
            }
            // Otherwise, ignore the error (allows a fresher source to still get called)
            modifiedSources.add(source.materialize()
                                    .doOnNext { it -> if (it.isOnError) Timber.e(it.error) }
                                    .filter { it -> !it.isOnError }
                                    .dematerialize<T>())
        }

        return Flowable.mergeDelayError(modifiedSources)
    }

    /**
     * To use when A is a Parcelable
     *
     * @param sources
     * @param <A>
     * @return
    </A> */
    @SafeVarargs
    protected fun <T : Parcelable> firstSingle(vararg sources: Single<T>): Single<T> {
        return Single.concatArray(*sources).firstOrError()
    }
}