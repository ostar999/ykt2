package com.beizi.fusion;

import android.content.Context;
import com.beizi.fusion.d.s;

/* loaded from: classes2.dex */
public class NativeUnifiedAd {

    /* renamed from: a, reason: collision with root package name */
    private s f4720a;

    public NativeUnifiedAd(Context context, String str, NativeUnifiedAdListener nativeUnifiedAdListener, long j2, int i2) {
        this.f4720a = new s(context, str, nativeUnifiedAdListener, j2, i2);
    }

    public void destroy() {
        s sVar = this.f4720a;
        if (sVar != null) {
            sVar.C();
        }
    }

    public boolean isLoaded() {
        s sVar = this.f4720a;
        if (sVar != null) {
            return sVar.B();
        }
        return false;
    }

    public void loadAd() throws InterruptedException {
        s sVar = this.f4720a;
        if (sVar != null) {
            sVar.b();
        }
    }

    public void resume() {
        s sVar = this.f4720a;
        if (sVar != null) {
            sVar.D();
        }
    }
}
