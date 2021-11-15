package com.cleanup.todoc.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class Repository {
    private TaskDao taskDao;
    private ProjectDao projectDao;

    public Repository(TaskDao taskDao, ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.loadAllTasks();
    }

    public void deleteTask(Task task) {
        new deleteAsyncTask(taskDao).execute(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        Task task = new Task(0, projectId, name, creationTimestamp);
        new insertAsyncTask(taskDao).execute(task);
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.loadAllProjects();
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao asyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            asyncTaskDao.insertTasks(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao asyncTaskDao;

        deleteAsyncTask(TaskDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            asyncTaskDao.deleteTasks(params[0]);
            return null;
        }
    }
}
