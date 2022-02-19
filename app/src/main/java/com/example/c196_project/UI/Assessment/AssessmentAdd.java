package com.example.c196_project.UI.Assessment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_project.R;

public class AssessmentAdd extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RadioButton rdb = (RadioButton) findViewById(R.id.radio_objective);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_objective:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_performance:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
