package com.arproject.russell.ar_t.teacher.classroom;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.utils.TextViewImmacBytes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder>{

    private ArrayList<Assignment> assignments;
    private AssignmentClickListener assignmentClickListener;

    public AssignmentsAdapter(ArrayList<Assignment> assignments, AssignmentClickListener assignmentClickListener) {
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

        String dateAssigned = assignment.getDateAssigned();
        String dateDue = assignment.getDateDue();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = format.parse(dateDue);
            Date currentDate = Calendar.getInstance().getTime();
            if (currentDate.before(dueDate)) {
                holder.setColouredText("Assigned", "#60bf04");
                holder.setDot(R.drawable.circle_green);
            } else {
                holder.setColouredText("Closed", "#DD6B55");
                holder.setDot(R.drawable.circle_red);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.setAssignedDate(dateAssigned);
        holder.setDateDue(dateDue);

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
        private RelativeLayout assignmentItem;
        private TextViewImmacBytes dateDue;
        private View sideBar;
        private View dot;
        private TextViewImmacBytes colouredText;

        AssignmentsViewHolder(View itemView, AssignmentClickListener assignmentClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.question_title);
            dateAssigned = itemView.findViewById(R.id.dateAssigned);
            assignmentItem = itemView.findViewById(R.id.assignment_item);
            dateDue = itemView.findViewById(R.id.due_date);
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
