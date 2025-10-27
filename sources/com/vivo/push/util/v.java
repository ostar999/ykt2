package com.vivo.push.util;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/* loaded from: classes6.dex */
final class v implements d {

    /* renamed from: a, reason: collision with root package name */
    private ContentResolver f24472a;

    @Override // com.vivo.push.util.d
    public final boolean a(Context context) {
        if (!j.b()) {
            return false;
        }
        this.f24472a = context.getContentResolver();
        return true;
    }

    @Override // com.vivo.push.util.d
    public final void b(String str, String str2) {
        try {
            Settings.System.putString(this.f24472a, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.b("SettingsCache", "putString error by ".concat(String.valueOf(str)));
        }
    }

    @Override // com.vivo.push.util.d
    public final String a(String str, String str2) {
        try {
            return Settings.System.getString(this.f24472a, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.b("SettingsCache", "getString error by ".concat(String.valueOf(str)));
            return str2;
        }
    }
}
