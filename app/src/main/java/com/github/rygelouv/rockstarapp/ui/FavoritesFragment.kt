package com.github.rygelouv.rockstarapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rygelouv.rockstarapp.*
import com.github.rygelouv.rockstarapp.presentation.RockStar
import com.github.rygelouv.rockstarapp.presentation.RockStarIntent
import com.github.rygelouv.rockstarapp.presentation.RockStarViewModel
import com.github.rygelouv.rockstarapp.presentation.ViewState
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class FavoritesFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModel<RockStarViewModel>()
    private val rockStarListAdapter = RockStarListAdapter(::onRockStarFavoriteButtonClicked)
    private val removeRockStarChannel = Channel<RockStar>(Channel.UNLIMITED)


    private fun onRockStarFavoriteButtonClicked(rockStar: RockStar) {
        Log.e("HOME", "Clicked =====> ${rockStar.firstName}")
        removeRockStarChannel.offer(rockStar)
    }


    override fun onResume() {
        super.onResume()
        bindViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = rockStarListAdapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }


    private fun bindViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        intents().onEach(viewModel::processIntents).launchIn(lifecycleScope)
    }


    private fun intents(): Flow<RockStarIntent> {
        return merge(
            flowOf(RockStarIntent.LoadFavoritesIntent),
            removeRockStarChannel.consumeAsFlow().map { RockStarIntent.RemoveRockStarIntent(it) }
        )
    }


    private fun render(state: ViewState) {
        Log.d("ROCK_STAR_TAG", "State ===> $state")
        when(state) {
            is ViewState.LoadFavoriteRockStarsSuccess -> {
                progressBar.gone()
                recyclerView.visible()
                state.rockStars?.let { rockStarListAdapter.submitList(it) }
                    ?: Toast.makeText(context, "No favorites found", Toast.LENGTH_SHORT).show()
            }
            ViewState.LoadFavoriteRockStarsInProgress -> {
                recyclerView.gone()
                progressBar.visible()
            }
        }
    }

}