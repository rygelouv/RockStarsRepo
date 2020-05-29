package com.github.rygelouv.rockstarapp.data.cache

/**
 * Created by rygelouv on 5/29/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */


interface Cache<DATA: Any> {
    val size: Int

    operator fun set(key: String, value: DATA)

    operator fun get(key: String): DATA?

    fun remove(key: String): DATA?

    fun clear()
}