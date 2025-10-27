package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes6.dex */
public class bh {

    /* renamed from: a, reason: collision with root package name */
    private static volatile bh f24635a;

    /* renamed from: a, reason: collision with other field name */
    private Context f207a;

    private bh(Context context) {
        this.f207a = context;
    }

    public static bh a(Context context) {
        if (f24635a == null) {
            synchronized (bh.class) {
                if (f24635a == null) {
                    f24635a = new bh(context);
                }
            }
        }
        return f24635a;
    }

    public synchronized long a(String str, String str2, long j2) {
        try {
        } catch (Throwable unused) {
            return j2;
        }
        return this.f207a.getSharedPreferences(str, 4).getLong(str2, j2);
    }

    public synchronized String a(String str, String str2, String str3) {
        try {
        } catch (Throwable unused) {
            return str3;
        }
        return this.f207a.getSharedPreferences(str, 4).getString(str2, str3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m231a(String str, String str2, long j2) {
        SharedPreferences.Editor editorEdit = this.f207a.getSharedPreferences(str, 4).edit();
        editorEdit.putLong(str2, j2);
        editorEdit.commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m232a(String str, String str2, String str3) {
        SharedPreferences.Editor editorEdit = this.f207a.getSharedPreferences(str, 4).edit();
        editorEdit.putString(str2, str3);
        editorEdit.commit();
    }
}
