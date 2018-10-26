package com.java.note.notekotlin.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup

class CurrentTaskFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_current_task, container, false)
    }
}