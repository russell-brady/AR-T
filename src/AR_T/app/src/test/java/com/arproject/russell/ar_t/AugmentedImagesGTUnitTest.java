package com.arproject.russell.ar_t;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.arproject.russell.ar_t.augmentedimages.AugmentedImagesActivity;
import com.arproject.russell.ar_t.augmentedimages.AugmentedImagesGuidedTourActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AugmentedImagesGTUnitTest {

    private AugmentedImagesGuidedTourActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AugmentedImagesGuidedTourActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void onDonePressedTest() {

        Fragment fragment = Mockito.mock(Fragment.class);

        activity.onDonePressed(fragment);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent(activity, AugmentedImagesActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }
}
