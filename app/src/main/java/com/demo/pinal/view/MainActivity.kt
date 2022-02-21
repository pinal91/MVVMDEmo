package com.demo.pinal.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.pinal.R
import com.demo.pinal.databinding.ActivityMainBinding
import com.demo.pinal.model.BannerItem
import com.demo.pinal.model.ProductsItemModel
import com.demo.pinal.util.DataBindingActivity
import com.demo.pinal.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DataBindingActivity() {

    lateinit var context: Context
    private var adapterList: ProductListAdapter? = null
    val WRITE_PERMISSON_REQUEST_CODE = 1
    private var isGenerating = false
    lateinit var mainActivityViewModel: MainActivityViewModel
    var data: ArrayList<BannerItem>? = null
    private var products:MutableList<ProductsItemModel>? = arrayListOf()
    private var currentPage:Int = 1
    private var totalPage:Int = 0
    private var totalCount:Int=0
    private val mBinding: ActivityMainBinding by binding(R.layout.activity_main)
    private var isLastPage:Boolean=false
    private var isLoading:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
        }

        context = this@MainActivity
        var layoutManager: GridLayoutManager? = null
        rcyProductList.setHasFixedSize(true)
        rcyProductList.itemAnimator
        layoutManager = GridLayoutManager(this, 2)
        rcyProductList.layoutManager = layoutManager as RecyclerView.LayoutManager?


        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        wp7progressBar.showProgressBar()

        mainActivityViewModel.servicesLiveData?.observe(this) { serviceSetterGetter ->

            wp7progressBar.hideProgressBar()
            /*adapterMoviesList = MoviesListAdapter(data, this)
                     rcyMovieList.adapter = adapterMoviesList*/
            data = serviceSetterGetter.Data?.mainBanner as ArrayList<BannerItem>?
            if (data != null && data!!.isNotEmpty()) {
                relBanner.visibility = View.VISIBLE
                pager!!.adapter = data?.let { HomeImageAdapter(this, it) }
                pager!!.startAutoScroll()
                viewPagerIndicator.setViewPager(pager)
                viewPagerIndicator.radius = resources.getDimension(R.dimen._3sdp)
            } else {
                relBanner.visibility = View.GONE
            }

            if (serviceSetterGetter.Data?.brandZoneBanner != null && serviceSetterGetter.Data.brandZoneBanner.isNotEmpty()) {
                relBrand.visibility = View.VISIBLE
                pagerBrand!!.adapter = serviceSetterGetter.Data.brandZoneBanner.let {
                    HomeImageAdapter(
                        this,
                        it as ArrayList<BannerItem>
                    )
                }
                pagerBrand!!.startAutoScroll()
                viewPagerIndicatorBrand.setViewPager(pagerBrand)
                viewPagerIndicatorBrand.radius = resources.getDimension(R.dimen._3sdp)
            } else {
                relBrand.visibility = View.GONE
            }
            if (serviceSetterGetter.Data?.promotionalBanner != null && serviceSetterGetter.Data.promotionalBanner.isNotEmpty()) {
                relPromotion.visibility = View.VISIBLE
                pagerPromotion!!.adapter = serviceSetterGetter.Data.promotionalBanner.let {
                    HomeImageAdapter(
                        this,
                        it as ArrayList<BannerItem>
                    )
                }
                pagerPromotion!!.startAutoScroll()
                viewPagerIndicatorPromotion.setViewPager(pagerPromotion)
                viewPagerIndicatorPromotion.radius = resources.getDimension(R.dimen._3sdp)
            } else {
                relPromotion.visibility = View.GONE

            }

            if (serviceSetterGetter.Data?.promotionalBanner2 != null && serviceSetterGetter.Data?.promotionalBanner2!!.isNotEmpty()) {
                relPromotion2.visibility = View.VISIBLE
                pagerPromotion2!!.adapter = serviceSetterGetter.Data?.promotionalBanner2?.let {
                    HomeImageAdapter(
                        this!!,
                        it as ArrayList<BannerItem>
                    )
                }
                pagerPromotion2!!.startAutoScroll()
                viewPagerIndicatorPromotion2.setViewPager(pagerPromotion2)
                viewPagerIndicatorPromotion2.radius = resources.getDimension(R.dimen._3sdp)
            } else {
                relPromotion2.visibility = View.GONE

            }

        }

        mainActivityViewModel.getBanner()

        mainActivityViewModel.servicesProductData?.observe(this) {


            products= it.Data!!.marketList
            totalPage= it.Data!!.Pagination!!.totalPage!!
            totalCount=it.Data!!.Pagination!!.totalCount!!
            currentPage=it.Data!!.Pagination!!.page!!+1
            products?.let { it1 -> adapterList!!.addAll(it1) }
       }


        adapterList = products?.let { ProductListAdapter(it, this) }
        rcyProductList.adapter = adapterList

        if (nestedScrollView != null) {

            nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                val TAG = "nested_sync"
                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN")

                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP")
                    relbottomView.visibility=View.GONE
                }
                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL")
                }
                if (scrollY == v.getChildAt(0)
                        .measuredHeight - v.measuredHeight
                ) {

                    Log.i(TAG, "BOTTOM SCROLL")
                    isLastPage=totalPage+1==currentPage

                    if (!isLastPage) {
                        relbottomView.visibility=View.VISIBLE
                        isLoading=true
                        mainActivityViewModel.getproduct(currentPage.toString())

                    }

                }
            })
        }
        mainActivityViewModel.getproduct(currentPage.toString())
    }


}
