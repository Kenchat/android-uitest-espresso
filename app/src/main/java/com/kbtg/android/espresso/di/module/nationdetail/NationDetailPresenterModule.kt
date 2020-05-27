package com.kbtg.android.espresso.di.module.nationdetail

import com.kbtg.android.espresso.ui.nationdetail.presenter.NationDetailPresenter
import com.kbtg.android.espresso.ui.nationdetail.presenter.NationDetailPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NationDetailPresenterModule {

    @Binds
    abstract fun provideNationDetailPresenter(nationDetailPresenterImpl: NationDetailPresenterImpl?): NationDetailPresenter?
}