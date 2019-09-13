package com.arproject.russell.ar_t;

import com.arproject.russell.ar_t.teacher.assignment.AssignmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AssignmentActivityUnitTest {

    private AssignmentActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AssignmentActivity.class)
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
        assertNotNull(activity.findViewById(R.id.container));
    }


}
