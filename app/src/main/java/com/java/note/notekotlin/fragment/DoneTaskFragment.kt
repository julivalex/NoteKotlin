package com.java.note.notekotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_done_task.view.*

class DoneTaskFragment : Fragment() {

//    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_done_task, container, false)

//        layoutManager = LinearLayoutManager(activity)
//        view.rvDoneTasks.layoutManager = layoutManager

        return view
    }
}