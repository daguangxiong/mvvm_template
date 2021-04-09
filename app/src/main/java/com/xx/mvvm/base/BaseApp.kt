package com.xx.mvvm.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.drake.brv.utils.BRV
import com.drake.logcat.LogCat
import com.drake.net.BuildConfig
import com.drake.net.cacheEnabled
import com.drake.net.initNet
import com.drake.net.logEnabled
import com.drake.statelayout.StateConfig
import com.example.mvvm.component.net.GsonConvert
import com.hjq.permissions.XXPermissions
import com.xx.mvvm.utils.AppActivityManager
import com.tencent.mmkv.MMKV
import com.xx.mvvm.BR
import com.xx.mvvm.R


 class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initApp(this)
        initNet("https://wanandroid.com/") {
            converter(GsonConvert()) // 转换器
            cacheEnabled() // 开启缓存
            logEnabled = BuildConfig.DEBUG // 网络日志, 建议安装OkHttpProfile插件查看日志
        }
        MMKV.initialize(this)
        BRV.modelId = BR.viewModel
        LogCat.enabled = BuildConfig.DEBUG

        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading

            setRetryIds(R.id.msg)

            onLoading {
                // 此生命周期可以拿到LoadingLayout创建的视图对象, 可以进行动画设置或点击事件.
            }
        }
        initResource(this)
    }


    companion object {
         lateinit var app: Application

        @JvmStatic
        fun initApp(app: Application) {
            Companion.app = app
            XXPermissions.setScopedStorage(true);
        }

        private fun initResource(app: Application) {
            // 监听所有 Activity 的创建和销毁
                app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                    override fun onActivityPaused(activity: Activity) {
                    }

                    override fun onActivityStarted(activity: Activity) {
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                        AppActivityManager.remove(activity)
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    }

                    override fun onActivityStopped(activity: Activity) {
                    }

                    override fun onActivityCreated(
                        activity: Activity,
                        savedInstanceState: Bundle?
                    ) {
                        AppActivityManager.add(activity)
                    }

                    override fun onActivityResumed(activity: Activity) {
                    }

                })
            }

        @JvmStatic
        fun getInstance(): Application {
            return app
        }
    }
}