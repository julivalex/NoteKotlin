package com.java.note.notekotlin.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.adapter.CurrentTaskAdapter
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database
import com.java.note.notekotlin.utils.Selection
import com.java.note.notekotlin.utils.Status

class CurrentTaskFragment : TaskFragment() {

    private var onTaskDoneListener: OnTaskDoneListener? = null

    interface OnTaskDoneListener {
        fun onTaskDone(task: ModelTask)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_current_task, container, false)
        layoutManagerRecycler = LinearLayoutManager(activity)
        adapterRecycler = CurrentTaskAdapter(this)

        recyclerView = view.findViewById(R.id.rvCurrentTasks)
        recyclerView.apply {
            layoutManager = layoutManagerRecycler
            adapter = adapterRecycler
        }

        val context: Context = view.context
        onTaskDoneListener = context as? OnTaskDoneListener

        return view
    }

    override fun moveTask(modelTask: ModelTask) {
        onTaskDoneListener?.onTaskDone(task = modelTask)
    }

    override fun addTaskFromDb() {
        val tasks: MutableList<ModelTask> = ArrayList()
        tasks.addAll(
            mainActivity.dbHelper.queryManager
                .getTasks(
                    "${Selection.STATUS} OR ${Selection.STATUS}",
                    arrayOf(Status.STATUS_CURRENT.toString(), Status.STATUS_OVERDUE.toString()),
                    Database.Column.TASK_DATE
                )
        )

        for (task: ModelTask in tasks) {
            addTask(task, false)
        }
    }
}