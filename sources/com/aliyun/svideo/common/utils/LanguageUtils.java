package com.aliyun.svideo.common.utils;

import android.content.Context;
import android.util.Log;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LanguageUtils {
    private static final String TAG = "LanguageUtils";

    public static boolean isCHEN(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        boolean zEquals = "zh".equals(locale.getLanguage().toLowerCase());
        Log.d(TAG, "当前系统语言 : " + locale.getLanguage() + " ,当前地区 ：" + locale.getCountry());
        return zEquals;
    }
}
