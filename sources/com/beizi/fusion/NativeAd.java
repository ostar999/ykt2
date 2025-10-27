package com.beizi.fusion;

import android.content.Context;
import com.beizi.fusion.d.p;

/* loaded from: classes2.dex */
public class NativeAd {

    /* renamed from: a, reason: collision with root package name */
    private p f4717a;

    public NativeAd(Context context, String str, NativeAdListener nativeAdListener, long j2, int i2) {
        this.f4717a = new p(context, str, nativeAdListener, j2, i2);
    }

    public void destroy() {
        p pVar = this.f4717a;
        if (pVar != null) {
            pVar.B();
        }
    }

    public int getECPM() {
        p pVar = this.f4717a;
        if (pVar != null) {
            return pVar.b();
        }
        return -1;
    }

    public void loadAd(float f2, float f3) throws InterruptedException {
        p pVar = this.f4717a;
        if (pVar != null) {
            pVar.a(f2, f3);
        }
    }

    public void resume() {
        p pVar = this.f4717a;
        if (pVar != null) {
            pVar.C();
        }
    }
}
