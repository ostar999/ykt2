package com.plv.thirdpart.litepal.util;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.plv.thirdpart.litepal.LitePalApplication;

/* loaded from: classes5.dex */
public class SharedUtil {
    private static final String LITEPAL_PREPS = "litepal_prefs";
    private static final String VERSION = "litepal_version";

    public static int getLastVersion(String str) {
        SharedPreferences sharedPreferences = LitePalApplication.getContext().getSharedPreferences(LITEPAL_PREPS, 0);
        if (TextUtils.isEmpty(str)) {
            return sharedPreferences.getInt(VERSION, 0);
        }
        if (str.endsWith(".db")) {
            str = str.replace(".db", "");
        }
        return sharedPreferences.getInt("litepal_version_" + str, 0);
    }

    public static void removeVersion(String str) {
        SharedPreferences.Editor editorEdit = LitePalApplication.getContext().getSharedPreferences(LITEPAL_PREPS, 0).edit();
        if (TextUtils.isEmpty(str)) {
            editorEdit.remove(VERSION);
        } else {
            if (str.endsWith(".db")) {
                str = str.replace(".db", "");
            }
            editorEdit.remove("litepal_version_" + str);
        }
        editorEdit.apply();
    }

    public static void updateVersion(String str, int i2) {
        SharedPreferences.Editor editorEdit = LitePalApplication.getContext().getSharedPreferences(LITEPAL_PREPS, 0).edit();
        if (TextUtils.isEmpty(str)) {
            editorEdit.putInt(VERSION, i2);
        } else {
            if (str.endsWith(".db")) {
                str = str.replace(".db", "");
            }
            editorEdit.putInt("litepal_version_" + str, i2);
        }
        editorEdit.apply();
    }
}
