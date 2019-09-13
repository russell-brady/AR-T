package com.arproject.russell.ar_t.teacher.classroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arproject.russell.ar_t.R;


public class ClassFragment extends Fragment {

    private static final String CLASSID = "classId";
    private static final String CLASSNAME = "className";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int classId;

    private int[] tabIcons = {
            R.drawable.ic_question_answer,
            R.drawable.ic_people_black_24dp
    };

    public static ClassFragment newInstance(String className, int id) {
        ClassFragment fragment = new ClassFragment();
        Bundle args = new Bundle();
        args.putInt(CLASSID, id);
        args.putString(CLASSNAME, className);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String className = bundle.getString(CLASSNAME);
            classId = bundle.getInt(CLASSID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_fragment, container, false);
        viewPager = view.findViewById(R.id.class_viewpager);
        tabLayout = view.findViewById(R.id.tab_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(pagerAdapter.getTabView(i));
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private final int PAGE_COUNT = 2;

        private final String[] mTabsTitle = {"Questions", "Students"};

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        View getTabView(int position) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar, null, false);
            ImageView icon = view.findViewById(R.id.icon);
            icon.setImageResource(tabIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return AssignmentsFragment.newInstance(classId);

                case 1:
                    return ClassStudentsFragment.newInstance(classId);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
}
