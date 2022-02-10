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
import com.example.c196_project.R;
import com.example.c196_project.UI.Assessment.AssessmentDetail;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("assessmentID", current.getId());
                    intent.putExtra("title", current.getAssessment_title());
                    intent.putExtra("startDate", current.getAssessment_start_date());
                    intent.putExtra("endDate", current.getAssessment_end_date());
                    intent.putExtra("type", current.getAssessment_type());
                    intent.putExtra("courseID", current.getCourse_id());
                    context.startActivity(intent);
                }
            });
        }
    }


    private List<AssessmentEntity> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessmentView=mInflater.inflate(R.layout.activity_assessment_item, parent, false);
        return new AssessmentViewHolder(assessmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments!= null) {
            AssessmentEntity current = mAssessments.get(position);
            String title = current.getAssessment_title();
            holder.assessmentItemView.setText(title);
        } else {
            holder.assessmentItemView.setText("No Assesssment title");
        }
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mAssessments!=null) {
            return mAssessments.size();
        } else {
            return 0;
        }
    }

}
