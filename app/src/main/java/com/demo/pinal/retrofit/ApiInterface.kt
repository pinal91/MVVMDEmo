package com.demo.pinal.retrofit

import com.demo.pinal.model.BannerResponse
import com.demo.pinal.model.MovieDetailModel
import com.demo.pinal.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("home?marketCode=UZ")
    fun getServices() : Call<BannerResponse>

    @GET("productlist")
    fun getProduct(@Query("page") page:String,
    @Query("productTagId")productTagId:String="13", @Query("marketCode")marketCode:String="UZ"
    ) : Call<ProductResponse>

}