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
import com.example.c196_project.Entities.CourseEntity;

@Database(entities = {CourseEntity.class}, version = 3, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();
    private static volatile CourseDatabase INSTANCE;

    public static CourseDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (CourseDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "courseDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}