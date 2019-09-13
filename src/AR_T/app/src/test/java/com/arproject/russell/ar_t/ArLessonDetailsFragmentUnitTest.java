package com.arproject.russell.ar_t;

import android.content.Intent;

import com.arproject.russell.ar_t.ar_lessons.ArLessonActivity;
import com.arproject.russell.ar_t.ar_lessons.navigation.ArLessonDetailsFragment;
import com.arproject.russell.ar_t.login.LoginActivity;
import com.arproject.russell.ar_t.models.Topic;
import com.arproject.russell.ar_t.models.Topics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ArLessonDetailsFragmentUnitTest {

    private ArLessonDetailsFragment arLessonDetailsFragment;

    @Before
    public void setUp() {
        arLessonDetailsFragment = ArLessonDetailsFragment.newInstance(1);
        startFragment(arLessonDetailsFragment);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(arLessonDetailsFragment);
    }

    @Test
    public void getTopicTest() {
        Topic topic = arLessonDetailsFragment.getTopic();
        assertEquals(Topics.getItems().get(1), topic);
    }

    @Test
    public void getDifferentTopicTest() {
        Topic topic = arLessonDetailsFragment.getTopic();
        assertNotSame(Topics.getItems().get(4), topic);
    }

    @Test
    public void testActivity() {
        assertNotNull(arLessonDetailsFragment.getActivity());
    }

    @Test
    public void testView() {
        assertNotNull(arLessonDetailsFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(arLessonDetailsFragment.getView().findViewById(R.id.arFab));
    }

    @Test
    public void testArActivityNavigation() {
        arLessonDetailsFragment.getView().findViewById(R.id.arFab).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(arLessonDetailsFragment.getActivity());
        Intent expectedIntent = new Intent(arLessonDetailsFragment.getActivity(), ArLessonActivity.class);
        expectedIntent.putExtra("route", 1);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertTrue(startedIntent.filterEquals(expectedIntent));
    }

}
