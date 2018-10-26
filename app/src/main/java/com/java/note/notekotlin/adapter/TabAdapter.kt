package com.java.note.notekotlin.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.java.note.notekotlin.fragment.CurrentTaskFragment
import com.java.note.notekotlin.fragment.DoneTaskFragment
import java.lang.NumberFormatException


class TabAdapter(fragmentManager: FragmentManager, private val countTabs: Int) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(item: Int) = when (item) {
        0 -> CurrentTaskFragment()
        1 -> DoneTaskFragment()
        else -> throw NumberFormatException("Wrong fragment")
    }


    override fun getCount(): Int {
        return countTabs
    }
}