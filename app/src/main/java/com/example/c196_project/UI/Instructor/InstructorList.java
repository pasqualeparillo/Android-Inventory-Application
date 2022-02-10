package com.example.c196_project.UI.Instructor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.Repositories.InstructorRepository;
import com.example.c196_project.UI.Adapter.CourseAdapter;
import com.example.c196_project.UI.Adapter.InstructorAdapter;
import com.example.c196_project.UI.Course.CourseAdd;
import com.example.c196_project.UI.Course.CourseList;

import java.util.List;

public class InstructorList extends AppCompatActivity {


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view3);
        InstructorRepository repository = new InstructorRepository(getApplication());
        List<InstructorEntity> filteredInstructors = repository.getAllInstructors();
        InstructorAdapter adapter = new InstructorAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setInstructors(filteredInstructors);
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

    public void goToAddInstructor(View view) {
        Intent nextPage = new Intent(InstructorList.this, InstructorAdd.class);
        startActivity(nextPage);
    }
}
