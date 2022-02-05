package com.example.c196_project.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.Entities.AssessmentEntity;

@Database(entities = {AssessmentEntity.class}, version = 2, exportSchema = false)
public abstract class AssessmentDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    private static volatile AssessmentDatabase INSTANCE;

    public static AssessmentDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AssessmentDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AssessmentDatabase.class, "assessmentDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
        return INSTANCE;
    }
}
