package com.beizi.fusion;

import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.d.d;

/* loaded from: classes2.dex */
public class BannerAd {

    /* renamed from: a, reason: collision with root package name */
    private d f4708a;

    public BannerAd(Context context, String str, BannerAdListener bannerAdListener, long j2) {
        this.f4708a = new d(context, str, bannerAdListener, j2);
    }

    public void destroy() {
        d dVar = this.f4708a;
        if (dVar != null) {
            dVar.b();
        }
    }

    public void loadAd(float f2, float f3, ViewGroup viewGroup) throws InterruptedException {
        d dVar = this.f4708a;
        if (dVar != null) {
            dVar.a(f2, f3, viewGroup);
        }
    }
}
