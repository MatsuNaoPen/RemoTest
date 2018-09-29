package com.matusnao.remotest.view.vcInterface

import com.matusnao.remotest.data.SignalListData

/**
 * Created by DevUser on 2018/06/10.
 */
interface RemoCallback {
    fun updateLogArea(logStr: String)

    fun updateResultArea(result: String)

    fun showSignalArea(data: SignalListData)
}