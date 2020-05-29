package com.github.rygelouv.rockstarapp.data

import com.github.rygelouv.rockstarapp.data.api.RockStarRemoteModel
import com.github.rygelouv.rockstarapp.presentation.RockStar

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