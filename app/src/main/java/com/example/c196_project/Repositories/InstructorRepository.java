package com.example.c196_project.Repositories;

import android.app.Application;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.DB.InstructorDatabase;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstructorRepository {
    private final InstructorDAO mInstructorDAO;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public List<InstructorEntity> mAllInstructors;

    public InstructorRepository(Application application) {
        InstructorDatabase db= InstructorDatabase.getDatabase(application);
        mInstructorDAO=db.instructorDAO();

    }

    public void insertInstructor(InstructorEntity instructor) {
        databaseExecutor.execute(()-> {
            mInstructorDAO.insert(instructor);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete passed instructor
     * @param instructor passed instructor
     */
    public void deleteInstructor(InstructorEntity instructor) {
        databaseExecutor.execute(()-> {
            mInstructorDAO.delete(instructor);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update passed instructor
     * @param instructor passed instructor
     */
    public void updateInstructor(InstructorEntity instructor) {
        databaseExecutor.execute(()-> {
            mInstructorDAO.update(instructor);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all instructors
     */
    public List<InstructorEntity> getAllInstructors(InstructorEntity instructor) {
        databaseExecutor.execute(()-> {
            mInstructorDAO.getAllInstructors();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;
    }
    /**
     * Delete all courses
     */
    public List<InstructorEntity> deleteAllInstructors() {
        databaseExecutor.execute(()-> {
            mInstructorDAO.deleteAll();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;
    }
}
