package com.kbtg.android.espresso.base

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kbtg.android.espresso.idlingresource.CustomIdlingResTestRule
import com.kbtg.android.espresso.main.view.MainActivity
import com.kbtg.android.espresso.network.ServiceBuilder
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseMockService {

    private var mockWebServer = MockWebServer()
    private lateinit var dispatcher: Dispatcher
    protected fun getDispatcher(): Dispatcher = dispatcher
    abstract fun initDispatcher(): Dispatcher

    companion object {
        const val SLEEP_TIME = 1000L
    }

    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    //use @JvmField to fix: Delegate runner 'androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner' for AndroidJUnit4 could not be loaded.
    @Rule
    @JvmField
    val idlingResourceTestRule = CustomIdlingResTestRule()

    @Rule
    @JvmField
    val grantPermissionRule = GrantPermissionRule.grant(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE")

    @Before
    open fun setup() {
        //ServiceBuilder.BASE_URL = "http://localhost:8081/"
        //
        mockWebServer.start(8081)
        dispatcher = initDispatcher()
        mockWebServer.dispatcher = dispatcher

        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MainActivity::class.java
        )
        activityTestRule.launchActivity(intent)

        idlingResourceTestRule.idlingResource.updateIdleState()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}