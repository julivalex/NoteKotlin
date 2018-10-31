package com.java.note.notekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.java.note.notekotlin.R
import com.java.note.notekotlin.fragment.CurrentTaskFragment
import kotlinx.android.synthetic.main.model_task.view.*

class CurrentTaskAdapter(taskFragment: CurrentTaskFragment) : TaskAdapter(taskFragment) {

    companion object {
        const val TYPE_TASK = 0
        const val TYPE_SEPARATOR = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.model_task, viewGroup, false)

        return when (viewType) {
            TYPE_TASK -> {
                ViewHolder(view, view.tvTaskTitle, view.tvTaskDate)
            }
            else -> {
                ViewHolder(view, view.tvTaskTitle, view.tvTaskDate)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isTask()) {
            TYPE_TASK
        } else {
            TYPE_SEPARATOR
        }
    }
}