package com.cleanup.todoc.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Task>> tasksList;
    private LiveData<List<Project>> projectList;

    public TaskListViewModel(Application application) {
        super(application);
        repository = DI.getTaskRepository(application);
        tasksList = repository.getAllTasks();
        projectList = repository.getAllProjects();
    }

    public LiveData<List<Task>> getTasksList() {
        return tasksList;
    }

    public LiveData<List<Project>> getProjectList() {
        return projectList;
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        repository.addNewTask(projectId, name, creationTimestamp);
    }
}
