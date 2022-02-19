package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.Repositories.TermRepository;
import com.example.c196_project.UI.Term.TermAdd;
import com.example.c196_project.UI.Term.TermDetail;
import com.example.c196_project.UI.Term.TermList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseAdd extends AppCompatActivity {
    CourseRepository repo;
    final Calendar myDate= Calendar.getInstance();
    int termID;
    EditText startDateText;
    EditText endDateText;
    EditText courseTitle;
    EditText courseNote;
    Switch courseAlert;
    String courseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        termID=getIntent().getIntExtra("termID", -1);
        repo=new CourseRepository(getApplication());
        courseTitle=findViewById(R.id.courseTitle);
        courseNote=findViewById(R.id.courseNote);
        courseAlert=findViewById(R.id.addCourseAlert);
        startDateText=(EditText) findViewById(R.id.startDate);
        endDateText=(EditText) findViewById(R.id.endDate);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH,month);
                myDate.set(Calendar.DAY_OF_MONTH,day);
                updateStartLabel();
            }
        };
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseAdd.this,date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseAdd.this,date2,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        String[] arraySpinner = new String[] {
                "In Progress", "Completed", "Dropped", "Plan to Take"
        };
        Spinner s = (Spinner) findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseStatus = s.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void updateStartLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        startDateText.setText(dateFormat.format(myDate.getTime()));
    }

    private void updateEndLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        endDateText.setText(dateFormat.format(myDate.getTime()));
    }
    public void saveButton(View view) {
        boolean alert = courseAlert.isChecked();
        CourseEntity course;
        if(repo.getAllCourses().size() == 0) {
            course = new CourseEntity(1, courseTitle.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString(), courseStatus, courseNote.getText().toString(),alert, termID, "", "", "");
        } else {
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() -1).getCourse_id() + 1;
            course = new CourseEntity(newID, courseTitle.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString(), courseStatus, courseNote.getText().toString(),alert, termID, "", "", "");
        }
        repo.insertCourse(course);
        Intent intent = new Intent(CourseAdd.this, TermDetail.class);
        intent.putExtra("termID", termID);
        intent.putExtra("title", getIntent().getStringExtra("termTitle"));
        intent.putExtra("startDate", getIntent().getStringExtra("termStart"));
        intent.putExtra("endDate", getIntent().getStringExtra("termEnd"));

        startActivity(intent);
    }
}