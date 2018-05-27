package com.matusnao.remotest.view.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matusnao.remotest.R

/**
 * Created by DevUser on 2018/05/05.
 */
class SignalSettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        return inflater.inflate(R.layout.fragment_signal_setting, container, false)
    }
}