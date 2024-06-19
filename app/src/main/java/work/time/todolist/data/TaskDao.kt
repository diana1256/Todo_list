package work.time.todolist.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}