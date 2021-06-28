package com.thiyaga.ottsampleproject.model
import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("name") val name : String,
    @SerializedName("poster-image") val image : String
): MovieList {
    override fun areItemsTheSame(newItem: BaseModel): Boolean {
        newItem as MovieModel
       return name==newItem.name
                && image==newItem.image
    }

    override fun areContentsTheSame(newItem: BaseModel): Boolean {
        newItem as MovieModel
        return name==newItem.name && image==newItem.image
    }
}
