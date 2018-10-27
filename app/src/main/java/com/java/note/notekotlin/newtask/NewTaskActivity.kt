package com.java.note.notekotlin.newtask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.java.note.notekotlin.R

import com.java.note.notekotlin.utils.getToolbarTitleColor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        toolbarNewTask.setTitleTextColor(getToolbarTitleColor(this))
        toolbarNewTask.setTitle(R.string.new_task_title)
        setSupportActionBar(toolbar)

        tilTaskTitle.hint = getString(R.string.task_title)
        tilTaskDate.hint = getString(R.string.task_date)
        tilTaskTime.hint = getString(R.string.task_time)

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
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }

        editTaskTime.setOnClickListener {
            if (it is EditText) {
                if (it.length() == 0) {
                    it.setText(" ")
                }
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TimePickerFragment")
            }
        }

        buttonOk.setOnClickListener { }
        buttonCancel.setOnClickListener {
            onBackPressed()
        }
    }
}