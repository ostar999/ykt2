package com.lxj.xpopup.animator;

import android.view.View;

/* loaded from: classes4.dex */
public class EmptyAnimator extends PopupAnimator {
    public EmptyAnimator(View view, int i2) {
        super(view, i2);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        observerAnimator(this.targetView.animate().alpha(0.0f).setDuration(this.animationDuration).withLayer()).start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.animate().alpha(1.0f).setDuration(this.animationDuration).withLayer().start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setAlpha(0.0f);
    }
}
