package com.arproject.russell.ar_t;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.teacher.assignment.AssignmentActivity;
import com.arproject.russell.ar_t.teacher.classes.TeacherClassesFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TeacherClassesFragmentUnitTest {

    private TeacherClassesFragment teacherClassesFragment;

    @Before
    public void setUp() {
        teacherClassesFragment = TeacherClassesFragment.newInstance();
        startFragment(teacherClassesFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(teacherClassesFragment);
    }

    @Test
    public void testView() {
        assertNotNull(teacherClassesFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(teacherClassesFragment.getView().findViewById(R.id.classList));
        assertEquals(teacherClassesFragment.getView().findViewById(R.id.classList).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(teacherClassesFragment.getActivity());
    }
}
