package com.kbtg.android.espresso.di.module.main

import android.app.Activity
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.main.view.MainActivity
import com.kbtg.android.espresso.ui.main.view.IMainView
import dagger.Binds
import dagger.Module

@Module(includes = [MainPresenterModule::class])
abstract class MainActivityModule {

    @Binds
    @PerActivity
    abstract fun provideMainActivity(mainActivity: MainActivity?): Activity?

    @Binds
    @PerActivity
    abstract fun provideMainView(mainActivity: MainActivity?): IMainView?
}