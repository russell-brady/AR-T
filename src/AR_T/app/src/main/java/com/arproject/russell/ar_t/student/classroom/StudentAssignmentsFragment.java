package com.arproject.russell.ar_t.student.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.models.GetAssignmentsResponse;
import com.arproject.russell.ar_t.student.assignmentsubmission.AssignmentSubmissionActivity;
import com.arproject.russell.ar_t.teacher.classroom.AssignmentClickListener;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAssignmentsFragment extends Fragment implements AssignmentClickListener {

    private ApiInterface apiInterface;
    private ArrayList<Assignment> assignments;
    private StudentAssignmentsAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ContentLoadingProgressBar progressBar;
    private int userId;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static StudentAssignmentsFragment newInstance() {
        Bundle args = new Bundle();
        StudentAssignmentsFragment fragment = new StudentAssignmentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = ((MainActivity) getActivity()).findViewById(R.id.main_progress_bar);

        PrefConfig prefConfig = new PrefConfig(getContext());
        userId = prefConfig.getLoggedInUser().getId();
        apiInterface = ApiUtils.getApiService();
        assignments = new ArrayList<>();
        adapter = new StudentAssignmentsAdapter(assignments, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_fragment, container, false);
        recyclerView = view.findViewById(R.id.assignmentsRecyclerView);
        fab = view.findViewById(R.id.add_assignment);
        swipeRefreshLayout = view.findViewById(R.id.assignmentsSwipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::getAssignments);

        fab.setVisibility(View.GONE);
        getAssignments();
    }

    private void getAssignments() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getStudentAssignments(userId).enqueue(new Callback<GetAssignmentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAssignmentsResponse> call, @NonNull Response<GetAssignmentsResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    assignments.clear();
                    assignments.addAll(response.body().getAssignments());
                    Collections.reverse(assignments);
                    adapter.setList(assignments);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.fetch_assignments_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<GetAssignmentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.fetch_assignments_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onAssignmentClick(int position) {
        Assignment assignment = assignments.get(position);
        Intent intent = new Intent(getActivity(), AssignmentSubmissionActivity.class);
        intent.putExtra("assignment", assignment);
        startActivity(intent);
    }
}
