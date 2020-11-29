package com.acdprd.image_utils.picasso

import android.net.Uri
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request

class ImageFullPathTransformer(var baseImageUrl: String /*= Const.Network.getBaseImageUrl()*/) :
    Picasso.RequestTransformer {

    override fun transformRequest(request: Request): Request {
        val path = request.uri.toString()


        return if (request.uri.scheme != null) {
            request
        } else {
            request.buildUpon().setUri(Uri.parse(baseImageUrl + path))
                .build()
        }
    }
}