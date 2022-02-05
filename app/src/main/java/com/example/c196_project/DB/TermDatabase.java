package com.example.c196_project.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.Entities.TermEntity;


@Database(entities = {TermEntity.class}, version = 2, exportSchema = false)
public abstract class TermDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    private static volatile TermDatabase INSTANCE;

    public static TermDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TermDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabase.class, "termDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
        return INSTANCE;
    }
}
