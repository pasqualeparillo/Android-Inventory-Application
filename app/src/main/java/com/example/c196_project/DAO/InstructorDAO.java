package com.example.c196_project.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_project.Entities.InstructorEntity;

import java.util.List;

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(InstructorEntity instructor);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<InstructorEntity> instructor);

    @Delete
    void delete(InstructorEntity instructor);

    @Query("DELETE FROM Instructors")
    void deleteAll();

    @Query("SELECT * FROM Instructors ORDER BY instructor_id ASC")
    List<InstructorEntity> getAllInstructors();

    @Update
    void update(InstructorEntity instructor);
}
