package com.cyplay.atproj.asperteam.utils;

/**
 * Created by andre on 10-Apr-18.
 */

public class AsperTeamValidator {

    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean validateEmail(String email) {
        boolean valid = email.trim().matches(emailPattern)
                && email.length() > 0;
        return valid;
    }

    public static boolean validateUsername(String username) {
        boolean valid = username.length() >= 6;
        return valid;
    }

    public static boolean validatePassword(String password) {
        boolean valid = password.length() >= 1;
        return valid;
    }
}
