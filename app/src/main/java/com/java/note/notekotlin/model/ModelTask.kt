package com.java.note.notekotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTask(var title: String, var date: Long) : Item, Parcelable {

    constructor() : this("", 0)

    override fun isTask() = true
}