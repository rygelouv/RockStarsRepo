package com.github.rygelouv.rockstarapp.data.cache

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


interface Cache<DATA: Any> {
    val size: Int

    operator fun set(key: String, value: DATA)

    operator fun get(key: String): DATA?

    fun remove(key: String): DATA?

    fun clear()
}