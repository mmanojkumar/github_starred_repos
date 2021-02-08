package com.github.presentation.di.component


import android.content.Context
import com.github.data.network.RestClient
import com.github.presentation.di.module.ApplicationModule
import com.github.presentation.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApplicationModule::class])
interface NetworkComponent {
    fun retrofit(): Retrofit
    fun restClient(): RestClient
    fun context(): Context
}