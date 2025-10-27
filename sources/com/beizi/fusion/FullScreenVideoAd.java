package com.beizi.fusion;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.beizi.fusion.d.j;

/* loaded from: classes2.dex */
public class FullScreenVideoAd {

    /* renamed from: a, reason: collision with root package name */
    private j f4715a;

    public FullScreenVideoAd(Context context, String str, FullScreenVideoAdListener fullScreenVideoAdListener, long j2) {
        this.f4715a = new j(context, str, fullScreenVideoAdListener, j2);
    }

    public void destroy() {
        j jVar = this.f4715a;
        if (jVar != null) {
            jVar.C();
        }
    }

    public boolean isLoaded() {
        j jVar = this.f4715a;
        if (jVar != null) {
            return jVar.b();
        }
        return false;
    }

    public void loadAd() throws InterruptedException {
        j jVar = this.f4715a;
        if (jVar != null) {
            jVar.B();
        }
    }

    public void setAdVersion(int i2) {
        j jVar = this.f4715a;
        if (jVar != null) {
            jVar.d(i2);
        }
    }

    public void showAd(@NonNull Activity activity) {
        j jVar = this.f4715a;
        if (jVar != null) {
            jVar.a(activity);
        }
    }
}
