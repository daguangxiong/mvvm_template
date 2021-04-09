package com.xx.mvvm.const

import com.drake.serialize.serialize.serial

/**
 * 应用配置
 */
object AppConfig {
    var isFirstLaunch: Boolean by serial(default = true) // 是否是第一次启动App
}