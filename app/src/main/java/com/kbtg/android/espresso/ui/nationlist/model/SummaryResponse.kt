package com.kbtg.android.espresso.ui.nationlist.model

data class SummaryResponse(
    val Countries: List<Country>,
    val Date: String,
    val Global: Global
)