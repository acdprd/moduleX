@file:Suppress("ConstantConditionIf")

package com.acdprd.image_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toFile
import androidx.exifinterface.media.ExifInterface

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * by acdprd | 11.03.2019.
 *
 * ресайзит изображение
 */

open class ImageResizer {

    open fun resizeWork(file: File, path: String): File {
        return resizeAndCompressImageBeforeSend(
            file,
            path
        )
    }

    open fun resizeWork(uri: Uri, path: String): File {
        return resizeWork(uri.toFile(), path)
    }

    companion object {
        private const val INNER_LOG = false
        private const val MAX_IMAGE_SIZE = 1000 * 1024

        @JvmStatic
        private fun log(what: Any) {
            if (INNER_LOG) {
                Log.w(ImageResizer::class.java.simpleName, what.toString())
            }
        }

        @JvmStatic
        fun getPathToImages(context: Context): String? {
            return try {
                val storageDir =
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                if (storageDir?.exists() != true) {
                    storageDir?.mkdirs()
                }
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    ?.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        @Suppress("ConstantConditionIf")
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val debugTag = "MemoryInformation"
            // Image nin islenmeden onceki genislik ve yuksekligi
            val height = options.outHeight
            val width = options.outWidth
            log("image height: $height---image width: $width")
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }
            log("inSampleSize: $inSampleSize")
            return inSampleSize
        }

        fun bitmapMatrixRotate(source: Bitmap, matrix: Matrix): Bitmap {
            return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        }

        fun resizeAndCompressImageBeforeSend(file: File, fixedPath: String): File {

            val exif = ExifInterface(file.name) //todo check | prev: (file)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_NORMAL -> {
                }
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    matrix.postRotate(90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    matrix.postRotate(180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    matrix.postRotate(270f)
                }
            }

            // First decode with inJustDecodeBounds=true to check dimensions of image
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
//            BitmapFactory.decodeFile(file.path, options)

            // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
            //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
            options.inSampleSize =
                calculateInSampleSize(
                    options,
                    800,
                    800
                )

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            val bmpPic = BitmapFactory.decodeFile(file.path, options)
            val rotated = bitmapMatrixRotate(bmpPic, matrix)

            var compressQuality = 100 // quality decreasing by 5 every loop.
            var streamLength: Int
            do {
                val bmpStream = ByteArrayOutputStream()
                log("compressBitmap Quality: $compressQuality")
                rotated.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
                compressQuality -= 5
                log("compressBitmap Size: " + streamLength / 1024 + " kb")
            } while (streamLength >= MAX_IMAGE_SIZE)

            try {
                val bmpFile = FileOutputStream(
                    fixFileName(
                        fixedPath,
                        file
                    )
                )
                rotated.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile)
                bmpFile.flush()
                bmpFile.close()
            } catch (e: Exception) {
                Log.e("compressBitmap", "Error on saving file")
            }

            //return the path of resized and compressed file

            return fixedFile(file, fixedPath)
        }

        fun fixedFile(file: File, fixedPath: String): File {
            return File(
                fixFileName(
                    fixedPath,
                    file
                )
            )
        }

        private fun fixFileName(file: File): String {
            val pos = file.path.indexOfLast { it == '.' }
            return buildString {
                append(file.path.substring(0, pos)).append("_resize").append(".jpg")
            }
        }

        private fun fixFileName(picturePath: String, file: File): String {
            val fName = file.name
            val fPos = fName.indexOfLast { it == '.' }
            return buildString {
                append(picturePath).append("/").append(fName.substring(0, fPos)).append("_resize")
                    .append(".jpg")
            }
        }
    }
}