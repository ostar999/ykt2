package com.beizi.ad.internal;

import android.graphics.Rect;
import android.view.View;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class r {

    /* renamed from: b, reason: collision with root package name */
    private View f4400b;

    /* renamed from: d, reason: collision with root package name */
    private Runnable f4402d;

    /* renamed from: e, reason: collision with root package name */
    private ScheduledExecutorService f4403e;

    /* renamed from: a, reason: collision with root package name */
    private boolean f4399a = false;

    /* renamed from: c, reason: collision with root package name */
    private ArrayList<a> f4401c = new ArrayList<>();

    public interface a {
        void a(boolean z2);
    }

    private r(View view) {
        this.f4400b = view;
        b();
    }

    public void d() {
        ScheduledExecutorService scheduledExecutorService = this.f4403e;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
        View view = this.f4400b;
        if (view != null) {
            view.removeCallbacks(this.f4402d);
            this.f4400b = null;
        }
        this.f4401c = null;
    }

    public static r a(View view) {
        if (view != null) {
            return new r(view);
        }
        HaoboLog.d(HaoboLog.nativeLogTag, "Unable to check visibility");
        return null;
    }

    public boolean b(a aVar) {
        return this.f4401c.remove(aVar);
    }

    public boolean c() {
        View view = this.f4400b;
        if (view == null || view.getVisibility() != 0 || this.f4400b.getParent() == null) {
            return false;
        }
        Rect rect = new Rect();
        if (!this.f4400b.getGlobalVisibleRect(rect)) {
            return false;
        }
        int iHeight = rect.height() * rect.width();
        int height = this.f4400b.getHeight() * this.f4400b.getWidth();
        return height > 0 && iHeight * 100 >= height * 50;
    }

    public void b() {
        if (this.f4399a) {
            return;
        }
        this.f4399a = true;
        this.f4402d = new Runnable() { // from class: com.beizi.ad.internal.r.1
            @Override // java.lang.Runnable
            public void run() {
                if (r.this.f4401c != null) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = r.this.f4401c.iterator();
                    while (it.hasNext()) {
                        arrayList.add((a) it.next());
                    }
                    if (r.this.c()) {
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            ((a) it2.next()).a(true);
                        }
                    } else {
                        Iterator it3 = arrayList.iterator();
                        while (it3.hasNext()) {
                            ((a) it3.next()).a(false);
                        }
                    }
                }
            }
        };
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f4403e = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(new Runnable() { // from class: com.beizi.ad.internal.r.2
            @Override // java.lang.Runnable
            public void run() {
                r.this.f4400b.post(r.this.f4402d);
            }
        }, 0L, 250L, TimeUnit.MILLISECONDS);
    }

    public View a() {
        return this.f4400b;
    }

    public void a(a aVar) {
        if (aVar != null) {
            this.f4401c.add(aVar);
        }
    }
}
