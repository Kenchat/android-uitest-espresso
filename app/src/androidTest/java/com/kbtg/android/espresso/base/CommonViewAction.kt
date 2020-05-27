package com.kbtg.android.espresso.base

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

object CommonViewAction {
    private const val TAG = "LOG"
    private const val sleepTime = 1000L

    // maximum time in milliseconds to wait for a view visible
    private const val maximumWaitedTime = 10000L

    private const val WAIT_VIEW_VISIBLE = "waitViewVisible"
    private const val PERFORM_VIEW_VISIBLE = "performViewVisible"

    fun scrollRecyclerviewToPosition(position: Int?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                if (position == null || view !is RecyclerView) return
                view.post {
                    view.smoothScrollToPosition(position)
                }
            }
        }
    }

    fun getView(viewId: Int): ViewInteraction = Espresso.onView(ViewMatchers.withId(viewId))

    fun performClickOnRecyclerView(recyclerView: ViewInteraction, position: Int) {
        performViewVisible(
            recyclerView,
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
    }

    fun getCurrentActivityOrNull(): Activity? {
        val activity = arrayOfNulls<Activity>(1)
        activity[0] = null

        try {
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                val activities: Collection<Activity> =
                    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
                        Stage.RESUMED
                    )
                activity[0] = Iterables.getOnlyElement(activities)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("LOG", "CommonViewAction | getCurrentActivityOrNull() | e: ${e.message}")
        }
        return activity[0]
    }

    fun performViewVisible(viewInteraction: ViewInteraction, viewAction: ViewAction) {
        performViewVisible(viewInteraction, arrayListOf(viewAction))
    }

    private fun performViewVisible(
        viewInteraction: ViewInteraction,
        viewActions: ArrayList<ViewAction>
    ) {
        if (viewActions.isEmpty()) throw Exception("performViewVisible: viewActions is empty.")
        var isVisible: Boolean
        val startedTime: Long = System.currentTimeMillis()
        var elapsedTime: Long
        do {
            isVisible = runCatching {
                viewInteraction.perform(viewActions.first())
            }.isSuccess
            if (isVisible) break
            elapsedTime = System.currentTimeMillis() - startedTime
            Thread.sleep(sleepTime)
        } while (elapsedTime <= maximumWaitedTime)
        Log.d("LOG", "CommonViewAction | performViewVisible() done")
        if (!isVisible) throwException(PERFORM_VIEW_VISIBLE)
        if (viewActions.size > 1) {
            for (i in 1 until viewActions.size) {
                viewInteraction.perform(viewActions[i])
            }
        }
    }

    fun waitViewVisible(viewInteraction: ViewInteraction, block: (() -> Unit)? = null) {
        waitAssertView(viewInteraction, ViewAssertions.matches(ViewMatchers.isDisplayed()), block)
    }

    private fun waitAssertView(
        viewInteraction: ViewInteraction,
        assertion: ViewAssertion,
        block: (() -> Unit)? = null
    ) {
        val startedTime: Long = System.currentTimeMillis()
        var elapsedTime: Long
        var isVisible: Boolean

        do {
            isVisible = runCatching {
                viewInteraction.check(assertion).withFailureHandler { error, _ ->
                    Log.d(
                        TAG,
                        "CommonViewAction | waitViewVisible | failure | error; ${error?.message}"
                    )
                }
            }.isSuccess
            if (isVisible) break
            elapsedTime = System.currentTimeMillis() - startedTime
            Thread.sleep(sleepTime)
        } while (elapsedTime <= maximumWaitedTime)
        if (!isVisible) throwException(WAIT_VIEW_VISIBLE)

        Log.d(TAG, "CommonViewAction | waitViewVisible() done")

        block?.invoke()
    }

    private fun throwException(actionVisibleType: String): PerformException {
        throw PerformException.Builder()
            .withActionDescription(actionVisibleType)
            .withCause(TimeoutException())
            .build()
    }
}