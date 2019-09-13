package com.arproject.russell.ar_t.teacher.assignment;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.settings.SettingsFragment;
import com.arproject.russell.ar_t.utils.PrefConfig;

public class AssignmentActivity extends AppCompatActivity {

    private Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assignment = getIntent().getParcelableExtra("Assignment");

        Toolbar toolbar = findViewById(R.id.assignmentToolbar);
        toolbar.setTitle(R.string.assignment);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return InstructionsFragment.newInstance(assignment);
            } else {
                return StudentWorkFragment.newInstance(assignment);
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
