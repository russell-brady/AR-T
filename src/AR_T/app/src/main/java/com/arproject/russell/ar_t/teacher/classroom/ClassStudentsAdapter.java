package com.arproject.russell.ar_t.teacher.classroom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_models.ItemClickListener;
import com.arproject.russell.ar_t.models.User;

import java.util.ArrayList;

public class ClassStudentsAdapter extends RecyclerView.Adapter<ClassStudentsAdapter.ClassStudentsViewHolder> {

    private ArrayList<User> students;
    private ItemClickListener itemClickListener;

    public ClassStudentsAdapter(ArrayList<User> students, ItemClickListener itemClickListener) {
        this.students = students;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ClassStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ClassStudentsViewHolder(view, itemClickListener);
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

        ClassStudentsViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            email = itemView.findViewById(R.id.studentEmail);
            studentItem = itemView.findViewById(R.id.studentItem);

            studentItem.setOnClickListener(view -> itemClickListener.onItemClicked(getAdapterPosition()));
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setEmail(String email) {
            this.email.setText(email);
        }
    }
}
