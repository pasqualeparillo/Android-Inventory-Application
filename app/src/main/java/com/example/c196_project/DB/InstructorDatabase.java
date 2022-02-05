package com.example.c196_project.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;

@Database(entities = {InstructorEntity.class}, version = 3, exportSchema = false)
public abstract class InstructorDatabase extends RoomDatabase {

    public abstract InstructorDAO instructorDAO();


    private static volatile InstructorDatabase INSTANCE;

    public static InstructorDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (InstructorDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InstructorDatabase.class, "instructorDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
        return INSTANCE;
    }
}
