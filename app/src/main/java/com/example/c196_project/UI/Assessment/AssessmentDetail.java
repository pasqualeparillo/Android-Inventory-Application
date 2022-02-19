package com.example.c196_project.UI.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetail extends AppCompatActivity {
    EditText editAssessmentTitle;
    EditText editAssessmentID;
    EditText editAssessmentType;
    EditText editAssessmentStart;
    EditText editAssessmentEnd;
    EditText editAssessmentCourseID;
    Spinner courseSpinner;
    String title;
    String type;
    String start;
    String end;
    int courseID;
    int assessmentID;
    AssessmentRepository assessmentRepo;
    CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        editAssessmentTitle=findViewById(R.id.editAssessmentTitle);
        editAssessmentID=findViewById(R.id.editAssessmentID);
        editAssessmentType=findViewById(R.id.editAssessmentType);
        editAssessmentStart=findViewById(R.id.editAssessmentStart);
        editAssessmentEnd=findViewById(R.id.editAssessmentEnd);
        editAssessmentCourseID=findViewById(R.id.editAssessmentCourseID);

        title=getIntent().getStringExtra("title");
        type=getIntent().getStringExtra("type");
        start=getIntent().getStringExtra("startDate");
        end=getIntent().getStringExtra("endDate");
        courseID=getIntent().getIntExtra("courseID", -1);
        assessmentID=getIntent().getIntExtra("assessmentID", -1);
        editAssessmentTitle.setText(title);
        editAssessmentStart.setText(start);
        editAssessmentEnd.setText(end);
        editAssessmentID.setText(Integer.toString(assessmentID));
        editAssessmentCourseID.setText(Integer.toString(courseID));
        editAssessmentType.setText(type);
        courseRepository = new CourseRepository(getApplication());
        assessmentRepo=new AssessmentRepository(getApplication());
    }




    public void saveButton(View view) {
        AssessmentEntity assessment;
        if(assessmentID == -1) {
            int newID = assessmentRepo.getAllAssessments().get(assessmentRepo.getAllAssessments().size() -1).getCourse_id() + 1;
            assessment = new AssessmentEntity(newID, editAssessmentTitle.getText().toString(),editAssessmentType.getText().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), Integer.parseInt(editAssessmentCourseID.getText().toString()));
            assessmentRepo.insertAssessment(assessment);
        } else {
            assessment = new AssessmentEntity(assessmentID, editAssessmentTitle.getText().toString(),editAssessmentType.getText().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), Integer.parseInt(editAssessmentCourseID.getText().toString()));
            assessmentRepo.updateAssessment(assessment);
        }
    }
}