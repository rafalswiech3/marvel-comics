package com.rafal.marvelcomics.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.rafal.marvelcomics.databinding.FragmentMainBinding
import com.rafal.marvelcomics.model.MarvelComic
import com.rafal.marvelcomics.screens.home.HomeFragmentDirections
import com.rafal.marvelcomics.screens.shared.IOnRecyclerViewItemClick
import com.rafal.marvelcomics.screens.shared.MainPagingAdapter
import com.rafal.marvelcomics.screens.shared.ResultsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), IOnRecyclerViewItemClick {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var pagingAdapter: MainPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        setRetryButtonClickListener()
        observeViewModelComicLiveData()
        loadComicsOnAppLaunch()
    }

    private fun prepareRecyclerView() {
        pagingAdapter = MainPagingAdapter(this)
        val recyclerView = binding.mainRv

        recyclerView.adapter = pagingAdapter.apply {
            withLoadStateHeaderAndFooter(
                header = ResultsLoadStateAdapter { pagingAdapter.retry() },
                footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
            )

            addLoadStateListener { loadState ->
                binding.apply {
                    mainPb.isVisible = loadState.source.refresh is LoadState.Loading
                    mainRetryBtn.isVisible = loadState.source.refresh is LoadState.Error
                }
            }
        }
    }

    private fun setRetryButtonClickListener() {
        binding.mainRetryBtn.setOnClickListener {
            pagingAdapter.retry()
        }
    }

    private fun observeViewModelComicLiveData() {
        viewModel.comicsLiveData.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun loadComicsOnAppLaunch() {
        viewModel.loadComicsOnAppLaunch()
    }

    override fun onComicItemClick(comic: MarvelComic) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(comic = comic)
        findNavController().navigate(action)

    }
}