package com.java.note.notekotlin.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.adapter.CurrentTaskAdapter
import com.java.note.notekotlin.model.ModelTask
import kotlinx.android.synthetic.main.fragment_current_task.view.*

class CurrentTaskFragment : Fragment() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: CurrentTaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_current_task, container, false)
        layoutManager = LinearLayoutManager(activity)
        adapter = CurrentTaskAdapter()
        view.rvCurrentTasks.layoutManager = layoutManager
        view.rvCurrentTasks.adapter = adapter

        return view
    }

    fun addTask(newTask: ModelTask) {
        var position = -1
        val i = 0
        while (i < adapter.itemCount) {
            if (adapter.getItem(i).isTask()) {
                val oldTask = adapter.getItem(i)
                if (oldTask is ModelTask && newTask.date < oldTask.date) {
                    position = i
                    break
                }
            }
        }

        if (position != -1) {
            adapter.addItem(position, newTask)
        } else {
            adapter.addItem(newTask)
        }
    }
}