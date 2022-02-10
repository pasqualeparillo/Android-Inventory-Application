package com.example.c196_project.Repositories;

import android.app.Application;

import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.DB.CourseDatabase;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository {
    private final CourseDAO mCourseDAO;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public List<CourseEntity> mAllCourses;

    public CourseRepository(Application application) {
        CourseDatabase db= CourseDatabase.getDatabase(application);
        mCourseDAO=db.courseDAO();
    }


    public void insertCourse(CourseEntity course) {
        databaseExecutor.execute(()-> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete passed course
     * @param course passed course
     */
    public void deleteCourse(CourseEntity course) {
        databaseExecutor.execute(()-> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update passed assessment
     * @param course passed course
     */
    public void updateAssessment(CourseEntity course) {
        databaseExecutor.execute(()-> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all courses
     */
    public List<CourseEntity> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses= mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    /**
     * Delete all courses
     */
    public List<CourseEntity> deleteAllAssessments() {
        databaseExecutor.execute(()-> {
            mCourseDAO.deleteAll();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
}
