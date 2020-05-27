package com.kbtg.android.espresso.idlingresource

import androidx.test.espresso.IdlingResource

/**
 * An idling resource represents an asynchronous operation whose results
 * affect subsequent operations in a UI test.
 *
 * Idling Resources synchronize Espresso tests with your app’s background tasks.
 *
 * https://android.jlelse.eu/integrate-espresso-idling-resources-in-your-app-to-build-flexible-ui-tests-c779e24f5057
 */
class CustomIdlingResource : IdlingResource {
    private var mIsIdle = false
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    fun updateIdleState() {
        mIsIdle = true //false = app is busy, espresso pause actions
        resourceCallback?.onTransitionToIdle()
    }

    override fun getName(): String = this.javaClass.simpleName

    override fun isIdleNow(): Boolean = mIsIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}