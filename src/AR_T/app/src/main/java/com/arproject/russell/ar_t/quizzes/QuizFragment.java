package com.arproject.russell.ar_t.quizzes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.utils.ShadowTransformer;

import static com.arproject.russell.ar_t.ar_lessons.navigation.ArLessonsFragment.dpToPixels;

public class QuizFragment extends Fragment {

    private ViewPager viewPager;

    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.quizzes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        viewPager = view.findViewById(R.id.quizzesViewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QuizzesAdapter mFragmentCardAdapter = new QuizzesAdapter(getChildFragmentManager(), dpToPixels(2, getContext()), getContext());
        ShadowTransformer mFragmentCardShadowTransformer = new ShadowTransformer(viewPager, mFragmentCardAdapter);
        viewPager.setAdapter(mFragmentCardAdapter);
        viewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        mFragmentCardShadowTransformer.enableScaling(true);
    }

}
