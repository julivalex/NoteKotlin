package com.java.note.notekotlin.model

class ModelTask(var title: String, var date: String) : Item {

    constructor() : this("", "")

    override fun isTask() = true
}