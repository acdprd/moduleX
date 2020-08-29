package com.acdprd.baseproject.helpers

import android.os.Bundle
import java.io.Serializable

/**
 * удобно передавать бандлы через этот класс.
 * Если использовать котлин, то классу можно "расширять" поведение под нужны проекта
 */
class ArgsBuilder constructor(bundle: Bundle?) {
    private var bundle: Bundle = bundle ?: Bundle()

    constructor() : this(Bundle())

    companion object {
        private const val TAG: String = "ArgsBuilder"
        const val ID: String = TAG + "_id"
        const val TEXT: String = TAG + "_text"
        const val BOOL = TAG + "_boolean"
        const val SERIALIZABLE: String = TAG + "_serializable"

        fun create() = ArgsBuilder(null)
        fun create(bundle: Bundle?) = ArgsBuilder(bundle)
    }

    fun bundle(): Bundle {
        return this.bundle
    }

    fun text(text: String?): ArgsBuilder {
        bundle.putString(TEXT, text)
        return this
    }

    fun text(): String? {
        return bundle.getString(TEXT)
    }

    fun id(id: Int): ArgsBuilder {
        bundle.putInt(ID, id)
        return this
    }

    fun id(): Int {
        return bundle.getInt(ID)
    }

    fun serializable(s: Serializable?): ArgsBuilder {
        bundle.putSerializable(SERIALIZABLE, s)
        return this
    }

    fun serializable(): Serializable? = bundle.getSerializable(SERIALIZABLE)

    fun bool(): Boolean {
        return bundle.getBoolean(BOOL)
    }

    fun bool(b: Boolean): ArgsBuilder {
        bundle.putBoolean(BOOL, b)
        return this
    }

    fun contains(key: String): Boolean = bundle.containsKey(key)
}