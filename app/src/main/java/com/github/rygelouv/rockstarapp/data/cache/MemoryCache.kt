package com.github.rygelouv.rockstarapp.data.cache

import com.github.rygelouv.rockstarapp.presentation.RockStar
import com.github.rygelouv.rockstarapp.presentation.buildHash

/**
 * Created by rygelouv on 5/29/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
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