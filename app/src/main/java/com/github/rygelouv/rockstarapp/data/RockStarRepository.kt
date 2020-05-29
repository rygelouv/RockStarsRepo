package com.github.rygelouv.rockstarapp.data

import android.util.Log
import com.github.rygelouv.rockstarapp.data.api.RockStartApiService
import com.github.rygelouv.rockstarapp.data.cache.MemoryCache
import com.github.rygelouv.rockstarapp.data.pref.PreferenceManager
import com.github.rygelouv.rockstarapp.presentation.RockStar
import com.github.rygelouv.rockstarapp.presentation.buildHash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */


interface RockStarRepository {
    suspend fun getRockStars(): List<RockStar>
    suspend fun saveRockStar(rockStar: RockStar): List<RockStar>
    suspend fun getFavoriteRockStars(): List<RockStar?>? // List<RockStar?>? is quite ugly.
    suspend fun removeFavorite(rockStar: RockStar): List<RockStar?>?
    suspend fun search(name: String): List<RockStar>
}


class RockStarRepositoryImpl(
    private val service: RockStartApiService,
    private val mapper: RockStarMapper,
    private val preferenceManager: PreferenceManager,
    private val cache: MemoryCache
) : RockStarRepository {


    override suspend fun search(name: String): List<RockStar> {
        return cache.getAll().filter {
            it.firstName.toLowerCase(Locale.ROOT).startsWith(name.toLowerCase())
        }
    }


    override suspend fun getRockStars(): List<RockStar> {
        return withContext(Dispatchers.Default) {
            if (cache.size == 0) {
                val rockStarList = withContext(Dispatchers.IO) { service.getRockStars() }
                cache.putAll(rockStarList.team.map(mapper))
            }
            buildRockStarList()
        }
    }


    private fun buildRockStarList(): List<RockStar> {
        return cache.getAll().map {
            if (preferenceManager.check(it)!!) {
                return@map it.copy(isFavorite = true)
            }
            it
        }
    }


    @Throws(UnableToSaveRockStarException::class)
    override suspend fun saveRockStar(rockStar: RockStar): List<RockStar> {
        Log.e("REPO_TAG", "rockStar saving")
        try {
            val updatedRockStar = rockStar.copy(isFavorite = true) // Keeping things immutable
            cache[rockStar.buildHash()] = updatedRockStar
            withContext(Dispatchers.IO) { preferenceManager.saveRockStar(updatedRockStar) }
            return cache.getAll()
        } catch (e: Exception) {
            throw UnableToSaveRockStarException()
        }
    }


    override suspend fun getFavoriteRockStars(): List<RockStar?>? =
        try {
            withContext(Dispatchers.IO) {
                preferenceManager.getFavoriteRockStars() ?: throw UnableToGetFavoriteRockStarsException()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnableToGetFavoriteRockStarsException()
        }


    override suspend fun removeFavorite(rockStar: RockStar): List<RockStar?>? =
        preferenceManager.removeFavorite(rockStar)


    class UnableToSaveRockStarException : Exception("Unable to RockStar")
    class UnableToGetFavoriteRockStarsException : Exception("Unable to Get Favorite RockStars")
}