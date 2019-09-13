package com.arproject.russell.ar_t;

import android.view.View;

import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.student.classroom.ClassmatesFragment;
import com.arproject.russell.ar_t.theme.ChooseThemeFragment;

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
public class StudentClassmatesUnitTest {

    private ClassmatesFragment classmatesFragment;

    @Before
    public void setUp() {
        classmatesFragment = ClassmatesFragment.newInstance();
        startFragment(classmatesFragment, MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(classmatesFragment);
    }

    @Test
    public void testView() {
        assertNotNull(classmatesFragment.getView());
    }

    @Test
    public void testViewVisibility() {
        assertNotNull(classmatesFragment.getView().findViewById(R.id.studentsRecyclerView));
        assertEquals(classmatesFragment.getView().findViewById(R.id.studentsRecyclerView).getVisibility(), View.VISIBLE);
    }

    @Test
    public void testActivity() {
        assertNotNull(classmatesFragment.getActivity());
    }
}
