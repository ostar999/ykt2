package org.wrtca.video;

/* loaded from: classes9.dex */
public class DynamicBitrateAdjuster extends BaseBitrateAdjuster {
    private static final double BITRATE_ADJUSTMENT_MAX_SCALE = 4.0d;
    private static final double BITRATE_ADJUSTMENT_SEC = 3.0d;
    private static final int BITRATE_ADJUSTMENT_STEPS = 20;
    private static final double BITS_PER_BYTE = 8.0d;
    private double deviationBytes = 0.0d;
    private double timeSinceLastAdjustmentMs = 0.0d;
    private int bitrateAdjustmentScaleExp = 0;

    @Override // org.wrtca.video.BaseBitrateAdjuster, org.wrtca.video.BitrateAdjuster
    public int getAdjustedBitrateBps() {
        return (int) (this.targetBitrateBps * Math.pow(BITRATE_ADJUSTMENT_MAX_SCALE, this.bitrateAdjustmentScaleExp / 20.0d));
    }

    @Override // org.wrtca.video.BaseBitrateAdjuster, org.wrtca.video.BitrateAdjuster
    public void reportEncodedFrame(int i2) {
        int i3 = this.targetFps;
        if (i3 == 0) {
            return;
        }
        double d3 = this.targetBitrateBps / BITS_PER_BYTE;
        double d4 = i3;
        double d5 = this.deviationBytes + (i2 - (d3 / d4));
        this.deviationBytes = d5;
        this.timeSinceLastAdjustmentMs += 1000.0d / d4;
        double d6 = BITRATE_ADJUSTMENT_SEC * d3;
        double dMin = Math.min(d5, d6);
        this.deviationBytes = dMin;
        double dMax = Math.max(dMin, -d6);
        this.deviationBytes = dMax;
        if (this.timeSinceLastAdjustmentMs <= 3000.0d) {
            return;
        }
        if (dMax > d3) {
            int i4 = this.bitrateAdjustmentScaleExp - ((int) ((dMax / d3) + 0.5d));
            this.bitrateAdjustmentScaleExp = i4;
            this.bitrateAdjustmentScaleExp = Math.max(i4, -20);
            this.deviationBytes = d3;
        } else {
            double d7 = -d3;
            if (dMax < d7) {
                int i5 = this.bitrateAdjustmentScaleExp + ((int) (((-dMax) / d3) + 0.5d));
                this.bitrateAdjustmentScaleExp = i5;
                this.bitrateAdjustmentScaleExp = Math.min(i5, 20);
                this.deviationBytes = d7;
            }
        }
        this.timeSinceLastAdjustmentMs = 0.0d;
    }

    @Override // org.wrtca.video.BaseBitrateAdjuster, org.wrtca.video.BitrateAdjuster
    public void setTargets(int i2, int i3) {
        int i4 = this.targetBitrateBps;
        if (i4 > 0 && i2 < i4) {
            this.deviationBytes = (this.deviationBytes * i2) / i4;
        }
        super.setTargets(i2, i3);
    }
}
