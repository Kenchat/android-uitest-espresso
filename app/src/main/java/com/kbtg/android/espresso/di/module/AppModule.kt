package com.kbtg.android.espresso.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(application: Application) {

    lateinit var applicationInst: Application

    init {
        applicationInst = application
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return applicationInst
    }
}