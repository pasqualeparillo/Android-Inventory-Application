package com.example.c196_project.UI.Term;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.TermRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TermAdd  extends AppCompatActivity {
    final Calendar myDate= Calendar.getInstance();
    EditText startDateText;
    EditText endDateText;
    EditText editTermTitle;
    TermRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        editTermTitle=findViewById(R.id.termName);
        startDateText=(EditText) findViewById(R.id.termStart);
        endDateText=(EditText) findViewById(R.id.termEnd);
        repo=new TermRepository(getApplication());
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
                new DatePickerDialog(TermAdd.this,date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(TermAdd.this,date2,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
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
        TermEntity term;
        int newID = repo.getAllTerms().get(repo.getAllTerms().size() -1).getId() + 1;
        term = new TermEntity(newID, editTermTitle.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString());
        repo.insertTerm(term);
        Intent intent = new Intent(TermAdd.this, TermList.class);
        startActivity(intent);
    }
}
