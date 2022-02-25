package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.c196_project.DB.CourseDatabase;
import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.UI.Adapter.AssessmentAdapter;
import com.example.c196_project.UI.Assessment.AssessmentAdd;
import com.example.c196_project.UI.MainActivity;
import com.example.c196_project.UI.Receiver;
import com.example.c196_project.UI.Term.TermDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    Switch courseAlert;
    int courseID;
    int termID;
    CourseRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        courseDB = CourseDatabase.getDatabase(getApplicationContext());
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        editCourseTitle=findViewById(R.id.editAssessmentTitle);
        editCourseType=findViewById(R.id.editCourseType);
        editCourseStart=findViewById(R.id.editCourseStart);
        editCourseEnd=findViewById(R.id.editCourseEnd);
        editCourseNote=findViewById(R.id.editCourseNote);
        editInstructorName=findViewById(R.id.editInstructorName);
        editInstructorEmail=findViewById(R.id.editInstructorEmail);
        editInstructorPhone=findViewById(R.id.editInstructorPhone);
        courseAlert=findViewById(R.id.courseAlertToggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        editCourseStart.setText(course.getCourse_start_date());
        editCourseEnd.setText(course.getCourse_end_date());
        editCourseNote.setText(course.getCourse_note());
        editInstructorName.setText(course.getInstructor_name());
        editInstructorEmail.setText(course.getInstructor_email());
        editInstructorPhone.setText(course.getInstructor_phone());
    }

    // Menu Start
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = editCourseTitle.getText().toString();
        String sDate = editCourseStart.getText().toString();
        String eDate = editCourseEnd.getText().toString();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Take a look at my Course Notes.");
                sendIntent.putExtra(Intent.EXTRA_TITLE, editCourseNote.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
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
                Intent notifyStart = new Intent(CourseDetail.this, Receiver.class);
                notifyStart.putExtra("key", "Your " + name + " course is starting today on: " + startDate);
                PendingIntent pendingStart = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.alertCount, notifyStart, 0);
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
                Intent notifyStart2 = new Intent(CourseDetail.this, Receiver.class);
                notifyStart2.putExtra("key", "Your " + name + " course is starting today on: " + endDate);
                PendingIntent pendingStart2 = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.alertCount, notifyStart2, 0);
                AlarmManager alarm2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarm2.set(AlarmManager.RTC_WAKEUP, curDate2, pendingStart2);
                return true;
            case R.id.delete:
                boolean deleteCourse = false;
                AssessmentRepository assessmentRepository = new AssessmentRepository(getApplication());
                CourseRepository courseRepository = new CourseRepository(getApplication());
                List<AssessmentEntity> allAssessments = assessmentRepository.getAllAssessments();
                for(AssessmentEntity assessment: allAssessments) {
                    if(assessment.getCourse_id() == courseID) {
                        deleteCourse = true;
                        Toast.makeText(CourseDetail.this,"Can't delete a course with assessments", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(!deleteCourse) {
                    for(CourseEntity course: courseRepository.getAllCourses()) {
                        if(course.getCourse_id() == courseID) {

                            Intent nextPage = new Intent(CourseDetail.this, TermDetail.class);
                            nextPage.putExtra("termID", getIntent().getIntExtra("termID", -1));
                            courseRepository.deleteCourse(course);
                            Toast.makeText(CourseDetail.this,"Course Deleted", Toast.LENGTH_LONG).show();
                            startActivity(nextPage);

                        }
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Menu End
    public void goToAddAssessments(View view) {
        Intent nextPage = new Intent(CourseDetail.this, AssessmentAdd.class);
        nextPage.putExtra("courseID", courseID);
        nextPage.putExtra("termID", getIntent().getIntExtra("termID", -1));
        startActivity(nextPage);
    }


    public void saveButton(View view) {
        CourseEntity course;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String name = editCourseTitle.getText().toString();
        String sDate = editCourseStart.getText().toString();
        String eDate = editCourseEnd.getText().toString();
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

        if (editInstructorName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Instructor Name is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editInstructorEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Instructor Email is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editInstructorPhone.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Instructor Phone is required", Toast.LENGTH_SHORT).show();
            return;
        }
        saveCourse();
    }
    private void saveCourse() {
        boolean alert = courseAlert.isChecked();
        for(CourseEntity course: repo.getAllCourses()) {
            if(course.getCourse_id() == courseID) {
                CourseEntity courseUpdate = new CourseEntity(course.getCourse_id(), editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseType.getText().toString(), editCourseNote.getText().toString(), alert, course.getTerm_id(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString());
                repo.updateCourse(courseUpdate);
            }
        }
        Intent nextPage = new Intent(CourseDetail.this, TermDetail.class);
        nextPage.putExtra("termID", getIntent().getIntExtra("termID", -1));
        nextPage.putExtra("courseID", courseID);
        startActivity(nextPage);
    }
}