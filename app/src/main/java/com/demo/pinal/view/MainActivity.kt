package com.demo.pinal.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.pinal.R
import com.demo.pinal.model.ResultsItem
import com.demo.pinal.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var context: Context
    private var adapterMoviesList: MoviesListAdapter? = null

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)
        rcyMovieList.layoutManager = layoutManager

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        wp7progressBar.showProgressBar()

        mainActivityViewModel.getUser()!!.observe(this, Observer { serviceSetterGetter ->

            wp7progressBar.hideProgressBar()

            val data = serviceSetterGetter.results
            adapterMoviesList = MoviesListAdapter(data as ArrayList<ResultsItem>?, this)
            rcyMovieList.adapter = adapterMoviesList

        })

    }
}
