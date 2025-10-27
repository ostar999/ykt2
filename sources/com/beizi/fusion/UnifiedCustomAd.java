package com.beizi.fusion;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.beizi.fusion.d.w;

/* loaded from: classes2.dex */
public class UnifiedCustomAd {

    /* renamed from: a, reason: collision with root package name */
    private w f4728a;

    public UnifiedCustomAd(Context context, String str, NativeAdListener nativeAdListener, long j2, int i2) {
        this.f4728a = new w(context, str, nativeAdListener, j2, i2);
    }

    public void destroy() {
        w wVar = this.f4728a;
        if (wVar != null) {
            wVar.C();
        }
    }

    public boolean isLoaded() {
        w wVar = this.f4728a;
        if (wVar != null) {
            return wVar.B();
        }
        return false;
    }

    public void loadAd() throws InterruptedException {
        w wVar = this.f4728a;
        if (wVar != null) {
            wVar.b();
        }
    }

    public void resume() {
        w wVar = this.f4728a;
        if (wVar != null) {
            wVar.D();
        }
    }

    public void showAd(@NonNull Activity activity) {
        w wVar = this.f4728a;
        if (wVar != null) {
            wVar.a(activity);
        }
    }
}
