package com.thiyaga.ottsampleproject.repositary

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thiyaga.ottsampleproject.networkmodel.ApiModelData
import com.thiyaga.ottsampleproject.utils.getJsonDataFromAsset
import kotlinx.coroutines.Dispatchers

class ApiRespositary( private val context: Context){

    suspend fun getMovieJson1(): ApiModelData{
        with(Dispatchers.IO) {
            val jsonFileString = context.getJsonDataFromAsset("CONTENTLISTINGPAGE-PAGE1.json")
            val gson = Gson()
            val listPersonType = object : TypeToken<ApiModelData>() {}.type
            val data: ApiModelData = gson.fromJson(jsonFileString, listPersonType)
            return data
        }
    }
    suspend fun getMovieJson2(): ApiModelData{
        with(Dispatchers.IO) {
            val jsonFileString = context.getJsonDataFromAsset("CONTENTLISTINGPAGE-PAGE2.json")
            val gson = Gson()
            val listPersonType = object : TypeToken<ApiModelData>() {}.type
            val data: ApiModelData = gson.fromJson(jsonFileString, listPersonType)
            return data
        }
    }
    suspend fun getMovieJson3(): ApiModelData{
        with(Dispatchers.IO) {
            val jsonFileString = context.getJsonDataFromAsset("CONTENTLISTINGPAGE-PAGE3.json")
            val gson = Gson()
            val listPersonType = object : TypeToken<ApiModelData>() {}.type
            val data: ApiModelData = gson.fromJson(jsonFileString, listPersonType)
            return data
        }

    }

}