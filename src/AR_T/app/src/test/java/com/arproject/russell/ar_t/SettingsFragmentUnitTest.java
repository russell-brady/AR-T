package com.arproject.russell.ar_t;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.arproject.russell.ar_t.guided_tour.GuidedTourActivity;
import com.arproject.russell.ar_t.login.LoginActivity;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.settings.SettingsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsFragmentUnitTest {

    private SettingsFragment settingsFragment;

    @Before
    public void setUp() {
        settingsFragment = SettingsFragment.newInstance();
        startFragment(settingsFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(settingsFragment);
    }

    @Test
    public void testView() {
        assertNotNull(settingsFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(settingsFragment.findPreference("theme"));
        assertNotNull(settingsFragment.findPreference("showcase"));
        assertNotNull(settingsFragment.findPreference("guidedtour"));
        assertNotNull(settingsFragment.findPreference("logout"));
        assertNotNull(settingsFragment.findPreference("deleteaccount"));

    }

    @Test
    public void testLogoutClick() {
        settingsFragment.findPreference("logout").performClick();
        ((android.support.v7.app.AlertDialog) ShadowAlertDialog.getLatestDialog()).getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(settingsFragment.getActivity());
        Intent expectedIntent = new Intent(settingsFragment.getActivity(), LoginActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testDeleteAccountClick() {
        settingsFragment.findPreference("deleteaccount").performClick();
        ((android.support.v7.app.AlertDialog) ShadowAlertDialog.getLatestDialog()).getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(settingsFragment.getActivity());
        Intent expectedIntent = new Intent(settingsFragment.getActivity(), LoginActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }


    @Test
    public void testShowCaseView() {
        settingsFragment.findPreference("showcase").performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(settingsFragment.getActivity());
        Intent expectedIntent = new Intent(settingsFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testGuidedTourNavigation() {
        settingsFragment.findPreference("guidedtour").performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(settingsFragment.getActivity());
        Intent expectedIntent = new Intent(settingsFragment.getActivity(), GuidedTourActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }


    @Test
    public void testChooseThemeNavigation() {
        settingsFragment.findPreference("theme").performClick();

        int index = settingsFragment.getFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = settingsFragment.getFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(settingsFragment.getString(R.string.choose_theme), tag);
    }

    @Test
    public void testActivity() {
        assertNotNull(settingsFragment.getActivity());
    }
}
