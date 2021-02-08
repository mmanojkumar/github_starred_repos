package com.github.presentation.di.component

import com.github.presentation.di.PerActivity
import com.github.presentation.di.module.ActivityModule
import com.github.presentation.di.module.GithubModule
import com.github.presentation.di.module.GithubViewModelModule
import com.github.presentation.fragment.RepositoryListFragment

import dagger.Component

@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class,
        GithubModule::class,
        GithubViewModelModule::class]
)
interface GithubComponent : ActivityComponent {
    fun inject(repositoryListFragment: RepositoryListFragment?)
}