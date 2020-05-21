package com.kbtg.android.espresso.di.module.nationdetail

import com.kbtg.android.espresso.listcountry.presenter.NationDetailPresenter
import com.kbtg.android.espresso.ui.countrydetail.presenter.NationDetailPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NationDetailPresenterModule {

    @Binds
    abstract fun countryDetailPresenter(countryDetailPresenterImpl: NationDetailPresenterImpl?): NationDetailPresenter?
}