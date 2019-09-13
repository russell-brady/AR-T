package com.arproject.russell.ar_t.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.AssignmentSubmission;
import com.arproject.russell.ar_t.models.User;

public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private SharedPreferences assignmentPreferences;
    private SharedPreferences persistantPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref), Context.MODE_PRIVATE);
        assignmentPreferences = context.getSharedPreferences(context.getString(R.string.assignment_shared_pref), Context.MODE_PRIVATE);
        persistantPreferences = context.getSharedPreferences(context.getString(R.string.persistent_preferences), Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void setLoginStatus(boolean loginStatus) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), loginStatus);
        editor.apply();
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = sharedPreferences.edit();
        userLocalDatabaseEditor.putInt("id", user.getId());
        userLocalDatabaseEditor.putString("name", user.getName());
        userLocalDatabaseEditor.putString("email", user.getEmail());
        userLocalDatabaseEditor.putString("password", user.getPassword());
        userLocalDatabaseEditor.putString("type", user.getType());
        userLocalDatabaseEditor.apply();
    }

    public User getLoggedInUser() {
        int id = sharedPreferences.getInt("id", -1);
        String name = sharedPreferences.getString("name", "");
        String type = sharedPreferences.getString("type", "");
        String password = sharedPreferences.getString("password", "");
        String email = sharedPreferences.getString("email", "");

        return new User(id, name, email, password, type);
    }

    public void storeAssignmentSubmission(AssignmentSubmission assignmentSubmission) {
        SharedPreferences.Editor userLocalDatabaseEditor = assignmentPreferences.edit();
        userLocalDatabaseEditor.putInt("assignmentSubmissionId", assignmentSubmission.getAssignmentSubmissionId());
        userLocalDatabaseEditor.putInt("assignmentId", assignmentSubmission.getAssignmentId());
        userLocalDatabaseEditor.putInt("userId", assignmentSubmission.getUserId());
        userLocalDatabaseEditor.putString("submission", assignmentSubmission.getSubmission());
        userLocalDatabaseEditor.putInt("id", assignmentSubmission.getId());
        userLocalDatabaseEditor.putString("name", assignmentSubmission.getName());
        userLocalDatabaseEditor.putString("email", assignmentSubmission.getEmail());
        userLocalDatabaseEditor.putInt("grade", assignmentSubmission.getGrade());
        userLocalDatabaseEditor.apply();
    }

    public AssignmentSubmission getAssignmentSubmission() {
        int assignmentSubmissionId = assignmentPreferences.getInt("assignmentSubmissionId", -1);
        int assignmentId = assignmentPreferences.getInt("assignmentId", -1);
        int userId = assignmentPreferences.getInt("userId", -1);
        String submission = assignmentPreferences.getString("submission", "");
        int id = assignmentPreferences.getInt("id", -1);
        String name = assignmentPreferences.getString("name", "");
        String email = assignmentPreferences.getString("email", "");
        int grade = assignmentPreferences.getInt("grade", 0);

        return new AssignmentSubmission(assignmentSubmissionId, assignmentId, userId, submission, id, name, email, grade);
    }

    public boolean isFirstRun() {
        return persistantPreferences.getBoolean(context.getString(R.string.first_run), true);
    }

    public void setFirstRun(boolean b) {
        SharedPreferences.Editor userLocalDatabaseEditor = persistantPreferences.edit();
        userLocalDatabaseEditor.putBoolean(context.getString(R.string.first_run), b);
        userLocalDatabaseEditor.apply();
    }

    public boolean showMainSequence() {
        return persistantPreferences.getBoolean(context.getString(R.string.main_sequence), true);
    }

    public void showSequence(boolean b) {
        SharedPreferences.Editor userLocalDatabaseEditor = persistantPreferences.edit();
        userLocalDatabaseEditor.putBoolean(context.getString(R.string.main_sequence), b);
        userLocalDatabaseEditor.apply();
    }

    public void setTheme(int theme) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.choose_theme), theme);
        editor.apply();
    }

    public int getTheme() {
        return sharedPreferences.getInt(context.getString(R.string.choose_theme), R.style.AppTheme);
    }
}
