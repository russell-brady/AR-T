package com.arproject.russell.ar_t.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_lessons.navigation.ArLessonsFragment;
import com.arproject.russell.ar_t.ar_models.ArModelFragment;
import com.arproject.russell.ar_t.augmentedimages.AugmentedImagesGuidedTourActivity;
import com.arproject.russell.ar_t.guided_tour.TapTargetPrompts;
import com.arproject.russell.ar_t.login.LoginActivity;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.quizzes.QuizFragment;
import com.arproject.russell.ar_t.settings.SettingsFragment;
import com.arproject.russell.ar_t.student.classroom.StudentFragment;
import com.arproject.russell.ar_t.teacher.classes.TeacherClassesFragment;
import com.arproject.russell.ar_t.user_profile.UserProfileFragment;
import com.arproject.russell.ar_t.utils.PrefConfig;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private User user;
    private ContentLoadingProgressBar progressBar;
    private Toolbar toolbar;
    private TapTargetPrompts tapTargetPrompts;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.assignmentToolbar);
        progressBar = findViewById(R.id.main_progress_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tapTargetPrompts = new TapTargetPrompts(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        TextView email = headerView.findViewById(R.id.email_address);

        user = prefConfig.getLoggedInUser();
        username.setText(user.getName());
        email.setText(user.getEmail());

        if (user.isTeacher()) {
            hideNavItems();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, TeacherClassesFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, ArLessonsFragment.newInstance())
                    .commitNow();
        }
    }

    public void logout() {
        deleteSharedPreferences(getString(R.string.shared_pref));
        deleteSharedPreferences(getString(R.string.assignment_shared_pref));
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        tapTargetPrompts.showSequence(toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            int fragmentCount = this.getSupportFragmentManager().getBackStackEntryCount();
            if (fragmentCount == 0) {
                if (user.isTeacher()) {
                    getSupportActionBar().setTitle(getString(R.string.classroom));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.ar_animations));
                }
            } else if(fragmentCount != 0) {
                FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(fragmentCount-1);
                String str=backEntry.getName(); //the tag of the fragment
                getSupportActionBar().setTitle(str);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                getLogoutDialog();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, SettingsFragment.newInstance())
                        .addToBackStack(getString(R.string.settings_title))
                        .commit();
                break;
            case R.id.ar_topics:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, ArModelFragment.newInstance())
                        .addToBackStack(getString(R.string.ar_topics_title))
                        .commit();
                break;
            case R.id.quizzes:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, QuizFragment.newInstance())
                        .addToBackStack(getString(R.string.quizzes))
                        .commit();
                break;
            case R.id.animations:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, ArLessonsFragment.newInstance())
                        .addToBackStack(getString(R.string.ar_animations))
                        .commit();
                break;
            case R.id.augnemtedImages:
                Intent intent = new Intent(this, AugmentedImagesGuidedTourActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, UserProfileFragment.newInstance())
                        .addToBackStack(getString(R.string.user_profile))
                        .commit();
                break;
            case R.id.classroom:
                if (user.isTeacher()) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_content, TeacherClassesFragment.newInstance())
                            .addToBackStack(getString(R.string.classroom))
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_content, StudentFragment.newInstance())
                            .addToBackStack(getString(R.string.classroom))
                            .commit();
                }
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Do you wish to logout?")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    logout();
                })
                .setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private void hideNavItems()
    {
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.animations).setVisible(false);
        navMenu.findItem(R.id.quizzes).setVisible(false);
    }
}
