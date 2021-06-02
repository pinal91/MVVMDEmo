package com.demo.pinal.retrofit

import com.demo.pinal.model.MovieDetailModel
import com.demo.pinal.model.MoviesListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("discover/movie")
    fun getServices(@Query("api_key") api_key:String,
    @Query("sort_by") sort_by:String) : Call<MoviesListModel>


    /*https://api.themoviedb.org/3/movie/337404?api_key=08e365d4ae0d7f8918ef7bdc5d9691b7&language=en-US
*/
    @GET("movie/{movie_id}")
    fun getDetail(@Path("movie_id") movie_id:String,
                    @Query("api_key") api_key:String) : Call<MovieDetailModel>

    /*@GET()
    fun getServices(@Url url: String) : Call<MovieDetailModel>*/
}