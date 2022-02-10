package com.example.c196_project.Repositories;

import android.app.Application;

import com.example.c196_project.DAO.AssessmentDAO;

import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.Entities.AssessmentEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssessmentRepository {
    private final AssessmentDAO mAssessmentDAO;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public List<AssessmentEntity>mAllAssessments;


    public AssessmentRepository(Application application) {
        AssessmentDatabase db= AssessmentDatabase.getDatabase(application);
        mAssessmentDAO=db.assessmentDAO();
    }

    /**
     * Insert passed assessment
     */
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

    /**
     * Delete passed assessment
     * @param assessment passed assessment
     */
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

    /**
     * Update passed assessment
     * @param assessment passed assessment
     */
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


    /**
     * Get all assessments
     */


    public List<AssessmentEntity> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments= mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    /**
     * Delete all assessments
     */
    public List<AssessmentEntity> deleteAllAssessments() {
        databaseExecutor.execute(()-> {
            mAssessmentDAO.deleteAll();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
}
