package com.aliyun.svideo.common.baseAdapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/* loaded from: classes2.dex */
public class AlphaInAnimation implements BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0.0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(0.0f);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.animation.BaseAnimation
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", this.mFrom, 1.0f)};
    }

    public AlphaInAnimation(float f2) {
        this.mFrom = f2;
    }
}
