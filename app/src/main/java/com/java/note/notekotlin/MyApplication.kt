package com.java.note.notekotlin

import android.app.Application

class MyApplication : Application() {

    companion object {
        private var activityVisible: Boolean = false

        fun isActivityVisible(): Boolean = activityVisible

        fun activityResumed() {
            activityVisible = true
        }

        fun activityPaused() {
            activityVisible = false
        }
    }
}