package com.github.rygelouv.rockstarapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@FlowPreview
@ExperimentalCoroutinesApi
class RockStarsFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by sharedViewModel<RockStarViewModel>()
    private val rockStarListAdapter = RockStarListAdapter(::onRockStarFavoriteButtonClicked)
    private val saveRockStarChannel = Channel<RockStar>(Channel.UNLIMITED)


    private fun onRockStarFavoriteButtonClicked(rockStar: RockStar) {
        saveRockStarChannel.offer(rockStar)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        bindViewModel()
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
            flowOf(RockStarIntent.InitialIntent),
            saveRockStarChannel.consumeAsFlow().map { RockStarIntent.SaveRockStarIntent(it) },
            search.textChanges().map {
                RockStarIntent.SearchRockStarIntent(it.toString())
            }
        )
    }


    private fun render(state: ViewState) {
        Log.d("ROCK_STAR_TAG", "State ===> $state")
        when(state) {
            is ViewState.LoadRockStarSuccess -> {
                progressBar.gone()
                recyclerView.visible()
                rockStarListAdapter.submitList(state.rockStars)
            }
            ViewState.LoadRockStarInProgress -> {
                recyclerView.gone()
                progressBar.visible()
            }
        }
    }
}