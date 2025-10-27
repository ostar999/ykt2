package com.tencent.tbs.one;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes6.dex */
public enum TBSOnePrivacy {
    Imei(false, "imei"),
    Imsi(false, "imsi"),
    AndroidId(false, SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID),
    DeviceModel(true, "device_model"),
    SN(true, "sn"),
    Qimei36(true, "qimei36");

    public static final String PREFERENCES_FILENAME = "com.tencent.tbs.one.privacy";

    /* renamed from: a, reason: collision with root package name */
    public final boolean f21689a;

    /* renamed from: b, reason: collision with root package name */
    public String f21690b;

    TBSOnePrivacy(boolean z2, String str) {
        this.f21689a = z2;
        this.f21690b = str;
    }

    public static SharedPreferences a(Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(PREFERENCES_FILENAME, 4);
    }

    public final String get(Context context) {
        return get(context, "");
    }

    public final String get(Context context, String str) {
        SharedPreferences sharedPreferencesA;
        try {
            return (isDisabled() || (sharedPreferencesA = a(context)) == null) ? str : sharedPreferencesA.getString(this.f21690b, str);
        } catch (Throwable unused) {
            return "";
        }
    }

    public final boolean isDisabled() {
        return !this.f21689a;
    }

    public final boolean isEnabled() {
        return this.f21689a;
    }

    public final synchronized void set(Context context, String str) {
        try {
            SharedPreferences sharedPreferencesA = a(context);
            if (sharedPreferencesA == null) {
                return;
            }
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putString(this.f21690b, str);
            editorEdit.apply();
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }
}
