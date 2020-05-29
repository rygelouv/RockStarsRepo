package com.github.rygelouv.rockstarapp.data

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


interface RockStarRepository {
    suspend fun getRockStars(): List<RockStar>
    suspend fun saveRockStar(rockStar: RockStar): List<RockStar>
    suspend fun getFavoriteRockStars(): List<RockStar?>? // List<RockStar?>? is quite ugly.
    suspend fun removeFavorite(rockStar: RockStar): List<RockStar?>?
    suspend fun search(name: String): List<RockStar>
}


class RockStarRepositoryImpl(
    private val apiService: RockStartApiService,
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
                val rockStarList = withContext(Dispatchers.IO) { apiService.getRockStars() }
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
        try {
            val updatedRockStar = rockStar.copy(isFavorite = true) // Keeping things immutable
            cache[rockStar.buildHash()] = updatedRockStar
            withContext(Dispatchers.IO) { preferenceManager.saveRockStar(updatedRockStar) }
            return buildRockStarList()
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