package com.acdprd.filegsonrepository

import java.io.File
import java.io.Reader
import java.io.Writer

class IOWorker(directory: File, fileName: String) {
    @Suppress("JoinDeclarationAndAssignment")
    private val file: File

    init {
        file = File(directory.path + File.separator + fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun save(toSave: String?) {
        file.delete()
        var writer: Writer? = null
        try {
            file.createNewFile()
            writer = file.bufferedWriter()
            toSave?.let {
                writer.write(toSave)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            writer?.close()
        }
    }

    fun get(): String? {
        var get: String? = null
        var reader: Reader? = null
        return try {
            reader = file.bufferedReader()
            get = reader.readText()
            get
        } catch (e: Exception) {
            null
        } finally {
            reader?.close()
        }
    }
}