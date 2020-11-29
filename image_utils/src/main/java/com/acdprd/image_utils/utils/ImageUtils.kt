package com.acdprd.image_utils.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.*

object ImageUtils {
    fun loadImage(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholder: Int,
        resize: Int?,
        transformation: Transformation?,
        centerCropGravity: Int?,
        withCache: Boolean
    ) {
        val imageUrl: String? = fixCacheImageSize(url, resize)
        if (imageUrl.isNullOrEmpty()) {
            if (placeholder != 0) {
                imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        imageView.resources,
                        placeholder,
                        null
                    )
                )
            } else {
                imageView.setImageDrawable(null)
            }
            return
        }
        val requestCreator = Picasso.get()
            .load(imageUrl)
        if (placeholder != 0) {
            requestCreator
                .placeholder(placeholder)
                .error(placeholder)
        }
        if (resize != null && centerCropGravity == null) {
            requestCreator.resize(resize, 0)
        }
        if (!withCache) {
            requestCreator
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
        }
        if (transformation != null) { //todo
            requestCreator.transform(transformation)
        }
        if (centerCropGravity != null) {
            requestCreator.centerCrop(centerCropGravity).fit()
        }
        requestCreator.into(imageView)
    }

    //todo реворкнуть все loadImages в один универсальный (можно с block:(RequestCreator)->Unit)
    fun loadImageScaleType(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholder: Int,
        resize: Int?,
        transformation: Transformation?,
        scaleTypes: Pair<ImageView.ScaleType?, ImageView.ScaleType?>,
        withCache: Boolean
    ) {
        val imageUrl = fixCacheImageSize(url, resize)
        imageView.scaleType = scaleTypes.second
        if (imageUrl.isNullOrEmpty()) {
            if (placeholder != 0) {
                imageView.scaleType = scaleTypes.second
                imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources,placeholder,null))
            }
            return
        }
        val requestCreator = Picasso.get()
            .load(imageUrl)
        if (!withCache) {
            requestCreator
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
        }
        if (placeholder != 0) {
            requestCreator
                .placeholder(placeholder)
                .error(placeholder)
        }
        if (resize != null) {
            requestCreator.resize(resize, 0)
        }
        if (transformation != null) {
            requestCreator.transform(transformation)
        }
        requestCreator.into(imageView, object : Callback {
            //todo weakReference?
            override fun onSuccess() {
                imageView.scaleType = scaleTypes.first
            }

            override fun onError(e: Exception) {
                imageView.scaleType = scaleTypes.second
            }
        })
    }

    private fun fixCacheImageSize(imageUrl: String?, resize: Int?): String? {
        return if (imageUrl != null && !imageUrl.contains("android.resource")) {
            if (resize != null) {
                "$imageUrl?$resize"
            } else {
                imageUrl
            }
        } else imageUrl
    }

    /**
     * picasso debug
     */
    fun picassoDebug(context: Context): Picasso {
        val p = Picasso.Builder(context)
            .listener { picasso, uri, exception -> exception.printStackTrace() }
            .build()
        p.setIndicatorsEnabled(true)
        p.isLoggingEnabled = true
        return p
    }
}