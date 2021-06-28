package com.thiyaga.ottsampleproject

import android.widget.Filter
import com.thiyaga.ottsampleproject.model.BaseModel
import com.thiyaga.ottsampleproject.model.MovieModel


class MovieFilter(
    private val movieList: List<BaseModel>,
    private val callback: ((List<BaseModel>) -> Unit)
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredLocalMovieList = filterList(movieList, constraint)
        return FilterResults().apply {
            values = filteredLocalMovieList
            count = filteredLocalMovieList.size
        }
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        val filterResults = results?.values as? List<BaseModel>
        callback.invoke(filterResults.orEmpty())
    }

    private fun filterList(list: List<BaseModel>, constraint: CharSequence?): List<BaseModel> {
        return if (constraint.isNullOrEmpty()) {
            list
        } else {
            list.filter { item ->
                when (item) {
                    is MovieModel -> item.name.contains(constraint, ignoreCase = true)
                    else -> true
                }
            }
        }
    }
}