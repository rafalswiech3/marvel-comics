package com.rafal.marvelcomics.screens.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rafal.marvelcomics.screens.main.MainFragment
import com.rafal.marvelcomics.screens.search.SearchFragment

class HomeFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return SearchFragment()
        }
        return MainFragment()
    }
}