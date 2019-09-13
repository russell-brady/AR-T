package com.arproject.russell.ar_t.quizzes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_lessons.navigation.CardAdapter;
import com.arproject.russell.ar_t.models.CompletedLesson;
import com.arproject.russell.ar_t.models.CompletedQuiz;
import com.arproject.russell.ar_t.models.LessonResponse;
import com.arproject.russell.ar_t.models.QuizResponse;
import com.arproject.russell.ar_t.models.Topic;
import com.arproject.russell.ar_t.models.Topics;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class QuizzesAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private ArrayList<CardFragment> mFragments;
    private float mBaseElevation;
    private ApiInterface apiInterface;
    private PrefConfig prefConfig;
    private Context context;

    public QuizzesAdapter(FragmentManager childFragmentManager, float baseElevation, Context context) {
        super(childFragmentManager);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        this.context = context;

        ArrayList<Topic> topics = Topics.getQuizItems();
        for(int i = 0; i < topics.size(); i++){
            addCardFragment(CardFragment.newInstance(i, topics.get(i)));
        }

        apiInterface = ApiUtils.getApiService();
        prefConfig = new PrefConfig(context);
        getStudentQuizResults();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        mFragments.add(fragment);
    }

    private void getStudentQuizResults() {
        apiInterface.getQuizzesCompleted(prefConfig.getLoggedInUser().getId()).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    for (CompletedQuiz quiz : response.body().getCompletedQuizzes()) {
                        CardFragment cardFragment = (CardFragment) getItem(quiz.getQuizId() - 1);
                        cardFragment.setButtonText(context.getString(R.string.redo));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<QuizResponse> call, @NonNull Throwable t) {
            }
        });
    }
}
