package com.kbtg.android.espresso.ui.nationdetail.view

import com.kbtg.android.espresso.ui.base.IBaseView
import com.kbtg.android.espresso.ui.nationdetail.model.NationDetailResponseItem

interface INationDetailView : IBaseView {

    fun updateCountryDetailData(dataList: List<NationDetailResponseItem>)

    fun onGetDataFailure()
}