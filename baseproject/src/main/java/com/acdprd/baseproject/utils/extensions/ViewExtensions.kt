package com.acdprd.baseproject.utils.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acdprd.baseproject.R
import com.acdprd.baseproject.utils.Const
import com.acdprd.baseproject.utils.Utils
import java.util.*


fun TextView.recolor(
    start: Int = 0,
    end: Int = this.length(),
    @ColorRes color: Int,
    toUpperCase: Boolean = false
): TextView =
    Utils.UiTextView.recolor(this, start, end, color, toUpperCase)

fun TextView.resize(start: Int = 0, end: Int = this.length(), @DimenRes size: Int): TextView =
    Utils.UiTextView.resize(this, start, end, size)

fun TextView.refont(start: Int = 0, end: Int = this.length(), ff: String): TextView =
    Utils.UiTextView.refont(this, start, end, ff)

fun TextView.refontCustom(
    start: Int = 0,
    end: Int = this.length(),
    @FontRes fontRes: Int
): TextView =
    Utils.UiTextView.refontCustom(this, start, end, fontRes)

fun TextView.clickSpan(
    start: Int = 0,
    end: Int = this.length(),
    withUnderline: Boolean,
    l: () -> Unit
): TextView =
    Utils.UiTextView.setUnderLineWithListener(this, start, end, withUnderline, l)

/**
 * добавит rect со скругленными краями
 * по умолчанию белый цвет и скругления 2dp
 */
fun View.addBackgroundRect(
    @ColorInt solidColor: Int = Color.WHITE,
    @DimenRes cornersRadius: Int = R.dimen.add_background_rect_default_corner_radius
): View {
    val rect = getRectWithCorners(this.context, cornersRadius).also { drawable ->
        drawable.setColor(solidColor)
    }
    this.background = rect
    return this
}

private fun getRectWithCorners(context: Context, @DimenRes cornerRadius: Int): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE
    val cornerRadiusF = context.resources.getDimensionPixelSize(cornerRadius).toFloat()
    drawable.cornerRadii = floatArrayOf(
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF
    )
    return drawable
}

@SuppressLint("ObsoleteSdkInt")
fun <V : View> V.addClickable(@ColorRes color: Int): V {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.background?.let { back ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val colorInt = resources.getColor(color)
                this.background = RippleDrawable(
                    ColorStateList.valueOf(colorInt),
                    back,
                    null/*getRippleMask(colorInt)*/
                )
            }
        } ?: let {
            this.addBackgroundSelectorRes(color)
        }
    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        this.addBackgroundSelectorRes(color)
    }
    return this
}


//todo test
private fun getRippleMask(color: Int): Drawable {
    val outerRadii = FloatArray(8)
    // 3 is radius of final ripple,
    // instead of 3 you can give required final radius
    Arrays.fill(outerRadii, 3f)

    val r = RoundRectShape(outerRadii, null, null)
    val shapeDrawable = ShapeDrawable(r)
    shapeDrawable.paint.color = color
    return shapeDrawable
}

@SuppressLint("ObsoleteSdkInt")
fun <V : View> V.addRipple(@ColorRes color: Int): V {
    this.background?.let { back ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background =
                RippleDrawable(ColorStateList.valueOf(resources.getColor(color)), back, null)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            this.addBackgroundSelectorRes(color)
        }
    }
    return this
}

@SuppressLint("ObsoleteSdkInt")
fun <V : View> V.addRippleColor(@ColorInt color: Int): V {
    this.background?.let { back ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background = RippleDrawable(ColorStateList.valueOf(color), back, null)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            this.addBackgroundSelector(color)
        }
    }
    return this
}

fun <V : View> V.addBackgroundSelector(@ColorInt color: Int): V {
    val sld = StateListDrawable().also {
        it.setExitFadeDuration(Const.Duration.SELECTOR_FADE_DURATION.toInt())
        it.setEnterFadeDuration(Const.Duration.SELECTOR_FADE_DURATION.toInt())
        it.addState(IntArray(1) { android.R.attr.state_pressed }, ColorDrawable(color))
        it.addState(IntArray(0) { 0 }, ColorDrawable(Color.TRANSPARENT))
    }
    background = sld
    return this
}

fun <V : View> V.addBackgroundSelectorRes(@ColorRes color: Int): V {
    val sld = StateListDrawable().also {
        it.setExitFadeDuration(Const.Duration.SELECTOR_FADE_DURATION.toInt())
        it.setEnterFadeDuration(Const.Duration.SELECTOR_FADE_DURATION.toInt())
        it.addState(
            IntArray(1) { android.R.attr.state_pressed },
            ColorDrawable(resources.getColor(color))
        )
        it.addState(IntArray(0) { 0 }, ColorDrawable(Color.TRANSPARENT))
    }
    background = sld
    return this
}

//insets

fun View.updatePaddings(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.addSystemTopBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
//        Log.w(tag(),"${targetView::class.java} tb c:${isConsumed}  t:${insets.systemWindowInsetTop} b:${insets.systemWindowInsetBottom} | ip:${initialPadding.asJson()}")
        targetView.updatePaddings(
            top = initialPadding.top + insets.systemWindowInsetTop,
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}

fun View.addSystemTopPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
//        Log.w(tag(),"${targetView::class.java} t c:${isConsumed}  t:${insets.systemWindowInsetTop}")
        targetView.updatePaddings(
            top = initialPadding.top + insets.systemWindowInsetTop
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
                )
            )
        } else {
            insets
        }
    }
}


fun View.addSystemBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
//        Log.w(tag(),"${targetView::class.java} b c:${isConsumed}  b:${insets.systemWindowInsetBottom}")
        targetView.updatePaddings(
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}


fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
//        Log.w(tag(),"INSETS v:${v.tag()} | t:${insets.systemWindowInsetTop} l:${insets.systemWindowInsetLeft} r:${insets.systemWindowInsetRight} b:${insets.systemWindowInsetBottom}")
        block(v, insets, initialPadding)
        insets
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}