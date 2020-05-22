package com.kbtg.android.espresso.base

import android.util.Log
import okhttp3.mockwebserver.MockResponse
import org.json.JSONException
import java.io.InputStream

object DispatcherUtil {
    private const val RESPONSE_CODE_SUCCESS = 200
    private const val EMPTY_JSON = "{}"

    fun getMockResponse(jsonPath: String): MockResponse {
        val mock = MockResponse()
        try {
            return mock.setResponseCode(RESPONSE_CODE_SUCCESS)
                .setBody(readJsonFileToString(jsonPath))
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d(
                "LOG",
                "DispatcherUtil | getMockResponse() | jsonPath: $jsonPath | error: ${e.message}"
            )
            return getEmptyResponse()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(
                "LOG",
                "DispatcherUtil | getMockResponse() | jsonPath: $jsonPath | error: ${e.message}"
            )
            return getEmptyResponse()
        }
    }

    fun readJsonFileToString(jsonFile: String): String {
        val json: InputStream = this.javaClass.classLoader!!.getResourceAsStream(jsonFile)
        return convertStreamToString(json)
    }

    private fun convertStreamToString(`is`: InputStream): String {
        val s = java.util.Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    fun getEmptyResponse(): MockResponse =
        MockResponse().setResponseCode(RESPONSE_CODE_SUCCESS).setBody(EMPTY_JSON)
}