package com.github.rygelouv.rockstarapp.data.api

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

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


interface RockStartApiService {
    @GET("5ecbd116bbaf1f258946ce22")
    suspend fun getRockStars(): RockStarModelResponse


    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<RockStartApiService>()
    }
}


data class RockStarModelResponse(
    val team: List<RockStarRemoteModel>
)
