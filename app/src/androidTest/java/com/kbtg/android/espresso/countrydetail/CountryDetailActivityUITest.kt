package com.kbtg.android.espresso.countrydetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.base.BaseMockService
import com.kbtg.android.espresso.base.CommonViewAction
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDetailActivityUITest : BaseMockService() {

    @Test
    fun verify_country_detail_activity() {
        //Click on first item in MainActivity
        CommonViewAction.performViewVisible(
            CommonViewAction.getView(
                R.id.recyclerView
            ),
            CommonViewAction.clickChildViewInRecyclerviewAtPosition(0, R.id.btnItem)
        )

        //Page3Activity
        val recyclerView = CommonViewAction.getView(R.id.rcvCovidSummaryData)

        CommonViewAction.performViewVisible(
            recyclerView, CommonViewAction.scrollRecyclerviewToPosition(167)
        )
        //sleep 1s before start new activity
        Thread.sleep(4000)

        CommonViewAction.performClickOnRecyclerView(recyclerView, 167)

        //sleep es before finish
        Thread.sleep(4000)
    }
}