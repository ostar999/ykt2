package com.tencent.tbs.one.impl;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.tencent.tbs.one.BuildConfig;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneDebugger;
import com.tencent.tbs.one.TBSOneDelegate;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.TBSOneManager;
import com.tencent.tbs.one.TBSOneOnlineService;
import com.tencent.tbs.one.TBSOneTiming;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.e.h;
import java.io.File;

/* loaded from: classes6.dex */
public final class d extends TBSOneManager {

    /* renamed from: a, reason: collision with root package name */
    public final Object f22016a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public String f22017b;

    /* renamed from: c, reason: collision with root package name */
    public h f22018c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f22019d;

    public d(Context context, String str) {
        TBSOneTiming.category(str).start("initializing");
        a.a(context);
        g.a("[%s] Initializing %s (%s)", str, BuildConfig.VERSION_NAME, BuildConfig.BUILD_TIMESTAMP);
        this.f22017b = str;
        this.f22018c = a.a(context, str);
        TBSOneTiming.category(str).end("initializing");
    }

    private void a() {
        synchronized (this.f22016a) {
            this.f22019d = true;
        }
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void cancelComponent(final String str) {
        g.a("[%s] Canceling component %s", this.f22017b, str);
        a();
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.5
            @Override // java.lang.Runnable
            public final void run() {
                d.this.f22018c.d(str);
            }
        });
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void configure(String str, Object obj) {
        g.a("[%s] Configuring %s = %s", this.f22017b, str, obj);
        this.f22018c.a(str, obj);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final String getBuildNumber() {
        return BuildConfig.BUILD_TIMESTAMP;
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final TBSOneDebugger getDebugger() {
        return this.f22018c.d();
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final File getInstalledComponentPath(String str) {
        if (isComponentInstalled(str)) {
            try {
                File fileInstallComponentSync = installComponentSync(str, null, 1000L);
                if (fileInstallComponentSync != null) {
                    return fileInstallComponentSync;
                }
            } catch (Exception e2) {
                g.a("handle Loading component %s exception:%s", str, e2.toString());
            }
        }
        return null;
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final int[] getInstalledVersionCodesOfComponent(String str) {
        return this.f22018c.c(str);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final TBSOneComponent getLoadedComponent(String str) {
        return this.f22018c.f(str);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final TBSOneOnlineService getOnlineService() {
        a();
        return this.f22018c.c();
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final int getVersionCode() {
        return 5;
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void installComponent(final String str, final Bundle bundle, final TBSOneCallback<File> tBSOneCallback) {
        g.a("[%s] Installing component %s with options %s", this.f22017b, str, bundle);
        a();
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.2
            @Override // java.lang.Runnable
            public final void run() {
                d.this.f22018c.b(str, bundle, tBSOneCallback);
            }
        });
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void installComponent(String str, TBSOneCallback<File> tBSOneCallback) {
        installComponent(str, null, tBSOneCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.tencent.tbs.one.TBSOneManager
    public final File installComponentSync(final String str, final Bundle bundle, long j2) throws TBSOneException {
        if (o.b()) {
            throw new RuntimeException("TBSOneManager.installComponentSync must not be called on TBSOne thread.");
        }
        a();
        final c cVar = new c();
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.6
            @Override // java.lang.Runnable
            public final void run() {
                d.this.f22018c.b(str, bundle, cVar);
            }
        });
        cVar.a(j2);
        int i2 = cVar.f21800b;
        if (i2 == 0) {
            return (File) cVar.f21799a;
        }
        throw new TBSOneException(i2, cVar.f21801c);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final boolean isComponentInstalled(String str) {
        return this.f22018c.b(str);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void loadComponentAsync(final String str, final Bundle bundle, final TBSOneCallback<TBSOneComponent> tBSOneCallback) {
        g.a("[%s] Loading component %s asynchronously", this.f22017b, str);
        a();
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.3
            @Override // java.lang.Runnable
            public final void run() {
                TBSOneTiming.category(d.this.f22017b).component(str).start("loadAsync");
                d.this.f22018c.a(str, bundle, new TBSOneCallback<TBSOneComponent>() { // from class: com.tencent.tbs.one.impl.d.3.1
                    @Override // com.tencent.tbs.one.TBSOneCallback
                    public final /* synthetic */ void onCompleted(TBSOneComponent tBSOneComponent) {
                        TBSOneComponent tBSOneComponent2 = tBSOneComponent;
                        TBSOneTiming.category(d.this.f22017b).component(str).end("loadAsync");
                        TBSOneCallback tBSOneCallback2 = tBSOneCallback;
                        if (tBSOneCallback2 != null) {
                            tBSOneCallback2.onCompleted(tBSOneComponent2);
                        }
                    }

                    @Override // com.tencent.tbs.one.TBSOneCallback
                    public final void onError(int i2, String str2) {
                        TBSOneCallback tBSOneCallback2 = tBSOneCallback;
                        if (tBSOneCallback2 != null) {
                            tBSOneCallback2.onError(i2, str2);
                        }
                    }

                    @Override // com.tencent.tbs.one.TBSOneCallback
                    public final void onProgressChanged(int i2, int i3) {
                        TBSOneCallback tBSOneCallback2 = tBSOneCallback;
                        if (tBSOneCallback2 != null) {
                            tBSOneCallback2.onProgressChanged(i2, i3);
                        }
                    }
                });
            }
        });
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void loadComponentAsync(String str, TBSOneCallback<TBSOneComponent> tBSOneCallback) {
        loadComponentAsync(str, null, tBSOneCallback);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final TBSOneComponent loadComponentSync(String str, long j2) {
        return loadComponentSync(str, null, j2);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final TBSOneComponent loadComponentSync(final String str, final Bundle bundle, long j2) throws TBSOneException {
        g.a("[%s] Loading component %s synchronously", this.f22017b, str);
        if (o.b()) {
            throw new RuntimeException("TBSOneManager.loadComponentSync must not be called on TBSOne thread.");
        }
        a();
        TBSOneTiming.category(this.f22017b).component(str).start("loadSync");
        final c cVar = new c();
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.4
            @Override // java.lang.Runnable
            public final void run() {
                d.this.f22018c.a(str, bundle, cVar);
            }
        });
        cVar.a(j2);
        int i2 = cVar.f21800b;
        if (i2 != 0) {
            throw new TBSOneException(i2, cVar.f21801c);
        }
        TBSOneTiming.category(this.f22017b).component(str).end("loadSync");
        return (TBSOneComponent) cVar.f21799a;
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void setAutoUpdateEnabled(boolean z2) {
        Object[] objArr = new Object[2];
        objArr[0] = this.f22017b;
        objArr[1] = z2 ? "Enabling" : "Disabling";
        g.a("[%s] %s auto update", objArr);
        this.f22018c.a(z2);
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void setDelegate(final TBSOneDelegate tBSOneDelegate) {
        g.a("[%s] Setting delegate %s", this.f22017b, tBSOneDelegate);
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.d.1
            @Override // java.lang.Runnable
            public final void run() {
                d.this.f22018c.f22217n = tBSOneDelegate;
            }
        });
    }

    @Override // com.tencent.tbs.one.TBSOneManager
    public final void setPolicy(TBSOneManager.Policy policy) {
        g.a("[%s] Setting policy %s", this.f22017b, policy);
        synchronized (this.f22016a) {
            if (this.f22019d) {
                Log.println(5, "TBSOne", Log.getStackTraceString(new Throwable("TBSOneManager.setPolicy should be called before doing any other operations.")));
            } else {
                this.f22018c.f22211h = policy;
            }
        }
    }
}
