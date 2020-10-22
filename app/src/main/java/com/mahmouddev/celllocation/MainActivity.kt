/*
 * Copyright (c) 10/22/20 11:38 PM. Created by Mahmoud-dev
 */

package com.mahmouddev.celllocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mahmouddev.celllocation.dbUtil.Status
import com.mahmouddev.celllocation.dbUtil.ViewModelFactory
import com.mahmouddev.celllocation.retrofit.ApiHelperImpl
import com.mahmouddev.celllocation.retrofit.RetrofitBuilder
import com.mahmouddev.celllocation.util.Helper
import com.mahmouddev.celllocation.util.Helper.REQUEST_CODE
import com.mahmouddev.celllocation.viewmodel.CellViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var viewModel: CellViewModel
    var accuracy: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        initViewModel()
        setupObserver()
    }

    private fun initViewModel() {

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService), application)
        ).get(CellViewModel::class.java)
    }


    @SuppressLint("SetTextI18n")
    private fun setupObserver() {

        viewModel.getLocation().observe(this,
            Observer {
                when (it.status) {
                    Status.SUCCESS -> {

                        it.data?.let { cellInfo ->
                            Log.e(TAG, "setupObserver: $cellInfo")
                            if (cellInfo.accuracy > accuracy) {
                                accuracy = cellInfo.accuracy
                                tvAddress.text =
                                    "highest accuracy: ${cellInfo.accuracy}, \n \n address: ${cellInfo.address} \n \n " +
                                            "lat: ${cellInfo.lat} \n lng: ${cellInfo.lon}"
                            }
                        }
                    }
                    Status.LOADING -> {
                        // Handle Loading

                    }
                    Status.ERROR -> {
                        //Handle Error
                        Log.e(TAG, "setupObserver: " + it.message)
                    }
                }

            }
        )
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE)
            requestCellLocation()

    }

    private fun requestCellLocation() {
        val allCellInfo = Helper.getCurrentCellInfo(this)
        allCellInfo.forEachIndexed { index, cellInfo ->
            viewModel.location(cellInfo)
            Log.e(TAG, "cellInfo: $cellInfo ")
        }
    }
}