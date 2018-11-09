package com.java.note.notekotlin.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.R
import com.java.note.notekotlin.extensions.setAnimationEnd
import com.java.note.notekotlin.fragment.CurrentTaskFragment
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelSeparator
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Status
import com.java.note.notekotlin.utils.getColorResource
import com.java.note.notekotlin.utils.getDateTime
import kotlinx.android.synthetic.main.model_separator.view.*
import kotlinx.android.synthetic.main.model_task.view.*
import java.util.*

class CurrentTaskAdapter(taskFragment: CurrentTaskFragment) : TaskAdapter(taskFragment) {

    companion object {
        const val TYPE_TASK = 0
        const val TYPE_SEPARATOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isTask()) {
            TYPE_TASK
        } else {
            TYPE_SEPARATOR
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TASK -> {
                val task: View = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.model_task, viewGroup, false)
                TaskViewHolder(task, task.tvTaskTitle, task.tvTaskDate, task.cvTaskPriority)
            }
            else -> {
                val separator: View = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.model_separator, viewGroup, false)
                SeparatorViewHolder(separator, separator.tvSeparatorName)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val modelTask: Item = getItem(position)

        if (modelTask is ModelTask && modelTask.isTask() && holder is TaskViewHolder) {
            val itemView: View = holder.view

            itemView.isEnabled = true
            holder.title.text = modelTask.title

            if (modelTask.date != 0L) {
                holder.date.text = getDateTime(modelTask.date)
            } else {
                holder.date.text = null
            }

            itemView.visibility = View.VISIBLE
            holder.priority.isEnabled = true

            if (modelTask.date != 0L && modelTask.date < Calendar.getInstance().timeInMillis) {
                itemView.setBackgroundColor(getColorResource(itemView.context, R.color.gray_200))
            } else {
                itemView.setBackgroundColor(getColorResource(itemView.context, R.color.gray_50))
            }

            holder.title.setTextColor(getColorResource(itemView.context, R.color.primary_text_default))
            holder.date.setTextColor(getColorResource(itemView.context, R.color.secondary_text_default))
            holder.priority.setColorFilter(
                getColorResource(
                    itemView.context,
                    modelTask.getPriorityColor()
                )
            )
            holder.priority.setImageResource(R.mipmap.ic_circle_white_48dp)

            itemView.setOnLongClickListener {
                val handler = Handler()
                handler.postDelayed({
                    taskFragment.removeTaskDialog(holder.layoutPosition)
                }, 1000)
                return@setOnLongClickListener true
            }

            holder.priority.setOnClickListener {
                holder.priority.isEnabled = false
                modelTask.status = Status.STATUS_DONE
                taskFragment.mainActivity.dbHelper.updateManager.updateStatus(modelTask.timestamp, modelTask.status)

                holder.title.setTextColor(
                    getColorResource(
                        itemView.context,
                        R.color.primary_text_disabled
                    )
                )
                holder.date.setTextColor(
                    getColorResource(
                        itemView.context,
                        R.color.secondary_text_disabled
                    )
                )
                holder.priority.setColorFilter(
                    getColorResource(
                        itemView.context,
                        modelTask.getPriorityColor()
                    )
                )

                val rotationY: ObjectAnimator =
                    ObjectAnimator.ofFloat(holder.priority, "rotationY", -180f, 0f)
                rotationY.setAnimationEnd {

                    if (modelTask.status == Status.STATUS_DONE) {
                        holder.priority.setImageResource(R.mipmap.ic_check_circle_white_48dp)

                        val translationX: ObjectAnimator =
                            ObjectAnimator.ofFloat(itemView, "translationX", 0f, itemView.width.toFloat())
                        val translationXBack: ObjectAnimator =
                            ObjectAnimator.ofFloat(itemView, "translationX", itemView.width.toFloat(), 0f)

                        translationX.setAnimationEnd {
                            itemView.visibility = View.GONE
                            taskFragment.moveTask(modelTask)
                            remoteItem(holder.layoutPosition)
                        }

                        val translationSet = AnimatorSet()
                        translationSet.play(translationX).before(translationXBack)
                        translationSet.start()
                    }
                }
                rotationY.start()
            }
        } else if (modelTask is ModelSeparator && !modelTask.isTask() && holder is SeparatorViewHolder) {

            val itemView: View = holder.view
            holder.type.text = itemView.resources.getString(modelTask.type)
        }

    }
}