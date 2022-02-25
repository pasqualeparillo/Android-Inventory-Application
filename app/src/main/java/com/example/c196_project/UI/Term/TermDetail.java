package com.example.c196_project.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_project.DB.TermDatabase;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.Repositories.TermRepository;
import com.example.c196_project.UI.Adapter.CourseAdapter;
import com.example.c196_project.UI.Course.CourseAdd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {
    int termID;
    final Calendar myDate= Calendar.getInstance();
    TermDatabase termDB;
    String termTitle;
    String termStart;
    String termEnd;
    TextView termTitleView;
    EditText termStartView;
    EditText termEndView;
    TermRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        repo = new TermRepository(getApplication());
        termDB = TermDatabase.getDatabase(getApplicationContext());
        termID=getIntent().getIntExtra("termID", -1);
        termTitleView=findViewById(R.id.termTitleView);
        termStartView=findViewById(R.id.termStartView);
        termEndView=findViewById(R.id.termEndView);

        //Get courses for term.
        RecyclerView recyclerView = findViewById(R.id.recycler_view5);
        CourseRepository repository = new CourseRepository(getApplication());
        List<CourseEntity> allCourses = repository.getAllCourses();
        List<CourseEntity> filteredCourses = new ArrayList<>();
        for(CourseEntity course: allCourses) {
            if(course.getTerm_id() == termID) {
                filteredCourses.add(course);
            }
        }
        CourseAdapter adapter = new CourseAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(filteredCourses);
        setValues();
        //Start Date Picker
        setupDatePicker();
    }
    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH,month);
                myDate.set(Calendar.DAY_OF_MONTH,day);
                updateStartLabel();
            }
        };
        termStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetail.this,date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
        termEndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetail.this,date2,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateStartLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        termStartView.setText(dateFormat.format(myDate.getTime()));
    }

    private void updateEndLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        termEndView.setText(dateFormat.format(myDate.getTime()));
    }

    public void setValues() {
        TermEntity term = termDB.termDAO().getTerm(termID);
        Log.d("TermID", Integer.toString(termID));
        termTitleView.setText(term.getTitle());
        termStartView.setText(term.getStartDate());
        termEndView.setText(term.getEndDate());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_delete, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save:
                saveButton();
                return true;
            case R.id.delete:
                boolean deleteTerm = false;
                CourseRepository courseRepository = new CourseRepository(getApplication());
                TermRepository termRepository = new TermRepository(getApplication());
                List<CourseEntity> allCourses = courseRepository.getAllCourses();
                for(CourseEntity course: allCourses) {
                    if(course.getTerm_id() == termID) {
                        deleteTerm = true;
                        Toast.makeText(TermDetail.this,"Can't delete a term with courses", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(!deleteTerm) {
                    for(TermEntity term: termRepository.getAllTerms()) {
                        if(term.getId() == termID) {
                            termRepository.deleteTerm(term);
                            Toast.makeText(TermDetail.this,"Term Deleted", Toast.LENGTH_LONG).show();
                            Intent nextPage = new Intent(TermDetail.this, TermList.class);
                            startActivity(nextPage);
                        }
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void goToAddCourse(View view) {
        Intent nextPage = new Intent(TermDetail.this, CourseAdd.class);
        nextPage.putExtra("termID", termID);
        nextPage.putExtra("termTitle", termTitle);
        nextPage.putExtra("termStart", termStart);
        nextPage.putExtra("termEnd", termEnd);
        startActivity(nextPage);
    }

    public void saveButton() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String name = termTitleView.getText().toString();
        String sDate = termStartView.getText().toString();
        String eDate = termEndView.getText().toString();
        Date startDate = null;
        Date endDate = null;
        Log.d("Start", sDate);
        Log.d("End", eDate);
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

        saveTerm();
    }
    public void saveTerm() {
        Log.d("TermID", Integer.toString(termID));
        TermEntity term = new TermEntity(termID, termTitleView.getText().toString(), termStartView.getText().toString(), termEndView.getText().toString());
        repo.updateTerm(term);
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }
}