package com.kbtg.android.espresso.base

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kbtg.android.espresso.idlingresource.CustomIdlingResTestRule
import com.kbtg.android.espresso.ui.nationlist.view.NationListActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseMockService {

    private var mockWebServer = MockWebServer()
    private lateinit var dispatcher: Dispatcher
    abstract fun initDispatcher(): Dispatcher

    private var activityTestRule = ActivityTestRule(NationListActivity::class.java)

    //use @JvmField to fix: Delegate runner 'androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner' for AndroidJUnit4 could not be loaded.
    @Rule
    @JvmField
    val idlingResourceTestRule = CustomIdlingResTestRule()

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )

    @Before
    open fun setup() {
        mockWebServer.start(8081)
        dispatcher = initDispatcher()
        mockWebServer.dispatcher = dispatcher

        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            NationListActivity::class.java
        )
        activityTestRule.launchActivity(intent)

        idlingResourceTestRule.idlingResource.updateIdleState()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}