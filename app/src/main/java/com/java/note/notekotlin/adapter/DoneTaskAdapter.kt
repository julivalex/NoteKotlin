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
import com.java.note.notekotlin.fragment.DoneTaskFragment
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Status
import com.java.note.notekotlin.utils.getColorResource
import com.java.note.notekotlin.utils.getDateTime
import kotlinx.android.synthetic.main.model_task.view.*

class DoneTaskAdapter(taskFragment: DoneTaskFragment) : TaskAdapter(taskFragment) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.model_task, viewGroup, false)

        return TaskViewHolder(view, view.tvTaskTitle, view.tvTaskDate, view.cvTaskPriority)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val modelTask: Item = getItem(position)

        if (holder is TaskViewHolder) {
            val itemView: View = holder.view

            if (modelTask is ModelTask && modelTask.isTask()) {
                itemView.isEnabled = true
                holder.title.text = modelTask.title

                if (modelTask.date != 0L) {
                    holder.date.text = getDateTime(modelTask.date)
                } else {
                    holder.date.text = null
                }

                itemView.visibility = View.VISIBLE
                holder.priority.isEnabled = true

                holder.title.setTextColor(getColorResource(itemView.context, R.color.primary_text_disabled))
                holder.date.setTextColor(getColorResource(itemView.context, R.color.secondary_text_disabled))
                holder.priority.setColorFilter(getColorResource(itemView.context, modelTask.getPriorityColor()))
                holder.priority.setImageResource(R.mipmap.ic_check_circle_white_48dp)

                itemView.setOnLongClickListener {
                    val handler = Handler()
                    handler.postDelayed({
                        taskFragment.removeTaskDialog(holder.layoutPosition)
                    }, 1000)
                    return@setOnLongClickListener true
                }

                holder.priority.setOnClickListener {
                    holder.priority.isEnabled = false
                    modelTask.status = Status.STATUS_CURRENT
                    taskFragment.mainActivity.dbHelper.updateManager.updateStatus(modelTask.timestamp, modelTask.status)

                    holder.title.setTextColor(getColorResource(itemView.context, R.color.primary_text_default))
                    holder.date.setTextColor(getColorResource(itemView.context, R.color.secondary_text_default))
                    holder.priority.setColorFilter(getColorResource(itemView.context, modelTask.getPriorityColor()))

                    val rotationY: ObjectAnimator = ObjectAnimator.ofFloat(holder.priority, "rotationY", 180f, 0f)
                    holder.priority.setImageResource(R.mipmap.ic_circle_white_48dp)

                    rotationY.setAnimationEnd {
                        if (modelTask.status != Status.STATUS_DONE) {

                            val translationX: ObjectAnimator =
                                ObjectAnimator.ofFloat(itemView, "translationX", 0f, -itemView.width.toFloat())
                            val translationXBack: ObjectAnimator =
                                ObjectAnimator.ofFloat(itemView, "translationX", -itemView.width.toFloat(), 0f)

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
            }
        }
    }
}