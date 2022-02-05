package com.example.c196_project.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessments")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int id;
    @ColumnInfo(name = "assessment_title")
    private String assessment_title;
    @ColumnInfo(name = "assessment_type")
    private String assessment_type;
    @ColumnInfo(name = "assessment_start_date")
    private String assessment_start_date;
    @ColumnInfo(name = "assessment_end_date")
    private String assessment_end_date;
    @ColumnInfo(name = "course_id")
    private int course_id;

    public AssessmentEntity(int id, String assessment_title, String assessment_type, String assessment_start_date, String assessment_end_date, int course_id) {
        this.id = id;
        this.assessment_title = assessment_title;
        this.assessment_type = assessment_type;
        this.assessment_start_date = assessment_start_date;
        this.assessment_end_date = assessment_end_date;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessment_title() {
        return assessment_title;
    }

    public void setAssessment_title(String assessment_title) {
        this.assessment_title = assessment_title;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public String getAssessment_start_date() {
        return assessment_start_date;
    }

    public void setAssessment_start_date(String assessment_start_date) {
        this.assessment_start_date = assessment_start_date;
    }

    public String getAssessment_end_date() {
        return assessment_end_date;
    }

    public void setAssessment_end_date(String assessment_end_date) {
        this.assessment_end_date = assessment_end_date;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
