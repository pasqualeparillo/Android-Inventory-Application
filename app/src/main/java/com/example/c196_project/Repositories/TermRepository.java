package com.example.c196_project.Repositories;

import android.app.Application;

import com.example.c196_project.DAO.AssessmentDAO;
import com.example.c196_project.DAO.CourseDAO;
import com.example.c196_project.DAO.InstructorDAO;
import com.example.c196_project.DAO.TermDAO;
import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.DB.TermDatabase;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TermRepository {
    private final TermDAO mTermDAO;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public List<TermEntity> mAllTerms;


    public TermRepository(Application application) {
        TermDatabase db= TermDatabase.getDatabase(application);
        mTermDAO=db.termDAO();

    }

    public void insertTerm(TermEntity term) {
        databaseExecutor.execute(()-> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete passed term
     * @param term passed terms
     */
    public void deleteTerm(TermEntity term) {
        databaseExecutor.execute(()-> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update passed term
     * @param term passed course
     */
    public void updateTerm(TermEntity term) {
        databaseExecutor.execute(()-> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all terms
     */
    public List<TermEntity> getAllTerms(TermEntity course) {
        databaseExecutor.execute(()-> {
            mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    /**
     * Delete all terms
     */
    public List<TermEntity> deleteAllTerm() {
        databaseExecutor.execute(()-> {
            mTermDAO.deleteAll();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

}
