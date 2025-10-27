package com.beizi.ad;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.a.a.f;
import com.beizi.ad.a.a.i;
import com.beizi.ad.a.a.l;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.ViewUtil;
import java.util.List;

/* loaded from: classes2.dex */
public final class UnifiedCustomAd implements AdLifeControl {

    /* renamed from: a, reason: collision with root package name */
    private int f3658a;

    /* renamed from: b, reason: collision with root package name */
    private int f3659b;

    /* renamed from: c, reason: collision with root package name */
    private final com.beizi.ad.internal.nativead.b f3660c;

    /* renamed from: d, reason: collision with root package name */
    private List<Pair<String, Integer>> f3661d;

    /* renamed from: e, reason: collision with root package name */
    private View f3662e;

    @RequiresPermission("android.permission.INTERNET")
    public UnifiedCustomAd(Context context, String str, NativeAdListener nativeAdListener) {
        com.beizi.ad.internal.nativead.b bVar = new com.beizi.ad.internal.nativead.b(context, str, 1);
        this.f3660c = bVar;
        bVar.a(nativeAdListener);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        com.beizi.ad.internal.nativead.b bVar = this.f3660c;
        if (bVar != null) {
            bVar.f4314a.a();
            this.f3660c.cancel(true);
        }
    }

    public NativeAdListener getAdListener() {
        return this.f3660c.e();
    }

    public int getAdOptimizePercent() {
        return this.f3658a;
    }

    public int getAdOptimizeSize() {
        return this.f3659b;
    }

    public String getAdUnitId() {
        return this.f3660c.b();
    }

    public List<Pair<String, Integer>> getOrderOptimizeList() {
        return this.f3661d;
    }

    public String getPrice() {
        com.beizi.ad.internal.nativead.b bVar = this.f3660c;
        if (bVar == null) {
            return null;
        }
        return bVar.c();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getValidOptimizePercent() {
        /*
            r6 = this;
            com.beizi.ad.internal.nativead.b r0 = r6.f3660c
            r1 = -1
            if (r0 == 0) goto L39
            java.lang.String r0 = r0.h()
            if (r0 == 0) goto L39
            com.beizi.ad.internal.nativead.b r0 = r6.f3660c
            java.lang.String r0 = r0.h()
            java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> r2 = r6.f3661d
            if (r2 == 0) goto L39
            java.util.Iterator r2 = r2.iterator()
            r3 = r1
        L1a:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L3a
            java.lang.Object r4 = r2.next()
            android.util.Pair r4 = (android.util.Pair) r4
            java.lang.Object r5 = r4.first
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5
            boolean r5 = r0.contains(r5)
            if (r5 == 0) goto L1a
            java.lang.Object r3 = r4.second
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            goto L1a
        L39:
            r3 = r1
        L3a:
            if (r3 != r1) goto L3e
            int r3 = r6.f3658a
        L3e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.UnifiedCustomAd.getValidOptimizePercent():int");
    }

    public void loadAd() {
        this.f3660c.a((a.C0055a) null);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onCreateLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onDestoryLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onPauseLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onRestartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onResumeLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStopLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void openAdInNativeBrowser(boolean z2) {
        this.f3660c.a(z2);
    }

    public void optimizeClickArea(int i2, View view, View view2, String str) {
        int validOptimizePercent = getValidOptimizePercent();
        boolean zA = l.a(validOptimizePercent);
        i.c("BeiZisAd", "percent = " + validOptimizePercent + ",isPass = " + zA);
        if (zA) {
            this.f3662e = f.a(i2, view, view2, str);
        }
    }

    public void setAdOptimizePercent(int i2) {
        this.f3658a = i2;
    }

    public void setAdOptimizeSize(int i2) {
        this.f3659b = i2;
    }

    public void setAdUnitId(String str) {
        this.f3660c.a(str);
    }

    public void setOrderOptimizeList(List<Pair<String, Integer>> list) {
        this.f3661d = list;
    }

    public void setTouchAreaNormal() {
        View view = this.f3662e;
        if (view != null) {
            ViewUtil.removeChildFromParent(view);
        }
    }
}
