package com.kbtg.android.espresso.di.module

import android.app.Application
import com.kbtg.android.espresso.ApplicationClass
import com.kbtg.android.espresso.di.module.main.MainActivityModule
import com.kbtg.android.espresso.di.module.nationdetail.NationDetailActivityModule
import com.kbtg.android.espresso.di.module.nations.NationActivityModule
import com.kbtg.android.espresso.di.module.page2.Page2ActivityModule
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.countrydetail.view.NationDetailActivity
import com.kbtg.android.espresso.ui.main.view.MainActivity
import com.kbtg.android.espresso.ui.nationlist.view.NationListActivity
import com.kbtg.android.espresso.ui.page2.view.Page2Activity
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
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [NationActivityModule::class])
    internal abstract fun nationlistActivityInjector(): NationListActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [NationDetailActivityModule::class])
    internal abstract fun nationDetailActivityInjector(): NationDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [Page2ActivityModule::class])
    internal abstract fun page2ActivityInjector(): Page2Activity
}