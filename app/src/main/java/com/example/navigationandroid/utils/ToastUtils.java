package com.example.navigationandroid.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import com.example.navigationandroid.base.BaseApp;

public class ToastUtils {

    public static void shortToast(@StringRes int text) {
        shortToast(BaseApp.getInstance().getApplicationContext().getString(text));
    }

    public static void shortToast(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void longToast(@StringRes int text) {
        longToast(BaseApp.getInstance().getApplicationContext().getString(text));
    }

    public static void longToast(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    private static Toast makeToast(String text, @ToastLength int length) {
        return Toast.makeText(BaseApp.getInstance().getApplicationContext(), text, length);
    }

    private static void show(String text, @ToastLength int length) {
        makeToast(text, length).show();
    }

    @IntDef({Toast.LENGTH_LONG, Toast.LENGTH_SHORT})
    private @interface ToastLength {

    }
}
