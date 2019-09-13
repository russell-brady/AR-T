package com.arproject.russell.ar_t;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;

import com.arproject.russell.ar_t.augmentedimages.AugmentedImagesGuidedTourActivity;
import com.arproject.russell.ar_t.login.LoginActivity;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.User;
import com.arproject.russell.ar_t.utils.PrefConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityUnitTest {

    private MainActivity activity;
    private PrefConfig prefConfig = Mockito.mock(PrefConfig.class);

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class)
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
        FrameLayout mainContent = activity.findViewById(R.id.main_content);
        assertNotNull(mainContent);
    }

    @Test
    public void activityContainsFragment() {
        assertNotNull(activity.getSupportFragmentManager().findFragmentById(R.id.main_content));
    }

    @Test
    public void clickAugmentedImagesNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.augnemtedImages));

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent(activity, AugmentedImagesGuidedTourActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void clickTeacherClassroomNavDrawer() {

        when(prefConfig.getLoggedInUser()).thenReturn(new User(1, "test", "test", "test", "Teacher"));

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.classroom));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.classroom), tag);
    }

    @Test
    public void clickStudentClassroomNavDrawer() {

        when(prefConfig.getLoggedInUser()).thenReturn(new User(1, "test", "test", "test", "Student"));

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.classroom));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.classroom), tag);
    }

    @Test
    public void clickLogoutNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.logout));

        ((android.support.v7.app.AlertDialog) ShadowAlertDialog.getLatestDialog()).getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void clickSettingsNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.settings));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.settings_title), tag);
    }

    @Test
    public void clickUserProfileNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.profile));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.user_profile), tag);
    }

    @Test
    public void clickArLessonsNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.animations));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.ar_animations), tag);
    }

    @Test
    public void clickArModelsNavDrawer() {

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        activity.onNavigationItemSelected(new RoboMenuItem(R.id.ar_topics));

        int index = activity.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = activity.getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();

        assertEquals(activity.getString(R.string.ar_topics_title), tag);
    }
}
