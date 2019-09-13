package com.arproject.russell.ar_t.teacher.assignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.Assignment;

public class InstructionsFragment extends Fragment {

    private Assignment assignment;

    private TextView title;
    private TextView desc;
    private TextView assignedDate;
    private TextView dueDate;

    public static InstructionsFragment newInstance(Assignment assignment) {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Assignment", assignment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assignment = getArguments().getParcelable("Assignment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        title = view.findViewById(R.id.assignmentTitle);
        desc = view.findViewById(R.id.assignmentDescription);
        dueDate = view.findViewById(R.id.assignmentDueDate);
        assignedDate = view.findViewById(R.id.assignmentAssignedDate);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title.setText(assignment.getTitle());
        desc.setText(assignment.getAssignmentDesc());
        String duedate = getString(R.string.due_date) + " " + assignment.getDateDue();
        String assigneddate = getString(R.string.assigneddate) + " " + assignment.getDateAssigned();
        dueDate.setText(duedate);
        assignedDate.setText(assigneddate);
    }
}
