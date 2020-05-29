package com.github.rygelouv.rockstarapp

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.gone() { this.visibility = View.GONE }


@ExperimentalCoroutinesApi
@CheckResult
fun View.clicks(): Flow<View> {
    return callbackFlow {
        setOnClickListener { offer(it) }
        awaitClose { setOnClickListener(null) }
    }
}


@ExperimentalCoroutinesApi
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                offer(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}