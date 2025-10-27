package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public abstract class BaseEntry {
    private Object mData;
    private Drawable mIcon;

    /* renamed from: y, reason: collision with root package name */
    private float f6559y;

    public BaseEntry() {
        this.f6559y = 0.0f;
        this.mData = null;
        this.mIcon = null;
    }

    public Object getData() {
        return this.mData;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public float getY() {
        return this.f6559y;
    }

    public void setData(Object obj) {
        this.mData = obj;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
    }

    public void setY(float f2) {
        this.f6559y = f2;
    }

    public BaseEntry(float f2) {
        this.mData = null;
        this.mIcon = null;
        this.f6559y = f2;
    }

    public BaseEntry(float f2, Object obj) {
        this(f2);
        this.mData = obj;
    }

    public BaseEntry(float f2, Drawable drawable) {
        this(f2);
        this.mIcon = drawable;
    }

    public BaseEntry(float f2, Drawable drawable, Object obj) {
        this(f2);
        this.mIcon = drawable;
        this.mData = obj;
    }
}
