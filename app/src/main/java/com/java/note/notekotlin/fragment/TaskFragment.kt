package com.java.note.notekotlin.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.java.note.notekotlin.MainActivity
import com.java.note.notekotlin.R
import com.java.note.notekotlin.adapter.TaskAdapter
import com.java.note.notekotlin.extensions.setViewDetachedFromWindow
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.receiver.AlarmHelper

abstract class TaskFragment : Fragment() {

    protected lateinit var layoutManagerRecycler: RecyclerView.LayoutManager
    protected lateinit var adapterRecycler: TaskAdapter
    protected lateinit var recyclerView: RecyclerView
    lateinit var mainActivity: MainActivity

    private var isRemoved: Boolean = false
    lateinit var alarmHelper: AlarmHelper

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity) {
            mainActivity = activity as MainActivity
        }

        alarmHelper = AlarmHelper(mainActivity)
        addTaskFromDb()
    }

    fun addTask(newTask: ModelTask, saveToDb: Boolean) {
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

        if (position != -1) {
            adapterRecycler.addItem(position, newTask)
        } else {
            adapterRecycler.addItem(newTask)
        }

        if (saveToDb)
            mainActivity.dbHelper.saveTask(newTask)
    }

    fun removeTaskDialog(position: Int) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(R.string.dialog_removing_message)

        val removingTask: Item = adapterRecycler.getItem(position)

        if (removingTask is ModelTask && removingTask.isTask()) {
            val timestamp: Long = removingTask.timestamp
            isRemoved = false

            dialogBuilder.setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                adapterRecycler.remoteItem(position)
                isRemoved = true

                val snackbar: Snackbar =
                    Snackbar.make(mainActivity.findViewById(R.id.coordinator), R.string.removed, Snackbar.LENGTH_LONG)

                snackbar.setAction(R.string.dialog_cancel) {
                    addTask(mainActivity.dbHelper.queryManager.getTask(timestamp), false)
                    isRemoved = false
                }

                snackbar.setViewDetachedFromWindow {
                    if (isRemoved) {
                        mainActivity.dbHelper.removeTask(timestamp)
                        alarmHelper.removeAlarm(timestamp)
                    }
                }
                snackbar.show()

                dialog.dismiss()
            }

            dialogBuilder.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
        }
        dialogBuilder.show()
    }

    abstract fun findTasks(title: String)

    abstract fun addTaskFromDb()

    abstract fun moveTask(modelTask: ModelTask)
}