package com.java.note.notekotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.java.note.notekotlin.fragment.TaskFragment
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelSeparator
import com.java.note.notekotlin.utils.Separator
import de.hdodenhof.circleimageview.CircleImageView

abstract class TaskAdapter(val taskFragment: TaskFragment) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var containsSeparatorOverdue: Boolean = false
    var containsSeparatorToday: Boolean = false
    var containsSeparatorTomorrow: Boolean = false
    var containsSeparatorFuture: Boolean = false

    val items: MutableList<Item> = ArrayList()

    fun getItem(position: Int): Item = items[position]

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

            if (position - 1 >= 0 && position <= itemCount - 1) {
                if (!getItem(position).isTask() && !getItem(position - 1).isTask()) {
                    val separator: Item = getItem(position - 1)

                    if (separator is ModelSeparator) {
                        checkSeparators(separator.type)
                        items.removeAt(position - 1)
                        notifyItemRemoved(position - 1)
                    }
                }
            } else if (itemCount - 1 >= 0 && !getItem(itemCount - 1).isTask()) {
                val separator: Item = getItem(itemCount - 1)

                if (separator is ModelSeparator) {
                    checkSeparators(separator.type)
                    val positionTemp: Int = itemCount - 1
                    items.removeAt(positionTemp)
                    notifyItemRemoved(positionTemp)
                }
            }
        }
    }

    fun removeAllItems() {
        if (itemCount != 0) {
            items.clear()
            notifyDataSetChanged()
            containsSeparatorOverdue = false
            containsSeparatorToday = false
            containsSeparatorTomorrow = false
            containsSeparatorFuture = false
        }
    }

    fun checkSeparators(type: Int) {
        when (type) {
            Separator.TYPE_OVERDUE -> containsSeparatorOverdue = false
            Separator.TYPE_TODAY -> containsSeparatorToday = false
            Separator.TYPE_TOMORROW -> containsSeparatorTomorrow = false
            Separator.TYPE_FUTURE -> containsSeparatorFuture = false
        }
    }


    override fun getItemCount(): Int = items.size

    open class ViewHolder(
        val view: View,
        val title: TextView,
        val date: TextView,
        val priority: CircleImageView
    ) : RecyclerView.ViewHolder(view)

    open class SeparatorViewHolder(
        val view: View,
        val type: TextView
    ) : RecyclerView.ViewHolder(view)
}