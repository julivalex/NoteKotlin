package com.java.note.notekotlin.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.java.note.notekotlin.adapter.TaskAdapter
import com.java.note.notekotlin.model.ModelTask

abstract class TaskFragment : Fragment() {

    protected lateinit var layoutManagerRecycler: RecyclerView.LayoutManager
    protected lateinit var adapterRecycler: TaskAdapter
    protected lateinit var recyclerView: RecyclerView

    fun addTask(newTask: ModelTask) {
        var position = -1
        var i = 0
        while (i < adapterRecycler.itemCount) {
            if (adapterRecycler.getItem(i).isTask()) {
                val oldTask = adapterRecycler.getItem(i)
                if (oldTask is ModelTask && newTask.date < oldTask.date) {
                    position = i
                    break
                }
            }
            i++
        }

        if (position != -1) {
            adapterRecycler.addItem(position, newTask)
        } else {
            adapterRecycler.addItem(newTask)
        }
    }
}