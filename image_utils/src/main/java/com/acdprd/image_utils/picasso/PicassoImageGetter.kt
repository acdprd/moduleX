package com.acdprd.image_utils.picasso

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class PicassoImageGetter(var tv: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        val placeHolder = BitmapDrawablePlaceHolder(tv)
        Picasso.get().load(source).into(placeHolder)

        return placeHolder
    }


    @Suppress("PropertyName")
    private inner class BitmapDrawablePlaceHolder(var tv: TextView) : BitmapDrawable(), Target {
        var _drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            _drawable?.draw(canvas)
        }

        fun setDrawable(drawable: Drawable) {
            this._drawable = drawable
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            tv.text = tv.text
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            setDrawable(BitmapDrawable(tv.context.resources, bitmap))
        }

        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
            errorDrawable?.let {
                setDrawable(errorDrawable)
            }
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit
    }
}