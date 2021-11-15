package com.cleanup.todoc.di;

import android.app.Application;
import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.repository.Repository;

public class DI {
    private static boolean instantiateDbInMemory = false;

    private static Repository repository = null;
    public static Repository getTaskRepository(Application application) {
        if (repository == null) {
            Database db;
            if (instantiateDbInMemory) {
                db = Database.getNewDatabaseInMemory(application);
            } else {
                db = Database.getDatabase(application);
            }
            repository = new Repository(db.taskDao(), db.projectDao());
        }
        return repository;
    }
    public static void setInstantiateDbInMemory(boolean val) {
        instantiateDbInMemory = val;
    }
}
