package com.kbtg.android.espresso.ui.nationlist.view

import com.kbtg.android.espresso.ui.base.IBaseView
import com.kbtg.android.espresso.ui.nationlist.model.Country

interface INationListView : IBaseView {

    fun updateDataSummary(dataList: List<Country>)

    fun onGetDataFailure()

    fun goToCountryDetail(countryName: String)
}