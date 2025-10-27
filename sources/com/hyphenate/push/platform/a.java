package com.hyphenate.push.platform;

import android.content.Context;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8871a = "IPush";

    /* renamed from: b, reason: collision with root package name */
    private String f8872b;

    public String a() {
        return this.f8872b;
    }

    public abstract String a(EMPushConfig eMPushConfig);

    public void a(Context context) {
        b(context);
    }

    public void a(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        this.f8872b = a(eMPushConfig);
        b(context, eMPushConfig, pushListener);
    }

    public abstract EMPushType b();

    public abstract void b(Context context);

    public abstract void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener);
}
