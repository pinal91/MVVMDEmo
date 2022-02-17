package com.demo.pinal.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.pinal.model.MovieDetailModel
import com.demo.pinal.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DetailActivityRepository {

    val serviceSetterGetter = MutableLiveData<MovieDetailModel>()

}