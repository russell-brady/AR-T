package com.arproject.russell.ar_t;

import android.view.View;
import android.widget.TextView;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.user_profile.UserProfileFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UserProfileFragmentUnitTest {

    UserProfileFragment userProfileFragment;

    @Before
    public void setUp() {
        userProfileFragment = UserProfileFragment.newInstance();
        startFragment(userProfileFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(userProfileFragment);
    }

    @Test
    public void testView() {
        assertNotNull(userProfileFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        TextView username = userProfileFragment.getView().findViewById(R.id.usersname);
        assertNotNull(username);
        assertEquals(username.getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(userProfileFragment.getActivity());
    }


}
