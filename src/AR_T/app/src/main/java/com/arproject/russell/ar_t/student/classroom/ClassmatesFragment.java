package com.arproject.russell.ar_t.student.classroom;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.GetStudentsResponse;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassmatesFragment extends Fragment {

    private static final String CLASS_NUMBER = "classNumber";

    private RecyclerView recyclerView;
    private ClassmatesAdapter adapter;
    private ArrayList<User> students;
    private ApiInterface apiInterface = ApiUtils.getApiService();
    private ContentLoadingProgressBar progressBar;
    private int userId;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static ClassmatesFragment newInstance() {

        Bundle args = new Bundle();
        ClassmatesFragment fragment = new ClassmatesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefConfig prefConfig = new PrefConfig(getContext());
        userId = prefConfig.getLoggedInUser().getId();
        progressBar = ((MainActivity) getActivity()).findViewById(R.id.main_progress_bar);
        students = new ArrayList<>();
        adapter = new ClassmatesAdapter(students);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.classroom);
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
        apiInterface.getClassmates(userId).enqueue(new Callback<GetStudentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetStudentsResponse> call, @NonNull Response<GetStudentsResponse> response) {
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
            public void onFailure(@NonNull Call<GetStudentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.fetch_students_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
