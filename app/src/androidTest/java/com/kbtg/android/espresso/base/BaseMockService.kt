package com.kbtg.android.espresso.base

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.kbtg.android.espresso.idlingresource.CustomIdlingResTestRule
import com.kbtg.android.espresso.idlingresource.CustomIdlingResource
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

    // wait until espresso idle and all requests of apis are finished.
    var idlingResource: CustomIdlingResource = CustomIdlingResource()

    @Before
    open fun setup() {
        mockWebServer.start(8080)
        dispatcher = initDispatcher()
        mockWebServer.dispatcher = dispatcher

        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            NationListActivity::class.java
        )
        activityTestRule.launchActivity(intent)

        IdlingRegistry.getInstance().register(idlingResource)

        idlingResource.updateIdleState()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}