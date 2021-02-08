package com.github.data.executor

import androidx.annotation.NonNull
import com.github.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JobExecutor @Inject internal constructor() : ThreadExecutor {
    private val threadPoolExecutor: ThreadPoolExecutor
    override fun execute(runnable: Runnable?) {
        threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0
        override fun newThread(@NonNull runnable: Runnable): Thread {
            return Thread(runnable, "android_" + counter++)
        }
    }

    init {
        threadPoolExecutor = ThreadPoolExecutor(
            3, 5, 10, TimeUnit.SECONDS,
            LinkedBlockingQueue(), JobThreadFactory()
        )
    }
}