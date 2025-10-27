package com.hjq.toast.style;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hjq.toast.config.IToastStyle;

/* loaded from: classes4.dex */
public class CustomToastStyle implements IToastStyle<View> {
    private final int mGravity;
    private final float mHorizontalMargin;
    private final int mLayoutId;
    private final float mVerticalMargin;
    private final int mXOffset;
    private final int mYOffset;

    public CustomToastStyle(int i2) {
        this(i2, 17);
    }

    @Override // com.hjq.toast.config.IToastStyle
    public View createView(Context context) {
        return LayoutInflater.from(context).inflate(this.mLayoutId, (ViewGroup) null);
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

    public CustomToastStyle(int i2, int i3) {
        this(i2, i3, 0, 0);
    }

    public CustomToastStyle(int i2, int i3, int i4, int i5) {
        this(i2, i3, i4, i5, 0.0f, 0.0f);
    }

    public CustomToastStyle(int i2, int i3, int i4, int i5, float f2, float f3) {
        this.mLayoutId = i2;
        this.mGravity = i3;
        this.mXOffset = i4;
        this.mYOffset = i5;
        this.mHorizontalMargin = f2;
        this.mVerticalMargin = f3;
    }
}
