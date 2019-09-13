package com.arproject.russell.ar_t;

import com.arproject.russell.ar_t.login.SignUpViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SignUpViewModelUnitTest {

    private SignUpViewModel signUpViewModel;

    @Before
    public void before() {
        signUpViewModel = new SignUpViewModel();
    }

    @Test
    public void testValidEmail() {
        Assert.assertTrue(signUpViewModel.isValidEmail("test@test.com"));
    }

    @Test
    public void testInvalidEmail() {
        Assert.assertFalse(signUpViewModel.isValidEmail("test"));
    }

    @Test
    public void testNoEmail() {
        Assert.assertFalse(signUpViewModel.isValidEmail(""));
    }

    @Test
    public void testValidPassword() {
        Assert.assertTrue(signUpViewModel.isValidPassword("pass", "pass"));
    }

    @Test
    public void testInvalidPassword() {
        Assert.assertFalse(signUpViewModel.isValidPassword("pass", "notpass"));
    }

    @Test
    public void testNoPassword() {
        Assert.assertFalse(signUpViewModel.isValidPassword("", ""));
    }

    @Test
    public void testValidName() {
        Assert.assertTrue(signUpViewModel.isValidName("pass"));
    }

    @Test
    public void testInvalidName() {
        Assert.assertFalse(signUpViewModel.isValidName(""));
    }

    @Test
    public void testValidClass() {
        assertTrue(signUpViewModel.isValidClassId(1234));
    }

    @Test
    public void testInvalidClass() {
        assertFalse(signUpViewModel.isValidClassId(900));
    }
}

