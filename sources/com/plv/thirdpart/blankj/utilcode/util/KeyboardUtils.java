package com.plv.thirdpart.blankj.utilcode.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes5.dex */
public final class KeyboardUtils {
    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void clickBlankArea2HideSoftInput() {
        Log.d("tips", "U should copy the following code.");
    }

    public static void hideSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static void showSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.showSoftInput(currentFocus, 2);
    }

    public static void toggleSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.toggleSoftInput(2, 0);
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftInput(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager == null || inputMethodManager.showSoftInput(view, 2)) {
            return;
        }
        inputMethodManager.toggleSoftInput(0, 0);
    }
}
