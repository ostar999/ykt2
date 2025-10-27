package com.hjq.toast.config;

import android.content.Context;
import android.view.View;

/* loaded from: classes4.dex */
public interface IToastStyle<V extends View> {
    V createView(Context context);

    int getGravity();

    float getHorizontalMargin();

    float getVerticalMargin();

    int getXOffset();

    int getYOffset();
}
