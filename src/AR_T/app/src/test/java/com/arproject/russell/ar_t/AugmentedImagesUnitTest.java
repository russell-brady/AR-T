package com.arproject.russell.ar_t;

import com.arproject.russell.ar_t.augmentedimages.AugmentedImagesActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AugmentedImagesUnitTest {

    private AugmentedImagesActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AugmentedImagesActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

}
