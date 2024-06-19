package work.time.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import work.time.todo_list.databinding.ActivityNewTaskBinding
import work.time.todolist.data.AppDatabase
import work.time.todolist.data.Task

class NewTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTaskBinding
    private lateinit var vm: MainViewModel

    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getInstance(application).taskDao()
        val factory = MainViewModelFactory(dao)
        vm = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        taskId = intent.getIntExtra("task_id", -1)
        binding.etTitle.setText(intent.getStringExtra("task_title"))
        binding.etDesc.setText(intent.getStringExtra("task_description"))

        binding.btnSave.setOnClickListener {
            val taskTitle = binding.etTitle.text.toString()
            val taskDescription = binding.etDesc.text.toString()
            if (taskId != -1) {
                vm.updateTask(Task(taskId, taskTitle, taskDescription))
            } else {
                vm.insertTask(Task(task = taskTitle, description = taskDescription))
            }
            finish()
        }
    }
}