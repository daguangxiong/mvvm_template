/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION", "unused")

package com.example.mvvm.utils

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


/**
 * 设置背景透明
 */
fun Dialog.setTransparent() {
    window?.apply { setBackgroundDrawableResource(android.R.color.transparent) }
}


/**
 * 设置列表对话框的分割线
 * 分割线的间距由Drawable来控制
 */
fun AlertDialog.setDivider(@DrawableRes divider: Int) {
    listView?.apply {
        overscrollFooter = ColorDrawable(Color.TRANSPARENT)
        this.divider = ContextCompat.getDrawable(context, divider)
    }
}

/**
 * 下拉菜单
 */
fun View.pullMenu(list: List<String>): ListPopupWindow {
    return ListPopupWindow(context).apply {
        anchorView = this@pullMenu
        setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, list))
    }
}

fun View.pullMenu(vararg list: String): ListPopupWindow {
    return ListPopupWindow(context).apply {
        anchorView = this@pullMenu
        setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, list))
    }
}

/**
 * 解决DialogFragment宽度限制问题
 */
fun androidx.fragment.app.DialogFragment.setWidthNoLimit() {
    @Suppress("DEPRECATION")
    setStyle(
        androidx.fragment.app.DialogFragment.STYLE_NO_TITLE,
        android.R.style.Theme_Material_Light_Dialog_MinWidth
    )
}

/**
 * 警告对话框
 */
fun Activity.alert(block: AlertDialog.Builder.() -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.block()
    return builder.show()
}

fun AlertDialog.Builder.yes(
    text: String,
    block: AlertDialog.() -> Unit = {}
) {
    setPositiveButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}


fun AlertDialog.Builder.yes(@StringRes text: Int, block: AlertDialog.() -> Unit = {}) {
    setPositiveButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}


fun AlertDialog.Builder.medium(
    text: String,
    block: AlertDialog.() -> Unit = {}
) {
    setNeutralButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}

fun AlertDialog.Builder.medium(@StringRes text: Int, block: AlertDialog.() -> Unit = {}) {
    setNeutralButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}


fun AlertDialog.Builder.no(
    text: String,
    block: AlertDialog.() -> Unit = {}
) {
    setNegativeButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}


fun AlertDialog.Builder.no(@StringRes text: Int, block: AlertDialog.() -> Unit = {}) {
    setNegativeButton(text) { dialogInterface, _ -> (dialogInterface as AlertDialog).block() }
}


/**
 * 进度对话框
 * @receiver Activity
 * @param msg String?
 * @param block [@kotlin.ExtensionFunctionType] Function1<ProgressDialog, Unit>
 */
fun Activity.progress(msg: String? = null, block: ProgressDialog.() -> Unit = {}): ProgressDialog {
    return ProgressDialog(this).apply {
        setMessage(msg)
        block()
        show()
    }
}