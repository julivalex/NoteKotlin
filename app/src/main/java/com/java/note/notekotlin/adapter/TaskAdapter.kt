package com.java.note.notekotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.java.note.notekotlin.fragment.TaskFragment
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.getDateTime

abstract class TaskAdapter(val taskFragment: TaskFragment) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    val items: MutableList<Item> = ArrayList()

    fun getItem(position: Int) = items[position]

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun addItem(position: Int, item: Item) {
        items.add(index = position, element = item)
        notifyItemInserted(position)
    }

    fun remoteItem(position: Int) {
        if (position >= 0 && position <= itemCount - 1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = items.size

    open class ViewHolder(val view: View, private val holderTitle: TextView, private val holderDate: TextView) :
        RecyclerView.ViewHolder(view) {

        fun onBind(modelTask: Item) {
            if (modelTask.isTask() && modelTask is ModelTask) {
                view.isEnabled = true
                holderTitle.text = modelTask.title
                if (modelTask.date != 0L) {
                    holderDate.text = getDateTime(modelTask.date)
                }
            }
        }
    }
}