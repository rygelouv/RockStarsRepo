package com.github.rygelouv.rockstarapp.presentation

import android.util.Log
import androidx.lifecycle.*
import com.github.rygelouv.rockstarapp.data.RockStarRepository
import com.github.rygelouv.rockstarapp.data.RockStarRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception

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


@ExperimentalCoroutinesApi
@FlowPreview
class RockStarViewModel(private val repo: RockStarRepository): ViewModel() {

    private val _intentChannel = BroadcastChannel<RockStarIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntents(intent: RockStarIntent) = _intentChannel.send(intent)

    private val _viewStateD = MutableLiveData<ViewState>().apply { value = ViewState.InitialState }
    var viewState: LiveData<ViewState> = _viewStateD.distinctUntilChanged()

    init {
        _intentChannel
            .asFlow()
            .getResult()
            .launchIn(viewModelScope)
    }


    private fun Flow<RockStarIntent>.getResult() = onEach {
        when(it) {
            RockStarIntent.InitialIntent -> loadRockStars()
            is RockStarIntent.SaveRockStarIntent -> saveRockStar(it.rockStar)
            is RockStarIntent.LoadFavoritesIntent -> loadFavoriteRockStars()
            is RockStarIntent.RemoveRockStarIntent -> removeFavoriteRockStar(it.rockStar)
            is RockStarIntent.SearchRockStarIntent -> searchRockStar(it.name)
        }
    }

    private fun searchRockStar(name: String) {
        viewModelScope.launch {
            Log.e("ROCK", "LAUNCH")
            _viewStateD.postValue(ViewState.LoadRockStarInProgress)
            _viewStateD.postValue(ViewState.LoadRockStarSuccess(repo.search(name)))
        }
    }

    private fun removeFavoriteRockStar(rockStar: RockStar) {
        viewModelScope.launch {
            Log.e("ROCK", "LAUNCH")
            _viewStateD.postValue(ViewState.LoadRockStarInProgress)
            _viewStateD.postValue(ViewState.LoadFavoriteRockStarsSuccess(repo.removeFavorite(rockStar)))
            //loadRockStars()
            _intentChannel.offer(RockStarIntent.LoadFavoritesIntent)
        }
    }


    private fun saveRockStar(rockStar: RockStar) {
        viewModelScope.launch {
            Log.e("ROCK", "SAVING ---")
            val updateList = repo.saveRockStar(rockStar)
            _viewStateD.postValue(ViewState.LoadRockStarSuccess(updateList))
        }
    }


    private fun loadRockStars() {
        viewModelScope.launch {
            Log.e("ROCK", "LAUNCH")
            _viewStateD.postValue(ViewState.LoadRockStarInProgress)
            _viewStateD.postValue(ViewState.LoadRockStarSuccess(repo.getRockStars()))
        }
    }


    private fun loadFavoriteRockStars() {
        viewModelScope.launch {
            Log.e("ROCK", "GET FAVORITES")
            _viewStateD.postValue(ViewState.LoadFavoriteRockStarsInProgress)
            _viewStateD.postValue(try {
                val favorite = repo.getFavoriteRockStars()
                ViewState.LoadFavoriteRockStarsSuccess(favorite)
            } catch (e: RockStarRepositoryImpl.UnableToGetFavoriteRockStarsException) {
                e.printStackTrace()
                ViewState.LoadFavoriteRockStarsSuccess(null)
            })
        }
    }
}
