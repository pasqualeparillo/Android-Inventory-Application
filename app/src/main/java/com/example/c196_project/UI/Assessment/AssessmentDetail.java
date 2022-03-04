package com.example.c196_project.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import com.example.c196_project.UI.MainActivity;
import com.example.c196_project.UI.Receiver;
import com.example.c196_project.UI.Term.TermDetail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        getMenuInflater().inflate(R.menu.menu_alert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = assessmentTitle.getText().toString();
        String sDate = assessmentStart.getText().toString();
        String eDate = assessmentEnd.getText().toString();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save:
                saveButton();
                return true;
            case R.id.startAlert:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                Date startDate = null;
                Long curDate = new Date().getTime();
                try{
                    startDate = simpleDateFormat.parse(sDate);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent notifyStart = new Intent(AssessmentDetail.this, Receiver.class);
                notifyStart.putExtra("key", "Your " + name + " assessment is starting today on: " + startDate);
                PendingIntent pendingStart = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.alertCount, notifyStart, 0);
                AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, curDate, pendingStart);
                return true;
            case R.id.endAlert:
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                Date endDate = null;
                Long curDate2 = new Date().getTime();
                try{
                    endDate = simpleDateFormat2.parse(eDate);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent notifyStart2 = new Intent(AssessmentDetail.this, Receiver.class);
                notifyStart2.putExtra("key", "Your " + name + " assessment is ending today on: " + endDate);
                PendingIntent pendingStart2 = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.alertCount, notifyStart2, 0);
                AlarmManager alarm2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarm2.set(AlarmManager.RTC_WAKEUP, curDate2, pendingStart2);
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

    public void saveButton() {
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
        if (sDate.trim().isEmpty()) {
            Toast.makeText(this, "Start date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (eDate.trim().isEmpty()) {
            Toast.makeText(this, "End date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startDate.after(endDate)) {
            Toast.makeText(this, "Start date cant be after the end date", Toast.LENGTH_SHORT).show();
            return;
        }
        saveAssessment();
    }

    public void saveAssessment() {
        title=assessmentTitle.getText().toString();
        startDate=assessmentStart.getText().toString();
        endDate=assessmentEnd.getText().toString();
        AssessmentEntity assessment;
        assessment = new AssessmentEntity(assessmentID, title, type, startDate, endDate, courseID);
        repo.updateAssessment(assessment);
        Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}