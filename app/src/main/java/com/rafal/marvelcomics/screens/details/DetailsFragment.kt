package com.rafal.marvelcomics.screens.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rafal.marvelcomics.R
import com.rafal.marvelcomics.databinding.FragmentDetailsBinding
import com.rafal.marvelcomics.databinding.FragmentSearchBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomSheetBehavior.from(binding.detailsBottomSheet).apply {
            peekHeight = 500
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        loadCover()

        binding.apply {
            detailsComicTitle.text = args.comic.title
            args.comic.creators.items.forEach { creator ->
                var comma = ", "
                if(creator == args.comic.creators.items.last()) {
                   comma = ""
                }
                detailsComicAuthor.text = "${detailsComicAuthor.text}${creator.name}$comma"
            }

            detailsComicDescription.text = args.comic.description

            detailsBtn.setOnClickListener {
                val url = args.comic.urls.firstOrNull {
                    it.type == "detail"
                }?.url
                url?.let {
                    openWebPage(it)
                }
            }
        }
    }

    private fun loadCover() {
        Glide.with(requireContext())
            .load("${args.comic.thumbnail.path}.${args.comic.thumbnail.extension}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.detailsIv)
    }

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

}