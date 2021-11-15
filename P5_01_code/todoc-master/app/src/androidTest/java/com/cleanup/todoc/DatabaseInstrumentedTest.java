package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private Database db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Database.getNewDatabaseInMemory(context);
        taskDao = db.taskDao();
        projectDao = db.projectDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {

        Task expectedTask = new Task(1,0, "test", 0);
        taskDao.insertTasks(expectedTask);

        List<Project> allProjects = LiveDataTestUtil.getValue(projectDao.loadAllProjects());
        assertNotNull(allProjects);

        List<Task> allTasks = LiveDataTestUtil.getValue(taskDao.loadAllTasks());
        assertThat(allTasks, contains(expectedTask));

        taskDao.deleteTasks(expectedTask);
        List<Task> emptyAllTasks = LiveDataTestUtil.getValue(taskDao.loadAllTasks());
        assertThat(emptyAllTasks, empty());
    }
}
