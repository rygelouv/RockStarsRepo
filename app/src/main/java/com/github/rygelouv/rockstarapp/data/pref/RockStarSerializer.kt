package com.github.rygelouv.rockstarapp.data.pref

import android.util.Log
import com.github.rygelouv.rockstarapp.presentation.RockStar

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


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


interface RockStarSerializer {
    fun serialize(rockStar: RockStar): String
    fun deserialize(json: String): RockStar?
}


class RockStarSerializerImpl(private val moshi: Moshi):
    RockStarSerializer {

    private val type = Types.newParameterizedType(List::class.java, RockStar::class.java)

    override fun serialize(rockStar: RockStar): String {
        val adapter = moshi.adapter(RockStar::class.java)
        return adapter.toJson(rockStar).also {
            Log.e("SERIALIZER_TAG", it)
        }
    }

    override fun deserialize(json: String): RockStar? {
        val adapter = moshi.adapter(RockStar::class.java)
        return adapter.fromJson(json)
    }

}