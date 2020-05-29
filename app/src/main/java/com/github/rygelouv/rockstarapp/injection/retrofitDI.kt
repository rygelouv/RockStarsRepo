package com.github.rygelouv.rockstarapp.injection

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by rygelouv on 5/29/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */

private const val BASE_URL = "BASE_URL"

val retrofitDIModule = module {
    single {
        provideRetrofit(get(named(BASE_URL)), get(), get())
    }

    single { provideMoshi() }

    single { provideOkHttpClient() }

    single(named(BASE_URL)) { "https://api.jsonbin.io/b/" }
}


private fun provideMoshi(): Moshi {
    return Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}


private fun provideRetrofit(baseUrl: String, moshi: Moshi, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()
}


private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        .build()
}