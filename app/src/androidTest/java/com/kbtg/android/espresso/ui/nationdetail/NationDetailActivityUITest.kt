package com.kbtg.android.espresso.ui.nationdetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.base.BaseMockService
import com.kbtg.android.espresso.base.CommonViewAction
import com.kbtg.android.espresso.ui.nationdetail.dispatcher.NationDetailActivityDispatcher
import okhttp3.mockwebserver.Dispatcher
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NationDetailActivityUITest : BaseMockService() {

    private val waitForScrolling = 2000L
    private val positionThaiLand = 167
    private val positionEndList = 9

    //2 command lines to run compare screenshot
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.ui.nationdetail.NationDetailActivityUITest
    //./gradlew mockWebServerDebugExecuteScreenshotTests -Pandroid.testInstrumentationRunnerArguments.class=com.kbtg.android.espresso.ui.nationdetail.NationDetailActivityUITest
    override fun initDispatcher(): Dispatcher = NationDetailActivityDispatcher()

    @Test
    fun verify_country_detail_activity() {
        //NationListActivity
        val rcvCovidSummaryData = CommonViewAction.getView(R.id.rcvCovidSummaryData)

        CommonViewAction.performViewVisible(
            rcvCovidSummaryData, CommonViewAction.scrollRecyclerviewToPosition(positionThaiLand)
        )

        CommonViewAction.performClickOnRecyclerView(rcvCovidSummaryData, positionThaiLand)

        //NationDetailActivity
        val rcvCountryDetailByDate = CommonViewAction.getView(R.id.rcvCountryDetailByDate)

        Thread.sleep(waitForScrolling)
        CommonViewAction.performViewVisible(
            rcvCountryDetailByDate, CommonViewAction.scrollRecyclerviewToPosition(positionEndList)
        )
        Thread.sleep(waitForScrolling)
    }
}