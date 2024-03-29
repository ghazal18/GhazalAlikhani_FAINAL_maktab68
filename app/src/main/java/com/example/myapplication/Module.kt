package com.example.myapplication

import com.example.myapplication.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Singleton
    @Provides
    fun getMoshi(): Moshi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return moshi
    }

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .baseUrl("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .build()
        return retrofit
    }

    @Singleton
    @Provides
    fun getApiService(): ApiService {
        val movieApiService = getRetrofit().create(ApiService::class.java)
        return movieApiService
    }
}