package com.kbtg.android.espresso.idlingresource

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CustomIdlingResTestRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                //NOTE: use try cache to ignore UI Test Failures when run coverage report
                /*try {
                    //it will run actions in ui test method
                    base?.evaluate()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }*/
                //remove try catch when only run test
                base?.evaluate()
            }
        }
    }
}