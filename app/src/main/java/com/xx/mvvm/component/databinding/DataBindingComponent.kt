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

package com.example.mvvm.component.databinding


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.View.NO_ID
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.drake.brv.listener.throttleClick
import com.xx.mvvm.utils.UnitUtils
import com.xx.mvvm.utils.format
import com.xx.mvvm.utils.px
import com.google.android.material.button.MaterialButton


object DataBindingComponent {

    /**
     * 在绑定视图时可以用于Model来处理UI, 由于破坏视图和逻辑解耦的规则不是很建议使用
     * 这会导致不方便业务逻辑进行单元测试
     *
     * @see OnBindListener 该接口支持泛型定义具体视图
     *
     * @receiver View
     * @param listener OnBindListener<View>
     */
    @JvmStatic
    @BindingAdapter("onBind")
    fun View.setView(listener: OnBindListener) {
        listener.onBind(this)
    }
}


// <editor-fold desc="图片">

@BindingAdapter(
    value = ["leftDrawable", "topDrawable", "rightDrawable", "bottomDrawable"],
    requireAll = false
)
fun TextView.setTextViewDrawable(
    drawableLeft: Int,
    drawableTop: Int,
    drawableRight: Int,
    drawableBottom: Int
) {
    if (drawableLeft != 0 || drawableTop != 0 || drawableRight != 0 || drawableBottom != 0) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }
}

@BindingAdapter("android:background")
fun View.setBackgroundRes(drawableId: Int) {
    if (drawableId != 0 && drawableId != NO_ID) {
        setBackgroundResource(drawableId)
    }
}

@BindingAdapter("android:backgroundTint")
fun MaterialButton.setBackgroundTintRes(color: Int) {
    if (color != 0 && color != NO_ID) {
        backgroundTintList = ColorStateList(arrayOf(intArrayOf()), intArrayOf(color))
    }
}


@BindingAdapter("android:src")
fun ImageView.setImageRes(drawableId: Int) {
    if (drawableId != 0 && drawableId != NO_ID) {
        setImageResource(drawableId)
    }
}

/**
 * 加载圆角图片
 *
 * @param url 图片来源
 * @param holder 占位图, 如果不设置且存在 android:src 则为占位图
 * @param corner 设置圆角, 默认为圆
 */
@BindingAdapter(value = ["imgCorner", "holder", "corner"], requireAll = false)
fun ImageView.loadImageCornerWithHolder(url: Any?, holder: Drawable?, @Dimension corner: Int = 0) {
    val requestOptions = if (corner == 0) {
        RequestOptions().circleCrop().placeholder(holder)
    } else {
        RequestOptions().transforms(CenterCrop(), RoundedCorners(corner.px())).placeholder(holder)
    }
    Glide.with(context).load(url).apply(requestOptions).into(this)
}

@BindingAdapter(value = ["imgCorner", "corner"], requireAll = false)
fun ImageView.loadImageCorner(url: Any?, @Dimension corner: Int = 0) {
    val requestOptions = if (corner == 0) {
        RequestOptions().circleCrop().placeholder(drawable)
    } else {
        RequestOptions().transforms(CenterCrop(), RoundedCorners(corner.px())).placeholder(drawable)
    }
    Glide.with(context).load(url).apply(requestOptions).into(this)
}


@BindingAdapter(value = ["img", "holder"])
fun ImageView.loadImageWithHolder(url: Any?, holder: Drawable?) {
    Glide.with(context).load(url).apply(RequestOptions().placeholder(holder)).into(this)
}

@BindingAdapter("img")
fun ImageView.loadImage(url: Any?) {
    Glide.with(context).load(url).apply(RequestOptions().placeholder(drawable)).into(this)
}


@SuppressLint("CheckResult")
@BindingAdapter(value = ["gif", "holder"], requireAll = false)
fun ImageView.loadGif(url: Any?, holder: Drawable? = null) {

    if (url == null || (url is CharSequence && url.isEmpty()) || (url is Int && url == NO_ID)) {
        holder?.let {
            setImageDrawable(it)
        }
        return
    }

    val requestOptions = if (holder == null && drawable != null) {
        RequestOptions().placeholder(drawable)
    } else {
        RequestOptions().placeholder(holder)
    }

    Glide.with(context).asGif().load(url).apply(requestOptions).into(this)
}


// </editor-fold>


// <editor-fold desc="显示">

/**
 * 隐藏控件
 */
@BindingAdapter("invisible")
fun View.setInvisible(visibilityVar: Boolean) {
    visibility = if (visibilityVar) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("invisible")
fun View.setInvisible(visibilityVar: Any?) {
    visibility = if (visibilityVar != null) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

/**
 * 取消控件
 */
@BindingAdapter("gone")
fun View.setGone(visibilityVar: Boolean) {
    visibility = if (visibilityVar) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("gone")
fun View.setGone(visibilityVar: Any?) {
    visibility = if (visibilityVar != null) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


// </editor-fold>


// <editor-fold desc="阴影">


@BindingAdapter("android:elevation")
fun View.setElevationDp(dp: Int) {
    ViewCompat.setElevation(this, dp.px().toFloat())
}

@BindingAdapter("android:elevation")
fun CardView.setElevationDp(dp: Int) {
    cardElevation = dp.toFloat()
}

// </editor-fold>


@BindingAdapter("del")
fun TextView.setDel(isAdd: Boolean) {
    if (isAdd) {
        paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG   // 设置中划线并加清晰
    }
}

@BindingAdapter("url")
fun WebView.setUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        loadDataWithBaseURL(null, url, "text/html", "UTF-8", null)
    }
}


// <editor-fold desc="状态">

@BindingAdapter("android:enabled")
fun View.setEnableBind(enable: Boolean) {
    isEnabled = enable
}

@BindingAdapter("android:enabled")
fun View.setEnableBind(enable: Any?) {
    isEnabled = enable != null
}

@BindingAdapter("selected")
fun View.setSelectedBind(selected: Boolean) {
    isSelected = selected
}

@BindingAdapter("selected")
fun View.setSelectedBind(selected: Any?) {
    isSelected = selected != null
}


@BindingAdapter("activated")
fun View.setActivatedBind(activated: Boolean) {
    isActivated = activated
}


@BindingAdapter("activated")
fun View.setActivatedBind(activated: Any?) {
    isActivated = activated != null
}

// </editor-fold>


// <editor-fold desc="点击事件">

/**
 * 防止暴力点击
 */
@SuppressLint("CheckResult")
@BindingAdapter("click")
fun View.setPreventClickListener(onClickListener: View.OnClickListener?) {
    if (onClickListener != null) {
        throttleClick { onClickListener.onClick(this) }
    }
}


/**
 * 自动将点击事件映射到Activity上
 *
 * @param isPrevent 是否只支持快速点击
 */
@SuppressLint("CheckResult")
@BindingAdapter("hit")
fun View.hit(isPrevent: Boolean = true) {
    var context = context

    while (context is ContextWrapper) {
        if (context is View.OnClickListener) {
            val clickListener = context as View.OnClickListener

            if (isPrevent) {
                throttleClick { clickListener.onClick(this) }
            } else setOnClickListener(clickListener)
        }
        context = context.baseContext
    }
}


/**
 * 关闭当前界面
 *
 * @param enabled 是否启用
 */
@SuppressLint("CheckResult")
@BindingAdapter("finish")
fun View.finishActivity(enabled: Boolean = true) {
    if (enabled) {
        var temp = context
        var activity: Activity? = null

        while (temp is ContextWrapper) {
            if (temp is Activity) {
                activity = temp
            }
            temp = temp.baseContext
        }

        val finalActivity = activity

        throttleClick {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finalActivity!!.finishAfterTransition()
            } else {
                finalActivity!!.finish()
            }
        }
    }
}

// </editor-fold>


/**
 * 在绑定视图时可以用于Model来处理UI, 由于破坏视图和逻辑解耦的规则不是很建议使用
 * 这会导致不方便业务逻辑进行单元测试
 *
 */
interface OnBindListener {

    fun onBind(v: View)
}


// <editor-fold desc="人民币">

@SuppressLint("SetTextI18n")
@BindingAdapter("rmb")
fun TextView.setRMB(number: String?) {
    if (!number.isNullOrEmpty()) {
        val format = "¥${number.format()}"
        if (format != text.toString()) text = format
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("rmb")
fun TextView.setRMB(number: Double) {
    val format = "¥${number.format()}"
    if (format != text.toString()) text = format
}

/**
 * 设置rmb，已经除100
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("rmb")
fun TextView.setRMB(number: Long) {
    val format = "¥${number.format()}"
    if (format != text.toString()) text = format
}

// </editor-fold>


// <editor-fold desc="时间">

@BindingAdapter(value = ["dateMilli", "dateFormat"], requireAll = false)
fun TextView.setDateFromMillis(milli: Long, format: String? = "yyyy-MM-dd") {
    val formatText = UnitUtils.formatDate(milli, format)
    if (milli == 0L || formatText == text.toString()) {
        return
    }
    text = formatText
}


/**
 * 根据毫秒值来显示时间
 */
@BindingAdapter(value = ["dateMilli", "dateFormat"], requireAll = false)
fun TextView.setDateFromMillis(milli: String?, format: String? = "yyyy-MM-dd") {
    val formatText = UnitUtils.formatDate(milli, format)
    if (milli.isNullOrEmpty() || formatText == text.toString()) {
        return
    }
    text = formatText
}

@BindingAdapter(value = ["dateSecond", "dateFormat"], requireAll = false)
fun TextView.setDateFromSecond(second: Long, format: String? = "yyyy-MM-dd") {
    val formatText = UnitUtils.formatDate(second * 1000, format)
    if (second == 0L || formatText == text.toString()) {
        return
    }
    text = formatText
}

@BindingAdapter(value = ["dateSecond", "dateFormat"], requireAll = false)
fun TextView.setDateFromSecond(second: String, format: String? = "yyyy-MM-dd") {
    val formatText = UnitUtils.formatDate(java.lang.Long.parseLong(second) * 1000, format)
    if (TextUtils.isEmpty(second) || formatText == text.toString()) {
        return
    }
    text = formatText
}

// </editor-fold>