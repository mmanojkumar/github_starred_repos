package com.github.presentation.di.module

import android.app.Application
import android.content.Context
import com.github.data.executor.JobExecutor
import com.github.data.repository.GitHubDataRepository
import com.github.domain.executor.PostExecutionThread
import com.github.domain.executor.ThreadExecutor
import com.github.domain.repository.GitHubRepository
import com.github.presentation.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private var application: Application) {


    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @Singleton
    fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @Singleton
    fun provideGithubRepository(gitHubDataRepository: GitHubDataRepository): GitHubRepository {
        return gitHubDataRepository
    }

}