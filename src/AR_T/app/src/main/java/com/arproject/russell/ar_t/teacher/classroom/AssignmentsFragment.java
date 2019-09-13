package com.arproject.russell.ar_t.teacher.classroom;

import android.content.Intent;
import android.os.Bundle;
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
import com.arproject.russell.ar_t.dialog.CreateAssignmentDialog;
import com.arproject.russell.ar_t.dialog.CreateAssignmentListener;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.models.GetAssignmentsResponse;
import com.arproject.russell.ar_t.teacher.assignment.AssignmentActivity;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentsFragment extends Fragment implements CreateAssignmentListener, AssignmentClickListener {

    private static final String CLASS_NUMBER = "classNumber";
    private ApiInterface apiInterface;
    private int classNumber;
    private ArrayList<Assignment> assignments;
    private AssignmentsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private ContentLoadingProgressBar progressBar;

    public static AssignmentsFragment newInstance(int classNumber) {
        Bundle args = new Bundle();
        AssignmentsFragment fragment = new AssignmentsFragment();
        args.putInt(CLASS_NUMBER, classNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            classNumber = getArguments().getInt(CLASS_NUMBER);
        }

        progressBar = getActivity().findViewById(R.id.main_progress_bar);

        apiInterface = ApiUtils.getApiService();
        assignments = new ArrayList<>();
        adapter = new AssignmentsAdapter(assignments, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_fragment, container, false);
        recyclerView = view.findViewById(R.id.assignmentsRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.assignmentsSwipeRefresh);
        fab = view.findViewById(R.id.add_assignment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            CreateAssignmentDialog createAssignmentDialog = CreateAssignmentDialog.newInstance();
            createAssignmentDialog.setListener(this);
            createAssignmentDialog.show(getFragmentManager(), "createAssignmentDialog");
        });

        swipeRefreshLayout.setOnRefreshListener(this::getAssignments);

        getAssignments();
    }

    private void getAssignments() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getClassAssignments(classNumber).enqueue(new Callback<GetAssignmentsResponse>() {
            @Override
            public void onResponse(Call<GetAssignmentsResponse> call, Response<GetAssignmentsResponse> response) {
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
            public void onFailure(Call<GetAssignmentsResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.fetch_assignments_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void createAssignment(String title, String desc, String dateDue) {
        progressBar.setVisibility(View.VISIBLE);
        String dateAssigned = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        apiInterface.createAssignment(classNumber, title, desc, dateAssigned, dateDue).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    Toast.makeText(getContext(), R.string.create_assignments_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.create_assignments_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.create_assignments_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onAssignmentCreated(String title, String desc, String date) {
        createAssignment(title, desc, date);
    }

    @Override
    public void onAssignmentClick(int position) {
        Intent intent = new Intent(getActivity(), AssignmentActivity.class);
        intent.putExtra("Assignment", assignments.get(position));
        startActivity(intent);
    }
}
