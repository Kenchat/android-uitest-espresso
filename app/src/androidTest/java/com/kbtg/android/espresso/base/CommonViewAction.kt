package com.kbtg.android.espresso.base

import android.app.Activity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.internal.util.Checks
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.google.android.material.tabs.TabLayout
import org.hamcrest.*
import org.hamcrest.Matchers.`is`
import java.util.concurrent.TimeoutException

object CommonViewAction {
    const val TAG = "LOG"
    private const val sleepTime = 1000L

    // maximum time in milliseconds to wait for a view visible
    private const val maximumWaitedTime = 10000L
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout.widget.ConstraintLayout"

    const val WAIT_VIEW_VISIBLE = "waitViewVisible"
    const val PERFORM_VIEW_VISIBLE = "performViewVisible"

    fun clickChildViewWithId(id: Int?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                if (id == null) return
                val child: View = view.findViewById<View>(id)
                child.performClick()
            }
        }
    }

    fun scrollRecyclerviewToPosition(position: Int?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                if (position == null) return
                if (view !is RecyclerView) return

                view.post {
                    view.smoothScrollToPosition(position)
                }
            }
        }
    }

    // wait childView with id @childId in item at position @positionItem of recyclerview
    fun waitChildViewInRecyclerviewVisible(positionItem: Int?, childId: Int?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                if (view !is RecyclerView) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | view $view is not recyclerview")
                    return
                } // return if parentView not recyclerview
                if (positionItem == null) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | position of item is null")
                    return
                }
                if (childId == null) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | id of child view in recyclerview is null")
                    return
                }

                val item: View
                try {
                    item = (view as RecyclerView)[positionItem]
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                    Log.d(
                        TAG,
                        "CommonViewAction | waitChildViewInRecyclerviewVisible() | error: ${e.message}"
                    )
                    throw  IndexOutOfBoundsException(e.message)
                }

                val startTime: Long = System.currentTimeMillis()
                var child: View? = null

                while ((System.currentTimeMillis() - startTime) <= maximumWaitedTime) {
                    child = item.findViewById<View>(childId)

                    if (child != null) return

                    Thread.sleep(sleepTime)
                }
                // throw exception after 10 seconds couldn't find childView
                throwException(
                    "CommonViewAction | waitChildViewInRecyclerviewVisible() " +
                            "| can't find view in item with position $positionItem in the recyclerview $view"
                )
            }
        }
    }

    fun clickChildViewInRecyclerviewAtPosition(positionItem: Int?, childId: Int?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                if (view !is RecyclerView) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | view $view is not recyclerview")
                    return
                } // return if parentView not recyclerview
                if (positionItem == null) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | position of item is null")
                    return
                }
                if (childId == null) {
                    throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | id of child view in recyclerview is null")
                    return
                }

                val item: View
                try {
                    item = (view as RecyclerView)[positionItem]
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                    Log.d(
                        TAG,
                        "CommonViewAction | waitChildViewInRecyclerviewVisible() | error: ${e.message}"
                    )
                    throw  IndexOutOfBoundsException(e.message)
                }

                val startTime: Long = System.currentTimeMillis()
                var child: View? = null

                while ((System.currentTimeMillis() - startTime) <= maximumWaitedTime) {
                    child = item.findViewById<View>(childId)
                    if (child != null) {
                        child.performClick()
                        return
                    }

                    Thread.sleep(sleepTime)
                }
                // throw exception after 10 seconds couldn't find childView
                throwException("CommonViewAction | waitChildViewInRecyclerviewVisible() | can't find view in item with position $positionItem in the recyclerview $view")
            }
        }
    }

    @JvmStatic
    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object :
            BoundedMatcher<View, RecyclerView>(androidx.recyclerview.widget.RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: androidx.recyclerview.widget.RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    fun getView(viewId: Int): ViewInteraction = Espresso.onView(ViewMatchers.withId(viewId))

    fun getView(
        className: String,
        parentViewId: Int,
        positionOfViewInParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withClassName(Matchers.`is`<String>(className)),
            getChildAtPosition(ViewMatchers.withId(parentViewId), positionOfViewInParentView)
        )
    )

    fun getView(viewId: Int, parentViewId: Int, positionOfViewInParentView: Int): ViewInteraction =
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(viewId),
                getChildAtPosition(ViewMatchers.withId(parentViewId), positionOfViewInParentView)
            )
        )

    fun getView(
        viewId: Int, parentViewId: Int, positionOfViewInParentView: Int, grandParentViewId: Int,
        positionOfParentViewInGrandParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                Matchers.allOf(
                    ViewMatchers.withId(parentViewId),
                    getChildAtPosition(
                        ViewMatchers.withId(grandParentViewId),
                        positionOfParentViewInGrandParentView
                    )
                ),
                positionOfViewInParentView
            )
        )
    )

    fun getView(
        viewId: Int, parentViewId: Int, positionOfViewInParentView: Int,
        grandParentViewId: Int, positionOfParentViewInGrandParentView: Int,
        greatGrandParentViewId: Int, positionOfGrandParentViewInGreatGrandParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                Matchers.allOf(
                    ViewMatchers.withId(parentViewId),
                    getChildAtPosition(
                        Matchers.allOf(
                            ViewMatchers.withId(grandParentViewId),
                            getChildAtPosition(
                                ViewMatchers.withId(greatGrandParentViewId),
                                positionOfGrandParentViewInGreatGrandParentView
                            )
                        ), positionOfParentViewInGrandParentView
                    )
                ), positionOfViewInParentView
            )
        )
    )

    fun getView(
        viewId: Int,
        parentViewId: Int,
        positionOfViewInParentView: Int,
        grandParentViewId: Int,
        positionOfParentViewInGrandParentView: Int,
        greatGrandParentViewId: Int,
        positionOfGrandParentViewInGreatGrandParentView: Int,
        greatGreatGrandParentViewId: Int,
        positionGreatGrandParentViewInGreatGreatGrandParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                Matchers.allOf(
                    ViewMatchers.withId(parentViewId),
                    getChildAtPosition(
                        Matchers.allOf(
                            ViewMatchers.withId(grandParentViewId),
                            getChildAtPosition(
                                Matchers.allOf(
                                    ViewMatchers.withId(greatGrandParentViewId),
                                    getChildAtPosition(
                                        ViewMatchers.withId(greatGreatGrandParentViewId),
                                        positionGreatGrandParentViewInGreatGreatGrandParentView
                                    )
                                ), positionOfGrandParentViewInGreatGrandParentView
                            )
                        ), positionOfParentViewInGrandParentView
                    )
                ), positionOfViewInParentView
            )
        )
    )

    fun getViewWithoutParentViewId(
        viewId: Int, positionOfViewInParentView: Int, grandParentViewId: Int,
        positionOfParentViewInGrandParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                getChildAtPosition(
                    ViewMatchers.withId(grandParentViewId),
                    positionOfParentViewInGrandParentView
                ), positionOfViewInParentView
            )
        )
    )

    fun getViewInParentConstraintLayout(
        viewId: Int,
        positionOfViewInParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                ViewMatchers.withClassName(`is`<String>(CONSTRAINT_LAYOUT)),
                positionOfViewInParentView
            )
        )
    )

    fun getViewInGrandParentConstraintLayout(
        viewId: Int, parentViewId: Int, positionOfViewInParentView: Int,
        positionOfParentViewInGrandParentView: Int
    ): ViewInteraction = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewId),
            getChildAtPosition(
                Matchers.allOf(
                    ViewMatchers.withId(parentViewId),
                    getChildAtPosition(
                        ViewMatchers.withClassName(`is`<String>(CONSTRAINT_LAYOUT)),
                        positionOfParentViewInGrandParentView
                    )
                ), positionOfViewInParentView
            )
        )
    )

    //https://google-developer-training.github.io/android-developer-fundamentals-course-practicals/en/Unit%202/61_p_use_espresso_to_test_your_ui.html
    fun getChildAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }

    fun performTypeTextAndPressBack(editText: ViewInteraction, text: String) {
        performViewVisible(
            editText,
            ViewActions.click(),
            ViewActions.replaceText(text), ViewActions.closeSoftKeyboard(),
            ViewActions.pressKey(KeyEvent.KEYCODE_BACK)//hide keyboard
        )
    }

    fun performTypeTextAndPressImeActionButton(editText: ViewInteraction, text: String) {
        performViewVisible(
            editText,
            ViewActions.click(),
            ViewActions.typeText(text), ViewActions.closeSoftKeyboard(),
            ViewActions.pressImeActionButton()
        )
    }

    fun performClickOnRecyclerView(recyclerView: ViewInteraction, position: Int) {
        performViewVisible(
            recyclerView,
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
    }

    fun performTabOnTabLayout(tabLayout: ViewInteraction, position: Int) {
        performViewVisible(tabLayout, selectTabAtPosition(position))
    }

    fun getActivityInstance(): Activity {
        var currentActivity: Activity? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
                    Stage.RESUMED
                )
            for (act in resumedActivities) {
                currentActivity = act
                break
            }
        }
        return currentActivity!!
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
            Log.d("LOG", "CommonViewAcrtion | getCurrentActivityOrNull() | e: ${e.message}")
        }
        return activity[0]
    }

    fun waitId(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = ViewMatchers.withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }

    fun withTextColor(color: Int): Matcher<View> {
        Checks.checkNotNull(color)
        return object : BoundedMatcher<View, TextView>(TextView::class.java) {
            override fun matchesSafely(textView: TextView): Boolean {
                return ContextCompat.getColor(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    color
                ) == textView.currentTextColor
            }

            override fun describeTo(description: Description) {
                description.appendText("with text color: ")
            }
        }
    }

    fun setChecked(checked: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): BaseMatcher<View> {
                return object : BaseMatcher<View>() {
                    override fun matches(item: Any): Boolean {
                        return Matchers.isA(Checkable::class.java).matches(item)
                    }

                    override fun describeMismatch(item: Any, mismatchDescription: Description) {}

                    override fun describeTo(description: Description) {}
                }
            }

            override fun getDescription(): String? {
                return "checked: $checked"
            }

            override fun perform(uiController: UiController, view: View) {
                val checkableView = view as Checkable
                checkableView.isChecked = checked
            }
        }
    }

    fun withChecked(): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {
            public override fun matchesSafely(view: View): Boolean {
                val checkAble = view as Checkable
                return checkAble.isChecked
            }

            override fun describeTo(description: Description) {
                description.appendText("with checked: ")
            }
        }
    }

    fun setChildChecked(id: Int, checked: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): BaseMatcher<View> {
                return object : BaseMatcher<View>() {
                    override fun matches(item: Any): Boolean {
                        return Matchers.isA(Checkable::class.java).matches(item)
                    }

                    override fun describeMismatch(item: Any, mismatchDescription: Description) {}

                    override fun describeTo(description: Description) {}
                }
            }

            override fun getDescription(): String? {
                return "checked: $checked"
            }

            override fun perform(uiController: UiController, view: View) {

                val checkableView = view.findViewById<View>(id) as Checkable
                checkableView.isChecked = checked
            }
        }
    }

    fun setProgressPercent(progress: Int): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val seekBar = view as SeekBar
                seekBar.progress = seekBar.max * progress / 100
            }

            override fun getDescription(): String {
                return "Set a progress"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(SeekBar::class.java)
            }
        }
    }

    fun withProgressPercent(expectedProgress: Int): Matcher<View> {
        return object : BoundedMatcher<View, SeekBar>(SeekBar::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("expected: ")
                description.appendText("" + expectedProgress)
            }

            override fun matchesSafely(seekBar: SeekBar): Boolean {
                return seekBar.progress == seekBar.max * expectedProgress / 100
            }
        }
    }

    fun selectTabAtPosition(position: Int): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tab = tabLayout.getTabAt(position)

                tab?.select()
            }

            override fun getDescription(): String {
                return "with tab at index $position"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TabLayout::class.java)
            }
        }
    }

    fun isCurrentTab(tabTitle: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("unable to match title of current selected tab with $tabTitle")
            }

            override fun matchesSafely(item: View?): Boolean {
                val tabLayout = item as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabLayout.selectedTabPosition)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index ${tabLayout.selectedTabPosition}"))
                        .build()

                return tabAtIndex.text.toString().contains(tabTitle, true)
            }
        }
    }

    fun withTextNotEmptyString(): Matcher<View> {
        return object : BoundedMatcher<View, TextView>(TextView::class.java) {
            public override fun matchesSafely(textView: TextView): Boolean {

                return textView.text.toString() != ""
            }

            override fun describeTo(description: Description) {
                description.appendText("with text not empty string: ")
            }
        }
    }

    fun getText(viewInteraction: ViewInteraction): String {

        var text = ""
        viewInteraction.perform(object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }

    fun getTextOfAppCompatTextView(matcher: ViewInteraction): String {
        val description = "getting text from a AppCompatTextView"
        var stringHolder = ""
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(AppCompatTextView::class.java)
            }

            override fun getDescription(): String {
                return description
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as AppCompatTextView
                stringHolder = tv.text.toString()
            }
        })
        return stringHolder
    }

    fun performViewVisible(viewInteraction: ViewInteraction, viewAction: ViewAction) {
        performViewVisible(viewInteraction, arrayListOf(viewAction))
    }

    fun performViewVisible(viewInteraction: ViewInteraction, vararg viewActions: ViewAction) {
        val actions = ArrayList<ViewAction>()
        for (action in viewActions) {
            actions.add(action)
        }
        performViewVisible(viewInteraction, actions)
    }

    fun performViewVisible(viewInteraction: ViewInteraction, viewActions: ArrayList<ViewAction>) {
        if (viewActions.isEmpty()) throw Exception("performViewVisible: viewActions is empty.")
        var isVisible = false
        val startedTime: Long = System.currentTimeMillis()
        var elapsedTime: Long = 0
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

    fun checkViewWithText(viewInteraction: ViewInteraction, text: String) {
        viewInteraction.check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    fun waitViewVisible(viewInteraction: ViewInteraction, block: (() -> Unit)? = null) {
        waitAssertView(viewInteraction, ViewAssertions.matches(ViewMatchers.isDisplayed()), block)
    }

    fun waitViewGone(viewInteraction: ViewInteraction, block: (() -> Unit)? = null) {
        waitAssertView(
            viewInteraction,
            ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())),
            block
        )
    }

    fun waitAssertView(
        viewInteraction: ViewInteraction,
        assertion: ViewAssertion,
        block: (() -> Unit)? = null
    ) {
        val startedTime: Long = System.currentTimeMillis()
        var elapsedTime: Long = 0
        var isVisible = false

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

    fun swipeLeft(viewInteraction: ViewInteraction) {
        viewInteraction.perform(ViewActions.swipeLeft())
    }
}