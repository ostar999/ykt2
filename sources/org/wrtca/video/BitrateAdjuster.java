package org.wrtca.video;

/* loaded from: classes9.dex */
public interface BitrateAdjuster {
    int getAdjustedBitrateBps();

    int getAdjustedFramerate();

    void reportEncodedFrame(int i2);

    void setTargets(int i2, int i3);
}
