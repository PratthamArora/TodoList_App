package com.pratthamarora.todoapp.utils

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
}