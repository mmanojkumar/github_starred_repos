package com.github.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


import com.github.presentation.di.factory.ViewModelFactory
import com.github.presentation.di.factory.ViewModelKey
import com.github.presentation.fragment.RepositoryListViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class GithubViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryListViewModel::class)
    abstract fun bindUserViewModel(repositoryListViewModel: RepositoryListViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}