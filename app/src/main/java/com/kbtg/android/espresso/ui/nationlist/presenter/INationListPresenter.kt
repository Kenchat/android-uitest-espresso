package com.kbtg.android.espresso.ui.nationlist.presenter

interface INationListPresenter {

    fun getNationListData()

    fun onItemSelected(countryName: String)
}