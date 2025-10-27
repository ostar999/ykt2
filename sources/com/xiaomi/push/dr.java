package com.xiaomi.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;

/* loaded from: classes6.dex */
public class dr implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a, reason: collision with root package name */
    private Context f24745a;

    /* renamed from: a, reason: collision with other field name */
    private String f333a;

    /* renamed from: b, reason: collision with root package name */
    private String f24746b;

    public dr(Context context, String str) {
        this.f24745a = context;
        this.f333a = str;
    }

    private void a(String str) {
        ig igVar = new ig();
        igVar.a(str);
        igVar.a(System.currentTimeMillis());
        igVar.a(hz.ActivityActiveTimeStamp);
        ei.a(this.f24745a, igVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        String localClassName = activity.getLocalClassName();
        if (TextUtils.isEmpty(this.f333a) || TextUtils.isEmpty(localClassName)) {
            return;
        }
        this.f24746b = "";
        if (!TextUtils.isEmpty("") && !TextUtils.equals(this.f24746b, localClassName)) {
            this.f333a = "";
            return;
        }
        a(this.f24745a.getPackageName() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + localClassName + ":" + this.f333a + "," + String.valueOf(System.currentTimeMillis() / 1000));
        this.f333a = "";
        this.f24746b = "";
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (TextUtils.isEmpty(this.f24746b)) {
            this.f24746b = activity.getLocalClassName();
        }
        this.f333a = String.valueOf(System.currentTimeMillis() / 1000);
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
