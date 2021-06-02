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

    fun getDetailApiCall(id: String?): MutableLiveData<MovieDetailModel> {

        val call = RetrofitClient.apiInterface.getDetail(id!!,"08e365d4ae0d7f8918ef7bdc5d9691b7"
            )

        call.enqueue(object: Callback<MovieDetailModel> {
            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
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