package com.demo.pinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.pinal.model.MovieDetailModel
import com.demo.pinal.repository.DetailActivityRepository

class DetailActivityViewModel : ViewModel() {

    var detailLiveData: MutableLiveData<MovieDetailModel>? = null

    fun getDetailData(id: String?): LiveData<MovieDetailModel>? {
        return detailLiveData
    }

}