package com.java.note.notekotlin.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onChange(check: (CharSequence) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(p0: Editable) {
        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            check(s)
        }

    })
}