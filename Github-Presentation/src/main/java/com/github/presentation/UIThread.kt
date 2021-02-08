package com.github.presentation

import com.github.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject internal constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}