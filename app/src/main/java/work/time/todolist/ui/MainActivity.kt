package work.time.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import work.time.todo_list.R
import work.time.todo_list.databinding.ActivityMainBinding
import work.time.todolist.data.AppDatabase
import work.time.todolist.data.Task


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var taskAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getInstance(application).taskDao()
        val factory = MainViewModelFactory(dao)
        vm = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        taskAdapter = MainAdapter(this::onClick, this::onLongClick)
        binding.rvHome.adapter = taskAdapter

        vm.tasks.observe(this) { tasks ->
            tasks?.let { taskAdapter.updateList(it) }
        }

        binding.fabHome.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClick(task: Task) {
        val intent = Intent(this, NewTaskActivity::class.java).apply {
            putExtra("task_id", task.id)
            putExtra("task_title", task.task)
            putExtra("task_description", task.description)
        }
        startActivity(intent)
    }

    private fun onLongClick(task: Task) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.are_you_sure_you_want_to_delete))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                vm.deleteTask(task)
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        vm.tasks.value?.let { taskAdapter.updateList(it) }
    }
}
