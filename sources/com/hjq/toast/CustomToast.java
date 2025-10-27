package com.hjq.toast;

import android.view.View;
import android.widget.TextView;
import com.hjq.toast.config.IToast;

/* loaded from: classes4.dex */
public abstract class CustomToast implements IToast {
    private int mDuration;
    private int mGravity;
    private float mHorizontalMargin;
    private TextView mMessageView;
    private float mVerticalMargin;
    private View mView;
    private int mXOffset;
    private int mYOffset;
    private int mAnimations = 16973828;
    private int mShortDuration = 2000;
    private int mLongDuration = 3500;

    @Override // com.hjq.toast.config.IToast
    public /* synthetic */ TextView findMessageView(View view) {
        return a1.a.a(this, view);
    }

    public int getAnimationsId() {
        return this.mAnimations;
    }

    @Override // com.hjq.toast.config.IToast
    public int getDuration() {
        return this.mDuration;
    }

    @Override // com.hjq.toast.config.IToast
    public int getGravity() {
        return this.mGravity;
    }

    @Override // com.hjq.toast.config.IToast
    public float getHorizontalMargin() {
        return this.mHorizontalMargin;
    }

    public int getLongDuration() {
        return this.mLongDuration;
    }

    public int getShortDuration() {
        return this.mShortDuration;
    }

    @Override // com.hjq.toast.config.IToast
    public float getVerticalMargin() {
        return this.mVerticalMargin;
    }

    @Override // com.hjq.toast.config.IToast
    public View getView() {
        return this.mView;
    }

    @Override // com.hjq.toast.config.IToast
    public int getXOffset() {
        return this.mXOffset;
    }

    @Override // com.hjq.toast.config.IToast
    public int getYOffset() {
        return this.mYOffset;
    }

    public void setAnimationsId(int i2) {
        this.mAnimations = i2;
    }

    @Override // com.hjq.toast.config.IToast
    public void setDuration(int i2) {
        this.mDuration = i2;
    }

    @Override // com.hjq.toast.config.IToast
    public void setGravity(int i2, int i3, int i4) {
        this.mGravity = i2;
        this.mXOffset = i3;
        this.mYOffset = i4;
    }

    public void setLongDuration(int i2) {
        this.mLongDuration = i2;
    }

    @Override // com.hjq.toast.config.IToast
    public void setMargin(float f2, float f3) {
        this.mHorizontalMargin = f2;
        this.mVerticalMargin = f3;
    }

    public void setShortDuration(int i2) {
        this.mShortDuration = i2;
    }

    @Override // com.hjq.toast.config.IToast
    public void setText(int i2) {
        View view = this.mView;
        if (view == null) {
            return;
        }
        setText(view.getResources().getString(i2));
    }

    @Override // com.hjq.toast.config.IToast
    public void setView(View view) {
        this.mView = view;
        if (view == null) {
            this.mMessageView = null;
        } else {
            this.mMessageView = findMessageView(view);
        }
    }

    @Override // com.hjq.toast.config.IToast
    public void setText(CharSequence charSequence) {
        TextView textView = this.mMessageView;
        if (textView == null) {
            return;
        }
        textView.setText(charSequence);
    }
}
