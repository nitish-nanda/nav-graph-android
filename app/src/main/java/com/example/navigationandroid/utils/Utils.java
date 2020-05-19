package com.example.navigationandroid.utils;

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

import java.util.Random;

public class Utils {

    public static String capSentences(final String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhone(CharSequence target) {
        return (TextUtils.isEmpty(target) || !Patterns.PHONE.matcher(target).matches());
    }


    public static void openUrl(Context context, String url) {
        if (!URLUtil.isValidUrl(url)) {
            ToastUtils.longToast("Invalid Url");
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

    public static int generateRandomNumber() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public static void formatNumber(EditText editText, char insertedCharacter, boolean isInsert) {
        String string = editText.getText().toString();
        StringBuilder sb = new StringBuilder(string);
        if (isInsert)
            sb.append(insertedCharacter);
        else
            sb.deleteCharAt(string.length() - 1);
        editText.setText(sb);
        editText.setSelection(editText.getText().length());
    }

    public static String maskCardNumber(String cardNumber) {
        cardNumber = cardNumber.replace(" ", "");
        int var1 = 0;
        StringBuffer var2;
        for (var2 = new StringBuffer(); var1 < cardNumber.length(); ++var1) {
            if (var1 > cardNumber.length() - 5) {
                var2.append(cardNumber.charAt(var1));
            } else {
                if (var1 == 5)
                    var2.append(" ");
                else if (var1 % 4 == 0)
                    var2.append(" ");
                else
                    var2.append("*");
            }
        }
        return var2.toString();
    }

}
