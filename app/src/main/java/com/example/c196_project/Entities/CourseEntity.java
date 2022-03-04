package com.example.c196_project.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "course_id")
    private int course_id;
    @ColumnInfo(name = "course_title")
    private String course_title;
    @ColumnInfo(name = "course_start_date")
    private String course_start_date;
    @ColumnInfo(name = "course_end_date")
    private String course_end_date;
    @ColumnInfo(name = "course_status")
    private String course_status;
    @ColumnInfo(name = "course_note")
    private String course_note;
    @ColumnInfo(name = "term_id")
    private int term_id;
    @ColumnInfo(name = "instructor_name")
    private String instructor_name;
    @ColumnInfo(name = "instructor_phone")
    private String instructor_phone;
    @ColumnInfo(name = "instructor_email")
    private String instructor_email;

    public CourseEntity(int course_id, String course_title, String course_start_date, String course_end_date, String course_status, String course_note,int term_id, String instructor_name, String instructor_phone, String instructor_email) {
        this.course_id = course_id;
        this.course_title = course_title;
        this.course_start_date = course_start_date;
        this.course_end_date = course_end_date;
        this.course_status = course_status;
        this.course_note = course_note;
        this.term_id = term_id;
        this.instructor_name = instructor_name;
        this.instructor_phone = instructor_phone;
        this.instructor_email = instructor_email;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_start_date() {
        return course_start_date;
    }

    public void setCourse_start_date(String course_start_date) {
        this.course_start_date = course_start_date;
    }

    public String getCourse_end_date() {
        return course_end_date;
    }

    public void setCourse_end_date(String course_end_date) {
        this.course_end_date = course_end_date;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getCourse_note() {
        return course_note;
    }

    public void setCourse_note(String course_note) {
        this.course_note = course_note;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getInstructor_phone() {
        return instructor_phone;
    }

    public void setInstructor_phone(String instructor_phone) {
        this.instructor_phone = instructor_phone;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }
}
