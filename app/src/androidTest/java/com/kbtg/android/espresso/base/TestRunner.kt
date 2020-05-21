package com.kbtg.android.espresso.base

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner

/**
 * TestRunner class for "shot" library
 */
class TestRunner : AndroidJUnitRunner() {

    override fun onCreate(args: Bundle?) {
        super.onCreate(args)
        //ScreenshotRunner.onCreate(this, args)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        //ScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }
}