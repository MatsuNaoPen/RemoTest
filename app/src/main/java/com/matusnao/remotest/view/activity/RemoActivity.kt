package com.matusnao.remotest.view.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.android.gms.maps.MapFragment
import com.matusnao.household.connection.api.service.BaseService
import com.matusnao.remotest.R
import com.matusnao.remotest.connection.request.RequestGetAppliances
import com.matusnao.remotest.connection.request.RequestGetDevices
import com.matusnao.remotest.connection.request.RequestGetUsersMe
import com.matusnao.remotest.connection.service.GetAppliancesService
import com.matusnao.remotest.connection.service.GetDeviceService
import com.matusnao.remotest.connection.service.GetUserEventService
import com.matusnao.remotest.data.SignalListData
import com.matusnao.remotest.enums.EventEnum
import com.matusnao.remotest.view.VCInterface.MainCallback
import com.matusnao.remotest.view.fragment.SignalListFragment
import kotlinx.android.synthetic.main.activity_remo.*

/**
 * Created by DevUser on 2018/05/04.
 */

class RemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remo)
        setUpMenuButton()
    }

    private fun setUpMenuButton() {
        for (event in EventEnum.values()) {
            val buttonView: Button = this.layoutInflater.inflate(R.layout.remo_menu_button_layout, null) as Button
            buttonView.text = event.text
            buttonView.setOnClickListener {
                val retrofit = BaseService.getRetrofit(this)

                when (event) {
                    EventEnum.GET_USER_EVENT -> {
                        val request = retrofit.create(RequestGetUsersMe::class.java).request()
                        val service = GetUserEventService(getCallback())
                        request.enqueue(service.getService())
                    }
                    EventEnum.GET_DEVICES -> {
                        val request = retrofit.create(RequestGetDevices::class.java).request()
                        val service = GetDeviceService(getCallback())
                        request.enqueue(service.getService())
                    }
                    EventEnum.GET_APPLIANCES -> {
                        val request = retrofit.create(RequestGetAppliances::class.java).request()
                        val service = GetAppliancesService(getCallback())
                        request.enqueue(service.getService())
                    }
                    EventEnum.POST_SIGNALS_XXX_SEND -> {
                        // NOP
                    }
                }
            }
            menu_layout.addView(buttonView)
        }
    }

    private fun getCallback(): MainCallback {
        return object : MainCallback {
            override fun showSignalArea(data: SignalListData) {
                signal_setting_list.setOnClickListener {
                    showSignalSetting(true, data)
                }

                signal_setting_setting.setOnClickListener {
                    showSignalSetting(false, data)
                }
            }

            override fun updateResultArea(str: String) {
                this@RemoActivity.updateResultArea(str)
            }
        }
    }

    fun updateResultArea(resultText: String) {
        result_area.text = resultText
    }

    fun showSignalSetting(isList: Boolean, data: SignalListData) {
        val fragment: Fragment = if (isList) {
            SignalListFragment()
        } else {
            MapFragment.newInstance()
        }

        val bundle = Bundle()
        bundle.putSerializable("SIGNALLIST", data)
        fragment.arguments = bundle

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_signal_area, fragment)
        transaction.commit()
    }
}