package com.arproject.russell.ar_t.teacher.classroom;

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
import com.arproject.russell.ar_t.ar_models.ItemClickListener;
import com.arproject.russell.ar_t.models.GetStudentsResponse;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassStudentsFragment extends Fragment implements ItemClickListener {

    private static final String CLASS_NUMBER = "classNumber";

    private RecyclerView recyclerView;
    private ClassStudentsAdapter adapter;
    private ArrayList<User> students;
    private int classNumber;
    private ApiInterface apiInterface = ApiUtils.getApiService();
    private ContentLoadingProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ClassStudentsFragment newInstance(int classNumber) {

        Bundle args = new Bundle();
        ClassStudentsFragment fragment = new ClassStudentsFragment();
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
        students = new ArrayList<>();
        adapter = new ClassStudentsAdapter(students, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.students_fragment, container, false);
        recyclerView = view.findViewById(R.id.studentsRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.studentsSwipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::getStudents);

        getStudents();
    }

    private void getStudents() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getClassStudents(classNumber).enqueue(new Callback<GetStudentsResponse>() {
            @Override
            public void onResponse(Call<GetStudentsResponse> call, Response<GetStudentsResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    students.clear();
                    students.addAll(response.body().getStudents());
                    adapter.setList(students);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.fetch_students_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetStudentsResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.fetch_students_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), StudentProgressActivity.class);
        intent.putExtra("student", students.get(position));
        startActivity(intent);
    }
}
