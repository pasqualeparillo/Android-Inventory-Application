package com.example.c196_project.Repositories;

import android.app.Application;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.DB.ScheduleDatabase;
import com.example.c196_project.Entities.AssessmentEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssessmentRepository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private InstructorDAO mInstructorDAO;
    private TermDAO mTermDAO;

    private static int NUMBER_OF_THREADS=4;

    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public List<AssessmentEntity>mAllAssessments;
    public AssessmentRepository(Application application) {
        ScheduleDatabase db=ScheduleDatabase.getDatabase(application);
        mAssessmentDAO=db.assessmentDAO();
        mCourseDAO=db.courseDAO();
        mInstructorDAO=db.instructorDAO();
        mTermDAO=db.termDAO();
    }
    public void insertAssessment(AssessmentEntity assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateAssessment(AssessmentEntity assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AssessmentEntity> getAllAssessments(AssessmentEntity assessment) {
        databaseExecutor.execute(()-> {
           mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
}
