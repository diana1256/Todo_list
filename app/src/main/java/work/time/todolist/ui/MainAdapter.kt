package work.time.todolist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import work.time.todo_list.databinding.ItemTaskBinding
import work.time.todolist.data.Task
import work.time.todolist.ext.convertTime

class MainAdapter(
    private val onClick: (Task) -> Unit,
    private val onLongClick: (Task) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val tasks = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.task
            binding.tvDesc.text = task.description
            binding.data.text = task.date.convertTime()
            itemView.setOnClickListener {
                onClick(task)
            }
            itemView.setOnLongClickListener {
                onLongClick(task)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }
}