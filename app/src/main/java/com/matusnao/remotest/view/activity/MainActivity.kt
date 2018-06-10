package com.matusnao.remotest.view.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.google.android.gms.maps.MapFragment
import com.matusnao.household.connection.api.service.BaseService
import com.matusnao.remotest.R
import com.matusnao.remotest.connection.request.RequestGetAppliances
import com.matusnao.remotest.connection.request.RequestGetDevices
import com.matusnao.remotest.connection.request.RequestGetUsersMe
import com.matusnao.remotest.connection.response.ResponseGetAppliances
import com.matusnao.remotest.connection.response.ResponseGetUsersMe
import com.matusnao.remotest.connection.service.GetDeviceService
import com.matusnao.remotest.connection.service.GetDeviceServiceOld
import com.matusnao.remotest.data.SignalListData
import com.matusnao.remotest.data.Signals
import com.matusnao.remotest.enums.EventEnum
import com.matusnao.remotest.view.VCInterface.DeviceUpdateEvent
import com.matusnao.remotest.view.fragment.SignalListFragment
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by DevUser on 2018/05/04.
 */

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMenuButton()
    }

    override fun onResume() {
        super.onResume()
        updateResultArea("")
    }

    private fun setUpMenuButton() {
        for (event in EventEnum.values()) {
            val buttonView: Button = this.layoutInflater.inflate(R.layout.menu_button_layout, null) as Button
            buttonView.text = event.text
            buttonView.setOnClickListener {
                val retrofit = BaseService.getRetrofit()

                when (event) {
                    EventEnum.GET_USER_EVENT -> {
                        val request = retrofit.create(RequestGetUsersMe::class.java).request()
                        request.enqueue(object : Callback<ResponseGetUsersMe> {
                            override fun onResponse(call: Call<ResponseGetUsersMe>?, responseGet: Response<ResponseGetUsersMe>?) {
                                when (responseGet!!.code()) {
                                    200 -> {
                                        Log.d(TAG, "on Response:" + responseGet.toString())
                                        val result: ResponseGetUsersMe = responseGet.body()!!
                                        updateResultArea(result.toString())
                                    }
                                    else ->
                                        Log.d(TAG, "on Response:" + responseGet.errorBody()!!.string())
                                }
                            }

                            override fun onFailure(call: Call<ResponseGetUsersMe>?, t: Throwable?) {
                                Log.d(TAG, "on onFailure:" + t.toString())
                                updateResultArea(t.toString())
                            }
                        })
                    }
                    EventEnum.GET_DEVICES -> {
//                        val request = retrofit.create(RequestGetDevices::class.java).request();
//                        val service = GetDeviceService(getDeviceUpdateEvent())
//                        request.enqueue(service.getService())
                        val request = GetDeviceServiceOld(getDeviceUpdateEvent())
                        request.getService().execute()
                    }
                    EventEnum.GET_APPLIANCES -> {
                        setGetApplicationsEvent(retrofit)
                    }
                }
            }
            menu_layout.addView(buttonView)
        }
    }

    private fun setGetApplicationsEvent(retrofit: Retrofit) {
        val request = retrofit.create(RequestGetAppliances::class.java).request()
        request.enqueue(object : Callback<List<ResponseGetAppliances>> {
            override fun onResponse(call: Call<List<ResponseGetAppliances>>?,
                                    responseGet: Response<List<ResponseGetAppliances>>?) {
                when (responseGet!!.code()) {
                    200 -> {
                        Log.d(TAG, "on Response:" + responseGet.toString())
                        val result: List<ResponseGetAppliances> = responseGet.body()!!
                        Log.d(TAG, result.toString())
                        showSignalArea(result)
                        updateResultArea(result.toString())
                    }
                    else ->
                        Log.d(TAG, "on Response:" + responseGet.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<List<ResponseGetAppliances>>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                updateResultArea(t.toString())
            }
        })
    }

    fun getDeviceUpdateEvent(): DeviceUpdateEvent {
        return object : DeviceUpdateEvent {
            override fun updateResultArea(str: String) {
                result_area.text = str
            }
        }
    }

    fun updateResultArea(resultText: String) {
        result_area.text = resultText
    }

    fun showSignalArea(result: List<ResponseGetAppliances>) {
        Log.d(TAG, "showSignalArea")

        val converted = getSignalList(result)
        // TODO 毎回セットアップする必要はない
        signal_setting_list.setOnClickListener {
            showSignalSetting(true, converted)
        }

        signal_setting_setting.setOnClickListener {
            showSignalSetting(false, converted)
        }
    }

    fun showSignalSetting(isList: Boolean, data: SignalListData) {
        val fragment: Fragment = if (isList) {
            SignalListFragment()
        } else {
//            SignalSettingFragment()
            MapFragment.newInstance()
        }

        var bundle = Bundle()
        bundle.putSerializable("SIGNALLIST", data)
        fragment.arguments = bundle

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_signal_area, fragment)
        transaction.commit()
    }

    private fun getSignalList(result: List<ResponseGetAppliances>): SignalListData {
        val map: MutableMap<String, List<Signals>> = mutableMapOf()
        for (tmp in result) {
            if (tmp.nickname != null) {
                map.put(tmp.nickname, tmp.signals)
            }
        }
        return SignalListData(map)
    }
}