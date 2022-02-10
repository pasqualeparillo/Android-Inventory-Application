package com.example.c196_project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.Repositories.InstructorRepository;
import com.example.c196_project.Repositories.TermRepository;
import com.example.c196_project.UI.Assessment.AssessmentList;
import com.example.c196_project.UI.Course.CourseList;
import com.example.c196_project.UI.Instructor.InstructorList;
import com.example.c196_project.UI.Term.TermList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewCourses(View view) throws InterruptedException {
        Intent nextScreen=new Intent(MainActivity.this, CourseList.class);
        startActivity(nextScreen);
        CourseRepository repo=new CourseRepository(getApplication());
        CourseEntity course=new CourseEntity(2, "test", "type", "dateStart", "dateEnd", "test", 2, 2);
        repo.insertCourse(course);
    }
    public void viewAssessments(View view) throws InterruptedException {
        Intent nextScreen=new Intent(MainActivity.this, AssessmentList.class);
        startActivity(nextScreen);
        AssessmentRepository repo=new AssessmentRepository(getApplication());
        AssessmentEntity assessment=new AssessmentEntity(2, "test", "type", "dateStart", "dateEnd", 1);
        AssessmentEntity assessment2=new AssessmentEntity(1, "test", "type", "dateStart", "dateEnd", 1);
        repo.insertAssessment(assessment);
        repo.insertAssessment(assessment2);
    }
    public void viewTerms(View view) throws InterruptedException {
        Intent nextScreen=new Intent(MainActivity.this, TermList.class);
        startActivity(nextScreen);
        TermRepository repo=new TermRepository(getApplication());
        TermEntity term=new TermEntity(2, "test", "type", "dateStart");
        repo.insertTerm(term);
    }
    public void viewInstructors(View view) throws InterruptedException {
        Intent nextScreen=new Intent(MainActivity.this, InstructorList.class);
        startActivity(nextScreen);
        InstructorRepository repo=new InstructorRepository(getApplication());
        InstructorEntity instructor=new InstructorEntity(2, "test", "type", "dateStart", "dateEnd");
        repo.insertInstructor(instructor);
    }

}