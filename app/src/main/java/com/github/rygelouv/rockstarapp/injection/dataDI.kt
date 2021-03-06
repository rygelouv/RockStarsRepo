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


val dataModule = module {

    factory { RockStarMapper() }

    single<PreferenceManager> { PreferenceManagerImpl(androidContext(), get()) }

    factory<RockStarRepository> { RockStarRepositoryImpl(get(), get(), get(), get()) }

    single { MemoryCache }

    single { RockStartApiService(get()) }

    single<RockStarSerializer> { RockStarSerializerImpl(get()) }
}


