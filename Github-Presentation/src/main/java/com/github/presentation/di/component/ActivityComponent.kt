package com.github.presentation.di.component

import androidx.appcompat.app.AppCompatActivity
import com.github.presentation.di.PerActivity
import com.github.presentation.di.module.ActivityModule

import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun activity(): AppCompatActivity?
}