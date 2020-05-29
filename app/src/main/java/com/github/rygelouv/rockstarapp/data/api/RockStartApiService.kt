package com.github.rygelouv.rockstarapp.data.api

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
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
