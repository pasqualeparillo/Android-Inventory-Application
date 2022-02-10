package com.example.c196_project.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_project.Entities.AssessmentEntity;
import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.R;
import com.example.c196_project.UI.Assessment.AssessmentDetail;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("courseID", current.getCourse_id());
                    intent.putExtra("title", current.getCourse_title());
                    intent.putExtra("startDate", current.getCourse_start_date());
                    intent.putExtra("endDate", current.getCourse_end_date());
                    intent.putExtra("status", current.getCourse_status());
                    intent.putExtra("note", current.getCourse_note());
                    intent.putExtra("courseID", current.getCourse_id());
                    context.startActivity(intent);
                }
            });
        }
    }


    private List<CourseEntity> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView=mInflater.inflate(R.layout.activity_course_item, parent, false);
        return new CourseAdapter.CourseViewHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!= null) {
            CourseEntity current = mCourses.get(position);
            String title = current.getCourse_title();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No course title");
        }
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mCourses!=null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }
}
