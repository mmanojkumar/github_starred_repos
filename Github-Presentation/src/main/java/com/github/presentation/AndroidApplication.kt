package com.github.presentation

import android.app.Application
import com.github.presentation.di.component.ApplicationComponent



open class AndroidApplication : Application() {

    open lateinit var applicationComponent: ApplicationComponent

}