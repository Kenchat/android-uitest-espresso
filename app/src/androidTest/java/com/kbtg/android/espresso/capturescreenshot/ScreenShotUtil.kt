package com.kbtg.android.espresso.capturescreenshot

import com.facebook.testing.screenshot.Screenshot
import com.kbtg.android.espresso.base.CommonViewAction

object ScreenShotUtil {
    private const val WAIT_FOR_CAPTURE_SCREENSHOT = 200L
    private var numericalOrder = 1
    private const val FORMAT_SCREEN_NAME = "%d_%s"

    fun captureScreen(screenName: String) {
        val currentActivity = CommonViewAction.getCurrentActivityOrNull()
        //waiting for capture screenshot
        Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)
        Screenshot.snapActivity(currentActivity)
            .setName(String.format(FORMAT_SCREEN_NAME, numericalOrder++, screenName)).record()
        //waiting for capture screenshot
        Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)
    }
}