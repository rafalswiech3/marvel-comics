package com.rafal.marvelcomics.screens.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.rafal.marvelcomics.R
import com.rafal.marvelcomics.databinding.FragmentMainBinding
import com.rafal.marvelcomics.databinding.FragmentSearchBinding
import com.rafal.marvelcomics.screens.home.HomeFragmentDirections
import com.rafal.marvelcomics.screens.main.MainPagingAdapter
import com.rafal.marvelcomics.screens.shared.ResultsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = MainPagingAdapter()
        val recyclerView = binding.searchRv
        
        recyclerView.adapter = pagingAdapter.apply {
            withLoadStateHeaderAndFooter(
                header = ResultsLoadStateAdapter { pagingAdapter.retry() },
                footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
            )

            addLoadStateListener { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && pagingAdapter.itemCount == 0
                if (!searchQuery.isNullOrEmpty()) {
                    if (isListEmpty) {
                        setSearchInfoEmptyText()
                    }
                }
                binding.searchInfoGroup.isVisible = isListEmpty
                binding.searchPb.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }

        viewModel.comicsLiveData.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.action_search_search_fragment).actionView as SearchView
        searchView.queryHint = getString(R.string.search_query)
        setSearchViewOnQueryTextListener(searchView)
    }

    private fun setSearchViewOnQueryTextListener(view: SearchView) {
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchComic(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    private fun searchComic(title: String) {
        searchQuery = title
        binding.apply {
            searchInfoGroup.visibility = View.GONE
        }
        viewModel.searchComics(title)
    }

    private fun setSearchInfoEmptyText() {
        binding.searchInfoTv.text = "${getString(R.string.comic_search_no_comics_begin)} " +
                "\"$searchQuery\" ${getString(R.string.comic_search_no_comics_end)}"
    }

}