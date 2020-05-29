package com.github.rygelouv.rockstarapp

import android.app.Application
import com.github.rygelouv.rockstarapp.injection.dataModule
import com.github.rygelouv.rockstarapp.injection.presentationModel
import com.github.rygelouv.rockstarapp.injection.retrofitDIModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */


class RockStarApp: Application() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RockStarApp)
            modules(
                presentationModel + dataModule + retrofitDIModule
            )
        }
    }
}