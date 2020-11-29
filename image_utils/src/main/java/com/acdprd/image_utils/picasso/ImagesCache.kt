package com.acdprd.image_utils.picasso

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest

open class ImagesCache(context: Context) : Cache {
    protected open val path = DEFAULT_IMAGES_PATH
    protected open var overwrite = false
    protected open val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    protected open val filesDir = File(storageDir, path)

    private var list = mutableSetOf<String>()

    constructor(context: Context, overwrite: Boolean) : this(context) {
        this.overwrite = overwrite
    }

    init {
        if (!filesDir.exists())
            filesDir.mkdirs()
    }

    override fun clear() {
        // do nothing
        //todo
    }

    protected open fun list(): List<String> {
        if (!filesDir.exists()) return listOf()
        return filesDir.listFiles().map { it.name }.toList()
    }

    override fun size(): Int {
        return 0
    }

    override fun get(key: String): Bitmap? {
        val options = BitmapFactory.Options()
        //options.inPreferredConfig = Bitmap.Config.RGB_565
        val file = File(filesDir, key.md5())
        if (!file.exists()) return null
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    @Synchronized
    override fun set(key: String, bitmap: Bitmap) {
        if (list.contains(key) && !overwrite) return //already saved
        list.addAll(list())
        try {
            FileOutputStream(File(filesDir, key.md5())).use {
                //                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)    //or PNG 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun clearKeyUri(keyPrefix: String) {
    }

    override fun maxSize(): Int {
        return 500 * 1024 * 1024    //500Мбайт, например
    }

    open fun saveAll(
        uris: List<Uri>,
        withContainsCheck: Boolean
    ) {
        if (withContainsCheck) {
            saveAllWithContainsCheck(uris)
        } else {
            uris.forEach { Picasso.get().load(it).fetch() }
        }
    }

    open fun saveAllWithContainsCheck(uris: List<Uri>) {
        val nonFetched = mutableListOf<Uri>()
        uris.forEach { uri ->
            get(uri.toString()) ?: let {
                nonFetched.add(uri)
            }
        }
        saveAll(nonFetched, false)
    }

    /** Получить md5 */
    protected fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        const val DEFAULT_IMAGES_PATH = "images_cache"
    }
}
