package com.tencent.smtt.sdk.ui.dialog;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import com.tencent.smtt.sdk.TbsConfig;
import java.io.BufferedInputStream;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private Context f21317a;

    /* renamed from: b, reason: collision with root package name */
    private ResolveInfo f21318b;

    /* renamed from: c, reason: collision with root package name */
    private Drawable f21319c;

    /* renamed from: d, reason: collision with root package name */
    private String f21320d;

    /* renamed from: e, reason: collision with root package name */
    private String f21321e;

    /* renamed from: f, reason: collision with root package name */
    private String f21322f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f21323g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f21324h;

    /* renamed from: i, reason: collision with root package name */
    private String f21325i;

    public b(Context context, int i2, String str, String str2) throws Resources.NotFoundException {
        Drawable drawable;
        this.f21320d = "";
        this.f21321e = "";
        this.f21323g = false;
        this.f21324h = false;
        this.f21325i = "";
        if (i2 != -1) {
            try {
                drawable = context.getResources().getDrawable(i2);
            } catch (Exception unused) {
            }
        } else {
            drawable = null;
        }
        drawable = drawable == null ? e.a("application_icon") : drawable;
        this.f21317a = context.getApplicationContext();
        this.f21318b = null;
        this.f21322f = null;
        this.f21319c = drawable;
        this.f21320d = str2;
        this.f21323g = true;
        this.f21325i = str;
    }

    public b(Context context, ResolveInfo resolveInfo) {
        this.f21320d = "";
        this.f21321e = "";
        this.f21323g = false;
        this.f21324h = false;
        this.f21325i = "";
        this.f21317a = context.getApplicationContext();
        this.f21318b = resolveInfo;
        this.f21319c = null;
        this.f21320d = null;
        this.f21322f = null;
    }

    public b(Context context, Drawable drawable, String str, String str2, String str3) {
        this.f21320d = "";
        this.f21321e = "";
        this.f21323g = false;
        this.f21324h = false;
        this.f21325i = "";
        this.f21317a = context.getApplicationContext();
        this.f21318b = null;
        this.f21319c = drawable;
        this.f21320d = str;
        this.f21322f = str2;
        this.f21324h = true;
        this.f21321e = str3;
    }

    public static Drawable a(Context context, String str) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        if (TbsConfig.APP_QB.equals(str)) {
            try {
                return e.a("application_icon");
            } catch (Throwable th) {
                Log.e("error", "getApkIcon Error:" + Log.getStackTraceString(th));
                return null;
            }
        }
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
            if (applicationInfo == null) {
                return null;
            }
            Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo);
            TypedValue typedValue = new TypedValue();
            resourcesForApplication.getValue(applicationInfo.icon, typedValue, true);
            try {
                return Drawable.createFromResourceStream(resourcesForApplication, typedValue, new BufferedInputStream(resourcesForApplication.getAssets().openNonAssetFd(typedValue.assetCookie, typedValue.string.toString()).createInputStream()), null);
            } catch (Exception unused) {
                return null;
            }
        } catch (Exception e2) {
            Log.e("sdk", "e = " + e2);
            return null;
        }
    }

    public Drawable a() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Drawable drawable = this.f21319c;
        if (drawable != null) {
            return drawable;
        }
        Drawable drawableA = a(this.f21317a, d());
        if (drawableA != null) {
            return drawableA;
        }
        ResolveInfo resolveInfo = this.f21318b;
        return resolveInfo != null ? resolveInfo.loadIcon(this.f21317a.getPackageManager()) : this.f21319c;
    }

    public void a(ResolveInfo resolveInfo) {
        this.f21318b = resolveInfo;
    }

    public void a(Drawable drawable) {
        this.f21319c = drawable;
    }

    public void a(String str) {
        this.f21321e = str;
    }

    public void a(boolean z2) {
        this.f21324h = z2;
    }

    public String b() {
        ResolveInfo resolveInfo = this.f21318b;
        return resolveInfo != null ? resolveInfo.loadLabel(this.f21317a.getPackageManager()).toString() : this.f21320d;
    }

    public ResolveInfo c() {
        return this.f21318b;
    }

    public String d() {
        ResolveInfo resolveInfo = this.f21318b;
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.packageName;
        }
        String str = this.f21322f;
        return str == null ? "" : str;
    }

    public boolean e() {
        return this.f21323g;
    }

    public boolean f() {
        return this.f21324h;
    }

    public String g() {
        return this.f21325i;
    }

    public String h() {
        return this.f21321e;
    }
}
