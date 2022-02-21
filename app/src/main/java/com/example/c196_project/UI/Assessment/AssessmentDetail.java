package com.example.c196_project.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.DB.TermDatabase;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.UI.Course.CourseDetail;

public class AssessmentDetail extends AppCompatActivity {
    AssessmentDatabase assessmentDB;
    EditText assessmentTitle;
    EditText assessmentStart;
    EditText assessmentEnd;
    Integer assessmentID;
    Integer courseID;
    String title;
    String type;
    String startDate;
    String endDate;
    AssessmentRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        assessmentDB = AssessmentDatabase.getDatabase(getApplicationContext());
        assessmentID=getIntent().getIntExtra("assessmentID", -1);
        courseID=getIntent().getIntExtra("courseID", -1);
        assessmentTitle=findViewById(R.id.editAssessmentTitle);
        assessmentStart=findViewById(R.id.editAssessmentStartDate);
        assessmentEnd=findViewById(R.id.editAssessmentEndDate);
        type=getIntent().getStringExtra("type");
        repo=new AssessmentRepository(getApplication());

        String[] arraySpinner = new String[] {
                "OA", "PA"
        };
        Spinner s = (Spinner) findViewById(R.id.editAssessmentType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(type);
        s.setSelection(spinnerPosition);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = s.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setValues();
    }
    public void setValues() {
        AssessmentEntity assessment = assessmentDB.assessmentDAO().getAssessment(assessmentID);
        assessmentTitle.setText(assessment.getAssessment_title());
        assessmentStart.setText(assessment.getAssessment_start_date());
        assessmentEnd.setText(assessment.getAssessment_end_date());
    }
    public void saveButton(View view) {
        title=assessmentTitle.getText().toString();
        startDate=assessmentStart.getText().toString();
        endDate=assessmentEnd.getText().toString();
        AssessmentEntity assessment;
        assessment = new AssessmentEntity(assessmentID, title, type, startDate, endDate, courseID);
        repo.updateAssessment(assessment);
        Intent nextPage = new Intent(AssessmentDetail.this, CourseDetail.class);
        nextPage.putExtra("courseID", courseID);
        startActivity(nextPage);
    }
}