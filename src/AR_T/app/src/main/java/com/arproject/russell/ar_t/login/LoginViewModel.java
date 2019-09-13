package com.arproject.russell.ar_t.login;

import android.arch.lifecycle.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

    public boolean isValidEmail(String userEmail) {
        return (!isEmptyString(userEmail) && isValidPattern(userEmail));
    }

    public boolean isValidPassword(String pword) {
        return (!isEmptyString(pword));
    }

    private boolean isEmptyString(String string) {
        return (string == null || string.trim().equals(""));
    }

    private boolean isValidPattern(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.find();
    }
}
