package com.kbtg.android.espresso

import android.app.Application
import com.kbtg.android.espresso.di.component.ApplicationComponent
import com.kbtg.android.espresso.di.component.DaggerApplicationComponent
import com.kbtg.android.espresso.di.module.AppModule
import com.kbtg.android.espresso.di.module.NetModule

open class ApplicationClass : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .netModule(NetModule())
                .build()

        applicationComponent.inject(this)
    }
}