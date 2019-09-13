package com.arproject.russell.ar_t.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.PrefConfig;

public class LoginActivity extends AppCompatActivity {

    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        progressBar = findViewById(R.id.progress_bar);

        if (savedInstanceState == null) {

            if (prefConfig.isLoggedIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, LoginFragment.newInstance())
                        .commit();
            }

        }
    }
}
