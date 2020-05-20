package com.kbtg.android.espresso.base

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.kbtg.android.espresso.idlingresource.CustomIdlingResTestRule
import com.kbtg.android.espresso.main.view.MainActivity
import org.junit.Before
import org.junit.Rule

abstract class BaseMockService {
    companion object {
        const val SLEEP_TIME = 1000L
    }

    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    //use @JvmField to fix: Delegate runner 'androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner' for AndroidJUnit4 could not be loaded.
    @Rule
    @JvmField
    val idlingResourceTestRule = CustomIdlingResTestRule()

    @Before
    open fun setup() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MainActivity::class.java
        )
        activityTestRule.launchActivity(intent)

        idlingResourceTestRule.idlingResource.updateIdleState()
    }
}