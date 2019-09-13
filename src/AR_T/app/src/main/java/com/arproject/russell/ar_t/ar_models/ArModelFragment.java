package com.arproject.russell.ar_t.ar_models;

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
import com.arproject.russell.ar_t.models.ARModel;
import com.arproject.russell.ar_t.models.ARResponse;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArModelFragment extends Fragment implements ItemClickListener{

    private RecyclerView recyclerView;
    private ArModelRecyclerViewAdapter adapter;
    private ArrayList<ARModel> arModels;
    private ApiInterface apiInterface = ApiUtils.getApiService();
    private ContentLoadingProgressBar progressBar;
    private int userId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;

    public static ArModelFragment newInstance() {
        ArModelFragment fragment = new ArModelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefConfig prefConfig = new PrefConfig(getContext());
        userId = prefConfig.getLoggedInUser().getId();
        progressBar = ((MainActivity) getActivity()).findViewById(R.id.main_progress_bar);
        arModels = new ArrayList<>();
        adapter = new ArModelRecyclerViewAdapter(arModels, this);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.ar_topics_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_armodel_list, container, false);
        recyclerView = view.findViewById(R.id.arModelsRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.arModelsSwipeRefresh);
        fab = view.findViewById(R.id.resolve_anchor);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::getArModels);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ArResolveActivity.class);
            startActivity(intent);
        });

        getArModels();
    }

    private void getArModels() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getARModels(userId).enqueue(new Callback<ARResponse>() {
            @Override
            public void onResponse(@NonNull Call<ARResponse> call, @NonNull Response<ARResponse> response) {
                if (response.body() != null) {
                    arModels.clear();
                    arModels.addAll(response.body().getModels());
                    Collections.reverse(arModels);
                    adapter.setList(arModels);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.models_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ARResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.models_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getContext(), ArUploadActivity.class);
        intent.putExtra("ArModel", arModels.get(position));
        startActivity(intent);
    }
}
