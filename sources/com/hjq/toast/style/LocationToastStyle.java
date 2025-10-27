package com.hjq.toast.style;

import android.content.Context;
import android.view.View;
import com.hjq.toast.config.IToastStyle;

/* loaded from: classes4.dex */
public class LocationToastStyle implements IToastStyle<View> {
    private final int mGravity;
    private final float mHorizontalMargin;
    private final IToastStyle<?> mStyle;
    private final float mVerticalMargin;
    private final int mXOffset;
    private final int mYOffset;

    public LocationToastStyle(IToastStyle<?> iToastStyle, int i2) {
        this(iToastStyle, i2, 0, 0, 0.0f, 0.0f);
    }

    @Override // com.hjq.toast.config.IToastStyle
    public View createView(Context context) {
        return this.mStyle.createView(context);
    }

    @Override // com.hjq.toast.config.IToastStyle
    public int getGravity() {
        return this.mGravity;
    }

    @Override // com.hjq.toast.config.IToastStyle
    public float getHorizontalMargin() {
        return this.mHorizontalMargin;
    }

    @Override // com.hjq.toast.config.IToastStyle
    public float getVerticalMargin() {
        return this.mVerticalMargin;
    }

    @Override // com.hjq.toast.config.IToastStyle
    public int getXOffset() {
        return this.mXOffset;
    }

    @Override // com.hjq.toast.config.IToastStyle
    public int getYOffset() {
        return this.mYOffset;
    }

    public LocationToastStyle(IToastStyle<?> iToastStyle, int i2, int i3, int i4, float f2, float f3) {
        this.mStyle = iToastStyle;
        this.mGravity = i2;
        this.mXOffset = i3;
        this.mYOffset = i4;
        this.mHorizontalMargin = f2;
        this.mVerticalMargin = f3;
    }
}
