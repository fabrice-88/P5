package com.cleanup.todoc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.cleanup.todoc.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertTasks(Task... tasks);

    @Delete
    public void deleteTasks(Task... tasks);

    @Query("SELECT * from task_table")
    public LiveData<List<Task>> loadAllTasks();
}
