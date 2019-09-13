package com.arproject.russell.ar_t;

import com.arproject.russell.ar_t.ar_lessons.navigation.LessonFragment;
import com.arproject.russell.ar_t.models.Topic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LessonFragmentUnitTest {

    private LessonFragment lessonFragment;

    @Before
    public void setUp() {
        lessonFragment = LessonFragment.newInstance(1, new Topic("", "", 1, 1, ""));
        startFragment(lessonFragment);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(lessonFragment);
    }

    @Test
    public void getCardViewTest() {
        assertNotNull(lessonFragment.getCardView());
    }

    @Test
    public void testActivity() {
        assertNotNull(lessonFragment.getActivity());
    }

    @Test
    public void testView() {
        assertNotNull(lessonFragment.getView());
    }

}
