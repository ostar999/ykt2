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
public final class NativeAd implements AdLifeControl {

    /* renamed from: a, reason: collision with root package name */
    private int f3641a;

    /* renamed from: b, reason: collision with root package name */
    private int f3642b;

    /* renamed from: c, reason: collision with root package name */
    private String f3643c;

    /* renamed from: d, reason: collision with root package name */
    private int f3644d;

    /* renamed from: e, reason: collision with root package name */
    private String f3645e;

    /* renamed from: f, reason: collision with root package name */
    private List<Pair<String, Integer>> f3646f;

    /* renamed from: g, reason: collision with root package name */
    private final com.beizi.ad.internal.nativead.b f3647g;

    /* renamed from: h, reason: collision with root package name */
    private View f3648h;

    @RequiresPermission("android.permission.INTERNET")
    public NativeAd(Context context, String str, int i2, NativeAdListener nativeAdListener) {
        com.beizi.ad.internal.nativead.b bVar = new com.beizi.ad.internal.nativead.b(context, str, i2);
        this.f3647g = bVar;
        bVar.a(nativeAdListener);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        com.beizi.ad.internal.nativead.b bVar = this.f3647g;
        if (bVar != null) {
            bVar.f4314a.a();
            this.f3647g.cancel(true);
        }
    }

    public String getAdId() {
        com.beizi.ad.internal.nativead.b bVar = this.f3647g;
        if (bVar == null) {
            return null;
        }
        return bVar.d();
    }

    public NativeAdListener getAdListener() {
        return this.f3647g.e();
    }

    public int getAdOptimizePercent() {
        return this.f3641a;
    }

    public int getAdOptimizeSize() {
        return this.f3642b;
    }

    public String getAdUnitId() {
        return this.f3647g.b();
    }

    public String getDirection() {
        return this.f3645e;
    }

    public List<Pair<String, Integer>> getOrderOptimizeList() {
        return this.f3646f;
    }

    public String getPrice() {
        com.beizi.ad.internal.nativead.b bVar = this.f3647g;
        if (bVar == null) {
            return null;
        }
        return bVar.c();
    }

    public String getScrollDistance() {
        return this.f3643c;
    }

    public int getScrollDistancePercent() {
        return this.f3644d;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getValidOptimizePercent() {
        /*
            r6 = this;
            com.beizi.ad.internal.nativead.b r0 = r6.f3647g
            r1 = -1
            if (r0 == 0) goto L39
            java.lang.String r0 = r0.h()
            if (r0 == 0) goto L39
            com.beizi.ad.internal.nativead.b r0 = r6.f3647g
            java.lang.String r0 = r0.h()
            java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> r2 = r6.f3646f
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
            int r3 = r6.f3641a
        L3e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.NativeAd.getValidOptimizePercent():int");
    }

    public void loadAd() {
        this.f3647g.a((a.C0055a) null);
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
        this.f3647g.a(z2);
    }

    public void optimizeClickArea(int i2, View view, View view2, String str) {
        int validOptimizePercent = getValidOptimizePercent();
        boolean zA = l.a(validOptimizePercent);
        i.c("BeiZisAd", "percent = " + validOptimizePercent + ",isPass = " + zA);
        if (zA) {
            this.f3648h = f.a(i2, view, view2, str);
        }
    }

    public void setAdOptimizePercent(int i2) {
        this.f3641a = i2;
    }

    public void setAdOptimizeSize(int i2) {
        this.f3642b = i2;
    }

    public void setAdUnitId(String str) {
        this.f3647g.a(str);
    }

    public void setDirection(String str) {
        this.f3645e = str;
    }

    public void setOrderOptimizeList(List<Pair<String, Integer>> list) {
        this.f3646f = list;
    }

    public void setScrollDistance(String str) {
        this.f3643c = str;
    }

    public void setScrollDistancePercent(int i2) {
        this.f3644d = i2;
    }

    public void setTouchAreaNormal() {
        View view = this.f3648h;
        if (view != null) {
            ViewUtil.removeChildFromParent(view);
        }
    }

    @Deprecated
    public void shouldLoadIcon(boolean z2) {
        this.f3647g.c(z2);
    }

    @Deprecated
    public void shouldLoadImage(boolean z2) {
        this.f3647g.b(z2);
    }
}
