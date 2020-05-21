package com.kbtg.android.espresso.countrydetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.base.BaseMockService
import com.kbtg.android.espresso.base.CommonViewAction
import com.kbtg.android.espresso.capturescreenshot.ScreenShotUtil
import com.kbtg.android.espresso.countrydetail.dispatcher.CountryDetailActivityDispatcher
import okhttp3.mockwebserver.Dispatcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDetailActivityUITest : BaseMockService() {

    override fun initDispatcher(): Dispatcher = CountryDetailActivityDispatcher()

    @Test
    fun verify_country_detail_activity() {

        //MainActivity
        val recyclerView = CommonViewAction.getView(R.id.recyclerView)

        CommonViewAction.waitViewVisible(recyclerView) {
            ScreenShotUtil.captureScreen("MainActivity")
        }

        //Click on first item in MainActivity
        CommonViewAction.performViewVisible(
            recyclerView,
            CommonViewAction.clickChildViewInRecyclerviewAtPosition(0, R.id.btnItem)
        )

        //Page3Activity
        val rcvCovidSummaryData = CommonViewAction.getView(R.id.rcvCovidSummaryData)

        CommonViewAction.waitViewVisible(rcvCovidSummaryData) {
            ScreenShotUtil.captureScreen("Page3Activity")
        }

        CommonViewAction.performViewVisible(
            rcvCovidSummaryData, CommonViewAction.scrollRecyclerviewToPosition(167)
        )

        CommonViewAction.performClickOnRecyclerView(rcvCovidSummaryData, 167)


        CommonViewAction.waitViewVisible(CommonViewAction.getView(R.id.rcvCountryDetailByDate)) {
            ScreenShotUtil.captureScreen("CountryDetailActivity")
        }

        //sleep 4s before finish
        Thread.sleep(4000)
    }
}