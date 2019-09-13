package com.arproject.russell.ar_t;

import android.widget.FrameLayout;

import com.arproject.russell.ar_t.login.LoginActivity;
import com.arproject.russell.ar_t.login.LoginFragment;
import com.arproject.russell.ar_t.login.SignUpFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityUnitTest {

    private LoginActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void validateMainContent() {
        FrameLayout mainContent = activity.findViewById(R.id.container);
        assertNotNull(mainContent);
    }

    @Test
    public void validateLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        startFragment(loginFragment);
        assertNotNull(loginFragment);
    }

    @Test
    public void validateSignUpFragment() {
        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        startFragment(signUpFragment);
        assertNotNull(signUpFragment);
    }
}
