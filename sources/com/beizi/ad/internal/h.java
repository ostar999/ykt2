package com.beizi.ad.internal;

import android.graphics.Rect;
import android.view.View;
import com.beizi.ad.internal.nativead.NativeAdShownListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private View f4201a;

    /* renamed from: b, reason: collision with root package name */
    private NativeAdShownListener f4202b;

    /* renamed from: c, reason: collision with root package name */
    private ScheduledExecutorService f4203c;

    /* renamed from: d, reason: collision with root package name */
    private Runnable f4204d;

    /* renamed from: e, reason: collision with root package name */
    private DecimalFormatSymbols f4205e = new DecimalFormatSymbols(Locale.ENGLISH);

    /* renamed from: f, reason: collision with root package name */
    private Format f4206f = new DecimalFormat("0.00", this.f4205e);

    public h(View view, NativeAdShownListener nativeAdShownListener) {
        this.f4201a = view;
        this.f4202b = nativeAdShownListener;
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        int i2;
        View view = this.f4201a;
        if (view != null && view.getVisibility() == 0 && this.f4201a.getParent() != null) {
            Rect rect = new Rect();
            this.f4201a.getLocalVisibleRect(rect);
            try {
                if (rect.left != 0 || rect.top < 0 || rect.bottom > this.f4201a.getHeight() || Float.parseFloat(this.f4206f.format(Double.valueOf((((rect.bottom - rect.top) * rect.right) * 1.0d) / (this.f4201a.getWidth() * this.f4201a.getHeight())))) <= 0.5d) {
                    if (rect.left <= 0 || (i2 = rect.right) < 0 || i2 > this.f4201a.getWidth()) {
                        return false;
                    }
                    if (Float.parseFloat(this.f4206f.format(Double.valueOf((((rect.right - rect.left) * (rect.bottom - rect.top)) * 1.0d) / (this.f4201a.getWidth() * this.f4201a.getHeight())))) <= 0.5d) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static h a(View view, NativeAdShownListener nativeAdShownListener) {
        if (view == null) {
            return null;
        }
        return new h(view, nativeAdShownListener);
    }

    private void a() {
        this.f4204d = new Runnable() { // from class: com.beizi.ad.internal.h.1
            @Override // java.lang.Runnable
            public void run() {
                if (h.this.b()) {
                    if (h.this.f4202b != null) {
                        h.this.f4202b.onAdShown();
                    }
                    if (h.this.f4203c != null) {
                        h.this.f4203c.shutdownNow();
                    }
                    h.this.f4202b = null;
                    h.this.f4201a = null;
                    h.this.f4203c = null;
                }
            }
        };
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f4203c = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(new Runnable() { // from class: com.beizi.ad.internal.h.2
            @Override // java.lang.Runnable
            public void run() {
                if (h.this.f4201a != null) {
                    h.this.f4201a.post(h.this.f4204d);
                }
            }
        }, 0L, 250L, TimeUnit.MILLISECONDS);
    }
}
