package com.example.c196_project.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.Repositories.AssessmentRepository;
import com.example.c196_project.Repositories.CourseRepository;
import com.example.c196_project.UI.Adapter.AssessmentAdapter;
import com.example.c196_project.UI.Adapter.CourseAdapter;
import com.example.c196_project.UI.Course.CourseAdd;

import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    int termID;
    String termTitle;
    String termStart;
    String termEnd;
    TextView termTitleView;
    TextView termStartView;
    TextView termEndView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        termID=getIntent().getIntExtra("termID", -1);
        termTitle=getIntent().getStringExtra("title");
        termStart=getIntent().getStringExtra("startDate");
        termEnd=getIntent().getStringExtra("endDate");
        termTitleView=findViewById(R.id.termName);
        termStartView=findViewById(R.id.termStart);
        termEndView=findViewById(R.id.termEnd);
        termTitleView.setText(termTitle);
        termStartView.setText(termStart);
        termEndView.setText(termEnd);
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

        Intent nextPage = new Intent(TermDetail.this, CourseAdd.class);
        nextPage.putExtra("termID", termID);
        nextPage.putExtra("termTitle", termTitle);
        nextPage.putExtra("termStart", termStart);
        nextPage.putExtra("termEnd", termEnd);
        startActivity(nextPage);
    }
}