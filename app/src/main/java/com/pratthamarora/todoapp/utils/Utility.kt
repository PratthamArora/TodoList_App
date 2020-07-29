package com.pratthamarora.todoapp.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object Utility {
    fun Context.hideTheKeyboard(editText: EditText) {
        editText.clearFocus()
        val `in` =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(
            editText.windowToken,
            0
        )
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}