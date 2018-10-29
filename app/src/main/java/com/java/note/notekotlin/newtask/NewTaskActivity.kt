package com.java.note.notekotlin.newtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.java.note.notekotlin.R
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var taskIntent: Intent
    private var datePickerFragment: DatePickerFragment? = null
    private var timePickerFragment: TimePickerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        taskIntent = Intent()

        toolbarNewTask.setTitleTextColor(getToolbarTitleColor(this))
        toolbarNewTask.setTitle(R.string.new_task_title)
        setSupportActionBar(toolbar)

        tilTaskTitle.hint = getString(R.string.task_title)
        tilTaskDate.hint = getString(R.string.task_date)
        tilTaskTime.hint = getString(R.string.task_time)

        val task = ModelTask()

        if (editTaskTitle.length() == 0) {
            buttonOk.isEnabled = false
            tilTaskTitle.error = getString(R.string.dialog_error_empty_title)
        }

        editTaskTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {
            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    buttonOk.isEnabled = false
                    tilTaskTitle.error = getString(R.string.dialog_error_empty_title)
                } else {
                    buttonOk.isEnabled = true
                    tilTaskTitle.isErrorEnabled = false
                }
            }
        })

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

            val dateTime =
                combineCalendars(datePickerFragment?.getDateCalendar(), timePickerFragment?.getTimeCalendar())

            task.title = editTaskTitle.text.toString()
            if (editTaskDate.length() != 0 || editTaskTime.length() != 0) {
                task.date = dateTime
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