package com.java.note.notekotlin.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.java.note.notekotlin.R
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.adapter.CurrentTaskAdapter
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelSeparator
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database
import com.java.note.notekotlin.utils.Selection
import com.java.note.notekotlin.utils.Separator
import com.java.note.notekotlin.utils.Status
import java.util.*

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
        alarmHelper.removeAlarm(modelTask.timestamp)
        onTaskDoneListener?.onTaskDone(task = modelTask)
    }

    override fun addTaskFromDb() {
        adapterRecycler.removeAllItems()
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

    override fun findTasks(title: String) {
        adapterRecycler.removeAllItems()
        val tasks: MutableList<ModelTask> = ArrayList()
        tasks.addAll(
            mainActivity.dbHelper.queryManager
                .getTasks(
                    "${Selection.SELECTION_LIKE_TITLE} AND ${Selection.STATUS} OR ${Selection.STATUS}",
                    arrayOf("%$title%", Status.STATUS_CURRENT.toString(), Status.STATUS_OVERDUE.toString()),
                    Database.Column.TASK_DATE
                )
        )

        for (task: ModelTask in tasks) {
            addTask(task, false)
        }
    }

    override fun addTask(newTask: ModelTask, saveToDb: Boolean) {
        var separator: ModelSeparator? = null
        var position = -1
        var i = 0

        while (i < adapterRecycler.itemCount) {
            if (adapterRecycler.getItem(i).isTask()) {
                val oldTask: Item = adapterRecycler.getItem(i)
                if (oldTask is ModelTask && newTask.date < oldTask.date) {
                    position = i
                    break
                }
            }
            i++
        }

        if (newTask.date != 0L) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = newTask.date

            if (calendar.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                newTask.dateStatus = Separator.TYPE_OVERDUE
                if (!adapterRecycler.containsSeparatorOverdue) {
                    adapterRecycler.containsSeparatorOverdue = true
                    separator = ModelSeparator(Separator.TYPE_OVERDUE)
                }
            } else if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                newTask.dateStatus = Separator.TYPE_TODAY
                if (!adapterRecycler.containsSeparatorToday) {
                    adapterRecycler.containsSeparatorToday = true
                    separator = ModelSeparator(Separator.TYPE_TODAY)
                }
            } else if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1) {
                newTask.dateStatus = Separator.TYPE_TOMORROW
                if (!adapterRecycler.containsSeparatorTomorrow) {
                    adapterRecycler.containsSeparatorTomorrow = true
                    separator = ModelSeparator(Separator.TYPE_TOMORROW)
                }
            } else if (calendar.get(Calendar.DAY_OF_YEAR) > Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1) {
                newTask.dateStatus = Separator.TYPE_FUTURE
                if (!adapterRecycler.containsSeparatorFuture) {
                    adapterRecycler.containsSeparatorFuture = true
                    separator = ModelSeparator(Separator.TYPE_FUTURE)
                }
            }
        }

        if (position != -1) {
            if (!adapterRecycler.getItem(position - 1).isTask()) {
                if (position - 2 >= 0 && adapterRecycler.getItem(position - 2).isTask()) {
                    val task: Item = adapterRecycler.getItem(position - 2)
                    if (task is ModelTask && task.dateStatus == newTask.dateStatus) {
                        position -= 1
                    }
                } else if (position - 2 < 0 && newTask.date == 0L) {
                    position -= 1
                }
            }
            if (separator != null) {
                adapterRecycler.addItem(position - 1, separator)
            }
            adapterRecycler.addItem(position, newTask)
        } else {
            if (separator != null) {
                adapterRecycler.addItem(separator)
            }
            adapterRecycler.addItem(newTask)
        }
        if (saveToDb)
            mainActivity.dbHelper.saveTask(newTask)
    }

}