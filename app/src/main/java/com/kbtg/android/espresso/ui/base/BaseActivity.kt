package com.kbtg.android.espresso.ui.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.kbtg.android.espresso.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector, IBaseView {

    private var mProgressDialog: ProgressDialog? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(setLayout())
        initialzeProgressDialoge()
        init(savedInstanceState)
    }

    private fun initialzeProgressDialoge() {
        mProgressDialog = ProgressDialog(this).apply {
            isIndeterminate = true
            setCancelable(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
        dismissProgress()
        mProgressDialog = null
    }

    @LayoutRes
    abstract fun setLayout(): Int
    abstract fun init(savedInstanceState: Bundle?)
    abstract fun stopScreen()

    private fun showProgress(msgResId: Int,
                             keyListener: DialogInterface.OnKeyListener?) {
        mProgressDialog?.run {
            if (isFinishing)
                return

            if (isShowing) {
                return
            }

            if (msgResId != 0) {
                setMessage(resources.getString(msgResId))
            }

            if (keyListener != null) {
                setOnKeyListener(keyListener)

            } else {
                setCancelable(false)
            }
            show()
        }
    }

    private fun dismissProgress() {
        mProgressDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }

    override fun hideLoading() {
        dismissProgress()
    }

    override fun showLoading() {
        showProgress(R.string.loading, null)
    }

    override fun loadError(e: Throwable) {
        showHttpError(e)
    }

    override fun loadError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showHttpError(e: Throwable) {
        e.localizedMessage?.let { loadError(it) }
    }

    override fun onStop() {
        super.onStop()
        stopScreen()
    }
}