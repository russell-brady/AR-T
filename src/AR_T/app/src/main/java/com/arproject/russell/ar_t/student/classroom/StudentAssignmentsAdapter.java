package com.arproject.russell.ar_t.student.classroom;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.teacher.classroom.AssignmentClickListener;
import com.arproject.russell.ar_t.utils.TextViewImmacBytes;

import java.util.ArrayList;

public class StudentAssignmentsAdapter extends RecyclerView.Adapter<StudentAssignmentsAdapter.AssignmentsViewHolder>{

    private ArrayList<Assignment> assignments;
    private AssignmentClickListener assignmentClickListener;

    public StudentAssignmentsAdapter(ArrayList<Assignment> assignments, AssignmentClickListener assignmentClickListener) {
         this.assignments = assignments;
         this.assignmentClickListener = assignmentClickListener;
    }

    @NonNull
    @Override
    public AssignmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentsViewHolder(view, assignmentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentsViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.setTitle(assignment.getTitle());
        holder.setAssignedDate(assignment.getDateAssigned());
        holder.setDateDue(assignment.getDateDue());

        if (assignment.getGrade() != null) {
            holder.setColouredText("Graded", "#60bf04");
            holder.setDot(R.drawable.circle_green);
        } else if (assignment.getSubmission() != null) {
            holder.setColouredText("Submitted", "#2196F3");
            holder.setDot(R.drawable.circle_blue);
        } else {
            holder.setColouredText("Not Answered", "#DD6B55");
            holder.setDot(R.drawable.circle_red);
        }
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public void setList(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    static class AssignmentsViewHolder extends RecyclerView.ViewHolder {

        private TextViewImmacBytes title;
        private TextViewImmacBytes dateAssigned;

        private TextViewImmacBytes dateDue;
        private RelativeLayout assignmentItem;
        private View sideBar;
        private View dot;
        private TextViewImmacBytes colouredText;

        AssignmentsViewHolder(View itemView, AssignmentClickListener assignmentClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.question_title);
            dateAssigned = itemView.findViewById(R.id.dateAssigned);
            dateDue = itemView.findViewById(R.id.due_date);
            assignmentItem = itemView.findViewById(R.id.assignment_item);
            sideBar = itemView.findViewById(R.id.sidebarcolor);
            dot = itemView.findViewById(R.id.dot);
            colouredText = itemView.findViewById(R.id.coloredText);

            assignmentItem.setOnClickListener(view -> assignmentClickListener.onAssignmentClick(getAdapterPosition()));
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setAssignedDate(String date) {
            this.dateAssigned.setText(date);
        }

        public void setDateDue(String date) {
            this.dateDue.setText(date);
        }

        public void setDot(int drawable) {
            this.dot.setBackgroundResource(drawable);
        }

        public void setColouredText(String text, String color) {
            this.colouredText.setText(text);
            this.colouredText.setTextColor(Color.parseColor(color));
            this.sideBar.setBackgroundColor(Color.parseColor(color));
        }
    }
}
