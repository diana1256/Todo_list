package work.time.todolist.data


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val description: String,
    val date: Long = System.currentTimeMillis()
)
