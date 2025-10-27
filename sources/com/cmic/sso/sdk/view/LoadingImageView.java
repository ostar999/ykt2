package com.cmic.sso.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class LoadingImageView extends ImageView {

    /* renamed from: a, reason: collision with root package name */
    private Animation f6422a;

    /* renamed from: b, reason: collision with root package name */
    private LinearInterpolator f6423b;

    public LoadingImageView(Context context) {
        super(context);
        this.f6422a = null;
        this.f6423b = null;
        a();
    }

    public LoadingImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f6422a = null;
        this.f6423b = null;
        a();
    }

    public LoadingImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f6422a = null;
        this.f6423b = null;
        a();
    }

    public void a() {
        this.f6422a = AnimationUtils.loadAnimation(getContext(), g.c(getContext(), "umcsdk_anim_loading"));
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        this.f6423b = linearInterpolator;
        this.f6422a.setInterpolator(linearInterpolator);
    }
}
