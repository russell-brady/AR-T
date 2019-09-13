package com.arproject.russell.ar_t;

import android.content.Intent;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.theme.ChooseThemeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChooseThemeFragmentUnitTest {

    private ChooseThemeFragment chooseThemeFragment;

    @Before
    public void setUp() {
        chooseThemeFragment = ChooseThemeFragment.newInstance();
        startFragment(chooseThemeFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(chooseThemeFragment);
    }

    @Test
    public void testView() {
        assertNotNull(chooseThemeFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(chooseThemeFragment.getView().findViewById(R.id.blueTheme));
        assertNotNull(chooseThemeFragment.getView().findViewById(R.id.mintTheme));
        assertNotNull(chooseThemeFragment.getView().findViewById(R.id.blackTheme));
        assertNotNull(chooseThemeFragment.getView().findViewById(R.id.pinkTheme));
        assertNotNull(chooseThemeFragment.getView().findViewById(R.id.orangeTheme));
    }

    @Test
    public void testBlueTheme() {
        chooseThemeFragment.getView().findViewById(R.id.blueTheme).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(chooseThemeFragment.getActivity());
        Intent expectedIntent = new Intent(chooseThemeFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testBlackTheme() {
        chooseThemeFragment.getView().findViewById(R.id.blackTheme).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(chooseThemeFragment.getActivity());
        Intent expectedIntent = new Intent(chooseThemeFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testMintTheme() {
        chooseThemeFragment.getView().findViewById(R.id.mintTheme).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(chooseThemeFragment.getActivity());
        Intent expectedIntent = new Intent(chooseThemeFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testPinkTheme() {
        chooseThemeFragment.getView().findViewById(R.id.pinkTheme).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(chooseThemeFragment.getActivity());
        Intent expectedIntent = new Intent(chooseThemeFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testOrangeTheme() {
        chooseThemeFragment.getView().findViewById(R.id.orangeTheme).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(chooseThemeFragment.getActivity());
        Intent expectedIntent = new Intent(chooseThemeFragment.getActivity(), MainActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void testActivity() {
        assertNotNull(chooseThemeFragment.getActivity());
    }
}
