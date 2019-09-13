package com.arproject.russell.ar_t;

import android.content.Intent;
import android.provider.MediaStore;

import com.arproject.russell.ar_t.student.assignmentsubmission.AssignmentSubmissionActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AssignmentSubmissionActivityUnitTest {

    private AssignmentSubmissionActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AssignmentSubmissionActivity.class)
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
        assertNotNull(activity.findViewById(R.id.assignmentTitle));
        assertNotNull(activity.findViewById(R.id.assignmentDueDate));
        assertNotNull(activity.findViewById(R.id.assignmentAssignedDate));
    }

    @Test
    public void openGalleryTest() {

        activity.openGallery();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent();
        expectedIntent.setType("image/*");
        expectedIntent.setAction(Intent.ACTION_GET_CONTENT);
        expectedIntent = Intent.createChooser(expectedIntent, "Select Picture");

        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

    @Test
    public void openCameraTest() {

        activity.openCamera();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

}
