package com.arproject.russell.ar_t.teacher.assignment;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.models.AssignmentSubmission;
import com.arproject.russell.ar_t.utils.ApiClient;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentSubmissionActivity extends AppCompatActivity {

    private AssignmentSubmission assignmentSubmission;
    private ApiInterface apiInterface;
    private ContentLoadingProgressBar progressBar;
    private EditText grade;
    //private Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_submission);

        Toolbar toolbar = findViewById(R.id.submissionToolbar);
        setSupportActionBar(toolbar);

        assignmentSubmission = getIntent().getParcelableExtra("submission");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiInterface = ApiUtils.getApiService();

        TextView studentName = findViewById(R.id.studentName);
        TextView studentEmail = findViewById(R.id.studentEmail);
        TextView assignmentName = findViewById(R.id.assignmentName);
        Button submitButton = findViewById(R.id.submitGrade);
        ImageView submissionImage = findViewById(R.id.submissionImage);
        grade = findViewById(R.id.grade);
        progressBar = findViewById(R.id.student_submission_progress_bar);


        if (assignmentSubmission != null) {

            toolbar.setTitle(assignmentSubmission.getTitle());
            assignmentName.setText(assignmentSubmission.getTitle());
            if (assignmentSubmission.getGrade() != 0){
                grade.setText(String.valueOf(assignmentSubmission.getGrade()));
                submitButton.setText(R.string.update_grade);
            }

            studentName.setText(assignmentSubmission.getName());
            studentEmail.setText(assignmentSubmission.getEmail());
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(ApiClient.BASE_URL + "/" + assignmentSubmission.getSubmission())
                    .into(submissionImage);
        }

        submitButton.setOnClickListener(view -> setGrade());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assignment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setGrade() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.submitGrade(assignmentSubmission.getAssignmentSubmissionId(), Integer.parseInt(grade.getText().toString())).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    Toast.makeText(getApplicationContext(), R.string.grade_added, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.grade_failed, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.grade_failed, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
