package com.umeng.analytics.pro;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.C;
import com.umeng.commonsdk.debug.UMRTLog;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class m implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a, reason: collision with root package name */
    private static m f22845a = new m();

    /* renamed from: b, reason: collision with root package name */
    private final int f22846b = 3000;

    /* renamed from: c, reason: collision with root package name */
    private boolean f22847c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f22848d = true;

    /* renamed from: e, reason: collision with root package name */
    private Handler f22849e = new Handler(Looper.getMainLooper());

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<n> f22850f = new ArrayList<>();

    /* renamed from: g, reason: collision with root package name */
    private a f22851g = new a();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!m.this.f22847c || !m.this.f22848d) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> still foreground.");
                return;
            }
            m.this.f22847c = false;
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> went background.");
            for (int i2 = 0; i2 < m.this.f22850f.size(); i2++) {
                ((n) m.this.f22850f.get(i2)).n();
            }
        }
    }

    private m() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.f22848d = true;
        a aVar = this.f22851g;
        if (aVar != null) {
            this.f22849e.removeCallbacks(aVar);
            this.f22849e.postDelayed(this.f22851g, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.f22848d = false;
        this.f22847c = true;
        a aVar = this.f22851g;
        if (aVar != null) {
            this.f22849e.removeCallbacks(aVar);
        }
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

    public synchronized void b(n nVar) {
        if (nVar != null) {
            for (int i2 = 0; i2 < this.f22850f.size(); i2++) {
                if (this.f22850f.get(i2) == nVar) {
                    this.f22850f.remove(i2);
                }
            }
        }
    }

    public static void a(Context context) {
        if (context instanceof Application) {
            ((Application) context).registerActivityLifecycleCallbacks(f22845a);
        }
    }

    public static m a() {
        return f22845a;
    }

    public synchronized void a(n nVar) {
        if (nVar != null) {
            this.f22850f.add(nVar);
        }
    }
}
