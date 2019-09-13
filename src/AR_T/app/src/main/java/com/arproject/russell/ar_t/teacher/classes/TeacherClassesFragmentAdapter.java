package com.arproject.russell.ar_t.teacher.classes;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.Class;

import java.util.ArrayList;

public class TeacherClassesFragmentAdapter extends RecyclerView.Adapter<TeacherClassesFragmentAdapter.ClassViewHolder> {

    private ArrayList<Class> classArrayList;
    private ItemClickListener itemClickListener;

    public TeacherClassesFragmentAdapter(ArrayList<Class> classArrayList, ItemClickListener itemClickListener) {
        this.classArrayList = classArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Class classItem = classArrayList.get(position);
        holder.setTitle(classItem.getName());
        holder.setId(classItem.getClassId());
    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public void setList(ArrayList<Class> classArrayList) {
        this.classArrayList = classArrayList;
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView id;
        private CardView classItem;

        ClassViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.classTitle);
            id = itemView.findViewById(R.id.classNumber);
            classItem = itemView.findViewById(R.id.classItem);

            classItem.setOnClickListener(view -> itemClickListener.onClassCLick(title.getText().toString(), Integer.parseInt(id.getText().toString())));
        }

        public void setId(int id) {
            this.id.setText(String.valueOf(id));
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }
    }
}
