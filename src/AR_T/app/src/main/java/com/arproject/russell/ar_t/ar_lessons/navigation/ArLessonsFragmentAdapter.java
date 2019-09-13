package com.arproject.russell.ar_t.ar_lessons.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.CompletedLesson;
import com.arproject.russell.ar_t.models.LessonResponse;
import com.arproject.russell.ar_t.models.Topic;
import com.arproject.russell.ar_t.models.Topics;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArLessonsFragmentAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private ArrayList<LessonFragment> mFragments;
    private float mBaseElevation;
    private ApiInterface apiInterface;
    private PrefConfig prefConfig;
    private Context context;

    ArLessonsFragmentAdapter(FragmentManager fm, float baseElevation, Context context) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        this.context = context;

        ArrayList<Topic> topics = Topics.getItems();
        for(int i = 0; i < topics.size(); i++){
            addCardFragment(LessonFragment.newInstance(i, topics.get(i)));
        }

        apiInterface = ApiUtils.getApiService();
        prefConfig = new PrefConfig(context);
        getStudentLessons();
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

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (LessonFragment) fragment);
        return fragment;
    }

    public void addCardFragment(LessonFragment fragment) {
        mFragments.add(fragment);
    }

    private void getStudentLessons() {
        apiInterface.getLessonsCompleted(prefConfig.getLoggedInUser().getId()).enqueue(new Callback<LessonResponse>() {
            @Override
            public void onResponse(@NonNull Call<LessonResponse> call, @NonNull Response<LessonResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    for (CompletedLesson lesson : response.body().getCompletedLessons()) {
                        LessonFragment lessonFragment = (LessonFragment) getItem(lesson.getLessonId() - 1);
                        lessonFragment.setButtonText(context.getString(R.string.redo));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LessonResponse> call, @NonNull Throwable t) {
            }
        });
    }


}
