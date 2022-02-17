package com.demo.pinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.pinal.model.BannerResponse
import com.demo.pinal.model.ProductResponse
import com.demo.pinal.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<BannerResponse>? = null
    var servicesProductData: MutableLiveData<ProductResponse>? = null

    init {
        servicesLiveData = MainActivityRepository.serviceSetterGetter
        servicesProductData = MainActivityRepository.serviceProduct
    }

    fun getBanner() {
        MainActivityRepository.getServicesApiCall()
    }

    fun getproduct(page:String) {
        MainActivityRepository.getProductApiCall(page)
    }

}