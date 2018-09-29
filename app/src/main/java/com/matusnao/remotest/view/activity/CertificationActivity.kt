package com.matusnao.remotest.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.matusnao.remotest.R
import com.matusnao.remotest.preference.ConstValues
import com.matusnao.remotest.preference.PreferenceUtils
import kotlinx.android.synthetic.main.activity_certification.*

/**
 * 認証画面
 *
 * Created by DevUser on 2018/09/29.
 */
class CertificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init() {
        certification_start_button.setOnClickListener {
            startActivity(getCertificationIntent())
        }

        certification_save_button.setOnClickListener {
            val token = input_remo_token.text.toString()
            if (!token.isEmpty()) {
                PreferenceUtils.putString(this, ConstValues.PREF_KEY_REMO_TOKEN, token)
                certification_token_error.visibility = View.GONE
                val intent = Intent(this, RemoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK;Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                certification_token_error.visibility = View.VISIBLE
            }
        }
    }

    private fun getCertificationIntent(): Intent {
        val uri = Uri.parse(getString(R.string.remo_certification_url))
        return Intent(Intent.ACTION_VIEW, uri)
    }

}