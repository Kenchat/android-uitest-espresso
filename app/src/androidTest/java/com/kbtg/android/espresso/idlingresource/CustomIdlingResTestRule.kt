package com.kbtg.android.espresso.idlingresource

import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CustomIdlingResTestRule : TestRule {
    // wait until espresso idle and all requests of apis are finished.
    var idlingResource: CustomIdlingResource = CustomIdlingResource()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(idlingResource)
                //NOTE: use try cache to ignore UI Test Failures when run coverage report
                /*try {
                    base?.evaluate()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }*/
                //remove try catch when only run test
                base?.evaluate()

                IdlingRegistry.getInstance().unregister(idlingResource)
            }

        }
    }
}