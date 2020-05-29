package com.github.rygelouv.rockstarapp.injection

import com.github.rygelouv.rockstarapp.presentation.RockStarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */

@FlowPreview
@ExperimentalCoroutinesApi
val presentationModel = module {
    viewModel {
        RockStarViewModel(
            get()
        )
    }
}