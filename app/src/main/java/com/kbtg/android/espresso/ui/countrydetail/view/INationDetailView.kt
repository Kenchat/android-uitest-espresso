package com.kbtg.android.espresso.ui.countrydetail.view

import com.kbtg.android.espresso.ui.countrydetail.model.CountryDetailResponseItem

interface INationDetailView {

    fun updateCountryDetailData(dataList: List<CountryDetailResponseItem>)

    fun onGetDataFailure()
}