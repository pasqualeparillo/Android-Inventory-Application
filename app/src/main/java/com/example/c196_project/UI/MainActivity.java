package com.example.c196_project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.Repositories.TermRepository;
import com.example.c196_project.UI.Assessment.AssessmentList;
import com.example.c196_project.UI.Course.CourseList;
import com.example.c196_project.UI.Term.TermList;

public class MainActivity extends AppCompatActivity {
    public static int alertCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void viewTerms(View view) throws InterruptedException {
        Intent nextScreen=new Intent(MainActivity.this, TermList.class);
        startActivity(nextScreen);
        TermRepository repo=new TermRepository(getApplication());
        CourseRepository courseRepo=new CourseRepository(getApplication());
        TermEntity term=new TermEntity(2, "test", "1/2/2022", "2/3/2022");
        CourseEntity course=new CourseEntity(1, "test", "type", "dateStart", "whatever", "whatever",false, 1, "", "", "");
        repo.insertTerm(term);
        courseRepo.insertCourse(course);
    }


}