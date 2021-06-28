package com.thiyaga.ottsampleproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.thiyaga.ottsampleproject.model.BaseModel


object BaseDiffCallBack : DiffUtil.ItemCallback<BaseModel>() {
    override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}