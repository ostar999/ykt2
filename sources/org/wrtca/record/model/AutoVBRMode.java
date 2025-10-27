package org.wrtca.record.model;

/* loaded from: classes9.dex */
public class AutoVBRMode extends BaseMediaBitrateConfig {
    public AutoVBRMode() {
        this.mode = 3;
    }

    public AutoVBRMode(int i2) {
        if (i2 >= 0 && i2 <= 51) {
            this.crfSize = i2;
            this.mode = 3;
            return;
        }
        throw new IllegalArgumentException("crfSize 在0~51之间");
    }
}
