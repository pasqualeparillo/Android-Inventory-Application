package com.example.c196_project.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_project.DB.TermDatabase;
import com.example.c196_project.Entities.InstructorEntity;
import com.example.c196_project.Entities.TermEntity;
import com.example.c196_project.R;
import com.example.c196_project.UI.Instructor.InstructorDetail;
import com.example.c196_project.UI.Term.TermDetail;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{

    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;

        private TermViewHolder(View itemView){
            super(itemView);
            termItemView=itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("title", current.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<TermEntity> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;


    public TermAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View termView=mInflater.inflate(R.layout.activity_term_detail, parent, false);
        return new TermAdapter.TermViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!= null) {
            TermEntity current = mTerms.get(position);
            String title = current.getTitle();
            holder.termItemView.setText(title);
        } else {
            holder.termItemView.setText("No course title");
        }
    }

    public void setTerms(List<TermEntity> term) {
        mTerms=term;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mTerms!=null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }
}
