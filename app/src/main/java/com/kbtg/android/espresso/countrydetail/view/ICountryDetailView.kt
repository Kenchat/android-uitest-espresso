package com.kbtg.android.espresso.countrydetail.view

import com.kbtg.android.espresso.countrydetail.model.CountryDetailResponseItem

interface ICountryDetailView {

    fun updateCountryDetailData(dataList: List<CountryDetailResponseItem>)

    fun onGetDataFailure()
}