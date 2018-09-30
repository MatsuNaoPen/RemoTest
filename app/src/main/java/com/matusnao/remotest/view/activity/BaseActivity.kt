package com.matusnao.remotest.view.activity

import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_remo.*

/**
 * Created by DevUser on 2018/09/30.
 */
open class BaseActivity:AppCompatActivity(){
    fun startProgress() {
        remo_progress.visibility = View.VISIBLE
    }

    fun disableProgress(){
        remo_progress.visibility = View.GONE
    }
}