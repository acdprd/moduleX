package com.acdprd.baseproject.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object HideKeyboard {
    fun hideKeyboardSafe(view: View) {
        try {
            view.context?.let {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideKeyboardForced(fragment: Fragment) {
        try {
            fragment.activity?.let {
                val view = it.currentFocus
                if (view != null) {
                    val imm =
                        it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideKeyboardForced(context: Context?) {
        try {
            context?.let { context ->
                if (context is Activity) {
                    val view = context.currentFocus
                    if (view != null) {
                        val imm =
                            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}