package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListItemTaskBinding
import com.example.todolist.model.Task
import java.util.UUID

class TaskHolder(
    private val binding: ListItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task, onShowTask: (id: UUID) -> Unit) {
        binding.apply {
            titleTextView.text = task.title
            dateTextView.text = task.date.toString()
            if (task.isComplete) {
                completeImageView.setImageResource(R.drawable.baseline_check_circle_24)
            }
            else {
                completeImageView.setImageResource(R.drawable.baseline_check_circle_outline_24)
            }

            root.setOnClickListener {
                onShowTask(task.id)
            }
        }
    }
}

class TaskListAdapter(
    private val tasks: List<Task>,
    private val onShowTask: (id: UUID) -> Unit
) : RecyclerView.Adapter<TaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val binding = ListItemTaskBinding.inflate(inflater)
        val binding = ListItemTaskBinding.inflate(inflater, parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onShowTask)
    }

    override fun getItemCount(): Int = tasks.size
}