package com.kbtg.android.espresso.di.module.page2

import android.app.Activity
import com.kbtg.android.espresso.di.scope.PerActivity
import com.kbtg.android.espresso.ui.page2.view.IPage2View
import com.kbtg.android.espresso.ui.page2.view.Page2Activity
import dagger.Binds
import dagger.Module

@Module(includes = [Page2PresenterModule::class])
abstract class Page2ActivityModule {

    @Binds
    @PerActivity
    abstract fun providePage2Activity(page2Activity: Page2Activity): Activity?

    @Binds
    @PerActivity
    abstract fun providePage2View(page2Activity: Page2Activity?): IPage2View?
}