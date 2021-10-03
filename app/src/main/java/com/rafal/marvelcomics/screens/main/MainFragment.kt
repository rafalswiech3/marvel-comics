package com.rafal.marvelcomics.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.rafal.marvelcomics.R
import com.rafal.marvelcomics.databinding.FragmentMainBinding
import com.rafal.marvelcomics.screens.shared.ResultsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = MainPagingAdapter()
        val recyclerView = binding.mainRv

        recyclerView.adapter = pagingAdapter.apply {
            withLoadStateHeaderAndFooter(
                header = ResultsLoadStateAdapter { pagingAdapter.retry() },
                footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
            )

            addLoadStateListener { loadState ->
                binding.mainPb.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }

        viewModel.comicsLiveData.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.getComics()
    }
}