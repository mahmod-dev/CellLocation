package com.mahmouddev.celllocation.retrofit

import com.mahmouddev.celllocation.model.CellInfoBody
import com.mahmouddev.celllocation.model.CellLocationResponse


interface ApiHelper {

    suspend fun getLocationByCellInfo(cellInfoBody: CellInfoBody): CellLocationResponse

}