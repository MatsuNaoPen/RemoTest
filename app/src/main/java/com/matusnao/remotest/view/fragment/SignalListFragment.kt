package com.matusnao.remotest.view.fragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.matusnao.household.connection.api.service.BaseService
import com.matusnao.remotest.R
import com.matusnao.remotest.connection.request.RequestPostSignalsXXXSend
import com.matusnao.remotest.connection.response.ResponsePostSignalsXXXSend
import com.matusnao.remotest.data.SignalListData
import com.matusnao.remotest.preference.ConstValues
import com.matusnao.remotest.view.activity.RemoActivity
import kotlinx.android.synthetic.main.fragment_signal_list.*
import kotlinx.android.synthetic.main.signal_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DevUser on 2018/05/05.
 */
class SignalListFragment : Fragment() {
    lateinit var inflater: LayoutInflater
    val TAG = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.inflater = inflater
        return inflater.inflate(R.layout.fragment_signal_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signalListData = arguments.getSerializable(ConstValues.REMO_KEY_SIGNALLIST) as SignalListData

        for ((key, data) in signalListData.data) {
            val modelLayout = inflater.inflate(R.layout.signal_item, null) as LinearLayout
            val modelSignalLayout = modelLayout.findViewById<LinearLayout>(R.id.signal_item_signal_layout)
            modelLayout.signal_item_model_text.text = key

            modelSignalLayout.visibility = View.GONE
            modelLayout.setOnClickListener {
                modelSignalLayout.let {
                    when (it.visibility) {
                        View.VISIBLE -> {
                            it.visibility = View.GONE
                        }
                        View.GONE -> {
                            it.visibility = View.VISIBLE
                        }
                    }
                }
            }

            for (tmpData in data) {
                val signalView = inflater.inflate(R.layout.signal_list_item, null)
                val signalName = signalView.findViewById<TextView>(R.id.signal_item_text)
                signalName.text = tmpData.name
                signalView.setOnClickListener {
                    getSignalOnClickEvent(tmpData.id)
                }
                modelSignalLayout.addView(signalView)
            }
            fragment_signal_list.addView(modelLayout)
        }
    }

    private fun getSignalOnClickEvent(signalId: String) {
        val retrofit = BaseService.getRetrofit(context)
        val request = retrofit.create(RequestPostSignalsXXXSend::class.java).request(signalId)
        request.enqueue(object : Callback<ResponsePostSignalsXXXSend> {
            override fun onResponse(call: Call<ResponsePostSignalsXXXSend>?, response: Response<ResponsePostSignalsXXXSend>?) {
                when (response?.code()) {
                    400, 401, 404 -> {
                        response.errorBody()?.let {
                            Log.d(TAG, "on Response:" + it.string())
                            setLogArea("Failure")
                        }
                    }
                    else -> {
                        Log.d(TAG, "on Response:" + response.toString())
                        setLogArea("Success")
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePostSignalsXXXSend>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                setLogArea("Failure")
            }
        })
    }

    private fun setLogArea(result: String) {
        try {
            val mainActivity = activity as RemoActivity
            mainActivity.updateLogArea(result)
        } catch (e: Exception) {

        }
    }
}
