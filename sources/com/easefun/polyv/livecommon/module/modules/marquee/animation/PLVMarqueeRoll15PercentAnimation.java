package com.easefun.polyv.livecommon.module.modules.marquee.animation;

/* loaded from: classes3.dex */
public class PLVMarqueeRoll15PercentAnimation extends PLVMarqueeRollAnimation {
    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation
    public void setActiveRect() {
        if (this.layoutParams == null) {
            return;
        }
        boolean z2 = Math.random() < 0.5d;
        int i2 = this.screenHeight;
        float f2 = i2 * 0.15f;
        if (z2) {
            if (this.viewHeight < f2) {
                this.layoutParams.topMargin = (int) (Math.random() * ((int) (f2 - r0)));
                return;
            } else {
                this.layoutParams.topMargin = 0;
                return;
            }
        }
        int i3 = this.viewHeight;
        if (i3 >= f2) {
            this.layoutParams.topMargin = i2 - Math.min(i2, i3);
        } else {
            this.layoutParams.topMargin = (int) (((int) (i2 - f2)) + (Math.random() * ((int) (f2 - i3))));
        }
    }
}
