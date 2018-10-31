package com.java.note.notekotlin.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup

class DoneTaskFragment : TaskFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_done_task, container, false)
        layoutManagerRecycler = LinearLayoutManager(activity)
        //adapterRecycler = DoneTaskAdapter(this)

        //recyclerView.apply {
            //layoutManager = layoutManagerRecycler
            //adapter = adapterRecycler
        //}

        return view
    }
}