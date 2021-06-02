package com.demo.pinal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.demo.pinal.R
import com.demo.pinal.model.MovieDetailModel
import com.demo.pinal.viewmodel.DetailActivityViewModel
import kotlinx.android.synthetic.main.activity_movies_detail.*
import kotlinx.android.synthetic.main.movies_list.view.*

class MoviesDetailActivity : AppCompatActivity() {
    lateinit var mainActivityViewModel: DetailActivityViewModel
    private lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)
        id=intent.getStringExtra("id")

        mainActivityViewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)


        mainActivityViewModel.getDetailData(id)!!.observe(this, Observer { serviceSetterGetter ->


            val data = serviceSetterGetter.original_title

            setData(serviceSetterGetter)

        })


    }
    private fun setData(serviceSetterGetter: MovieDetailModel?) {
        textName.text=serviceSetterGetter!!.original_title
        txtRating.text=serviceSetterGetter!!.vote_average.toString()
        textDetail.text=serviceSetterGetter!!.overview
        Glide.with(this@MoviesDetailActivity).
        load("http://image.tmdb.org/t/p/original/" + serviceSetterGetter.poster_path).crossFade()
            .into(imagePoster)
    }
}