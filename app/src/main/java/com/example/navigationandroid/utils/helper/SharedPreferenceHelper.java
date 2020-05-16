package com.example.navigationandroid.utils.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferenceHelper {

    private static final String APP_NAME = "Navigation Android";

    private static final String PROFILE_PIC = "profilePic";
    private static final String IS_PROFILE_COMPLETE = "isProfileComplete";
    private static final String USER_MODEL = "userModel";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor sharedPreferencesEditor;

    private static SharedPreferenceHelper instance;

    public static SharedPreferenceHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException("SharedHelper was not initialized!");
        }
        return instance;
    }

    private SharedPreferenceHelper(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public static void init(Context context) {
        if (instance == null)
            instance = new SharedPreferenceHelper(context);
    }

    private void delete(String key) {
        if (sharedPreferences.contains(key)) {
            sharedPreferencesEditor.remove(key).commit();
        }
    }

    private void savePref(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            sharedPreferencesEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            sharedPreferencesEditor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            sharedPreferencesEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sharedPreferencesEditor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            sharedPreferencesEditor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            sharedPreferencesEditor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        sharedPreferencesEditor.commit();
    }

    private <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(SharedPreferenceHelper.PROFILE_PIC);
    }

    private <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public void clearAll() {
        sharedPreferencesEditor.clear().apply();
    }

    public String getProfilePic() {
        return getPref(PROFILE_PIC);
    }

    public void saveProfilePic(String profilePic) {
        savePref(PROFILE_PIC, profilePic);
    }

    public boolean getIsProfileCompleted() {
        return getPref(IS_PROFILE_COMPLETE, false);
    }

    public void saveIsProfileCompleted(boolean b) {
        savePref(IS_PROFILE_COMPLETE, b);
    }

}