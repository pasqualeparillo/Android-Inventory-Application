package com.example.c196_project.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196_project.DB.AssessmentDatabase;
import com.example.c196_project.DB.TermDatabase;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.UI.Course.CourseDetail;
import com.example.c196_project.UI.Term.TermDetail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    final Calendar myDate= Calendar.getInstance();
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
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentTitle = findViewById(R.id.editAssessmentTitle);
        assessmentStart = findViewById(R.id.editAssessmentStartDate);
        assessmentEnd = findViewById(R.id.editAssessmentEndDate);
        type = getIntent().getStringExtra("type");
        repo = new AssessmentRepository(getApplication());

        String[] arraySpinner = new String[]{
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
        //Start Date picker

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH,month);
                myDate.set(Calendar.DAY_OF_MONTH,day);
                updateStartLabel();
            }
        };
        assessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetail.this,date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH,month);
                myDate.set(Calendar.DAY_OF_MONTH,day);
                updateEndLabel();
            }
        };
        assessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetail.this,date2,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateStartLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        assessmentStart.setText(dateFormat.format(myDate.getTime()));
    }
    private void updateEndLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        assessmentEnd.setText(dateFormat.format(myDate.getTime()));
    }

    public void setValues() {
        AssessmentEntity assessment = assessmentDB.assessmentDAO().getAssessment(assessmentID);
        assessmentTitle.setText(assessment.getAssessment_title());
        assessmentStart.setText(assessment.getAssessment_start_date());
        assessmentEnd.setText(assessment.getAssessment_end_date());
    }

    // Menu Start
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Take a look at my Course Notes.");
                sendIntent.putExtra(Intent.EXTRA_TITLE, assessmentTitle.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notify:
                return true;

            case R.id.delete:
                AssessmentRepository assessmentRepository = new AssessmentRepository(getApplication());
                for (AssessmentEntity assessment : assessmentRepository.getAllAssessments()) {
                    if (assessment.getId() == assessmentID) {
                        assessmentRepository.deleteAssessment(assessment);
                        Intent nextPage = new Intent(AssessmentDetail.this, CourseDetail.class);
                        nextPage.putExtra("courseID", courseID);
                        startActivity(nextPage);
                    }
                }
        return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Menu End
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