package com.xiaomi.mipush.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.push.fk;
import com.xiaomi.push.fl;
import java.util.HashSet;
import java.util.Set;

@TargetApi(14)
/* loaded from: classes6.dex */
public class a implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a, reason: collision with root package name */
    private Set<String> f24512a = new HashSet();

    private static void a(Application application) {
        application.registerActivityLifecycleCallbacks(new a());
    }

    public static void a(Context context) {
        a((Application) context.getApplicationContext());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        fl flVarA;
        String packageName;
        String strM418a;
        int i2;
        String str;
        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        String stringExtra = intent.getStringExtra("messageId");
        int intExtra = intent.getIntExtra("eventMessageType", -1);
        if (TextUtils.isEmpty(stringExtra) || intExtra <= 0 || this.f24512a.contains(stringExtra)) {
            return;
        }
        this.f24512a.add(stringExtra);
        if (intExtra == 3000) {
            flVarA = fl.a(activity.getApplicationContext());
            packageName = activity.getPackageName();
            strM418a = fk.m418a(intExtra);
            i2 = 3008;
            str = "App calls by business message is visiable";
        } else {
            if (intExtra != 1000) {
                return;
            }
            flVarA = fl.a(activity.getApplicationContext());
            packageName = activity.getPackageName();
            strM418a = fk.m418a(intExtra);
            i2 = 1008;
            str = "app calls by notification message is visiable";
        }
        flVarA.a(packageName, strM418a, stringExtra, i2, str);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }
}
