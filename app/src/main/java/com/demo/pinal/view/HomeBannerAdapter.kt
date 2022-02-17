package com.demo.pinal.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.demo.pinal.R
import com.demo.pinal.model.BannerItem
import kotlinx.android.synthetic.main.items_banner.view.*

/**
 * Created by pinal.
 */
  class HomeImageAdapter internal constructor(var mContext: Context,private val images: ArrayList<BannerItem>) : PagerAdapter() {
    private val inflater: LayoutInflater = (mContext as Activity).layoutInflater


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View?)
    }

    override fun finishUpdate(container: View) {}

    override fun getCount(): Int {
        return images.size
    }


    override fun instantiateItem(view: View, position: Int): Any {

        val imageLayout = inflater.inflate(
                R.layout.items_banner, null)
        Glide.with(mContext).load(images[position].imageUrl)
            .into(imageLayout.imgBrand)


        (view as ViewPager).addView(imageLayout)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

    override fun startUpdate(container: View) {}

}