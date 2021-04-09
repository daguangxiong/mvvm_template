package com.xx.mvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.intent.openActivity
import com.xx.mvvm.const.AppConfig
import com.xx.mvvm.BuildConfig
import com.xx.mvvm.R
import com.xx.mvvm.ui.activity.MainActivity

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (BuildConfig.DEBUG) {
            intoHome()
        } else { window.decorView.postDelayed({ intoHome() }, 1500)
        }
    }

    private fun intoHome() {
        openActivity<MainActivity>()
        AppConfig.isFirstLaunch = false
        finish()
    }
}