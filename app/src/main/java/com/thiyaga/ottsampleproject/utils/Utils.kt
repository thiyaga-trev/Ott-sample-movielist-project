package com.thiyaga.ottsampleproject.utils

import android.content.Context
import android.util.Log
import com.thiyaga.ottsampleproject.Application
import com.thiyaga.ottsampleproject.R
import java.io.IOException


val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            if (name.length <= 23) name else name.substring(0, 23)// first 23 chars
        } else {
            val name = javaClass.name
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)// last 23 chars
        }
    }
fun Context.getJsonDataFromAsset(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        Log.e(TAG, "getJsonDataFromAsset: "+ioException.localizedMessage )
        return null
    }
    return jsonString
}

fun Int.getSpaceDecoration(): Pair<HorizontalSpacingItemDecoration, VerticalSpacingItemDecoration> {
    return if (this == Orientation.LANDSCAPE){
        Pair<HorizontalSpacingItemDecoration,VerticalSpacingItemDecoration>(
            HorizontalSpacingItemDecoration(Application.appContext.resources.getDimension(R.dimen.land_horizontal_spacing).toInt(), false),
            VerticalSpacingItemDecoration(Application.appContext.resources.getDimension(R.dimen.vertical_spacing).toInt(), true))

    }else{
        Pair<HorizontalSpacingItemDecoration,VerticalSpacingItemDecoration>(
            HorizontalSpacingItemDecoration(Application.appContext.resources.getDimension(R.dimen.horizontal_spacing).toInt(), false) ,
            VerticalSpacingItemDecoration(Application.appContext.resources.getDimension(R.dimen.vertical_spacing).toInt(), true))
    }
}