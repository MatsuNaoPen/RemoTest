package com.matusnao.remotest.view.VCInterface

import com.matusnao.remotest.data.SignalListData

/**
 * Created by DevUser on 2018/06/10.
 */
interface RemoCallback {
    fun updateResultArea(str: String)

    fun showSignalArea(data: SignalListData)
}