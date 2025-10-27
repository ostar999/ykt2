package org.wrtca.video;

/* loaded from: classes9.dex */
public class BaseBitrateAdjuster implements BitrateAdjuster {
    public int targetBitrateBps = 0;
    public int targetFps = 0;

    @Override // org.wrtca.video.BitrateAdjuster
    public int getAdjustedBitrateBps() {
        return this.targetBitrateBps;
    }

    @Override // org.wrtca.video.BitrateAdjuster
    public int getAdjustedFramerate() {
        return this.targetFps;
    }

    @Override // org.wrtca.video.BitrateAdjuster
    public void reportEncodedFrame(int i2) {
    }

    @Override // org.wrtca.video.BitrateAdjuster
    public void setTargets(int i2, int i3) {
        this.targetBitrateBps = i2;
        this.targetFps = i3;
    }
}
