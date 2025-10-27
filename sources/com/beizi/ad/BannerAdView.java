package com.beizi.ad;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.a.a.f;
import com.beizi.ad.a.a.i;
import com.beizi.ad.internal.animation.TransitionDirection;
import com.beizi.ad.internal.animation.TransitionType;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.utilities.ViewUtil;
import java.util.List;

/* loaded from: classes2.dex */
public final class BannerAdView extends b {

    /* renamed from: b, reason: collision with root package name */
    private int f3616b;

    /* renamed from: c, reason: collision with root package name */
    private int f3617c;

    /* renamed from: d, reason: collision with root package name */
    private String f3618d;

    /* renamed from: e, reason: collision with root package name */
    private int f3619e;

    /* renamed from: f, reason: collision with root package name */
    private String f3620f;

    /* renamed from: g, reason: collision with root package name */
    private List<Pair<String, Integer>> f3621g;

    /* renamed from: h, reason: collision with root package name */
    private View f3622h;

    public BannerAdView(Context context) {
        super(context, l.BANNER);
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void cancel() {
        super.cancel();
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ AdListener getAdListener() {
        return super.getAdListener();
    }

    public int getAdOptimizePercent() {
        return this.f3616b;
    }

    public int getAdOptimizeSize() {
        return this.f3617c;
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ a getAdSize() {
        return super.getAdSize();
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ String getAdUnitId() {
        return super.getAdUnitId();
    }

    public String getDirection() {
        return this.f3620f;
    }

    public List<Pair<String, Integer>> getOrderOptimizeList() {
        return this.f3621g;
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ String getPrice() {
        return super.getPrice();
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ boolean getResizeAdToFitContainer() {
        return super.getResizeAdToFitContainer();
    }

    public String getScrollDistance() {
        return this.f3618d;
    }

    public int getScrollDistancePercent() {
        return this.f3619e;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getValidOptimizePercent() {
        /*
            r6 = this;
            com.beizi.ad.internal.view.BannerAdViewImpl r0 = r6.f3726a
            r1 = -1
            if (r0 == 0) goto L39
            java.lang.String r0 = r0.getLandingPageUrl()
            if (r0 == 0) goto L39
            com.beizi.ad.internal.view.BannerAdViewImpl r0 = r6.f3726a
            java.lang.String r0 = r0.getLandingPageUrl()
            java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> r2 = r6.f3621g
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
            int r3 = r6.f3616b
        L3e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.BannerAdView.getValidOptimizePercent():int");
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void isAutoRefresh() {
        super.isAutoRefresh();
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ boolean isLoading() {
        return super.isLoading();
    }

    @Override // com.beizi.ad.b
    @RequiresPermission("android.permission.INTERNET")
    public /* bridge */ /* synthetic */ void loadAd(AdRequest adRequest) {
        super.loadAd(adRequest);
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onCreateLifeCycle() {
        super.onCreateLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onDestoryLifeCycle() {
        super.onDestoryLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onPauseLifeCycle() {
        super.onPauseLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onRestartLifeCycle() {
        super.onRestartLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onResumeLifeCycle() {
        super.onResumeLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onStartLifeCycle() {
        super.onStartLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void onStopLifeCycle() {
        super.onStopLifeCycle();
    }

    @Override // com.beizi.ad.b, com.beizi.ad.AdLifeControl
    public /* bridge */ /* synthetic */ void openAdInNativeBrowser(boolean z2) {
        super.openAdInNativeBrowser(z2);
    }

    public void optimizeClickArea(int i2, View view, View view2, String str) {
        int validOptimizePercent = getValidOptimizePercent();
        boolean zA = com.beizi.ad.a.a.l.a(validOptimizePercent);
        i.c("BeiZisAd", "percent = " + validOptimizePercent + ",isPass = " + zA);
        if (zA) {
            this.f3622h = f.a(i2, view, view2, str);
        }
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void resume() {
        super.resume();
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setAdListener(AdListener adListener) {
        super.setAdListener(adListener);
    }

    public void setAdOptimizePercent(int i2) {
        this.f3616b = i2;
    }

    public void setAdOptimizeSize(int i2) {
        this.f3617c = i2;
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setAdUnitId(String str) {
        super.setAdUnitId(str);
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setAutoRefresh(boolean z2) {
        super.setAutoRefresh(z2);
    }

    public void setDirection(String str) {
        this.f3620f = str;
    }

    public void setOrderOptimizeList(List<Pair<String, Integer>> list) {
        this.f3621g = list;
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setResizeAdToFitContainer(boolean z2) {
        super.setResizeAdToFitContainer(z2);
    }

    public void setScrollDistance(String str) {
        this.f3618d = str;
    }

    public void setScrollDistancePercent(int i2) {
        this.f3619e = i2;
    }

    public void setTouchAreaNormal() {
        View view = this.f3622h;
        if (view != null) {
            ViewUtil.removeChildFromParent(view);
        }
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setTransitionDerection(TransitionDirection transitionDirection) {
        super.setTransitionDerection(transitionDirection);
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setTransitionDuration(int i2) {
        super.setTransitionDuration(i2);
    }

    @Override // com.beizi.ad.b
    public /* bridge */ /* synthetic */ void setTransitionType(TransitionType transitionType) {
        super.setTransitionType(transitionType);
    }

    public BannerAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, l.BANNER);
    }

    public BannerAdView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2, l.BANNER);
    }
}
