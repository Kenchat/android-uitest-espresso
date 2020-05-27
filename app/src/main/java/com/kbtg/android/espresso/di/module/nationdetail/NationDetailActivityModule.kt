package com.kbtg.android.espresso.di.module.nationdetail

import android.app.Activity
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.nationdetail.view.INationDetailView
import com.kbtg.android.espresso.ui.nationdetail.view.NationDetailActivity
import dagger.Binds
import dagger.Module

@Module(includes = [NationDetailPresenterModule::class])
abstract class NationDetailActivityModule {

    @Binds
    @PerActivity
    abstract fun provideNationDetailActivity(nationDetailActivity: NationDetailActivity?): Activity?

    @Binds
    @PerActivity
    abstract fun provideNationDetailView(nationDetailActivity: NationDetailActivity?): INationDetailView?
}