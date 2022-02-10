package com.example.c196_project.UI.Course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.UI.Adapter.CourseAdapter;

import java.util.List;

public class CourseList extends AppCompatActivity {


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view2);
        CourseRepository repository = new CourseRepository(getApplication());
        List<CourseEntity> filteredCourses = repository.getAllCourses();
        CourseAdapter adapter = new CourseAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(filteredCourses);
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

    public void goToAddCourse(View view) {
        Intent nextPage = new Intent(CourseList.this, CourseAdd.class);
        startActivity(nextPage);
    }
}