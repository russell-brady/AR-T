package com.arproject.russell.ar_t;

import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.student.classroom.StudentFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StudentFragmentUnitTest {

    private StudentFragment studentFragment;

    @Before
    public void setUp() {
        studentFragment = StudentFragment.newInstance();
        startFragment(studentFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(studentFragment);
    }

    @Test
    public void testView() {
        assertNotNull(studentFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(studentFragment.getView().findViewById(R.id.class_viewpager));
        assertEquals(studentFragment.getView().findViewById(R.id.class_viewpager).getVisibility(), View.VISIBLE);
        assertNotNull(studentFragment.getView().findViewById(R.id.tab_layout));
        assertEquals(studentFragment.getView().findViewById(R.id.tab_layout).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(studentFragment.getActivity());
    }
}
