package com.hjq.toast.config;

import android.view.View;
import android.widget.TextView;

/* loaded from: classes4.dex */
public interface IToast {
    void cancel();

    TextView findMessageView(View view);

    int getDuration();

    int getGravity();

    float getHorizontalMargin();

    float getVerticalMargin();

    View getView();

    int getXOffset();

    int getYOffset();

    void setDuration(int i2);

    void setGravity(int i2, int i3, int i4);

    void setMargin(float f2, float f3);

    void setText(int i2);

    void setText(CharSequence charSequence);

    void setView(View view);

    void show();
}
