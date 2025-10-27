package com.beizi.fusion.g;

import android.app.Application;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static final Application f5193a;

    static {
        Application application;
        Application application2 = null;
        try {
            try {
                application = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke(null, new Object[0]);
                if (application == null) {
                    try {
                        throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        try {
                            application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
                        } catch (Exception unused) {
                            e.printStackTrace();
                        }
                        f5193a = application;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                application = null;
            } catch (Throwable th) {
                th = th;
                f5193a = application2;
                throw th;
            }
            f5193a = application;
        } catch (Throwable th2) {
            th = th2;
            application2 = application;
            f5193a = application2;
            throw th;
        }
    }

    public static Application a() {
        return f5193a;
    }
}
