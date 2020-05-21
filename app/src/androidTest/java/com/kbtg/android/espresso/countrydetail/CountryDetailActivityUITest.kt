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
    //./gradlew executeScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.countrydetail.CountryDetailActivityUITest
    //./gradlew executeScreenshotTests -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.countrydetail.CountryDetailActivityUITest
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
            //wait for all items are shown on recyclerview
            Thread.sleep(3000)
            ScreenShotUtil.captureScreen("Page3Activity")
        }

        CommonViewAction.performViewVisible(
            rcvCovidSummaryData, CommonViewAction.scrollRecyclerviewToPosition(167)
        )

        CommonViewAction.performClickOnRecyclerView(rcvCovidSummaryData, 167)


        CommonViewAction.waitViewVisible(CommonViewAction.getView(R.id.rcvCountryDetailByDate)) {
            //wait for all items are shown on recyclerview
            Thread.sleep(3000)
            ScreenShotUtil.captureScreen("CountryDetailActivity")
        }
    }
}