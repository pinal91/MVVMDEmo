package com.demo.pinal.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.demo.pinal.R
import com.demo.pinal.model.ResultsItem
import kotlinx.android.synthetic.main.movies_list.view.*


class MoviesListAdapter(private val arrListProductsData: ArrayList<ResultsItem>?,
                        private val context: Activity) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context.resources.displayMetrics

        val p = arrListProductsData!![position]


        holder.itemView.textMoviesName.text = p.original_title
        holder.itemView.textRating.text=p.vote_average.toString()

        Glide.with(context).load("http://image.tmdb.org/t/p/original/" + p.poster_path).crossFade()
            .into(holder.itemView.imageView)

        holder.itemView.setOnClickListener {
            val i = Intent(context, MoviesDetailActivity::class.java)
            i.putExtra("id",p.id)
            context.startActivity(i)
        }

    }



    override fun getItemCount(): Int = arrListProductsData!!.count()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_list, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}