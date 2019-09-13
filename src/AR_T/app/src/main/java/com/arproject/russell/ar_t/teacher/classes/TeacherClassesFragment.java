package com.arproject.russell.ar_t.teacher.classes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.dialog.CreateClassDialog;
import com.arproject.russell.ar_t.dialog.DialogFinishedInterface;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.Class;
import com.arproject.russell.ar_t.models.GetClassesResponse;
import com.arproject.russell.ar_t.teacher.classroom.ClassFragment;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherClassesFragment extends Fragment implements DialogFinishedInterface, ItemClickListener{

    private FloatingActionButton floatingActionButton;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private TeacherClassesFragmentAdapter adapter;
    private int userId;
    private String className;
    private int classId;
    private ArrayList<Class> classArrayList;
    private ContentLoadingProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static TeacherClassesFragment newInstance() {
        TeacherClassesFragment fragment = new TeacherClassesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = ((MainActivity) getActivity()).findViewById(R.id.main_progress_bar);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.classroom);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        floatingActionButton = view.findViewById(R.id.add_class);
        recyclerView = view.findViewById(R.id.classList);
        swipeRefreshLayout = view.findViewById(R.id.classesSwipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apiInterface = ApiUtils.getApiService();
        PrefConfig prefConfig = new PrefConfig(getContext());
        userId = prefConfig.getLoggedInUser().getId();

        classArrayList = new ArrayList<>();
        adapter = new TeacherClassesFragmentAdapter(classArrayList, this);

        floatingActionButton.setOnClickListener(view -> {
            CreateClassDialog createClassDialog = new CreateClassDialog();
            createClassDialog.setListener(this);
            createClassDialog.show(getFragmentManager(), "createClassDialog");
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> getClasses(userId));

        getClasses(userId);
    }

    private void createClass() {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface.createClass(userId, className, classId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), R.string.class_created, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.class_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.class_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getClasses(int userId) {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getClasses(userId).enqueue(new Callback<GetClassesResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetClassesResponse> call, @NonNull Response<GetClassesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getClasses() != null) {
                        classArrayList.clear();
                        classArrayList.addAll(response.body().getClasses());
                        adapter.setList(classArrayList);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.fetch_classes_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetClassesResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.fetch_classes_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onFinished(int classId, String className) {
        this.classId = classId;
        this.className = className;
        createClass();
    }

    @Override
    public void onClassCLick(String className, int id) {
        getFragmentManager().beginTransaction()
                .add(R.id.main_content, ClassFragment.newInstance(className, id))
                .addToBackStack(null)
                .commit();
    }

}
