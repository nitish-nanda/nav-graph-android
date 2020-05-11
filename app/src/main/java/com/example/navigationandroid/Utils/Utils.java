package com.example.navigationandroid.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Utils {

    public static String capSentences(final String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhone(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches());
    }


    public static void openUrl(Context context, String url) {
        if (!URLUtil.isValidUrl(url)) {
            ToastUtils.longToast(context, "Invalid Url");
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void setMaxLength(int length, EditText editText) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(fArray);
    }

    public static void underLineText(TextView textview) {
        textview.setPaintFlags(textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

}
