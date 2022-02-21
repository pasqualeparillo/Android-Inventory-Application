package com.example.c196_project.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessment);

    @Query("SELECT * FROM Assessments WHERE assessment_id = :assessmentID ORDER BY assessment_id")
    AssessmentEntity getAssessment(int assessmentID);

    @Delete
    void delete(AssessmentEntity assessment);

    @Update
    void update(AssessmentEntity assessment);

    @Query("DELETE FROM Assessments")
    void deleteAll();

    @Query("SELECT * FROM Assessments ORDER BY assessment_id ASC")
    List<AssessmentEntity> getAllAssessments();

}
