/*
 * Copyright (c) 10/22/20 11:38 PM. Created by Mahmoud-dev
 */

package com.mahmouddev.celllocation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.mahmouddev.celllocation.model.Cell
import com.mahmouddev.celllocation.model.CellInfoBody
import com.mahmouddev.celllocation.model.RadioType

object Helper {
    const val REQUEST_CODE = 4040

    fun getCurrentCellInfo(context: Context): List<CellInfoBody> {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val allCellInfo = telephonyManager.allCellInfo

            return allCellInfo.mapNotNull {
                when (it) {
                    is CellInfoGsm -> getCellInfo(it)
                    is CellInfoWcdma -> getCellInfo(it)
                    is CellInfoLte -> getCellInfo(it)
                    else -> null
                }
            }
        } else
            return emptyList()

    }

    private fun getCellInfo(info: CellInfoGsm): CellInfoBody {
        val cellInfo = CellInfoBody()
        cellInfo.radio = RadioType.GSM

        info.cellIdentity.let {
            val (mcc, mnc) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Pair(it.mccString?.toInt() ?: 0, it.mncString?.toInt() ?: 0)
            } else {
                Pair(it.mcc, it.mnc)
            }
            cellInfo.mcc = mcc
            cellInfo.mnc = mnc
            cellInfo.cells = listOf(Cell(it.lac, it.cid, it.psc))
        }

        return cellInfo
    }

    private fun getCellInfo(info: CellInfoWcdma): CellInfoBody {
        val cellInfo = CellInfoBody()
        cellInfo.radio = RadioType.CDMA

        info.cellIdentity.let {
            val (mcc, mnc) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Pair(it.mccString?.toInt() ?: 0, it.mncString?.toInt() ?: 0)
            } else {
                Pair(it.mcc, it.mnc)
            }
            cellInfo.mcc = mcc
            cellInfo.mnc = mnc
            cellInfo.cells = listOf(Cell(it.lac, it.cid, it.psc))
        }

        return cellInfo
    }

    private fun getCellInfo(info: CellInfoLte): CellInfoBody {
        val cellInfo = CellInfoBody()

        cellInfo.radio = RadioType.LTE

        info.cellIdentity.let {
            val (mcc, mnc) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Pair(it.mccString?.toInt() ?: 0, it.mncString?.toInt() ?: 0)
            } else {
                Pair(it.mcc, it.mnc)
            }
            cellInfo.mcc = mcc
            cellInfo.mnc = mnc
            cellInfo.cells = listOf(Cell(it.tac, it.ci, it.pci))
        }

        return cellInfo
    }


}