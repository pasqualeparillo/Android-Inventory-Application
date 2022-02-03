package com.example.c196_project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_project.R;
import com.example.c196_project.UI.Course.CourseList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterApplication(View view) {
        Intent nextScreen=new Intent(MainActivity.this, CourseList.class);
        startActivity(nextScreen);
    }
}