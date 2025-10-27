package com.meizu.cloud.pushsdk.notification.c;

import android.content.Context;
import android.content.res.AssetManager;
import com.meizu.cloud.pushinternal.DebugLogger;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    private static d f9489b;

    /* renamed from: a, reason: collision with root package name */
    private Context f9490a;

    /* renamed from: c, reason: collision with root package name */
    private AssetManager f9491c;

    private d(Context context) {
        this.f9490a = context;
        a();
    }

    public static d a(Context context) {
        if (f9489b == null) {
            f9489b = new d(context);
        }
        return f9489b;
    }

    private void a() {
        this.f9491c = this.f9490a.getAssets();
    }

    public int a(String str, String str2) {
        DebugLogger.i("ResourceReader", "Get resource type " + str2 + " " + str);
        return this.f9490a.getResources().getIdentifier(str, str2, this.f9490a.getApplicationInfo().packageName);
    }
}
