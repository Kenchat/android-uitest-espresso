package com.kbtg.android.espresso.di.module.nations

import android.app.Activity
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.nationlist.view.INationListView
import com.kbtg.android.espresso.ui.nationlist.view.NationListActivity
import dagger.Binds
import dagger.Module

@Module(includes = [NationPresenterModule::class])
abstract class NationActivityModule {

    @Binds
    @PerActivity
    abstract fun provideNationActivity(nationActivity: NationListActivity?): Activity?

    @Binds
    @PerActivity
    abstract fun provideNationView(nationActivity: NationListActivity?): INationListView?
}