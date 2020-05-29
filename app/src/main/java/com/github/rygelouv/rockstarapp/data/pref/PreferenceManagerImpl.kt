package com.github.rygelouv.rockstarapp.data.pref

import android.content.Context
import com.github.rygelouv.rockstarapp.presentation.RockStar
import com.github.rygelouv.rockstarapp.presentation.buildHash

/**
 * Created by rygelouv on 5/29/20.
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

interface PreferenceManager {
    fun saveRockStar(rockStar: RockStar)
    fun getFavoriteRockStars(): List<RockStar?>? // Ugly return type
    fun removeFavorite(rockStar: RockStar): List<RockStar?>?
    fun check(rockStar: RockStar): Boolean?
}


class PreferenceManagerImpl(
    context: Context,
    private val serializer: RockStarSerializer
) : PreferenceManager {
    private var mode = Context.MODE_PRIVATE
    private var prefFileName = PreferenceManagerImpl::class.java.simpleName
    private val preferences = context.getSharedPreferences(prefFileName, mode)

    override fun saveRockStar(rockStar: RockStar) {
        preferences?.edit()?.putString(rockStar.buildHash(), serializer.serialize(rockStar))?.apply()
    }


    override fun getFavoriteRockStars(): List<RockStar?>? {
        return preferences?.all?.map { serializer.deserialize(it.value as String) }
    }

    override fun removeFavorite(rockStar: RockStar): List<RockStar?>? {
        preferences?.edit()?.remove(rockStar.buildHash())?.apply()
        return getFavoriteRockStars()
    }

    override fun check(rockStar: RockStar) = preferences?.contains(rockStar.buildHash())

}