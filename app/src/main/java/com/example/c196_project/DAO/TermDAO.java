package com.example.c196_project.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<TermEntity> term);

    @Query("SELECT * FROM Terms WHERE term_id = :termID ORDER BY term_id")
    TermEntity getTerm(int termID);

    @Delete
    void delete(TermEntity term);

    @Query("DELETE FROM Terms")
    void deleteAll();

    @Query("SELECT * FROM Terms ORDER BY term_id ASC")
    List<TermEntity> getAllTerms();

    @Update
    void update(TermEntity term);
}
