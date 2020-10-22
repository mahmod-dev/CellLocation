package com.mahmouddev.celllocation.retrofit

import com.mahmouddev.celllocation.model.CellInfoBody
import com.mahmouddev.celllocation.model.CellLocationResponse
import retrofit2.http.*

interface ApiService {

    @POST("process.php")
    suspend fun getLocationByCellInfo(@Body cellInfoBody: CellInfoBody): CellLocationResponse

}