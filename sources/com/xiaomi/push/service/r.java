package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes6.dex */
public class r {

    /* renamed from: a, reason: collision with root package name */
    private static volatile r f25707a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f1084a;

    private r(Context context) {
        this.f1084a = context.getSharedPreferences("mipush", 0);
    }

    public static r a(Context context) {
        if (f25707a == null) {
            synchronized (r.class) {
                if (f25707a == null) {
                    f25707a = new r(context);
                }
            }
        }
        return f25707a;
    }

    public synchronized String a() {
        return this.f1084a.getString(Constants.EXTRA_KEY_MIID, "0");
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m761a() {
        SharedPreferences.Editor editorEdit = this.f1084a.edit();
        editorEdit.remove(Constants.EXTRA_KEY_MIID);
        editorEdit.commit();
    }

    public synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "0";
        }
        SharedPreferences.Editor editorEdit = this.f1084a.edit();
        editorEdit.putString(Constants.EXTRA_KEY_MIID, str);
        editorEdit.commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized boolean m762a() {
        return !TextUtils.equals("0", a());
    }
}
