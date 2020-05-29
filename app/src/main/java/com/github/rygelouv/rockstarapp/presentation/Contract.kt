package com.github.rygelouv.rockstarapp.presentation

/**
 * Created by rygelouv on 5/28/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */

sealed class RockStarIntent {
    object InitialIntent: RockStarIntent()
    object LoadFavoritesIntent: RockStarIntent()
    data class SaveRockStarIntent(val rockStar: RockStar): RockStarIntent()
    data class RemoveRockStarIntent(val rockStar: RockStar): RockStarIntent()
    data class SearchRockStarIntent(val name: String): RockStarIntent()
}


sealed class ViewState {
    object InitialState: ViewState()
    object LoadRockStarInProgress: ViewState()
    data class LoadRockStarSuccess(val rockStars: List<RockStar>): ViewState()

    object LoadFavoriteRockStarsInProgress: ViewState()
    data class LoadFavoriteRockStarsSuccess(val rockStars: List<RockStar?>?): ViewState()
}