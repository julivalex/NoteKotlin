package com.java.note.notekotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.java.note.notekotlin.fragment.TaskFragment
import com.java.note.notekotlin.model.Item
import de.hdodenhof.circleimageview.CircleImageView

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

    open class ViewHolder(
        val view: View,
        val title: TextView,
        val date: TextView,
        val priority: CircleImageView
    ) : RecyclerView.ViewHolder(view)
}