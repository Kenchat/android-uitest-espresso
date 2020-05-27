package com.kbtg.android.espresso.idlingresource

import androidx.test.espresso.IdlingResource

class CustomIdlingResource : IdlingResource {
    private var mIsIdle = false
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    fun updateIdleState() {
        mIsIdle = true //false = espresso is pending and cannot do actions
        resourceCallback?.onTransitionToIdle()
    }

    override fun getName(): String = this.javaClass.simpleName

    override fun isIdleNow(): Boolean = mIsIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}