package com.java.note.notekotlin.fragment

import android.app.AlertDialog
import android.content.Intent
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
import com.java.note.notekotlin.newtask.EditTaskActivity
import com.java.note.notekotlin.receiver.AlarmHelper
import com.java.note.notekotlin.utils.ModelTaskConst
import com.java.note.notekotlin.utils.RequestCode

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

    fun updateTask(task: ModelTask) {
        adapterRecycler.updateTask(task)
    }

    fun showEditTaskActivity(task: ModelTask) {
        val intent = Intent(activity, EditTaskActivity::class.java)
        intent.putExtra(ModelTaskConst.TASK, task)
        mainActivity.startActivityForResult(intent, RequestCode.REQUEST_CODE_EDIT_TASK)
    }

    abstract fun findTasks(title: String)

    abstract fun addTaskFromDb()

    abstract fun moveTask(modelTask: ModelTask)

    abstract fun addTask(newTask: ModelTask, saveToDb: Boolean)
}