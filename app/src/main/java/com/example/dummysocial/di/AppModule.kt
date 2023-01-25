package com.example.dummysocial.di

import com.example.dummysocial.API.APIService
import com.example.dummysocial.Utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun retrofitBuilder(): Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()


    @Provides
    fun providesApiServices(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)
}