package com.github.rygelouv.rockstarapp.data

import com.github.rygelouv.rockstarapp.data.api.RockStarRemoteModel
import com.github.rygelouv.rockstarapp.presentation.RockStar

/**
 * Created by rygelouv on 5/29/20.
 * <p>
 * RockStarApp
 * Copyright (c) 2020 Makeba Inc All rights reserved.
 */

typealias Mapper<T, R> = (T) -> R


class RockStarMapper: Mapper<RockStarRemoteModel, RockStar> {
    override fun invoke(model: RockStarRemoteModel) =
        RockStar(
            firstName = model.first_name,
            lastName = model.last_name,
            pictureUrl = model.picture_url,
            status = model.status
        )
}