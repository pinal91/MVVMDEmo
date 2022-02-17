package com.demo.pinal.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.pinal.model.BannerResponse
import com.demo.pinal.model.ProductResponse
import com.demo.pinal.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceProduct = MutableLiveData<ProductResponse>()
    val serviceSetterGetter = MutableLiveData<BannerResponse>()


    fun getServicesApiCall(): MutableLiveData<BannerResponse> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object: Callback<BannerResponse> {
            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<BannerResponse>,
                response: Response<BannerResponse>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.let {
                    serviceSetterGetter.postValue(it)
                }

                /*val msg = data!!.message

                serviceSetterGetter.value = ServicesSetterGetter(msg)*/
            }
        })

        return serviceSetterGetter
    }


    fun getProductApiCall(page:String): MutableLiveData<ProductResponse> {

        val call = RetrofitClient.apiInterface.getProduct(page)

        call.enqueue(object: Callback<ProductResponse> {
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.let {
                    serviceProduct.postValue(it)
                }

            }
        })

        return serviceProduct
    }
}