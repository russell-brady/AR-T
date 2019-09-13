package com.arproject.russell.ar_t.teacher.assignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.AssignmentSubmission;
import com.arproject.russell.ar_t.utils.ApiClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AssignmentSubmissionAdapter extends RecyclerView.Adapter<AssignmentSubmissionAdapter.AssignmentSubmissionViewHolder> {

    private ArrayList<AssignmentSubmission> submissions;
    private Context context;
    private SubmissionClickListener submissionClickListener;

    public AssignmentSubmissionAdapter(ArrayList<AssignmentSubmission> submissions, Context context, SubmissionClickListener submissionClickListener) {
        this.submissions = submissions;
        this.context = context;
        this.submissionClickListener = submissionClickListener;
    }

    @NonNull
    @Override
    public AssignmentSubmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_submission_item, parent, false);
        return new AssignmentSubmissionViewHolder(view, context, submissionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentSubmissionViewHolder holder, int position) {
        AssignmentSubmission assignmentSubmission = submissions.get(position);
        holder.setName(assignmentSubmission.getName());
        holder.setSubmission(assignmentSubmission.getSubmission());
    }

    @Override
    public int getItemCount() {
        return submissions.size();
    }

    public void setList(ArrayList<AssignmentSubmission> assignmentSubmissions) {
        submissions = assignmentSubmissions;
    }

    static class AssignmentSubmissionViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private CardView submissionItem;
        private TextView name;
        private ImageView submission;

        AssignmentSubmissionViewHolder(View itemView, Context context, SubmissionClickListener submissionClickListener) {
            super(itemView);
            this.context = context;
            name = itemView.findViewById(R.id.submissionName);
            submission = itemView.findViewById(R.id.submissionPic);
            submissionItem = itemView.findViewById(R.id.submissionItem);

            submissionItem.setOnClickListener(view -> submissionClickListener.onSubmissionClick(getAdapterPosition()));
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setSubmission(String submission) {
            Glide.with(context)
                    .asBitmap()
                    .load(ApiClient.BASE_URL + "/" + submission)
                    .into(this.submission);
        }
    }
}

