package com.kbtg.android.espresso.di.module.page2

import com.kbtg.android.espresso.ui.page2.presenter.IPage2Presenter
import com.kbtg.android.espresso.ui.page2.presenter.Page2PresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class Page2PresenterModule {

    @Binds
    abstract fun page2Presenter(page2PresenterImpl: Page2PresenterImpl): IPage2Presenter?
}