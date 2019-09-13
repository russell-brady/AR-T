package com.arproject.russell.ar_t;

import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.Assignment;
import com.arproject.russell.ar_t.student.classroom.StudentFragment;
import com.arproject.russell.ar_t.teacher.assignment.AssignmentActivity;
import com.arproject.russell.ar_t.teacher.assignment.InstructionsFragment;
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
public class AssignmentInstructionsFragmentsUnitTests {

    private InstructionsFragment instructionsFragment;

    @Before
    public void setUp() {
        instructionsFragment = InstructionsFragment.newInstance(new Assignment(1, 1, "", "", "", "", "", 1));
        startFragment(instructionsFragment, MainActivity.class);
    }

    @Test
    public void validateInstructionsFragment() {
        assertNotNull(instructionsFragment);
    }

    @Test
    public void testView() {
        assertNotNull(instructionsFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(instructionsFragment.getView().findViewById(R.id.assignmentTitle));
        assertEquals(instructionsFragment.getView().findViewById(R.id.assignmentTitle).getVisibility(), View.VISIBLE);
        assertNotNull(instructionsFragment.getView().findViewById(R.id.assignmentAssignedDate));
        assertEquals(instructionsFragment.getView().findViewById(R.id.assignmentAssignedDate).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(instructionsFragment.getActivity());
    }

}
