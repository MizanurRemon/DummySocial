package com.example.dummysocial

import android.app.Application
import com.example.dummysocial.di.ApplicationComponent
import com.example.dummysocial.di.DaggerApplicationComponent

class DaggerApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}