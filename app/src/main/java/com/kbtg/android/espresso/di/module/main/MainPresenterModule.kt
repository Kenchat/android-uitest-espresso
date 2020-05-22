package com.kbtg.android.espresso.di.module.main

import com.kbtg.android.espresso.ui.main.presenter.MainPresenter
import com.kbtg.android.espresso.ui.main.presenter.MainPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MainPresenterModule {

    @Binds
    abstract fun provideMainPresenter(mainPresenterImpl: MainPresenterImpl?): MainPresenter?
}