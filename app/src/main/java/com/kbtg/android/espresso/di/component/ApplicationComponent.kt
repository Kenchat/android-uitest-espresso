package com.kbtg.android.espresso.di.component

import com.kbtg.android.espresso.ApplicationClass
import com.kbtg.android.espresso.di.module.AppModule
import com.kbtg.android.espresso.di.module.NetModule
import com.kbtg.android.espresso.ui.nationlist.presenter.NationListPresenterImpl
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class])
interface ApplicationComponent {
    fun inject(mewApplication: ApplicationClass)

    fun inject(nationListPresenterImpl: NationListPresenterImpl)
}