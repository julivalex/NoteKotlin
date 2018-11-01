package com.java.note.notekotlin.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.adapter.DoneTaskAdapter
import com.java.note.notekotlin.model.ModelTask

class DoneTaskFragment : TaskFragment() {

    private var onTaskRestoreListener: OnTaskRestoreListener? = null

    interface OnTaskRestoreListener {
        fun onTaskRestore(task: ModelTask)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_done_task, container, false)
        layoutManagerRecycler = LinearLayoutManager(activity)
        adapterRecycler = DoneTaskAdapter(this)

        recyclerView = view.findViewById(R.id.rvDoneTasks)
        recyclerView.apply {
            layoutManager = layoutManagerRecycler
            adapter = adapterRecycler
        }

        val context: Context = view.context
        onTaskRestoreListener = context as OnTaskRestoreListener

        return view
    }

    override fun moveTask(modelTask: ModelTask) {
        onTaskRestoreListener?.onTaskRestore(task = modelTask)
    }
}