package com.github

import com.github.data.network.NetworkModule
import com.github.presentation.AndroidApplication
import com.github.presentation.di.component.ApplicationComponent
import com.github.presentation.di.component.DaggerApplicationComponent
import com.github.presentation.di.module.ApplicationModule


class TargetApplication : AndroidApplication() {

    override lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule()).build()
    }

}