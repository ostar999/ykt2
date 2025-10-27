package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.Utils;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LanguageUtils {
    private static final String KEY_LOCALE = "KEY_LOCALE";
    private static final String VALUE_FOLLOW_SYSTEM = "VALUE_FOLLOW_SYSTEM";

    private LanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void applyLanguage(@NonNull Locale locale) {
        applyLanguage(locale, false);
    }

    private static void applyLanguageReal(Locale locale, final boolean z2) {
        if (locale == null) {
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, VALUE_FOLLOW_SYSTEM, true);
        } else {
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, locale2String(locale), true);
        }
        if (locale == null) {
            locale = getLocal(Resources.getSystem().getConfiguration());
        }
        updateAppContextLanguage(locale, new Utils.Consumer<Boolean>() { // from class: com.blankj.utilcode.util.LanguageUtils.1
            @Override // com.blankj.utilcode.util.Utils.Consumer
            public void accept(Boolean bool) {
                if (bool.booleanValue()) {
                    LanguageUtils.restart(z2);
                } else {
                    UtilsBridge.relaunchApp();
                }
            }
        });
    }

    public static void applySystemLanguage() {
        applySystemLanguage(false);
    }

    public static Context attachBaseContext(Context context) {
        Locale localeString2Locale;
        String string = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(string) || VALUE_FOLLOW_SYSTEM.equals(string) || (localeString2Locale = string2Locale(string)) == null) {
            return context;
        }
        Configuration configuration = context.getResources().getConfiguration();
        setLocal(configuration, localeString2Locale);
        return context.createConfigurationContext(configuration);
    }

    public static Locale getAppContextLanguage() {
        return getContextLanguage(Utils.getApp());
    }

    public static Locale getAppliedLanguage() {
        String string = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(string) || VALUE_FOLLOW_SYSTEM.equals(string)) {
            return null;
        }
        return string2Locale(string);
    }

    public static Locale getContextLanguage(Context context) {
        return getLocal(context.getResources().getConfiguration());
    }

    private static Locale getLocal(Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static Locale getSystemLanguage() {
        return getLocal(Resources.getSystem().getConfiguration());
    }

    public static boolean isAppliedLanguage() {
        return getAppliedLanguage() != null;
    }

    private static boolean isRightFormatLocalStr(String str) {
        int i2 = 0;
        for (char c3 : str.toCharArray()) {
            if (c3 == '$') {
                if (i2 >= 1) {
                    return false;
                }
                i2++;
            }
        }
        return i2 == 1;
    }

    private static boolean isSameLocale(Locale locale, Locale locale2) {
        return UtilsBridge.equals(locale2.getLanguage(), locale.getLanguage()) && UtilsBridge.equals(locale2.getCountry(), locale.getCountry());
    }

    private static String locale2String(Locale locale) {
        return locale.getLanguage() + "$" + locale.getCountry();
    }

    public static void pollCheckAppContextLocal(final Locale locale, final int i2, final Utils.Consumer<Boolean> consumer) {
        Resources resources = Utils.getApp().getResources();
        Configuration configuration = resources.getConfiguration();
        Locale local = getLocal(configuration);
        setLocal(configuration, locale);
        Utils.getApp().getResources().updateConfiguration(configuration, resources.getDisplayMetrics());
        if (consumer == null) {
            return;
        }
        if (isSameLocale(local, locale)) {
            consumer.accept(Boolean.TRUE);
        } else if (i2 < 20) {
            UtilsBridge.runOnUiThreadDelayed(new Runnable() { // from class: com.blankj.utilcode.util.LanguageUtils.2
                @Override // java.lang.Runnable
                public void run() {
                    LanguageUtils.pollCheckAppContextLocal(locale, i2 + 1, consumer);
                }
            }, 16L);
        } else {
            Log.e("LanguageUtils", "appLocal didn't update.");
            consumer.accept(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void restart(boolean z2) {
        if (z2) {
            UtilsBridge.relaunchApp();
            return;
        }
        Iterator<Activity> it = UtilsBridge.getActivityList().iterator();
        while (it.hasNext()) {
            it.next().recreate();
        }
    }

    private static void setLocal(Configuration configuration, Locale locale) {
        configuration.setLocale(locale);
    }

    private static Locale string2Locale(String str) {
        Locale localeString2LocaleReal = string2LocaleReal(str);
        if (localeString2LocaleReal == null) {
            Log.e("LanguageUtils", "The string of " + str + " is not in the correct format.");
            UtilsBridge.getSpUtils4Utils().remove(KEY_LOCALE);
        }
        return localeString2LocaleReal;
    }

    private static Locale string2LocaleReal(String str) {
        if (!isRightFormatLocalStr(str)) {
            return null;
        }
        try {
            int iIndexOf = str.indexOf("$");
            return new Locale(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
        } catch (Exception unused) {
            return null;
        }
    }

    public static void updateAppContextLanguage(@NonNull Locale locale, @Nullable Utils.Consumer<Boolean> consumer) {
        pollCheckAppContextLocal(locale, 0, consumer);
    }

    private static void updateConfiguration(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        setLocal(configuration, locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void applyLanguage(@NonNull Locale locale, boolean z2) {
        applyLanguageReal(locale, z2);
    }

    public static void applySystemLanguage(boolean z2) {
        applyLanguageReal(null, z2);
    }

    public static boolean isAppliedLanguage(@NonNull Locale locale) {
        Locale appliedLanguage = getAppliedLanguage();
        if (appliedLanguage == null) {
            return false;
        }
        return isSameLocale(locale, appliedLanguage);
    }

    public static void applyLanguage(Activity activity) {
        Locale localeString2Locale;
        String string = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        if (VALUE_FOLLOW_SYSTEM.equals(string)) {
            localeString2Locale = getLocal(Resources.getSystem().getConfiguration());
        } else {
            localeString2Locale = string2Locale(string);
        }
        if (localeString2Locale == null) {
            return;
        }
        updateConfiguration(activity, localeString2Locale);
        updateConfiguration(Utils.getApp(), localeString2Locale);
    }
}
