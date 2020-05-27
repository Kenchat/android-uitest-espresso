package com.kbtg.android.espresso.countrydetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.base.BaseMockService
import com.kbtg.android.espresso.base.CommonViewAction
import com.kbtg.android.espresso.capturescreenshot.ScreenShotUtil
import com.kbtg.android.espresso.countrydetail.dispatcher.CountryDetailActivityDispatcher
import okhttp3.mockwebserver.Dispatcher
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CountryDetailActivityUITest : BaseMockService() {

    //2 commands to run compare screenshot
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.countrydetail.CountryDetailActivityUITest
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.countrydetail.CountryDetailActivityUITest
    override fun initDispatcher(): Dispatcher = CountryDetailActivityDispatcher()

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
            rcvCovidSummaryData, CommonViewAction.scrollRecyclerviewToPosition(10)
        )

        Thread.sleep(4000)
        ScreenShotUtil.captureScreen("NationListActivity-scrolled")

        CommonViewAction.performClickOnRecyclerView(rcvCovidSummaryData, 9)


        val rcvCountryDetailByDate = CommonViewAction.getView(R.id.rcvCountryDetailByDate)
        CommonViewAction.waitViewVisible(rcvCountryDetailByDate) {
            //wait for all items are shown on recyclerview
            Thread.sleep(2000)
            ScreenShotUtil.captureScreen("CountryDetailActivity")
        }

        CommonViewAction.performViewVisible(
            rcvCountryDetailByDate, CommonViewAction.scrollRecyclerviewToPosition(120)
        )

        Thread.sleep(4000)
        ScreenShotUtil.captureScreen("CountryDetailActivity-scrolled")
    }
}