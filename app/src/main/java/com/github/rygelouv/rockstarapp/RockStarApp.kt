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
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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