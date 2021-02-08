package com.github.presentation.di.module

import androidx.appcompat.app.AppCompatActivity
import com.github.presentation.di.PerActivity
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    fun activity(): AppCompatActivity {
        return activity
    }

}