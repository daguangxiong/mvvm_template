
package com.xx.mvvm.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
    Fragment(contentLayoutId), OnClickListener {

    lateinit var binding: B

    protected abstract fun initView()
    protected abstract fun initData()
    override fun onClick(v: View) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        val engineActivity = (requireActivity() as? BaseActivity<*>)
        engineActivity?.onBackPressed(this::onBackPressed)
        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("日志", "初始化失败")
            e.printStackTrace()
        }
    }
    /*
        * fragment中的返回键
        *
        * 默认返回flase，交给Activity处理
        * 返回true：执行fragment中需要执行的逻辑
        * 返回false：执行activity中的 onBackPressed
        * */
    open fun onBackPressed(): Boolean {
        return false
    }

}
