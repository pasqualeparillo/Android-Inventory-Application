package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;

public class CourseDetail extends AppCompatActivity {
    EditText editCourseTitle;
    EditText editCourseID;
    EditText editCourseType;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editCourseNote;
    EditText editCourseStatus;
    String title;
    String type;
    String startDate;
    String endDate;
    String status;
    String note;
    int courseID;
    CourseRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        editCourseTitle=findViewById(R.id.editCourseTitle);
        editCourseType=findViewById(R.id.editCourseType);
        editCourseStart=findViewById(R.id.editCourseStart);
        editCourseEnd=findViewById(R.id.editCourseEnd);
        editCourseNote=findViewById(R.id.editCourseNote);
        editCourseStatus=findViewById(R.id.editCourseStatus);
        editCourseID=findViewById(R.id.editCourseID);

        courseID=getIntent().getIntExtra("courseID", -1);
        title=getIntent().getStringExtra("title");
        type=getIntent().getStringExtra("title");
        startDate=getIntent().getStringExtra("startDate");
        endDate=getIntent().getStringExtra("endDate");
        status=getIntent().getStringExtra("status");
        note=getIntent().getStringExtra("note");

        editCourseID.setText(Integer.toString(courseID));
        editCourseTitle.setText(title);
        editCourseType.setText(type);
        editCourseStart.setText(startDate);
        editCourseEnd.setText(endDate);
        editCourseStatus.setText(status);
        editCourseNote.setText(note);

        repo=new CourseRepository(getApplication());
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
    }
}