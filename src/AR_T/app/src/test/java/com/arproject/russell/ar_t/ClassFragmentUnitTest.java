package com.arproject.russell.ar_t;

import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.student.classroom.StudentFragment;
import com.arproject.russell.ar_t.teacher.classroom.ClassFragment;

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
public class ClassFragmentUnitTest {

    private ClassFragment classFragment;

    @Before
    public void setUp() {
        classFragment = ClassFragment.newInstance("", 1);
        startFragment(classFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(classFragment);
    }

    @Test
    public void testView() {
        assertNotNull(classFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(classFragment.getView().findViewById(R.id.class_viewpager));
        assertEquals(classFragment.getView().findViewById(R.id.class_viewpager).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(classFragment.getActivity());
    }
}
