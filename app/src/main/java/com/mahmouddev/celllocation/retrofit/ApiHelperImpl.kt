package com.mahmouddev.celllocation.retrofit

import com.mahmouddev.celllocation.model.CellInfoBody
import com.mahmouddev.celllocation.model.CellLocationResponse

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getLocationByCellInfo(cellInfoBody: CellInfoBody): CellLocationResponse {
        return apiService.getLocationByCellInfo(cellInfoBody)
    }

}