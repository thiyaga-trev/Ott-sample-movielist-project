package com.thiyaga.ottsampleproject.model


interface BaseModel {
    fun areItemsTheSame(newItem: BaseModel): Boolean
    fun areContentsTheSame(newItem: BaseModel): Boolean
}

interface MovieList : BaseModel

