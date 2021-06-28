package com.thiyaga.ottsampleproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiyaga.ottsampleproject.model.BaseModel
import com.thiyaga.ottsampleproject.model.MovieModel
import com.thiyaga.ottsampleproject.networkmodel.ApiModelData
import com.thiyaga.ottsampleproject.networkmodel.Content
import com.thiyaga.ottsampleproject.repositary.ApiRespositary
import com.thiyaga.ottsampleproject.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val apiRespositary: ApiRespositary,
    private val context: Context
) : ViewModel() {

     var spaceDecorationV=MutableLiveData<VerticalSpacingItemDecoration>()
     var spaceDecorationH=MutableLiveData<HorizontalSpacingItemDecoration>()



    var minimumItemSize = MutableLiveData<Int>()

    fun setMinimumItemSize(size: Int?) {
        Log.e(TAG, "setMinimumItemSize: $size")
        this.minimumItemSize.value = size
    }
    var orientation=MutableLiveData<Int>()

    fun setOrientation(@Orientation orientation: Int) {
        Log.e(TAG, "setOrientation $orientation")
        if(orientation==Orientation.LANDSCAPE){
            setMinimumItemSize(7)
        }else{
            setMinimumItemSize(3)
        }
        val tempSpace= orientation.getSpaceDecoration()
        spaceDecorationH.value = tempSpace.first
        spaceDecorationV.value = tempSpace.second
        this.orientation.value = orientation
    }

    var movieJson1 = MutableLiveData<ApiModelData>()
    var movieJson2 = MutableLiveData<ApiModelData>()
    var movieJson3 = MutableLiveData<ApiModelData>()

    private  var allMovieList=MutableLiveData<List<Content>>()

    var mutableMovieList1 = MutableLiveData<List<BaseModel>>()

   fun init(){
        viewModelScope.launch{
            getJson()
        }
    }
  private suspend fun getJson() {
        val tempJson1=apiRespositary.getMovieJson1()
        movieJson1.postValue(tempJson1)
          with(Dispatchers.Main){
             setInitialData(tempJson1)
          }
      val tempJson2=apiRespositary.getMovieJson2()
      val tempJson3=apiRespositary.getMovieJson3()
        movieJson2.postValue(tempJson2)
        movieJson3.postValue(tempJson3)

        combineAllListJson(tempJson1,tempJson2,tempJson3)

  }

    private fun setInitialData(data: ApiModelData?) {

        data?.let {
            val allList=data.page.contentItems.contentList
            val mainList =mutableListOf<BaseModel>()

            if (mainList.size < allList.size) {
            val x: Int
            val y: Int
            if (allList.size - mainList.size >= 4*(minimumItemSize.value!!)) {
                x = mainList.size
                y = x + allList.size
            } else {
                x = mainList.size
                y = x + allList.size - mainList.size
            }
            for (i in x until y) {
                mainList.add(buildModelData(content=allList[i]))
            }
        }
            mutableMovieList1.postValue(mainList)

        }
    }

    private fun combineAllListJson(
        movieJson1: ApiModelData,
        movieJson2: ApiModelData,
        movieJson3: ApiModelData
    ) {
        val tempMovieList =mutableListOf<Content>()
        tempMovieList.addAll(movieJson1.page.contentItems.contentList)
        tempMovieList.addAll(movieJson2.page.contentItems.contentList)
        tempMovieList.addAll(movieJson3.page.contentItems.contentList)
        this.allMovieList.postValue(tempMovieList)

    }


    fun addNextMovieListItems() {
        Log.e(TAG, "addNextMovieListItems: "+allMovieList.value?.size +" "+mutableMovieList1.value?.size)
        allMovieList.let {
            val allList = it.value!!
            val mainList = mutableMovieList1.value!! as ArrayList<BaseModel>
            if (mainList.size < allList.size) {
                val x: Int
                val y: Int
                if (allList.size - mainList.size >= 4*(minimumItemSize.value!!)) {
                    Log.e(TAG, "addNextMovieListItems: if" )
                    x = mainList.size
                    y = x +  4*(minimumItemSize.value!!)
                } else {
                    Log.e(TAG, "addNextMovieListItems: else" )
                    x = mainList.size
                    y = x + allList.size - mainList.size
                }
                for (i in x until y) {
                    mainList.add(buildModelData(content=allList[i]))
                }
                Log.i("data", "> Item ${mainList.size}")
                mutableMovieList1.value = mainList

            }
        }
    }

    private fun buildModelData(content: Content): BaseModel {
        return  MovieModel(content.name,content.poster_image)
    }


}


