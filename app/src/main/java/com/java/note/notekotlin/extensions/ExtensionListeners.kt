package com.java.note.notekotlin.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner

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

fun Spinner.setItemSelected(setPriority: (Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            setPriority(position)
        }
    }
}

fun Snackbar.setViewDetachedFromWindow(removeTask: () -> Unit) {
    this.view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(view: View) {
            removeTask()
        }

        override fun onViewAttachedToWindow(view: View) {
        }
    })
}

fun SearchView.queryTextChange(change: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            change(newText)
            return false
        }
    })
}