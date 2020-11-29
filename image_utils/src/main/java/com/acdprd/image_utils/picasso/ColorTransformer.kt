package com.acdprd.image_utils.picasso

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.squareup.picasso.Transformation

class ColorTransformer(@ColorInt val toColor: Int) : Transformation {

    constructor(context: Context, @ColorRes colorRes: Int) : this(ContextCompat.getColor(context, colorRes))

    override fun key(): String = "colorTransformerTo${toColor}"

    override fun transform(source: Bitmap): Bitmap {
        val recolored = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val widthRange = IntRange(0, source.width - 1)
        val heightRange = IntRange(0, source.height - 1)
        widthRange.forEach { w ->
            heightRange.forEach { h ->
                val pixel = source.getPixel(w, h)
                val color = Color.argb(Color.alpha(pixel), Color.red(toColor), Color.green(toColor), Color.blue(toColor))
                recolored.setPixel(w, h, color)
            }
        }
        source.recycle()
        return recolored
    }
}