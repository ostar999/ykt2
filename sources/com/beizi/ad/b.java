package com.beizi.ad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.internal.animation.TransitionDirection;
import com.beizi.ad.internal.animation.TransitionType;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.view.BannerAdViewImpl;

/* loaded from: classes2.dex */
class b extends FrameLayout implements AdLifeControl {

    /* renamed from: a, reason: collision with root package name */
    protected final BannerAdViewImpl f3726a;

    public b(Context context, l lVar) {
        super(context);
        this.f3726a = new BannerAdViewImpl(context);
        a();
    }

    private void a() {
        setBackgroundColor(0);
        setPadding(0, 0, 0, 0);
        addView(this.f3726a);
    }

    public void cancel() {
        BannerAdViewImpl bannerAdViewImpl = this.f3726a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.cancel();
        }
    }

    public AdListener getAdListener() {
        return this.f3726a.getAdListener();
    }

    public a getAdSize() {
        return this.f3726a.getAdSize();
    }

    public String getAdUnitId() {
        return this.f3726a.getAdUnitId();
    }

    public String getPrice() {
        return this.f3726a.getPrice();
    }

    public boolean getResizeAdToFitContainer() {
        return this.f3726a.getResizeAdToFitContainer();
    }

    public void isAutoRefresh() {
        this.f3726a.isAutoRefresh();
    }

    public boolean isLoading() {
        return this.f3726a.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public void loadAd(AdRequest adRequest) {
        this.f3726a.loadAd(adRequest.impl());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onCreateLifeCycle() {
    }

    public void onDestoryLifeCycle() {
        BannerAdViewImpl bannerAdViewImpl = this.f3726a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.onDestoryLifeCycle();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BannerAdViewImpl bannerAdViewImpl = this.f3726a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.activityOnDestroy();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        a adSize;
        int iA;
        if (getResizeAdToFitContainer()) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824);
            super.onMeasure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(iMakeMeasureSpec) / 6, 1073741824));
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        View.MeasureSpec.getSize(i3);
        int measuredWidth = 0;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            try {
                adSize = getAdSize();
            } catch (NullPointerException e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Unable to retrieve ad size.", e2);
                adSize = null;
            }
            if (adSize != null) {
                Context context = getContext();
                int iB = adSize.b(context);
                iA = adSize.a(context);
                measuredWidth = iB;
            } else {
                iA = 0;
            }
        } else {
            measureChild(childAt, i2, i3);
            measuredWidth = childAt.getMeasuredWidth();
            iA = childAt.getMeasuredHeight();
        }
        int iMax = Math.max(measuredWidth, getSuggestedMinimumWidth());
        int iMax2 = Math.max(iA, getSuggestedMinimumHeight());
        if (mode == 1073741824) {
            iMax = View.MeasureSpec.getSize(i2);
        }
        if (mode2 == 1073741824) {
            iMax2 = View.MeasureSpec.getSize(i3);
        }
        setMeasuredDimension(View.resolveSize(iMax, i2), View.resolveSize(iMax2, i3));
    }

    public void onPauseLifeCycle() {
    }

    public void onRestartLifeCycle() {
    }

    public void onResumeLifeCycle() {
    }

    public void onStartLifeCycle() {
    }

    public void onStopLifeCycle() {
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i2) {
        if (i2 == 8) {
            this.f3726a.activityOnPause();
        } else {
            this.f3726a.activityOnResume();
        }
    }

    public void openAdInNativeBrowser(boolean z2) {
        this.f3726a.openAdInNativeBrowser(z2);
    }

    public void resume() {
        this.f3726a.activityOnResume();
    }

    public void setAdListener(AdListener adListener) {
        this.f3726a.setAdListener(adListener);
    }

    public void setAdSize(a aVar) {
        this.f3726a.setAdSize(aVar.b(), aVar.a());
    }

    public void setAdUnitId(String str) {
        this.f3726a.setAdUnitId(str);
    }

    public void setAutoRefresh(boolean z2) {
        this.f3726a.setAutoRefresh(z2);
    }

    public void setResizeAdToFitContainer(boolean z2) {
        this.f3726a.setResizeAdToFitContainer(z2);
        if (getResizeAdToFitContainer()) {
            this.f3726a.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        }
    }

    public void setTransitionDerection(TransitionDirection transitionDirection) {
        this.f3726a.setTransitionDirection(transitionDirection);
    }

    public void setTransitionDuration(int i2) {
        this.f3726a.setTransitionDuration(i2);
    }

    public void setTransitionType(TransitionType transitionType) {
        this.f3726a.setTransitionType(transitionType);
    }

    public b(Context context, AttributeSet attributeSet, l lVar) {
        super(context, attributeSet);
        this.f3726a = new BannerAdViewImpl(context, attributeSet);
        a();
    }

    public b(Context context, AttributeSet attributeSet, int i2, l lVar) {
        super(context, attributeSet, i2);
        this.f3726a = new BannerAdViewImpl(context, attributeSet, i2);
        a();
    }
}
