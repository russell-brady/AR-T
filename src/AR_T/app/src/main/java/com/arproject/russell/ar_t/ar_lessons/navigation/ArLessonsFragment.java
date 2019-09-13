package com.arproject.russell.ar_t.ar_lessons.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_models.ItemClickListener;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.utils.ShadowTransformer;

public class ArLessonsFragment extends Fragment implements ItemClickListener {

    private ViewPager mViewPager;

    public static ArLessonsFragment newInstance() {
        Bundle args = new Bundle();
        ArLessonsFragment fragment = new ArLessonsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.ar_animations);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ar_fragment, container, false);
        mViewPager = view.findViewById(R.id.lessonsViewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArLessonsFragmentAdapter mFragmentCardAdapter = new ArLessonsFragmentAdapter(getChildFragmentManager(), dpToPixels(2, getContext()), getContext());
        ShadowTransformer mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mFragmentCardShadowTransformer.enableScaling(true);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onItemClicked(int position) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_content, ArLessonDetailsFragment.newInstance(position))
                .addToBackStack(getString(R.string.ar_animations))
                .commit();
    }
}
