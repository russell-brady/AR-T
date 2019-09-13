package com.arproject.russell.ar_t.teacher.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.models.AssignmentSubmission;
import com.arproject.russell.ar_t.models.AssignmentSubmissionResponse;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentWorkFragment extends Fragment implements SubmissionClickListener {

    private Assignment assignment;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AssignmentSubmissionAdapter adapter;
    private ArrayList<AssignmentSubmission> submissions;
    private ContentLoadingProgressBar progressBar;
    private PrefConfig prefConfig;

    public static StudentWorkFragment newInstance(Assignment assignment) {
        StudentWorkFragment fragment = new StudentWorkFragment();
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
        apiInterface = ApiUtils.getApiService();

        progressBar = ((AssignmentActivity) getActivity()).findViewById(R.id.assignment_progress_bar);

        submissions = new ArrayList<>();
        adapter = new AssignmentSubmissionAdapter(submissions, getContext(), this);

        prefConfig = new PrefConfig(getContext());

        getAssignmentSubmissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_work, container, false);
        recyclerView = view.findViewById(R.id.assignmentSubmissionRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.studentsworkSwipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::getAssignmentSubmissions);
    }

    private void getAssignmentSubmissions() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getAssignmentSubmissions(assignment.getId()).enqueue(new Callback<AssignmentSubmissionResponse>() {
            @Override
            public void onResponse(Call<AssignmentSubmissionResponse> call, Response<AssignmentSubmissionResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    submissions = response.body().getAssignmentSubmissions();
                    adapter.setList(submissions);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<AssignmentSubmissionResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onSubmissionClick(int position) {
        Intent intent = new Intent(getActivity(), StudentSubmissionActivity.class);
        intent.putExtra("submission", submissions.get(position));
        startActivity(intent);
    }
}
