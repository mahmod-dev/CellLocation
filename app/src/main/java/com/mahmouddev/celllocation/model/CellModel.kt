/*
 * Copyright (c) 10/22/20 11:37 PM. Created by Mahmoud-dev
 */

package com.mahmouddev.celllocation.model

//site: https://opencellid.org
// documentation: https://unwiredlabs.com/api#documentation

data class CellLocationResponse(
    val accuracy: Int,
    val address: String,
    val balance: Int,
    val lat: Double,
    val lon: Double,
    val status: String

)

data class CellInfoBody(
    val token: String = "YOUR API KEY", // Login into this site https://opencellid.org  will let you 100 request every day
    var radio: String? = null,
    var mcc: Int? = null,
    var mnc: Int? = null,
    var cells: List<Cell> = emptyList(),
    val address: Int = 1,
)

data class Cell(
    val lac: Int,
    val cid: Int,
    val psc: Int? = null
)

object RadioType {
    const val GSM = "gsm"
    const val CDMA = "cdma"
    const val LTE = "lte"
    const val UMTS = "umts"
}