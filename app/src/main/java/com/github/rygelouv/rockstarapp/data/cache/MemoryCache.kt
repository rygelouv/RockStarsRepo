package com.github.rygelouv.rockstarapp.data.cache

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

object MemoryCache: Cache<RockStar> {

    private val cache = HashMap<String, RockStar>()
    override val size: Int
        get() = cache.size

    override fun set(key: String, value: RockStar) {
        cache[key] = value
    }

    override fun get(key: String): RockStar? = cache[key]

    override fun remove(key: String): RockStar? = cache.remove(key)

    override fun clear() = cache.clear()

    fun getAll(): List<RockStar> = cache.toList().map { it.second }

    fun putAll(list: List<RockStar>) {
        list.forEach { cache[it.buildHash()] = it }
    }
}