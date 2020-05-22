package com.kbtg.android.espresso.capturescreenshot

import android.app.Activity
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

    fun captureDialog(dialogTag: String, screenName: String) {
        val currentActivity: Activity? = CommonViewAction.getCurrentActivityOrNull()

        var dialogFragment: Fragment? = null

        if (currentActivity is FragmentActivity) {
            dialogFragment = currentActivity.supportFragmentManager.findFragmentByTag(dialogTag)
        }

        if (dialogFragment == null) return

        if (dialogFragment is DialogFragment) {
            //waiting for capture screenshot
            Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)
            Screenshot.snap(dialogFragment.dialog?.window?.decorView)
                .setName(String.format(FORMAT_SCREEN_NAME, numericalOrder++, screenName)).record()
            //waiting for capture screenshot
            Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)
        }
    }

    fun captureDialog(id: Int?, screenName: String) {
        if (id == null) {
            Log.d("LOG", "ScreenShotUtil | captureDialog() | id is null")
            return
        }
        val currentActivity: Activity? = CommonViewAction.getCurrentActivityOrNull()

        //waiting for capture screenshot
        Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)

        if (currentActivity != null) {
            val screenshotName: String =
                String.format(FORMAT_SCREEN_NAME, numericalOrder++, screenName)
            CaptureScreenShotDialogUtil.captureDialog(currentActivity, id, screenshotName)
        }

        //waiting for capture screenshot
        Thread.sleep(WAIT_FOR_CAPTURE_SCREENSHOT)
    }
}