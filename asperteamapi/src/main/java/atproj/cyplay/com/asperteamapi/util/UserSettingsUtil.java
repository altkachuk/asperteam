package atproj.cyplay.com.asperteamapi.util;

import android.content.Context;
import android.content.SharedPreferences;

import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;

/**
 * Created by andre on 29-Mar-18.
 */

public class UserSettingsUtil {

    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";
    private final String TOKEN = "TOKEN";
    private final String ID = "ID";
    private final String BAND_USE_AGREE = "BAND_USE_AGREE";
    private final String CALL_USE_AGREE = "CALL_USE_AGREE";
    private final String CHAT_USE_AGREE = "CHAT_USE_AGREE";
    private final String GEOLOCATION_SENDING = "GEOLOCATION_SENDING";
    private final String FACEBOOK_ACCOUNT = "FACEBOOK_ACCOUNT";
    private final String STRESS_LEVEL_MIN = "STRESS_LEVEL_MIN";
    private final String STRESS_LEVEL_MAX = "STRESS_LEVEL_MAX";

    private Context _context;
    private SharedPreferences _sharedPreferences;
    private SharedPreferences.Editor _editor;

    public UserSettingsUtil(Context context) {
        _context = context;
        _sharedPreferences = _context.getSharedPreferences("com.cyplay.atproj.asperteam", Context.MODE_PRIVATE);
        _editor = _sharedPreferences.edit();
    }

    public String getUsername() {
        return _sharedPreferences.getString(USERNAME, null);
    }

    public void setUsername(String value) {
        _editor.putString(USERNAME, value);
        _editor.commit();
    }

    public String getPassword() {
        return _sharedPreferences.getString(PASSWORD, null);
    }

    public void setPassword(String value) {
        _editor.putString(PASSWORD, value);
        _editor.commit();
    }

    public String getToken() {
        String token = _sharedPreferences.getString(TOKEN, null);
        return token;
    }

    public void setToken(String value) {
        _editor.putString(TOKEN, value);
        _editor.commit();
    }

    public String getId() {
        return _sharedPreferences.getString(ID, null);
    }

    public void setId(String value) {
        _editor.putString(ID, value);
        _editor.commit();
    }

    public Boolean isBandUseAgree() {
        return _sharedPreferences.getBoolean(BAND_USE_AGREE, false);
    }

    public void setBandUseAgree(Boolean value) {
        _editor.putBoolean(BAND_USE_AGREE, value);
        _editor.commit();
    }

    public Boolean isCallUseAgree() {
        return _sharedPreferences.getBoolean(CALL_USE_AGREE, false);
    }

    public void setCallUseAgree(Boolean value) {
        _editor.putBoolean(CALL_USE_AGREE, value);
        _editor.commit();
    }

    public Boolean isChatUseAgree() {
        return _sharedPreferences.getBoolean(CHAT_USE_AGREE, false);
    }

    public void setChatUseAgree(Boolean value) {
        _editor.putBoolean(CHAT_USE_AGREE, value);
        _editor.commit();
    }

    public Boolean isGeolocationSending() {
        return _sharedPreferences.getBoolean(GEOLOCATION_SENDING, true);
    }

    public void setGeolocationSending(Boolean value) {
        _editor.putBoolean(GEOLOCATION_SENDING, value);
        _editor.commit();
    }

    public void setAuthCredentials(OAuth2Credentials authCredentials) {
         setToken(authCredentials.getToken());
         setId(authCredentials.getId());
    }

    public void clearAuthCredentials() {
        setToken(null);
        setId(null);
    }

    public String getFacebookAccount() {
        return _sharedPreferences.getString(FACEBOOK_ACCOUNT, null);
    }

    public void setFacebookAccount(String value) {
        _editor.putString(FACEBOOK_ACCOUNT, value);
        _editor.commit();
    }

    public float getStressLevelMin() {
        return _sharedPreferences.getFloat(STRESS_LEVEL_MIN, 50);
    }

    public void setStressLevelMin(float value) {
        _editor.putFloat(STRESS_LEVEL_MIN, value);
        _editor.commit();
    }

    public float getStressLevelMax() {
        return _sharedPreferences.getFloat(STRESS_LEVEL_MAX, 85);
    }

    public void setStressLevelMax(float value) {
        _editor.putFloat(STRESS_LEVEL_MAX, value);
        _editor.commit();
    }
}
