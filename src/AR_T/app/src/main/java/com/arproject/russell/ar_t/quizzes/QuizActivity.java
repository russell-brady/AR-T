package com.arproject.russell.ar_t.quizzes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_lessons.ViewRenderablesController;
import com.arproject.russell.ar_t.ar_models.CustomNode;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.QuizQuestion;
import com.arproject.russell.ar_t.models.QuizQuestions;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private TextView findSurface;
    private AnchorNode anchorNode;
    private PrefConfig prefConfig;
    private ApiInterface apiInterface;
    private Node currentNode;
    private int position;

    private QuizQuestions quizQuestions;
    private LinearLayout answerButtons;
    private LinearLayout scoreLayout;
    private TextView scoreView;
    private TextView questionNumber;

    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;

    private ViewRenderablesController viewRenderablesController;

    private int mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        position = getIntent().getIntExtra("position", -1);
        apiInterface = ApiUtils.getApiService();

        setContentView(R.layout.quiz_activity);
        findSurface = findViewById(R.id.find_surface);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.setOnTapArPlaneListener(this::onPlaneTap);
        viewRenderablesController = new ViewRenderablesController(this);

        quizQuestions = new QuizQuestions(this, position);

        answerButtons = findViewById(R.id.answerbuttons);
        scoreView = findViewById(R.id.score);
        scoreLayout = findViewById(R.id.scorelayout);
        questionNumber = findViewById(R.id.question_number);

        mButtonChoice1 = findViewById(R.id.answer1);
        mButtonChoice2 = findViewById(R.id.answer2);
        mButtonChoice3 = findViewById(R.id.answer3);

        mButtonChoice1.setOnClickListener(view -> {
            if (1 == mAnswer){
                mScore = mScore + 1;
                updateScore(mScore);
                updateQuestion();
                //This line of code is optiona
                Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                updateQuestion();
            }
        });

        mButtonChoice2.setOnClickListener(view -> {
            if (2 == mAnswer){
                mScore = mScore + 1;
                updateScore(mScore);
                updateQuestion();
                //This line of code is optiona
                Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                updateQuestion();
            }
        });

        mButtonChoice3.setOnClickListener(view -> {
            if (3 == mAnswer){
                mScore = mScore + 1;
                updateScore(mScore);
                updateQuestion();
                //This line of code is optiona
                Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                updateQuestion();
            }
        });
    }

    private void onPlaneTap(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        findSurface.setVisibility(View.GONE);
        Anchor anchor = hitResult.createAnchor();
        if (anchorNode == null) {
            quizQuestions.initialiseQuizItems();
            anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            answerButtons.setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.VISIBLE);
            questionNumber.setVisibility(View.VISIBLE);

            updateQuestion();
        }
    }

    private void updateScore(int mScore) {
        scoreView.setText("Score: " + mScore);
    }

    private void updateQuestion() {

        if (mQuestionNumber == quizQuestions.getQuizQuestionsSize()) {
            finishQuiz();
        } else {
            questionNumber.setText("Question " + (mQuestionNumber + 1) + " of " + quizQuestions.getQuizQuestionsSize());
            QuizQuestion quizQuestion = quizQuestions.getQuizQuestion(mQuestionNumber);
            mButtonChoice1.setText(quizQuestion.getOption1());
            mButtonChoice2.setText(quizQuestion.getOption2());

            if (quizQuestion.getOption3() == null || quizQuestion.getOption3().equals("")) {
                mButtonChoice3.setVisibility(View.GONE);
            } else {
                mButtonChoice3.setVisibility(View.VISIBLE);
                mButtonChoice3.setText(quizQuestion.getOption3());
            }

            if (currentNode != null) {
                currentNode.setParent(null);
            }

            currentNode = quizQuestion.getModelNode();
            currentNode.setParent(anchorNode);

            CustomNode viewNode = new CustomNode();
            viewNode.setParent(currentNode);
            viewNode.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));
            viewNode.setRenderable(viewRenderablesController.getInfoCard());

            TextView textView = (TextView) viewRenderablesController.getInfoCard().getView();
            textView.setText(quizQuestion.getQuestion());

            mAnswer = quizQuestion.getAnswerNumber();
            mQuestionNumber++;
        }
    }

    private void finishQuiz() {
        String contentText = "You have scored " + mScore + " / " + quizQuestions.getQuizQuestionsSize();
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.quiz_complete))
                .setContentText(contentText)
                .setConfirmClickListener(sweetAlertDialog -> {
                    onQuizCompleted();
                    finish();
                })
                .show();
    }

    public void onQuizCompleted() {
        int score = getQuizScore();
        apiInterface.setQuizCompleted(prefConfig.getLoggedInUser().getId(), position, score).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {}
            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {}
        });
    }

    private int getQuizScore() {
        return ((mScore / quizQuestions.getQuizQuestionsSize()) * 100);
    }
}
