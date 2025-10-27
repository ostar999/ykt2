package org.wrtca.record.model;

/* loaded from: classes9.dex */
public class VBRMode extends BaseMediaBitrateConfig {
    public VBRMode(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("maxBitrate or bitrate value error!");
        }
        this.maxBitrate = i2;
        this.bitrate = i3;
        this.mode = 1;
    }
}
