package com.github.rygelouv.rockstarapp.injection

import com.github.rygelouv.rockstarapp.data.*
import com.github.rygelouv.rockstarapp.data.api.RockStartApiService
import com.github.rygelouv.rockstarapp.data.cache.MemoryCache
import com.github.rygelouv.rockstarapp.data.pref.PreferenceManager
import com.github.rygelouv.rockstarapp.data.pref.PreferenceManagerImpl
import com.github.rygelouv.rockstarapp.data.pref.RockStarSerializer
import com.github.rygelouv.rockstarapp.data.pref.RockStarSerializerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */


val dataModule = module {

    factory { RockStarMapper() }

    single<PreferenceManager> {
        PreferenceManagerImpl(
            androidContext(),
            get()
        )
    }

    factory<RockStarRepository> { RockStarRepositoryImpl(get(), get(), get(), get()) }

    single { MemoryCache }

    single { RockStartApiService(get()) }

    single<RockStarSerializer> {
        RockStarSerializerImpl(
            get()
        )
    }
}


