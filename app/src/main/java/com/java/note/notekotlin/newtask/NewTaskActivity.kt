package com.java.note.notekotlin.newtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.EditText
import com.java.note.notekotlin.R
import com.java.note.notekotlin.extensions.onChange
import com.java.note.notekotlin.extensions.setItemSelected
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.receiver.AlarmHelper
import com.java.note.notekotlin.utils.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var taskIntent: Intent
    private lateinit var alarmHelper: AlarmHelper
    private var datePickerFragment: DatePickerFragment? = null
    private var timePickerFragment: TimePickerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        taskIntent = Intent()
        alarmHelper = AlarmHelper(this)

        toolbarNewTask.setTitleTextColor(getToolbarTitleColor(this))
        toolbarNewTask.setTitle(R.string.new_task_title)
        setSupportActionBar(toolbar)

        tilTaskTitle.hint = getString(R.string.task_title)
        tilTaskDate.hint = getString(R.string.task_date)
        tilTaskTime.hint = getString(R.string.task_time)

        val task = ModelTask()

        spinnerPriority.adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Priority.PRIORITY_LEVELS)

        spinnerPriority.setItemSelected {
            task.priority = it
        }

        if (editTaskTitle.length() == 0) {
            buttonOk.isEnabled = false
            tilTaskTitle.error = getString(R.string.dialog_error_empty_title)
        }

        editTaskTitle.onChange {
            if (it.isEmpty()) {
                buttonOk.isEnabled = false
                tilTaskTitle.error = getString(R.string.dialog_error_empty_title)
            } else {
                buttonOk.isEnabled = true
                tilTaskTitle.isErrorEnabled = false
            }
        }

        editTaskDate.setOnClickListener {
            if (it is EditText) {
                if (it.length() == 0) {
                    it.setText(" ")
                }
                datePickerFragment = DatePickerFragment()
                datePickerFragment?.show(supportFragmentManager, "DatePickerFragment")
            }
        }

        editTaskTime.setOnClickListener {
            if (it is EditText) {
                if (it.length() == 0) {
                    it.setText(" ")
                }
                timePickerFragment = TimePickerFragment()
                timePickerFragment?.show(supportFragmentManager, "TimePickerFragment")
            }
        }

        buttonOk.setOnClickListener {
            val dateTime: Long =
                combineCalendars(datePickerFragment?.getDateCalendar(), timePickerFragment?.getTimeCalendar())

            task.title = editTaskTitle.text.toString()
            task.status = Status.STATUS_CURRENT
            if (editTaskDate.length() != 0 || editTaskTime.length() != 0) {
                task.date = dateTime

                alarmHelper.setAlarm(task)
            }

            taskIntent.putExtra(ModelTaskConst.TASK, task)
            setResult(Activity.RESULT_OK, taskIntent)
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}