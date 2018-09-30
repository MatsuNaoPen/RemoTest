package com.matusnao.remotest.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matusnao.remotest.R
import com.matusnao.remotest.preference.ConstValues
import kotlinx.android.synthetic.main.fragment_simple.*

/**
 * Created by DevUser on 2018/09/30.
 */
class SimpleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_simple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getString(ConstValues.REMO_KEY_SIMPLE_RESULT)
        fragment_simple_text_area.text = result
    }
}