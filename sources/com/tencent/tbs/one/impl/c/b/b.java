package com.tencent.tbs.one.impl.c.b;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.tencent.tbs.one.impl.a.g;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public final class b extends ContextWrapper {

    /* renamed from: b, reason: collision with root package name */
    public static Method f21940b;

    /* renamed from: a, reason: collision with root package name */
    public a f21941a;

    /* renamed from: c, reason: collision with root package name */
    public Resources f21942c;

    /* renamed from: d, reason: collision with root package name */
    public Resources.Theme f21943d;

    /* renamed from: e, reason: collision with root package name */
    public String f21944e;

    static {
        try {
            f21940b = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
        } catch (Throwable unused) {
        }
    }

    public b(Context context, String str) {
        super(context);
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 0);
        if (packageArchiveInfo != null) {
            ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
            if (applicationInfo != null) {
                this.f21944e = applicationInfo.packageName;
                applicationInfo.sourceDir = str;
                applicationInfo.publicSourceDir = str;
                try {
                    this.f21942c = context.getPackageManager().getResourcesForApplication(applicationInfo);
                } catch (Throwable unused) {
                    this.f21942c = a(context, str);
                }
                Resources resources = this.f21942c;
                if (resources != null) {
                    this.f21943d = resources.newTheme();
                    Resources.Theme theme = getBaseContext().getTheme();
                    if (theme != null) {
                        this.f21943d.setTo(theme);
                    }
                    int i2 = applicationInfo.theme;
                    if (i2 != 0) {
                        this.f21943d.applyStyle(i2, true);
                    }
                } else {
                    g.b("Failed to create resource info from %s", str);
                }
            } else {
                g.b("Failed to get application info from %s", str);
            }
        } else {
            g.b("Failed to get package info from %s", str);
        }
        this.f21941a = new a(this);
    }

    private Intent a(Intent intent) {
        ComponentName component = intent.getComponent();
        if (component != null) {
            intent.setComponent(new ComponentName(getBaseContext(), component.getClassName()));
        }
        return intent;
    }

    public static Resources a(Context context, String str) {
        if (f21940b == null) {
            return null;
        }
        try {
            AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
            f21940b.invoke(assetManager, str);
            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            Class<?> cls = resources.getClass();
            if (!"android.taobao.atlas.runtime.DelegateResources".equals(cls.getName())) {
                try {
                    return (Resources) cls.getConstructor(AssetManager.class, DisplayMetrics.class, Configuration.class).newInstance(assetManager, displayMetrics, configuration);
                } catch (Throwable unused) {
                }
            }
            return new Resources(assetManager, displayMetrics, configuration);
        } catch (Throwable th) {
            g.b("Failed to new resources from %s", str, th);
            return null;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        AssetManager assets;
        Resources resources = getResources();
        return (resources == null || (assets = resources.getAssets()) == null) ? super.getAssets() : assets;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final String getPackageName() {
        String str = this.f21944e;
        return str != null ? str : super.getPackageName();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        Resources resources = this.f21942c;
        return resources != null ? resources : super.getResources();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Object getSystemService(String str) {
        return "layout_inflater".equals(str) ? this.f21941a : super.getSystemService(str);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources.Theme getTheme() {
        Resources.Theme theme = this.f21943d;
        return theme != null ? theme : super.getTheme();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i2) {
        Resources.Theme theme = this.f21943d;
        if (theme != null) {
            theme.applyStyle(i2, true);
        } else {
            super.setTheme(i2);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void startActivity(Intent intent) {
        super.startActivity(a(intent));
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final ComponentName startService(Intent intent) {
        return super.startService(a(intent));
    }
}
