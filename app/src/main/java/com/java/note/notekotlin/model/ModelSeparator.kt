package com.java.note.notekotlin.model

class ModelSeparator(val type: Int) : Item {

    override fun isTask(): Boolean = false
}