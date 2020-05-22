package com.kbtg.android.espresso.di.module.nations

import com.kbtg.android.espresso.ui.nationlist.presenter.INationListPresenter
import com.kbtg.android.espresso.ui.nationlist.presenter.NationListPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NationPresenterModule {

    @Binds
    abstract fun provideNationListPresenter(nationListPresenterImpl: NationListPresenterImpl): INationListPresenter?
}