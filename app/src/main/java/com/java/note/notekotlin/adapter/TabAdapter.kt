package com.java.note.notekotlin.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.java.note.notekotlin.fragment.CurrentTaskFragment
import com.java.note.notekotlin.fragment.DoneTaskFragment
import java.lang.NumberFormatException

class TabAdapter(fragmentManager: FragmentManager, private val countTabs: Int) :
    FragmentStatePagerAdapter(fragmentManager) {

    var currentTaskFragment: CurrentTaskFragment = CurrentTaskFragment()
    var doneTaskFragment: DoneTaskFragment = DoneTaskFragment()

    companion object {
        const val CURRENT_TASK_FRAGMENT_POSITION = 0
        const val DONE_TASK_FRAGMENT_POSITION = 1
    }

    override fun getItem(item: Int) = when (item) {
        0 -> currentTaskFragment
        1 -> doneTaskFragment
        else -> throw NumberFormatException("Wrong fragment")
    }

    override fun getCount(): Int {
        return countTabs
    }
}