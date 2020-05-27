package com.kbtg.android.espresso.di.module

import android.app.Application
import com.kbtg.android.espresso.ApplicationClass
import com.kbtg.android.espresso.di.module.nationdetail.NationDetailActivityModule
import com.kbtg.android.espresso.di.module.nations.NationActivityModule
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.countrydetail.view.NationDetailActivity
import com.kbtg.android.espresso.ui.nationlist.view.NationListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBuilder {

    @Binds
    @Singleton
    internal abstract fun application(app: ApplicationClass): Application

    @PerActivity
    @ContributesAndroidInjector(modules = [NationActivityModule::class])
    internal abstract fun nationListActivityInjector(): NationListActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [NationDetailActivityModule::class])
    internal abstract fun nationDetailActivityInjector(): NationDetailActivity
}