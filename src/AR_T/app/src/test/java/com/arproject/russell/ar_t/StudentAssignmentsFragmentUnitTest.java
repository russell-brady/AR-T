package com.arproject.russell.ar_t;

import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.student.classroom.ClassmatesFragment;
import com.arproject.russell.ar_t.student.classroom.StudentAssignmentsFragment;

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
public class StudentAssignmentsFragmentUnitTest {

    private StudentAssignmentsFragment studentAssignmentsFragment;

    @Before
    public void setUp() {
        studentAssignmentsFragment = StudentAssignmentsFragment.newInstance();
        startFragment(studentAssignmentsFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(studentAssignmentsFragment);
    }

    @Test
    public void testView() {
        assertNotNull(studentAssignmentsFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(studentAssignmentsFragment.getView().findViewById(R.id.assignmentsRecyclerView));
        assertEquals(studentAssignmentsFragment.getView().findViewById(R.id.assignmentsRecyclerView).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(studentAssignmentsFragment.getActivity());
    }
}
