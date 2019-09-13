package com.arproject.russell.ar_t.student.assignmentsubmission;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.utils.ApiClient;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.ImageHelper;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class AssignmentSubmissionActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;

    private ImageView selectedImageView;
    private TextView assignedText;
    private Bitmap bitmap;
    private ApiInterface apiInterface;
    private PrefConfig prefConfig;
    private Assignment assignment;
    private ContentLoadingProgressBar progressBar;
    private String base64Image;

    private TextView addAttachment;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_submission);

        assignment = getIntent().getParcelableExtra("assignment");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = ApiUtils.getApiService();

        selectedImageView = findViewById(R.id.imageUpload);
        addAttachment = findViewById(R.id.addAttachment);
        submit = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.submissionProgressBar);
        TextView title = findViewById(R.id.assignmentTitle);
        TextView desc = findViewById(R.id.assignmentDescription);
        TextView dueDate = findViewById(R.id.assignmentDueDate);
        TextView assignedDate = findViewById(R.id.assignmentAssignedDate);
        TextView grade = findViewById(R.id.grade);
        assignedText = findViewById(R.id.assigned);

        if (assignment != null) {
            toolbar.setTitle(assignment.getTitle());
            title.setText(assignment.getTitle());
            desc.setText(assignment.getAssignmentDesc());
            dueDate.setText(assignment.getDateDue());
            assignedDate.setText(assignment.getDateAssigned());

            if (assignment.getSubmission() != null) {
                submit.setVisibility(View.GONE);
                addAttachment.setVisibility(View.GONE);
                setSubmission(assignment.getSubmission());
                assignedText.setText(R.string.submitted);
            } if (assignment.getGrade() != null) {
                assignedText.setText(R.string.graded);
                grade.setVisibility(View.VISIBLE);
                grade.setText(assignment.getGrade() + "%");
            }
        }

        addAttachment.setOnClickListener(view -> createDialog());
        submit.setOnClickListener(view -> submitAssignment());
    }

    private void createDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        String[] animals = {"Choose From Gallery", "Take Photo"};
        builderSingle.setItems(animals, (dialog, which) -> {
            switch (which) {
                case 0:
                    openGallery();
                    break;
                case 1:
                    openCamera();
                    break;
            }
        });
        builderSingle.show();
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
    }

    public void setSubmission(String submission) {
        selectedImageView.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(ApiClient.BASE_URL + "/" + submission)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_assignment_black_24dp))
                .into(this.selectedImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(imageStream);
                selectedImageView.setImageBitmap(bitmap);
                base64Image = ImageHelper.bitmapToString(bitmap);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            selectedImageView.setImageBitmap(bitmap);
            base64Image = ImageHelper.bitmapToString(bitmap);
        }
        selectedImageView.setVisibility(View.VISIBLE);
    }

    private void submitAssignment() {

        if (bitmap != null) {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface.assignmentSubmission(assignment.getId(), prefConfig.getLoggedInUser().getId(), base64Image).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.body() != null && response.body().getCode() == 200) {
                        Toast.makeText(getApplicationContext(), getString(R.string.submission_successful), LENGTH_SHORT).show();
                        assignedText.setText(R.string.submitted);
                        submit.setVisibility(View.GONE);
                        addAttachment.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.submission_failed), LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), getString(R.string.submission_failed), LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.choose_image), LENGTH_SHORT).show();
        }

    }
}
