package com.kbtg.android.espresso.idlingresource

import androidx.test.espresso.IdlingResource

class CustomIdlingResource : IdlingResource {
    private var mIsIdle = false
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private val waitAppLaunch = 2000L

    fun updateIdleState() {
        Thread.sleep(waitAppLaunch)
        mIsIdle = true
        resourceCallback?.onTransitionToIdle()
    }

    override fun getName(): String = this.javaClass.simpleName

    override fun isIdleNow(): Boolean = mIsIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}