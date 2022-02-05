package com.example.c196_project.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instructors")
public class InstructorEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "instructor_id")
    private int instructor_id;
    @ColumnInfo(name = "instructor_name")
    private String instructor_name;
    @ColumnInfo(name="course_id")
    private String course_id;
    @ColumnInfo(name = "instructor_phone_number")
    private String instructor_phone_number;
    @ColumnInfo(name = "instructor_email")
    private String instructor_email;

    public InstructorEntity(int instructor_id, String instructor_name, String course_id, String instructor_phone_number, String instructor_email) {
        this.instructor_id = instructor_id;
        this.instructor_name = instructor_name;
        this.course_id = course_id;
        this.instructor_phone_number = instructor_phone_number;
        this.instructor_email = instructor_email;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getInstructor_phone_number() {
        return instructor_phone_number;
    }

    public void setInstructor_phone_number(String instructor_phone_number) {
        this.instructor_phone_number = instructor_phone_number;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }
}
