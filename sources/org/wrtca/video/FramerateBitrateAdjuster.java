package org.wrtca.video;

/* loaded from: classes9.dex */
public class FramerateBitrateAdjuster extends BaseBitrateAdjuster {
    private static final int INITIAL_FPS = 30;

    @Override // org.wrtca.video.BaseBitrateAdjuster, org.wrtca.video.BitrateAdjuster
    public void setTargets(int i2, int i3) {
        if (this.targetFps == 0) {
            i3 = 30;
        }
        super.setTargets(i2, i3);
        this.targetBitrateBps *= 30 / this.targetFps;
    }
}
