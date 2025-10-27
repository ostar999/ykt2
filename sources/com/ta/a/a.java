package com.ta.a;

import android.content.Context;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f17204a = new a();
    private Context mContext = null;

    /* renamed from: a, reason: collision with other field name */
    private long f72a = 0;

    private a() {
    }

    public static a a() {
        return f17204a;
    }

    public Context getContext() {
        return this.mContext;
    }

    public synchronized void a(Context context) {
        if (this.mContext == null) {
            if (context == null) {
                return;
            }
            if (context.getApplicationContext() != null) {
                this.mContext = context.getApplicationContext();
            } else {
                this.mContext = context;
            }
        }
    }

    public void a(long j2) {
        this.f72a = j2 - System.currentTimeMillis();
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m103a() {
        return System.currentTimeMillis() + this.f72a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m104a() {
        return "" + m103a();
    }
}
