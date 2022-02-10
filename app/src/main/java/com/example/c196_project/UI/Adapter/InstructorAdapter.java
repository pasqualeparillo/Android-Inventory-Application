package com.example.c196_project.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_project.Entities.CourseEntity;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.R;
import com.example.c196_project.UI.Assessment.AssessmentDetail;
import com.example.c196_project.UI.Instructor.InstructorDetail;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    class InstructorViewHolder extends RecyclerView.ViewHolder{
        private final TextView instructorItemView;

        private InstructorViewHolder(View itemView){
            super(itemView);
            instructorItemView=itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final InstructorEntity current = mInstructors.get(position);
                    Intent intent = new Intent(context, InstructorDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("courseID", current.getCourse_id());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<InstructorEntity> mInstructors;
    private final Context context;
    private final LayoutInflater mInflater;


    public InstructorAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View instructorView=mInflater.inflate(R.layout.activity_instructor_item, parent, false);
        return new InstructorAdapter.InstructorViewHolder(instructorView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if(mInstructors!= null) {
            InstructorEntity current = mInstructors.get(position);
            String title = current.getInstructor_name();
            holder.instructorItemView.setText(title);
        } else {
            holder.instructorItemView.setText("No course title");
        }
    }

    public void setInstructors(List<InstructorEntity> instructor) {
        mInstructors=instructor;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mInstructors!=null) {
            return mInstructors.size();
        } else {
            return 0;
        }
    }
}
