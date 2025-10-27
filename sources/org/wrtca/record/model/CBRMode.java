package org.wrtca.record.model;

/* loaded from: classes9.dex */
public class CBRMode extends BaseMediaBitrateConfig {
    public CBRMode(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("bufSize or bitrate value error!");
        }
        this.bufSize = i2;
        this.bitrate = i3;
        this.mode = 2;
    }
}
