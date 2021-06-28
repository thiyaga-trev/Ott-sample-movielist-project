package com.thiyaga.ottsampleproject

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Space
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thiyaga.ottsampleproject.model.BaseModel
import com.thiyaga.ottsampleproject.model.MovieModel
import com.thiyaga.ottsampleproject.utils.BaseDiffCallBack
import com.thiyaga.ottsampleproject.utils.BaseViewHolder

class MovieListAdapter : ListAdapter<BaseModel, BaseViewHolder<BaseModel>>(BaseDiffCallBack),
    Filterable {

    private var mergedMovieList = emptyList<BaseModel>()
    private val movieFilter by lazy {
        MovieFilter(mergedMovieList) { result ->
            movieListFiltered = result
            submitList(movieListFiltered)
        }
    }

    protected var movieListFiltered: List<BaseModel> = emptyList()

    override fun getFilter(): Filter {
        return movieFilter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel> {
        return (when (viewType) {

            R.layout.item_movie_list -> {
                MovieViewHolder(parent = parent)
            }
            else -> {
                BaseViewHolder<Any>(Space(parent.context))
            }
        }) as BaseViewHolder<BaseModel>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseModel>, position: Int) {

            when (val item = getItem(position)) {
                is MovieModel -> holder.bindValue(item)
            }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount) {
            when (getItem(position)) {
                is MovieModel -> R.layout.item_movie_list
                else -> super.getItemViewType(position)
            }
        } else {
            super.getItemViewType(position)
        }
    }

    fun registerObserverToScrollView(layoutManager: RecyclerView.LayoutManager?) {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) layoutManager?.scrollToPosition(0)
            }
        })
    }

    override fun submitList(list: List<BaseModel>?) {
        mergedMovieList = list.orEmpty()
        super.submitList(list)
    }



}