package com.arproject.russell.ar_t.user_profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.LessonResponse;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileFragment extends Fragment {

    private TextView username;
    private TextView email;
    private TextView userType;

    private TextView activitiesDone;
    private TextView percentDone;
    private CardView activityCard;

    private PrefConfig prefConfig;
    private ApiInterface apiInterface;

    public static UserProfileFragment newInstance() {
        Bundle args = new Bundle();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        username = view.findViewById(R.id.usersname);
        email = view.findViewById(R.id.useremailaddress);
        userType = view.findViewById(R.id.userType);
        activitiesDone = view.findViewById(R.id.activitesCompleted);
        percentDone = view.findViewById(R.id.percentCompleted);
        activityCard = view.findViewById(R.id.activity_card);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.user_profile);
        prefConfig = new PrefConfig(getContext());
        apiInterface = ApiUtils.getApiService();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.userprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefConfig = new PrefConfig(getContext());
        User user = prefConfig.getLoggedInUser();
        username.setText(user.getName());
        email.setText(user.getEmail());
        userType.setText(user.getType());

        if (user.isTeacher()) {
            activityCard.setVisibility(View.GONE);
        } else {
            getStudentLessons();
        }
    }

    private void getStudentLessons() {
        apiInterface.getLessonsCompleted(prefConfig.getLoggedInUser().getId()).enqueue(new Callback<LessonResponse>() {
            @Override
            public void onResponse(@NonNull Call<LessonResponse> call, @NonNull Response<LessonResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    activitiesDone.setText(response.body().getCompletedLessons().size() + "");
                    percentDone.setText((response.body().getCompletedLessons().size() * 10) + "%");
                } else {
                    Toast.makeText(getContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<LessonResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
