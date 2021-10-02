package com.rafal.marvelcomics.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rafal.marvelcomics.R
import com.rafal.marvelcomics.databinding.FragmentMainBinding
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

        recyclerView.adapter = pagingAdapter

        viewModel.comicsLiveData.observe(viewLifecycleOwner) {
            //binding.photosEmptyIv.visibility = View.GONE
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.getComics()
    }
}