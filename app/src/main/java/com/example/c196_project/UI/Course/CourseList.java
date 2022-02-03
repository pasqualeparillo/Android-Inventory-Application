package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196_project.R;
import com.example.c196_project.UI.Assessment.AssessmentList;

public class CourseList extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
            return super.onOptionsItemSelected(item);
        }

    public void goToAssessmentList(View view) {
        Intent nextPage = new Intent(CourseList.this, CourseAdd.class);
        startActivity(nextPage);
    }
}