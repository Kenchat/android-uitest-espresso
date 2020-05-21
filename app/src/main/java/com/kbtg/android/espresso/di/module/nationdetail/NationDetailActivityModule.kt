package com.kbtg.android.espresso.di.module.nationdetail

import android.app.Activity
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.countrydetail.view.NationDetailActivity
import com.kbtg.android.espresso.ui.countrydetail.view.INationDetailView
import dagger.Binds
import dagger.Module

@Module(includes = [NationDetailPresenterModule::class])
abstract class NationDetailActivityModule {

    @Binds
    @PerActivity
    abstract fun nationCountryDetailActivity(countryDetailActivity: NationDetailActivity?): Activity?

    @Binds
    @PerActivity
    abstract fun provideCountryDetailView(countryDetailActivity: NationDetailActivity?): INationDetailView?
}