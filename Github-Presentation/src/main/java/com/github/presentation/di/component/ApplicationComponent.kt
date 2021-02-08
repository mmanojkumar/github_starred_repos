package com.github.presentation.di.component

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.github.domain.executor.PostExecutionThread
import com.github.domain.executor.ThreadExecutor
import com.github.domain.repository.GitHubRepository
import com.github.presentation.di.module.ApplicationModule
import com.github.presentation.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(baseActivity: AppCompatActivity)
    fun context(): Context
    fun threadExecutor(): ThreadExecutor
    fun postExecutionThread(): PostExecutionThread
    fun githubRepository(): GitHubRepository
}