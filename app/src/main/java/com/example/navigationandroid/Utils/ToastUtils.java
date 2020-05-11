package com.example.navigationandroid.Utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

public class ToastUtils {

    public static void shortToast(Context context, @StringRes int text) {
        shortToast(context, context.getString(text));
    }

    public static void shortToast(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void longToast(Context context, @StringRes int text) {
        longToast(context, context.getString(text));
    }

    public static void longToast(Context context, String text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    private static Toast makeToast(Context context, String text, @ToastLength int length) {
        return Toast.makeText(context, text, length);
    }

    private static void show(Context context, String text, @ToastLength int length) {
        makeToast(context, text, length).show();
    }

    @IntDef({Toast.LENGTH_LONG, Toast.LENGTH_SHORT})
    private @interface ToastLength {

    }
}
