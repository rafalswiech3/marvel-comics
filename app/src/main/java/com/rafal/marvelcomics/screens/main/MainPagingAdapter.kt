package com.rafal.marvelcomics.screens.main

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rafal.marvelcomics.R
import com.rafal.marvelcomics.databinding.ComicsItemBinding
import com.rafal.marvelcomics.model.MarvelComic

class MainPagingAdapter(

) : PagingDataAdapter<MarvelComic, MainPagingAdapter.ComicViewHolder>(COMIC_COMPARATOR) {
    class ComicViewHolder(private val binding: ComicsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: MarvelComic) {
            binding.apply {
                comicPb.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                    .apply(
                        RequestOptions()
                            .transform(
                                RoundedCorners(30)
                            )
                    )
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            comicPb.visibility = View.GONE
                            comicImageError.visibility = View.VISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            comicPb.visibility = View.GONE
                            comicImageError.visibility = View.GONE
                            return false
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(comicCover)

                if(comic.creators.items.isNotEmpty()) {
                    comicAuthor.text = "${comicAuthor.context.getString(R.string.written_by)} ${comic.creators.items[0].name}"
                }

                comicTitle.text = comic.title
                comicDescription.text = comic.description
            }
        }
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = ComicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }

    companion object {
        private val COMIC_COMPARATOR = object : DiffUtil.ItemCallback<MarvelComic>() {
            override fun areItemsTheSame(
                oldItem: MarvelComic,
                newItem: MarvelComic
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MarvelComic,
                newItem: MarvelComic
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}