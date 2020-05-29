package com.github.rygelouv.rockstarapp.data.pref

import android.util.Log
import com.github.rygelouv.rockstarapp.presentation.RockStar

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


/**
 * Created by rygelouv on 5/29/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
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