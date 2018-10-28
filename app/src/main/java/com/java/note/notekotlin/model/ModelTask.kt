package com.java.note.notekotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelTask(var title: String, var date: String) : Item, Parcelable {

    constructor() : this("", "")

    override fun isTask() = true
}