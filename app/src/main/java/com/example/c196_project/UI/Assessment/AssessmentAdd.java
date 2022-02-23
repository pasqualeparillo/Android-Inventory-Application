package com.example.c196_project.UI.Assessment;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.UI.Course.CourseAdd;
import com.example.c196_project.UI.Course.CourseDetail;
import com.example.c196_project.UI.Term.TermAdd;
import com.example.c196_project.UI.Term.TermDetail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentAdd extends AppCompatActivity {
    final Calendar myDate= Calendar.getInstance();
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
        assessmentStart=(EditText) findViewById(R.id.editAssessmentStartDate);
        assessmentEnd=(EditText) findViewById(R.id.editAssessmentEndDate);
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

        //Date Picker Start
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
                new DatePickerDialog(AssessmentAdd.this,date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AssessmentAdd.this,date2,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
    public void saveButton(View view) {
        String name=assessmentTitle.getText().toString();
        String sDate=assessmentStart.getText().toString();
        String eDate=assessmentEnd.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date startDate = null;
        Date endDate = null;

        try{
            startDate = simpleDateFormat.parse(sDate);
            endDate = simpleDateFormat.parse(eDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startDate.after(endDate)) {
            Toast.makeText(this, "Start date cant be after the end date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sDate.trim().isEmpty()) {
            Toast.makeText(this, "Start date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (eDate.trim().isEmpty()) {
            Toast.makeText(this, "End date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        saveAssessment();
    }
    public void saveAssessment() {
        String name=assessmentTitle.getText().toString();
        String sDate=assessmentStart.getText().toString();
        String eDate=assessmentEnd.getText().toString();
        AssessmentEntity assessment;
        if(repo.getAllAssessments().size() == 0) {
            assessment = new AssessmentEntity(1, name, type, sDate, eDate, courseID);
        } else {
            int newID = repo.getAllAssessments().get(repo.getAllAssessments().size() -1).getId() + 1;
            assessment = new AssessmentEntity(newID, name, type, sDate, eDate, courseID);
        }
        repo.insertAssessment(assessment);
        Intent intent = new Intent(AssessmentAdd.this, CourseDetail.class);
        intent.putExtra("termID", termID);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}
