package com.arproject.russell.ar_t;

import com.arproject.russell.ar_t.login.LoginViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginViewModelUnitTest {

    private LoginViewModel loginViewModel;


    @Before
    public void before() {
        loginViewModel = new LoginViewModel();
    }

    @Test
    public void testValidEmail() {
        Assert.assertTrue(loginViewModel.isValidEmail("test@test.com"));
    }

    @Test
    public void testInvalidEmail() {
        Assert.assertFalse(loginViewModel.isValidEmail("test"));
    }

    @Test
    public void testNoEmail() {
        Assert.assertFalse(loginViewModel.isValidEmail(""));
    }

    @Test
    public void testValidPassword() {
        Assert.assertTrue(loginViewModel.isValidPassword("p2-*as"));
    }

    @Test
    public void testInvalidPassword() {
        Assert.assertFalse(loginViewModel.isValidPassword(""));
    }
}
