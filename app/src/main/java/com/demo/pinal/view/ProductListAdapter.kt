package com.demo.pinal.view

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.pinal.R
import com.demo.pinal.model.ProductsItemModel
import kotlinx.android.synthetic.main.list_loading_view.view.*
import kotlinx.android.synthetic.main.movies_list.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*
import java.util.*


class ProductListAdapter(private val dataList: MutableList<ProductsItemModel>, private val context: Activity?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private var loadingWidth: Int = 0

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var isLoadingAdded = false

    override fun getItemCount(): Int = dataList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

         var bitmap: Bitmap? = null
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val metrics = context!!.getResources().displayMetrics


                loadingWidth = metrics.widthPixels


                if(dataList.get(position).brand!=""){
                    holder.itemView.txtProductBrand.visibility=View.VISIBLE
                    holder.itemView.txtProductBrand.text=dataList.get(position).brand

                }else{
                    holder.itemView.txtProductBrand.visibility=View.GONE
                }



                if(dataList.get(position).name!=""){
                    holder.itemView.txtProductname.visibility=View.VISIBLE
                    holder.itemView.txtProductname.text=dataList.get(position).name

                }else{
                    holder.itemView.txtProductname.visibility=View.GONE
                }


                holder.itemView.txtProductBrand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0F);
                holder.itemView.txtProductname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0F);
                holder.itemView.txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0F);


                holder.itemView.txtPrice.text= "UZS "+dataList[position].localPrice.toString()



                Glide.with(context).load(dataList[position].imgUrl)
                    .into(holder.itemView.imgProduct)

                holder.itemView.progreesBar.visibility=View.GONE






            }
            VIEW_TYPE_LOADING -> {

            }
        }
    }

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        var v1: View? = null


        v1 = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)




        return ViewHolder(v1!!, parent.context as Activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        /*      val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)

              return ViewHolder(view, parent.context as Activity)*/

        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW_TYPE_ITEM -> viewHolder = getViewHolder(parent, inflater)
            VIEW_TYPE_LOADING -> {
                val v2 = inflater.inflate(R.layout.list_loading_view, parent, false)
                viewHolder = LoadingViewHolder(v2)
            }
        }
        return viewHolder!!
    }

    private inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.loading

        init {

            val paramView = view.layoutParams as RecyclerView.LayoutParams
            paramView.width = Math.round(loadingWidth.toFloat())
        }
    }

    class ViewHolder(view: View, val activity: Activity) : RecyclerView.ViewHolder(view) {

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun bind(dataList: ProductsItemModel, context: Activity?, screenHeight: Int,login: Boolean, strUserID: String) {


        }
    }

    private fun add(mc: ProductsItemModel) {
        dataList.add(mc)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAll(mcList: MutableList<ProductsItemModel>) {
        dataList.addAll(mcList)
        notifyDataSetChanged()
    }

    fun remove(city: ProductsItemModel) {
        val position = dataList.indexOf(city)
        if (position > -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun getItem(position: Int): ProductsItemModel {
        return dataList.get(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 && isLoadingAdded) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ProductsItemModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = dataList.size - 1
        val item = getItem(position)

        if (item != null) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}