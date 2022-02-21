package com.example.c196_project.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_project.Entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CourseEntity> course);

    @Query("SELECT * FROM courses WHERE course_ID = :courseID ORDER BY course_id")
    CourseEntity getCourse(int courseID);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM Courses")
    void deleteAll();

    @Query("SELECT * FROM Courses ORDER BY course_id ASC")
    List<CourseEntity> getAllCourses();

    @Update
    void update(CourseEntity course);
}
