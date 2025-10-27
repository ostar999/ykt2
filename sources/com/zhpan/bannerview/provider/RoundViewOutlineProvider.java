package com.zhpan.bannerview.provider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(21)
/* loaded from: classes8.dex */
public class RoundViewOutlineProvider extends ViewOutlineProvider {
    private final float mRadius;

    public RoundViewOutlineProvider(float f2) {
        this.mRadius = f2;
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(new Rect(0, 0, view.getWidth(), view.getHeight()), this.mRadius);
    }
}
