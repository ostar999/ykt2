package com.easefun.polyv.livecommon.ui.widget.menudrawer.compat;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public final class ActionBarHelper {
    static final boolean DEBUG = false;
    private static final String TAG = "ActionBarHelper";
    private Activity mActivity;
    private Object mIndicatorInfo;
    private boolean mUsesCompat;

    public ActionBarHelper(Activity activity) throws NoSuchMethodException, SecurityException {
        this.mActivity = activity;
        try {
            activity.getClass().getMethod("getSupportActionBar", new Class[0]);
            this.mUsesCompat = true;
        } catch (NoSuchMethodException unused) {
        }
        this.mIndicatorInfo = getIndicatorInfo();
    }

    private Object getIndicatorInfo() {
        boolean z2 = this.mUsesCompat;
        return ActionBarHelperNative.getIndicatorInfo(this.mActivity);
    }

    public Drawable getThemeUpIndicator() {
        boolean z2 = this.mUsesCompat;
        return ActionBarHelperNative.getThemeUpIndicator(this.mIndicatorInfo, this.mActivity);
    }

    public void setActionBarDescription(int contentDesc) {
        boolean z2 = this.mUsesCompat;
        ActionBarHelperNative.setActionBarDescription(this.mIndicatorInfo, this.mActivity, contentDesc);
    }

    public void setActionBarUpIndicator(Drawable drawable, int contentDesc) {
        boolean z2 = this.mUsesCompat;
        ActionBarHelperNative.setActionBarUpIndicator(this.mIndicatorInfo, this.mActivity, drawable, contentDesc);
    }

    public void setDisplayShowHomeAsUpEnabled(boolean enabled) {
        boolean z2 = this.mUsesCompat;
        ActionBarHelperNative.setDisplayHomeAsUpEnabled(this.mActivity, enabled);
    }
}
