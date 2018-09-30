package com.matusnao.remotest.view.activity

import android.support.v4.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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
import com.matusnao.remotest.preference.ConstValues
import com.matusnao.remotest.preference.PreferenceUtils
import com.matusnao.remotest.view.vcInterface.RemoCallback
import com.matusnao.remotest.view.fragment.SignalListFragment
import com.matusnao.remotest.view.fragment.SimpleFragment
import kotlinx.android.synthetic.main.activity_remo.*

/**
 * Created by DevUser on 2018/05/04.
 */

class RemoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remo)
        setUpMenuButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.base_menu, menu)
        menu?.let { initMenu(menu) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.show_certification_activity ->
                    startCertificationActivity()
                R.id.swich_debug_area ->
                    switchLogState(item)
                R.id.menu_api_mock ->
                    switchApiState(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initMenu(menu: Menu) {
        setLogVisibility(PreferenceUtils.getBoolean(this, ConstValues.PREF_KEY_REMO_LOG_FLAG),
                menu.findItem(R.id.swich_debug_area))
        setApiMock(PreferenceUtils.getBoolean(this, ConstValues.PREF_KEY_REMO_API_DEBUG_FLAG)
                , menu.findItem(R.id.menu_api_mock))
    }

    private fun switchApiState(item: MenuItem) {
        val key = getKey(item)
        key?.let {
            val showLogFlag = PreferenceUtils.getBoolean(this, it)
            // TODO Mock差し替え
            updatePrefAndDisp(showLogFlag, item)
        }
    }

    private fun switchLogState(item: MenuItem) {
        val key = getKey(item)
        key?.let {
            val showLogFlag = PreferenceUtils.getBoolean(this, it)
            setLogVisibility(!showLogFlag, item)
        }
    }

    private fun setLogVisibility(showLogFlag: Boolean, item: MenuItem) {
        if (!showLogFlag) {
            log_area.visibility = View.GONE
        } else {
            log_area.visibility = View.VISIBLE
        }

        updatePrefAndDisp(showLogFlag, item)
    }

    private fun setApiMock(showLogFlag: Boolean, item: MenuItem) {
        // TODO Mock差し替え
        updatePrefAndDisp(showLogFlag, item)
    }

    private fun updatePrefAndDisp(showLogFlag: Boolean, item: MenuItem) {
        val key = getKey(item)
        key?.let {
            PreferenceUtils.putBoolean(this, key, showLogFlag)
        }
        item.title = getMenuTitle(item, showLogFlag)
    }

    private fun getKey(item: MenuItem): String? = when (item.itemId) {
        R.id.swich_debug_area ->
            ConstValues.PREF_KEY_REMO_LOG_FLAG
        R.id.menu_api_mock ->
            ConstValues.PREF_KEY_REMO_API_DEBUG_FLAG
        else ->
            null
    }

    private fun getMenuTitle(item: MenuItem, flag: Boolean): String =
            when (item.itemId) {
                R.id.swich_debug_area ->
                    if (flag)
                        getString(R.string.menu_switch_log_off)
                    else
                        getString(R.string.menu_switch_log_on)
                R.id.menu_api_mock ->
                    if (flag)
                        getString(R.string.menu_api_mock_off)
                    else
                        getString(R.string.menu_api_mock_on)
                else -> ""
            }

    private fun startCertificationActivity() {
        val intent = Intent(this, CertificationActivity::class.java)
        startActivity(intent)
    }

    private fun setUpMenuButton() {
        for (event in EventEnum.values()) {
            val buttonView: Button = this.layoutInflater.inflate(R.layout.remo_menu_button_layout, null) as Button
            buttonView.text = event.text
            buttonView.setOnClickListener {
                val retrofit = BaseService.getRetrofit(this)
                startProgress()

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

    private fun getCallback(): RemoCallback {
        return object : RemoCallback {
            override fun updateResultArea(result: String) {
                showResultArea(result)
            }

            override fun showSignalArea(data: SignalListData) {
                showSignalSetting(data)
            }

            override fun updateLogArea(logStr: String) {
                this@RemoActivity.updateLogArea(logStr)
            }
        }
    }

    fun updateLogArea(resultText: String) {
        log_area.text = resultText
    }

    private fun showResultArea(result: String) {
        val fragment = SimpleFragment()
        val bundle = Bundle()
        bundle.putString(ConstValues.REMO_KEY_SIMPLE_RESULT, result)
        fragment.arguments = bundle

        updateFragment(fragment)
    }

    fun showSignalSetting(data: SignalListData) {
        val fragment = SignalListFragment()
        val bundle = Bundle()
        bundle.putSerializable(ConstValues.REMO_KEY_SIGNALLIST, data)
        fragment.arguments = bundle

        updateFragment(fragment)
    }

    private fun updateFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_signal_area, fragment)
        transaction.commit()
        disableProgress()
    }
}