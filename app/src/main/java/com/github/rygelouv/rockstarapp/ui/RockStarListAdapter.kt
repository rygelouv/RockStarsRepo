package com.github.rygelouv.rockstarapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.github.rygelouv.rockstarapp.R
import com.github.rygelouv.rockstarapp.presentation.RockStar
import com.github.rygelouv.rockstarapp.databinding.RockstarItemBinding
import kotlinx.coroutines.flow.Flow

/**
 * Created by rygelouv on 5/28/20.
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

typealias Click = (rockStar: RockStar) -> Unit

class RockStarListAdapter(private val click: Click) :
    ListAdapter<RockStar, RockStarListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<RockStar>() {
        override fun areItemsTheSame(oldItem: RockStar, newItem: RockStar) =
            oldItem.firstName == newItem.firstName

        override fun areContentsTheSame(oldItem: RockStar, newItem: RockStar) = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RockstarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), click)


    class ViewHolder(private val binding: RockstarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RockStar, click: Click) {
            with(binding) {
                name.text = item.firstName
                status.text = item.status
                loadPicture(item.pictureUrl)
                setUpFavoriteButton(item, click)
            }
        }


        private fun setUpFavoriteButton(rockStar: RockStar, action: Click) {
            with(binding.favoriteButton) {
                if (rockStar.isFavorite) {
                    setImageResource(android.R.drawable.btn_star_big_on)
                } else {
                    setImageResource(android.R.drawable.btn_star_big_off)
                }
                setOnClickListener {
                    action(rockStar)
                }
            }
        }


        private fun loadPicture(url: String) {
            binding.avatarImage.load(url) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
                transformations(CircleCropTransformation())
            }
        }
    }
}