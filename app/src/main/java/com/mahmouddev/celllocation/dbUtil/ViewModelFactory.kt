package com.mahmouddev.celllocation.dbUtil

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmouddev.celllocation.retrofit.ApiHelper
import com.mahmouddev.celllocation.viewmodel.CellViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CellViewModel::class.java)) {
            return CellViewModel(apiHelper, application) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }


}