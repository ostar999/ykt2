package com.lxj.xpopup.animator;

import android.animation.FloatEvaluator;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class BlurAnimator extends PopupAnimator {
    public Bitmap decorBitmap;
    private FloatEvaluator evaluate;
    public boolean hasShadowBg;
    public int shadowColor;

    public BlurAnimator(View view, int i2) {
        super(view, 0);
        this.evaluate = new FloatEvaluator();
        this.hasShadowBg = false;
        this.shadowColor = i2;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.targetView.getResources(), XPopupUtils.renderScriptBlur(this.targetView.getContext(), this.decorBitmap, 25.0f, true));
        if (this.hasShadowBg) {
            bitmapDrawable.setColorFilter(this.shadowColor, PorterDuff.Mode.SRC_OVER);
        }
        this.targetView.setBackground(bitmapDrawable);
    }

    public BlurAnimator() {
        this.evaluate = new FloatEvaluator();
        this.hasShadowBg = false;
    }
}
