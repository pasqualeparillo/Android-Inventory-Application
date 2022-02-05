package com.example.c196_project.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.Entities.TermEntity;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, InstructorEntity.class, TermEntity.class}, version = 1, exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {

    public abstract CourseDAO courseDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract TermDAO termDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile ScheduleDatabase INSTANCE;

    public static ScheduleDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ScheduleDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabase.class, "scheduleDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
