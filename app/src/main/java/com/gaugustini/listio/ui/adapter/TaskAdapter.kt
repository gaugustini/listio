package com.gaugustini.listio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gaugustini.listio.databinding.ListItemTaskBinding
import com.gaugustini.listio.model.Task

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onMenuClick: (View, Task) -> Unit,
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback<Task>()) {

    class TaskViewHolder(
        private val binding: ListItemTaskBinding,
        val onClick: (Task) -> Unit,
        val onMenuClick: (View, Task) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                setClickListener {
                    onClick(binding.task!!)
                }
                setMenuClickListener {
                    onMenuClick(binding.options, binding.task!!)
                }
            }
        }

        fun bind(item: Task) {
            binding.apply {
                task = item
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ListItemTaskBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),
            onClick, onMenuClick
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

}
