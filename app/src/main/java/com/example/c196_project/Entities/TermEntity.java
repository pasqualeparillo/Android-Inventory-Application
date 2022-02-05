package com.example.c196_project.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int id;
    @ColumnInfo(name="term_title")
    private String title;
    @ColumnInfo(name="term_start_date")
    private String startDate;
    @ColumnInfo(name="term_end_date")
    private String endDate;

    public TermEntity(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
