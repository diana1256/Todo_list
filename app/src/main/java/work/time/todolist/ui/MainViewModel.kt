package work.time.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import work.time.todolist.data.Task
import work.time.todolist.data.TaskDao

class MainViewModel (private val dao: TaskDao) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            dao.getTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTask(task)
        }
    }
}
