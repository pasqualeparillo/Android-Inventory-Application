package com.example.c196_project.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.UI.Adapter.AssessmentAdapter;
import com.example.c196_project.UI.Course.CourseAdd;
import com.example.c196_project.UI.Course.CourseList;

import java.util.ArrayList;
import java.util.List;

public class AssessmentList extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AssessmentRepository repository = new AssessmentRepository(getApplication());
        List<AssessmentEntity> filteredAssessments = repository.getAllAssessments();
        AssessmentAdapter adapter = new AssessmentAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(filteredAssessments);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void goToAddAssessment(View view) {
        Intent nextPage = new Intent(AssessmentList.this, AssessmentAdd.class);
        startActivity(nextPage);
    }
}