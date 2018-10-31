package com.java.note.notekotlin.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.adapter.CurrentTaskAdapter
import com.java.note.notekotlin.model.ModelTask

class CurrentTaskFragment : TaskFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_current_task, container, false)
        layoutManagerRecycler = LinearLayoutManager(activity)
        adapterRecycler = CurrentTaskAdapter(this)

        recyclerView = view.findViewById(R.id.rvCurrentTasks)
        recyclerView.apply {
            layoutManager = layoutManagerRecycler
            adapter = adapterRecycler
        }

        return view
    }

    override fun moveTask(modelTask: ModelTask) {
    }
}