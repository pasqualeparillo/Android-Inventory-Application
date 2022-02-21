package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196_project.DB.CourseDatabase;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.UI.Adapter.AssessmentAdapter;
import com.example.c196_project.UI.Assessment.AssessmentAdd;
import com.example.c196_project.UI.Term.TermDetail;

import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends AppCompatActivity {
    CourseDatabase courseDB;
    EditText editCourseTitle;
    EditText editCourseType;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editCourseNote;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    String courseStatus;

    int courseID;
    CourseRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        courseDB = CourseDatabase.getDatabase(getApplicationContext());
        courseID = getIntent().getIntExtra("courseID", -1);
        editCourseTitle=findViewById(R.id.editAssessmentTitle);
        editCourseType=findViewById(R.id.editCourseType);
        editCourseStart=findViewById(R.id.editCourseStart);
        editCourseEnd=findViewById(R.id.editCourseEnd);
        editCourseNote=findViewById(R.id.editCourseNote);
        editInstructorName=findViewById(R.id.editInstructorName);
        editInstructorEmail=findViewById(R.id.editInstructorEmail);
        editInstructorPhone=findViewById(R.id.editInstructorPhone);

        setValues();

        String[] arraySpinner = new String[] {
                "In Progress", "Completed", "Dropped", "Plan to Take"
        };
        Spinner s = (Spinner) findViewById(R.id.editAssessmentType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(courseStatus);
        s.setSelection(spinnerPosition);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseStatus = s.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        repo=new CourseRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.assessmentRecycler);
        AssessmentRepository repository = new AssessmentRepository(getApplication());
        List<AssessmentEntity> allAssessments = repository.getAllAssessments();
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity assessment: allAssessments) {
            if(assessment.getCourse_id() == courseID) {
                filteredAssessments.add(assessment);
            }
        }

        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(getApplicationContext());
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(filteredAssessments);
    }
    public void setValues() {
        CourseEntity course = courseDB.courseDAO().getCourse(courseID);
        editCourseTitle.setText(course.getCourse_title());
        editCourseType.setText(course.getCourse_status());
        editCourseStart.setText(course.getCourse_start_date());
        editCourseEnd.setText(course.getCourse_end_date());
        editCourseNote.setText(course.getCourse_note());
        editInstructorName.setText(course.getInstructor_name());
        editInstructorEmail.setText(course.getInstructor_email());
        editInstructorPhone.setText(course.getInstructor_phone());
    }
    public void saveButton(View view) {
        CourseEntity course;
        if(courseID == -1) {
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() -1).getCourse_id() + 1;
            //course = new CourseEntity();
            //repo.insertCourse(course);
        } else {
            //course = new CourseEntity();
            //repo.updateCourse(course);
        }
        Intent nextPage = new Intent(CourseDetail.this, TermDetail.class);
        nextPage.putExtra("termID", getIntent().getIntExtra("termID", -1));
        nextPage.putExtra("courseID", courseID);
        startActivity(nextPage);
    }

    public void goToAddAssessments(View view) {
        Intent nextPage = new Intent(CourseDetail.this, AssessmentAdd.class);
        nextPage.putExtra("courseID", courseID);
        nextPage.putExtra("termID", getIntent().getIntExtra("termID", -1));
        startActivity(nextPage);
    }
}