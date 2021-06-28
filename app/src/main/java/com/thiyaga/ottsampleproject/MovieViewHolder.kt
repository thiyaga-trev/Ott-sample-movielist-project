package com.thiyaga.ottsampleproject

import android.util.Log
import android.view.ViewGroup
import com.google.android.material.imageview.ShapeableImageView
import com.thiyaga.ottsampleproject.databinding.ItemMovieListBinding
import com.thiyaga.ottsampleproject.model.MovieModel
import com.thiyaga.ottsampleproject.utils.BaseViewHolder
import com.thiyaga.ottsampleproject.utils.TAG


class MovieViewHolder(parent: ViewGroup) : BaseViewHolder<MovieModel>(parent, R.layout.item_movie_list) {

    private val binding: ItemMovieListBinding by lazy {
        ItemMovieListBinding.bind(itemView)
    }

    override fun bindValue(item: MovieModel) {
        with(itemView) {
            setAvatarImage(binding.mimgMovie, item.image.substringBeforeLast("."))
            binding.mtxtMovieName.text = item.name
        }
    }

    private fun setAvatarImage(view: ShapeableImageView, avatar: String?) {
        Log.e(TAG, "setAvatarImage: $avatar")
        val res: Int = view.context.resources.getIdentifier(avatar, "drawable", view.context.packageName)
        view.setImageResource(res)
    }
}