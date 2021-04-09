package com.xx.mvvm.ui.activity

import androidx.activity.viewModels
import com.drake.statusbar.immersive
import com.xx.mvvm.ui.model.MainViewModel
import com.xx.mvvm.R
import com.xx.mvvm.base.BaseActivity
import com.xx.mvvm.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val viewModel: MainViewModel by viewModels()


    override fun initView() {
        immersive(darkMode = true)


    }

    override fun initData() {

    }
}