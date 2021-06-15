package com.demo.pinal.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.pinal.model.MoviesListModel
import com.demo.pinal.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceSetterGetter = MutableLiveData<MoviesListModel>()

    fun getServicesApiCall(): MutableLiveData<MoviesListModel> {

        val call = RetrofitClient.apiInterface.getServices("08e365d4ae0d7f8918ef7bdc5d9691b7",
            "popularity.desc")

        call.enqueue(object: Callback<MoviesListModel> {
            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<MoviesListModel>,
                response: Response<MoviesListModel>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()
                serviceSetterGetter.postValue(data)

                /*val msg = data!!.message

                serviceSetterGetter.value = ServicesSetterGetter(msg)*/
            }
        })

        return serviceSetterGetter
    }
}