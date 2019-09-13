package com.arproject.russell.ar_t.teacher.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.AssignmentSubmission;
import com.arproject.russell.ar_t.models.AssignmentSubmissionResponse;
import com.arproject.russell.ar_t.models.LessonResponse;
import com.arproject.russell.ar_t.models.QuizResponse;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.teacher.assignment.AssignmentSubmissionAdapter;
import com.arproject.russell.ar_t.teacher.assignment.StudentSubmissionActivity;
import com.arproject.russell.ar_t.teacher.assignment.SubmissionClickListener;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProgressActivity extends AppCompatActivity implements SubmissionClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiInterface;
    private ArrayList<AssignmentSubmission> submissions;
    private AssignmentSubmissionAdapter adapter;
    private ContentLoadingProgressBar progressBar;
    private DonutProgress lessonProgress;
    private DonutProgress quizzesProgress;

    private User student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_progress);
        student = getIntent().getParcelableExtra("student");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(student.getName());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = ApiUtils.getApiService();

        progressBar = findViewById(R.id.assignment_progress_bar);
        lessonProgress = findViewById(R.id.donut_progress);
        quizzesProgress = findViewById(R.id.quizProgress);

        submissions = new ArrayList<>();
        adapter = new AssignmentSubmissionAdapter(submissions, this, this);

        recyclerView = findViewById(R.id.assignmentsCompletedRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.progressSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::getStudentSubmissions);

        getStudentSubmissions();
        getStudentLessons();
        getStudentQuizzes();
    }

    private void getStudentQuizzes() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getQuizzesCompleted(student.getId()).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    quizzesProgress.setProgress(response.body().getCompletedQuizzes().size() * 33);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<QuizResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getStudentLessons() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getLessonsCompleted(student.getId()).enqueue(new Callback<LessonResponse>() {
            @Override
            public void onResponse(@NonNull Call<LessonResponse> call, @NonNull Response<LessonResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    lessonProgress.setProgress(response.body().getCompletedLessons().size() * 10);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<LessonResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getStudentSubmissions() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getStudentSubmissions(student.getId()).enqueue(new Callback<AssignmentSubmissionResponse>() {
            @Override
            public void onResponse(@NonNull Call<AssignmentSubmissionResponse> call, @NonNull Response<AssignmentSubmissionResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    submissions = response.body().getAssignmentSubmissions();
                    adapter.setList(submissions);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<AssignmentSubmissionResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.assignment_submissions_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubmissionClick(int position) {
        Intent intent = new Intent(this, StudentSubmissionActivity.class);
        intent.putExtra("submission", submissions.get(position));
        startActivity(intent);
    }
}
