package com.mahmouddev.celllocation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmouddev.celllocation.retrofit.ApiHelper
import com.mahmouddev.celllocation.dbUtil.Resource
import com.mahmouddev.celllocation.model.CellInfoBody
import com.mahmouddev.celllocation.model.CellLocationResponse
import kotlinx.coroutines.launch

class CellViewModel(private val apiHelper: ApiHelper, var context: Context) : ViewModel() {
    private val TAG = "CellViewModel"
    private val location = MutableLiveData<Resource<CellLocationResponse>>()


    fun location(user: CellInfoBody) {
        viewModelScope.launch {
            location.postValue(Resource.loading(null))
            try {
                val usersFromApi = apiHelper.getLocationByCellInfo(user)

                if (usersFromApi.status == "ok")
                    location.postValue(Resource.success(usersFromApi))
                else {
                    location.postValue(Resource.error("Something Went Wrong", null))
                }


            } catch (e: Exception) {

                Log.e(TAG, "location exception: ${e.message}")
            }
        }
    }


    fun getLocation(): LiveData<Resource<CellLocationResponse>> {
        return location
    }


}