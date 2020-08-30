package com.acdprd.baseproject.utils.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.acdprd.baseproject.R
import com.acdprd.baseproject.utils.Utils

fun Context?.actionShare(link: String?) {
    link?.let {
        this?.startActivity(Utils.Intents.getIntentForShare(link))
    }
}


fun Context?.actionOpenLink(link: String?) {
    link?.let {
        this?.startActivity(Utils.Intents.getIntentForExternalLink(link))
    }
}

fun <C : Context?> C?.alertDialog(
    title: String?,
    message: String?,
    okButton: String,
    okListener: Function0<Unit>?,
    noButton: String?,
    noListener: Function0<Unit>?,
    cancelable: Boolean = true
) {
    this?.let {
        val builder = AlertDialog.Builder(this)
        if (title != null) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
        if (okListener != null) {
            builder.setPositiveButton(okButton) { d, w ->
                d.dismiss()
                okListener.invoke()
            }
        }
        if (noButton != null && noListener != null) {
            builder.setNegativeButton(noButton) { d, w ->
                d.dismiss()
                noListener.invoke()
            }
        }
        val b = builder.create()
        b.setOnShowListener {
            b.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(
                    ResourcesCompat.getColor(
                        this.resources,
                        android.R.color.black,
                        null
                    )
                )
            b.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(
                    ResourcesCompat.getColor(
                        this.resources,
                        android.R.color.black,
                        null
                    )
                )
            b.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(
                    ResourcesCompat.getColor(
                        this.resources,
                        android.R.color.black,
                        null
                    )
                )
        }
        b.setCancelable(cancelable)
        b.show()
    }
}

fun <C : Context?> C?.alertDialogWithStyle(
    @StyleRes dialogStyle: Int,
    title: String?,
    message: String?,
    okButton: String,
    okListener: Function0<Unit>?,
    noButton: String?,
    noListener: Function0<Unit>?,
    cancelable: Boolean = true
) {
    this?.let {
        val builder = AlertDialog.Builder(this, dialogStyle)
        if (title != null) {
            val spannedTitle = SpannableStringBuilder(title).also {
                it.setSpan(
                    ForegroundColorSpan(
                        ResourcesCompat.getColor(
                            this.resources,
                            android.R.color.black,
                            null
                        )
                    ),
                    0, title.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                it.setSpan(
                    TypefaceSpan("sans-serif-medium"), 0,
                    title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            builder.setTitle(spannedTitle)
        }
        if (message != null) {
            val spannedMessage = SpannableStringBuilder(message).also {
                it.setSpan(
                    ForegroundColorSpan(
                        ResourcesCompat.getColor(
                            this.resources,
                            android.R.color.black,
                            null
                        )
                    ),
                    0,
                    message.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            builder.setMessage(spannedMessage)
        }
        if (okListener != null) {
            builder.setPositiveButton(okButton) { d, w ->
                d.dismiss()
                okListener.invoke()
            }
        }
        if (noButton != null && noListener != null) {
            builder.setNegativeButton(noButton) { d, w ->
                d.dismiss()
                noListener.invoke()
            }
        }
        val b = builder.create()
        b.setOnShowListener {
//            val background = R.drawable.shape_rect_gray_fa_r8
            val rippleColor = android.R.color.holo_blue_dark
            val textColor = android.R.color.holo_blue_light

            b.getButton(AlertDialog.BUTTON_POSITIVE).also {
//                it.setBackgroundResource(background)
                it.addRipple(rippleColor)
                it.setTextColor(resources.getColor(textColor))
            }
            b.getButton(AlertDialog.BUTTON_NEGATIVE).also {
//                it.setBackgroundResource(background)
                it.addRipple(rippleColor)
                    .setTextColor(resources.getColor(textColor))
            }
            b.getButton(AlertDialog.BUTTON_NEUTRAL).also {
//                it.setBackgroundResource(background)
                it.addRipple(rippleColor)
                    .setTextColor(resources.getColor(textColor))
            }
        }
        b.setCancelable(cancelable)
        b.show()
    }
}

fun <C : Context?> C?.alertDialog(@StringRes textId: Int) {
    this?.let {
        alertDialog(resources.getString(textId))
    }
}

fun <C : Context?> C?.alertDialog(text: String?) {
    this?.let {
        this.alertDialog<Context>(
            null,
            text,
            resources.getString(R.string.ok),
            { },
            null,
            null
        )
    }
}

@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class Duration

fun Context?.toast(text: String?, @Duration length: Int = Toast.LENGTH_SHORT) {
    this?.let {
        Toast.makeText(this, text, length).show()
    }
}

fun Context?.toast(@StringRes textRes: Int, @Duration length: Int = Toast.LENGTH_SHORT) {
    this.toast(this?.resources?.getString(textRes))
}