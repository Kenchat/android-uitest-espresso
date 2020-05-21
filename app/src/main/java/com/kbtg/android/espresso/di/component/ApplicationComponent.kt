package com.kbtg.android.espresso.di.component

import com.kbtg.android.espresso.ApplicationClass
import com.kbtg.android.espresso.di.module.ActivityBuilder

import com.kbtg.android.espresso.di.module.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetModule::class,
    ActivityBuilder::class])
interface ApplicationComponent : AndroidInjector<ApplicationClass> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<ApplicationClass>
}