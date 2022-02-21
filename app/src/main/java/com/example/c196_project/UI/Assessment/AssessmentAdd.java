package com.example.c196_project.UI.Assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.UI.Course.CourseAdd;
import com.example.c196_project.UI.Course.CourseDetail;
import com.example.c196_project.UI.Term.TermDetail;

public class AssessmentAdd extends AppCompatActivity {
    AssessmentRepository repo;
    EditText assessmentStart;
    EditText assessmentEnd;
    EditText assessmentTitle;
    Spinner assessmentType;
    Integer courseID;
    Integer termID;
    String title;
    String type;
    String startDate;
    String endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        courseID=getIntent().getIntExtra("courseID", -1);
        termID=getIntent().getIntExtra("termID", -1);
        assessmentTitle=findViewById(R.id.editAssessmentTitle);
        assessmentType=findViewById(R.id.editAssessmentType);
        assessmentStart=findViewById(R.id.editAssessmentStartDate);
        assessmentEnd=findViewById(R.id.editAssessmentEndDate);
        repo=new AssessmentRepository(getApplication());

        String[] arraySpinner = new String[] {
                "OA", "PA"
        };
        Spinner s = (Spinner) findViewById(R.id.editAssessmentType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = s.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    public void saveButton(View view) {
        title=assessmentTitle.getText().toString();
        startDate=assessmentStart.getText().toString();
        endDate=assessmentEnd.getText().toString();
        AssessmentEntity assessment;
        if(repo.getAllAssessments().size() == 0) {
            assessment = new AssessmentEntity(1, title, type, startDate, endDate, courseID);
        } else {
            int newID = repo.getAllAssessments().get(repo.getAllAssessments().size() -1).getId() + 1;
            assessment = new AssessmentEntity(newID, title, type, startDate, endDate, courseID);
        }
        repo.insertAssessment(assessment);
        Intent intent = new Intent(AssessmentAdd.this, CourseDetail.class);
        intent.putExtra("termID", termID);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}
