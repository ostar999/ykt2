package com.beizi.fusion;

import android.content.Context;
import com.beizi.fusion.d.i;

/* loaded from: classes2.dex */
public class DrawAd {

    /* renamed from: a, reason: collision with root package name */
    private i f4714a;

    public DrawAd(Context context, String str, DrawAdListener drawAdListener, long j2) {
        this.f4714a = new i(context, str, drawAdListener, j2);
    }

    public void destroy() {
        i iVar = this.f4714a;
        if (iVar != null) {
            iVar.B();
        }
    }

    public void loadAd() throws InterruptedException {
        i iVar = this.f4714a;
        if (iVar != null) {
            iVar.b();
        }
    }
}
