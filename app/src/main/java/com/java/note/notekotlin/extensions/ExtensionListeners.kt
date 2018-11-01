package com.java.note.notekotlin.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.design.widget.TabLayout
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

fun TabLayout.onTabSelect(setPosition: (TabLayout.Tab) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

        override fun onTabReselected(tab: TabLayout.Tab) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            setPosition(tab)
        }
    })
}

fun ObjectAnimator.setAnimationEnd(doAfterEnd: () -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animator: Animator?) {
        }

        override fun onAnimationEnd(animator: Animator?) {
            doAfterEnd()
        }

        override fun onAnimationCancel(animator: Animator?) {
        }

        override fun onAnimationStart(animator: Animator?) {
        }
    })
}