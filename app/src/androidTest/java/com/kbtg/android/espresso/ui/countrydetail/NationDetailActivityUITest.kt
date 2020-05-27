package com.kbtg.android.espresso.ui.countrydetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.base.BaseMockService
import com.kbtg.android.espresso.base.CommonViewAction
import com.kbtg.android.espresso.capturescreenshot.ScreenShotUtil
import com.kbtg.android.espresso.ui.countrydetail.dispatcher.NationDetailActivityDispatcher
import okhttp3.mockwebserver.Dispatcher
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NationDetailActivityUITest : BaseMockService() {

    //2 command lines to run compare screenshot
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.ui.countrydetail.NationDetailActivityUITest
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.ui.countrydetail.NationDetailActivityUITest
    override fun initDispatcher(): Dispatcher = NationDetailActivityDispatcher()

    @Test
    fun verify_country_detail_activity() {
        //NationListActivity
        val rcvCovidSummaryData = CommonViewAction.getView(R.id.rcvCovidSummaryData)

        CommonViewAction.waitViewVisible(rcvCovidSummaryData) {
            //wait for all items are shown on recyclerview
            Thread.sleep(1000)
            ScreenShotUtil.captureScreen("NationListActivity")
        }

        CommonViewAction.performViewVisible(
            rcvCovidSummaryData, CommonViewAction.scrollRecyclerviewToPosition(9)
        )

        //wait for recyclerView scroll to position 9
        Thread.sleep(1000)
        ScreenShotUtil.captureScreen("NationListActivity-scrolled")

        CommonViewAction.performClickOnRecyclerView(rcvCovidSummaryData, 8)


        val rcvCountryDetailByDate = CommonViewAction.getView(R.id.rcvCountryDetailByDate)
        CommonViewAction.waitViewVisible(rcvCountryDetailByDate) {
            //wait for all items are shown on recyclerview
            Thread.sleep(1000)
            ScreenShotUtil.captureScreen("CountryDetailActivity")
        }

        CommonViewAction.performViewVisible(
            rcvCountryDetailByDate, CommonViewAction.scrollRecyclerviewToPosition(120)
        )

        //wait for recyclerView scroll to latest position
        Thread.sleep(4000)
        ScreenShotUtil.captureScreen("CountryDetailActivity-scrolled")
    }
}