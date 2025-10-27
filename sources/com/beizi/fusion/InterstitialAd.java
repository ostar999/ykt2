package com.beizi.fusion;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.beizi.fusion.d.m;

/* loaded from: classes2.dex */
public class InterstitialAd {

    /* renamed from: a, reason: collision with root package name */
    private m f4716a;

    public InterstitialAd(Context context, String str, InterstitialAdListener interstitialAdListener, long j2) {
        this.f4716a = new m(context, str, interstitialAdListener, j2, 0);
    }

    public void destroy() {
        m mVar = this.f4716a;
        if (mVar != null) {
            mVar.C();
        }
    }

    public boolean isLoaded() {
        m mVar = this.f4716a;
        if (mVar != null) {
            return mVar.b();
        }
        return false;
    }

    public void loadAd() throws InterruptedException {
        m mVar = this.f4716a;
        if (mVar != null) {
            mVar.B();
        }
    }

    public void setAdVersion(int i2) {
        m mVar = this.f4716a;
        if (mVar != null) {
            mVar.d(i2);
        }
    }

    public void showAd(@NonNull Activity activity) {
        m mVar = this.f4716a;
        if (mVar != null) {
            mVar.a(activity);
        }
    }

    public InterstitialAd(Context context, String str, InterstitialAdListener interstitialAdListener, long j2, int i2) {
        this.f4716a = new m(context, str, interstitialAdListener, j2, i2);
    }
}
