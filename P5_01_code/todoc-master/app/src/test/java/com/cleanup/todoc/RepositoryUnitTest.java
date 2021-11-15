package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class RepositoryUnitTest {

    private Repository repository;

    private TaskDao taskDao = mock(TaskDao.class);
    private ProjectDao projectDao = mock(ProjectDao.class);

    private List<Task> expectedTasks = Arrays.asList(
            new Task(0, 0, "name", 0),
            new Task(1, 1, "name", 1)
    );

    private List<Project> expectedProjects = Arrays.asList(Project.getAllProjects());

    LiveData<List<Task>> liveDataTaskList = new MutableLiveData<>(
            expectedTasks
    );

    LiveData<List<Project>> liveDataProjectList = new MutableLiveData<>(
            expectedProjects
    );

    @Before
    public void setup() {
        when(taskDao.loadAllTasks()).thenReturn(liveDataTaskList);
        when(projectDao.loadAllProjects()).thenReturn(liveDataProjectList);

        repository = new Repository(taskDao, projectDao);
    }

    @Test
    public void getAllTasksShouldReturnList() {
        assertEquals(liveDataTaskList, repository.getAllTasks());
    }

    @Test
    public void getAllProjectsShouldReturnList() {
        assertEquals(liveDataProjectList, repository.getAllProjects());
    }

}
