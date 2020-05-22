package com.kbtg.android.espresso.capturescreenshot

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.IBinder
import android.view.View
import android.view.WindowManager
import com.facebook.testing.screenshot.Screenshot
import java.lang.reflect.Field
import java.util.*

object CaptureScreenShotDialogUtil {
    private const val TAG = "LOG"
    private const val GLOBAL = "mGlobal"
    private const val ROOTS = "mRoots"
    private const val PARAMS = "mParams"
    private const val VIEW = "mView"

    //region Methods
    @Throws(InterruptedException::class)
    fun captureDialog(activity: Activity, childId: Int? = null, screenName: String) {
        if (childId == null) return
        val viewRoots: List<ViewRootData> = getRootViews(activity)
        if (viewRoots.isEmpty()) {
            throw UnableToTakeScreenshotException("Unable to capture any view data in $activity")
        }

        for (root: ViewRootData in viewRoots) {
            if (!root.isDialogType) continue
            val childView: View? = root.view.findViewById(childId)
            if (childView == null) continue
            Screenshot.snap(root.view).setName(screenName).record()
        }
    }

    internal fun getRootViews(activity: Activity): List<ViewRootData> {
        val globalWindowManager: Any?
        globalWindowManager = getFieldValue(GLOBAL, activity.windowManager)

        val rootObjects: Any? = getFieldValue(ROOTS, globalWindowManager)
        val paramsObject: Any? = getFieldValue(PARAMS, globalWindowManager)
        val roots: Array<Any?>
        val params: Array<WindowManager.LayoutParams>

        //  There was a change to ArrayList implementation in 4.4
        roots = (rootObjects as List<*>).toTypedArray()
        val paramsList: List<WindowManager.LayoutParams> =
            paramsObject as List<WindowManager.LayoutParams>
        params = paramsList.toTypedArray()

        val rootViews = viewRootData(roots, params)
        if (rootViews.isEmpty()) {
            return emptyList()
        }

        //offsetRootsTopLeft(rootViews)
        ensureDialogsAreAfterItsParentActivities(rootViews)
        return rootViews
    }

    private fun viewRootData(
        roots: Array<Any?>,
        params: Array<WindowManager.LayoutParams>
    ): MutableList<ViewRootData> {
        val rootViews: MutableList<ViewRootData> = ArrayList()
        for (i: Int in roots.indices) {
            val root = roots[i]
            val rootView = getFieldValue(VIEW, root) as View

            if (!rootView.isShown) {
                continue
            }
            val location = IntArray(2)
            rootView.getLocationOnScreen(location)
            val left = location[0]
            val top = location[1]
            val area = Rect(left, top, left + rootView.width, top + rootView.height)
            rootViews.add(ViewRootData(rootView, area, params[i]))
        }
        return rootViews
    }

    // This fixes issue #11. It is not perfect solution and maybe there is another case
    // of different type of view, but it works for most common case of dialogs.
    private fun ensureDialogsAreAfterItsParentActivities(viewRoots: MutableList<ViewRootData>) {
        if (viewRoots.size <= 1) {
            return
        }
        for (dialogIndex in 0 until viewRoots.size - 1) {
            val viewRoot = viewRoots[dialogIndex]
            if (!viewRoot.isDialogType) {
                continue
            }
            if (viewRoot.windowToken == null) {
                // make sure we will never compare null == null
                return
            }
            for (parentIndex in dialogIndex + 1 until viewRoots.size) {
                val possibleParent = viewRoots[parentIndex]
                if (possibleParent.isActivityType
                    && possibleParent.windowToken === viewRoot.windowToken
                ) {
                    viewRoots.remove(possibleParent)
                    viewRoots.add(dialogIndex, possibleParent)
                    break
                }
            }
        }
    }

    private fun getFieldValue(fieldName: String, target: Any?): Any? {
        return try {
            getFieldValueUnchecked(fieldName, target)
        } catch (e: Exception) {
            throw UnableToTakeScreenshotException(e)
        }
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    private fun getFieldValueUnchecked(fieldName: String, target: Any?): Any? {
        if (target == null) return null
        val field = findField(fieldName, target.javaClass)
        field.isAccessible = true
        return field[target]
    }

    @Throws(NoSuchFieldException::class)
    fun findField(name: String, clazz: Class<*>): Field {
        var currentClass: Class<*>? = clazz
        while (currentClass != Any::class.java) {
            for (field in currentClass!!.declaredFields) {
                if (name == field.name) {
                    return field
                }
            }
            currentClass = currentClass.superclass
        }
        throw NoSuchFieldException("Field $name not found for class $clazz")
    }
    //endregion
    //region Nested classes
    /**
     * Custom exception thrown if there is some exception thrown during
     * screenshot capturing to enable better client code exception handling.
     */
    private class UnableToTakeScreenshotException : RuntimeException {
        constructor(detailMessage: String) : super(detailMessage) {}
        constructor(detailMessage: String, exception: Exception) : super(
            detailMessage,
            extractException(exception)
        ) {
        }

        constructor(ex: Exception) : super(extractException(ex)) {}

        companion object {
            /**
             * Method to avoid multiple wrapping. If there is already our exception,
             * just wrap the cause again
             */
            private fun extractException(ex: Exception): Throwable? {
                return if (ex is UnableToTakeScreenshotException) {
                    ex.cause
                } else ex
            }
        }
    }

    internal class ViewRootData(
        val view: View,
        val winFrame: Rect,
        val layoutParams: WindowManager.LayoutParams
    ) {
        val isDialogType: Boolean
            get() = layoutParams.type == WindowManager.LayoutParams.TYPE_APPLICATION

        val isActivityType: Boolean
            get() = layoutParams.type == WindowManager.LayoutParams.TYPE_BASE_APPLICATION

        val windowToken: IBinder?
            get() = layoutParams.token

        fun context(): Context {
            return view.context
        }

    } //endregion
}