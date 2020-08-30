package com.acdprd.baseproject.utils

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import java.util.*

object Utils {

    object UiTextView {
        /**
         * @param fulltext
         * @param subtext  - часть текста, которую нужно выделить другим цветом
         * @param color    - цвет, которым выделяем subtext
         * @return
         */
        fun getSpannableStringWithForegroundColor(
            fulltext: String,
            subtext: String,
            color: Int
        ): SpannableString? {
            val spannableString = SpannableString(fulltext)
            val i = fulltext.toLowerCase(Locale.getDefault())
                .indexOf(subtext.toLowerCase(Locale.getDefault()))
            if (i >= 0 && i + subtext.length < fulltext.length) {
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    i,
                    i + subtext.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannableString
        }

        /**
         * @param tv          - tv
         * @param start       - start pos
         * @param end         - end pos
         * @param color       - colorRes
         * @param toUpperCase - toUpperCase
         */
        fun recolor(
            tv: TextView,
            start: Int,
            end: Int,
            @ColorRes color: Int,
            toUpperCase: Boolean
        ): TextView {
            val ssb: SpannableStringBuilder = if (toUpperCase) SpannableStringBuilder(
                tv.text.toString().toUpperCase(Locale.getDefault())
            ) else SpannableStringBuilder(tv.text)
            ssb.setSpan(
                ForegroundColorSpan(ResourcesCompat.getColor(tv.resources, color, null)),
                start,
                end,
                0
            )
            tv.setText(ssb, TextView.BufferType.SPANNABLE)
            return tv
        }

        //todo вызов recolor*(String) в recolor*(TextView)
        fun recolor(
            text: String,
            start: Int,
            end: Int,
            @ColorInt color: Int,
            toUpperCase: Boolean
        ): Spannable {
            val ssb: SpannableStringBuilder = if (toUpperCase) SpannableStringBuilder(
                text.toUpperCase(
                    Locale.getDefault()
                )
            ) else SpannableStringBuilder(
                text
            )
            ssb.setSpan(ForegroundColorSpan(color), start, end, 0)
            return ssb
        }

        fun resize(tv: TextView, start: Int, end: Int, @DimenRes size: Int): TextView {
            val ssb = SpannableStringBuilder(tv.text)
            ssb.setSpan(
                AbsoluteSizeSpan(
                    tv.context.resources.getDimensionPixelSize(size)
                ), start, end, 0
            )
            tv.setText(ssb, TextView.BufferType.SPANNABLE)
            return tv
        }

        fun resize(text: String?, start: Int, end: Int, size: Int): Spannable {
            val ssb = SpannableStringBuilder(text)
            ssb.setSpan(AbsoluteSizeSpan(size), start, end, 0)
            return ssb
        }


        @Deprecated("use refontCustom")
        fun refont(tv: TextView, start: Int, end: Int, fontFamily: String?): TextView {
            val ssb = SpannableStringBuilder(tv.text)
            ssb.setSpan(TypefaceSpan(fontFamily), start, end, 0)
            tv.setText(ssb, TextView.BufferType.SPANNABLE)
            return tv
        }

        fun refontCustom(
            tv: TextView,
            start: Int,
            end: Int,
            @FontRes fontRes: Int
        ): TextView {
            val ssb = SpannableStringBuilder(tv.text)
            val typeface: Typeface? = ResourcesCompat.getFont(tv.context, fontRes)
            if (typeface != null) {
                ssb.setSpan(CustomTypefaceSpan(typeface), start, end, 0)
                tv.setText(ssb, TextView.BufferType.SPANNABLE)
            } else {
                Log.w("TypeFaceSpan", "typeface==null")
            }
            return tv
        }

        fun refont(
            text: String?,
            start: Int,
            end: Int,
            fontFamily: String?
        ): Spannable {
            val ssb = SpannableStringBuilder(text)
            ssb.setSpan(TypefaceSpan(fontFamily), start, end, 0)
            return ssb
        }

        fun setUnderLineWithListener(
            tv: TextView,
            start: Int,
            end: Int,
            moveListener: (() -> Unit)?
        ): TextView {
            return setUnderLineWithListener(tv, start, end, true, moveListener)
        }

        /**
         * @param tv           - which underlined
         * @param moveListener - clickListener
         */
        fun setUnderLineWithListener(
            tv: TextView,
            start: Int,
            end: Int,
            withUnderLine: Boolean,
            moveListener: (() -> Unit)?
        ): TextView {
            val ssb = SpannableStringBuilder(tv.text)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    moveListener?.invoke()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = withUnderLine
                }
            }, start, end, 0)
            tv.movementMethod = LinkMovementMethod()
            tv.setText(ssb, TextView.BufferType.SPANNABLE)
            return tv
        }

        fun getFont(context: Context, @FontRes font: Int): Typeface? {
            return ResourcesCompat.getFont(context, font)
        }
    }

    object Intents {
        @JvmStatic
        fun getIntentForExternalLink(link: String): Intent? {
            return Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                null
            )
        }

        @JvmStatic
        fun getIntentForShare(link: String): Intent? {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, link)
            sendIntent.type = "text/plain"
            return Intent.createChooser(sendIntent, null)
        }

        /**
         * @param email - email
         * @return - intent for send email
         */
        @JvmStatic
        fun getIntentForEmail(email: String): Intent? {
            val uri = "mailto:"
            return Intent(Intent.ACTION_SENDTO, Uri.parse(uri + email))
        }

        /**
         * @param phoneNumber 79991112233 или +79991112233
         * @return intent for dial
         */
        @JvmStatic
        fun getIntentForPhoneDial(phoneNumber: String): Intent? {
            val plus = "+"
            val uri = "tel:"
            val formatted: String
            formatted = if (!phoneNumber.startsWith(plus)) {
                uri + (plus + phoneNumber)
            } else {
                uri + phoneNumber
            }
            return Intent(Intent.ACTION_DIAL, Uri.parse(formatted))
        }
    }


}