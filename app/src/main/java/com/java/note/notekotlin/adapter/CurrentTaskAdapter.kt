package com.java.note.notekotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.java.note.notekotlin.R
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.getDateTime
import kotlinx.android.synthetic.main.model_task.view.*

class CurrentTaskAdapter : RecyclerView.Adapter<CurrentTaskAdapter.ViewHolder>() {

    companion object {
        const val TYPE_TASK = 0
        const val TYPE_SEPARATOR = 1
    }

    private val items: MutableList<Item> = ArrayList()

    fun getItem(position: Int) = items[position]

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun addItem(position: Int, item: Item) {
        items.add(index = position, element = item)
        notifyItemInserted(position)
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

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isTask()) {
            TYPE_TASK
        } else {
            TYPE_SEPARATOR
        }
    }

    class ViewHolder(val view: View, private val holderTitle: TextView, private val holderDate: TextView) :
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