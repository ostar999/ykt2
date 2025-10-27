package com.psychiatrygarden.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes6.dex */
public class KeyboardInputUtil {
    public static void showKeyBoard(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(2, 0);
    }
}
