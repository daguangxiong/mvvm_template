
package com.xx.mvvm.base

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<B : ViewDataBinding>(context: Context, @StyleRes themeResId: Int = 0) :
    Dialog(context, themeResId), OnClickListener {

    lateinit var binding: B

    override fun setContentView(layoutResID: Int) {
        binding = DataBindingUtil.inflate(layoutInflater, layoutResID, null, false)
        setContentView(binding.root)
        init()
    }

    open fun init() {
        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("日志", "初始化失败")
            e.printStackTrace()
        }
    }

    protected abstract fun initView()
    protected abstract fun initData()
    override fun onClick(v: View) {}
}
