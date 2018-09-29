package com.matusnao.remotest.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.matusnao.remotest.R
import com.matusnao.remotest.preference.ConstValues
import com.matusnao.remotest.preference.PreferenceUtils
import android.widget.FrameLayout


/**
 * Created by DevUser on 2018/09/29.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = if (isRemoTokenExisit()) {
            // Remoのトークンが登録されていればRemo操作画面
            Intent(this, RemoActivity::class.java)

        } else {
            // Remoのトークンが登録されていなければ認証
            Intent(this, CertificationActivity::class.java)
        }

        startActivity(intent)
    }

    private fun isRemoTokenExisit(): Boolean = PreferenceUtils.isExist(this, ConstValues.PREF_KEY_REMO_TOKEN)
}