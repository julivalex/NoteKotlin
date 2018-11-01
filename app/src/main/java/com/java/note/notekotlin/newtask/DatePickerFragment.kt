package com.java.note.notekotlin.newtask

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.EditText
import com.java.note.notekotlin.R
import com.java.note.notekotlin.utils.getDate
import java.util.*

open class DatePickerFragment : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)

        val editDate: EditText? = activity?.findViewById(R.id.editTaskDate)
        editDate?.setText(getDate(calendar.timeInMillis))
    }

    fun getDateCalendar(): Calendar = calendar

}