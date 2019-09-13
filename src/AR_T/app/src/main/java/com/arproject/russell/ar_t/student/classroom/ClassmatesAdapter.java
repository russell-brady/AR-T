package com.arproject.russell.ar_t.student.classroom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.User;

import java.util.ArrayList;

public class ClassmatesAdapter extends RecyclerView.Adapter<ClassmatesAdapter.ClassStudentsViewHolder> {

    private ArrayList<User> students;

    public ClassmatesAdapter(ArrayList<User> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public ClassStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ClassStudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassStudentsViewHolder holder, int position) {
        User student = students.get(position);
        holder.setName(student.getName());
        holder.setEmail(student.getEmail());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setList(ArrayList<User> students) {
        this.students = students;
    }


    static class ClassStudentsViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView email;
        private RelativeLayout studentItem;

        ClassStudentsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            email = itemView.findViewById(R.id.studentEmail);
            studentItem = itemView.findViewById(R.id.studentItem);

        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setEmail(String email) {
            this.email.setText(email);
        }
    }
}
