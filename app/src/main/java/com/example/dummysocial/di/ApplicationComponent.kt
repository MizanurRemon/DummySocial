package com.example.dummysocial.di

import com.example.dummysocial.MainActivity
import dagger.Component
import dagger.Module
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
}