package com.java.note.notekotlin.newtask

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.EditText
import android.widget.TimePicker
import com.java.note.notekotlin.R
import com.java.note.notekotlin.utils.getTime
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.activity_new_task.view.*
import java.util.*

class TimePickerFragment : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            context, this,
            hourOfDay, minute, DateFormat.is24HourFormat(context)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        calendar.set(0, 0, 0, hourOfDay, minute)

        val editTime = activity?.findViewById<EditText>(R.id.editTaskTime)
        editTime?.setText(getTime(calendar.timeInMillis))

    }

    override fun onCancel(dialog: DialogInterface?) {
        tilTaskTime.editTaskTime.text = null
    }
}