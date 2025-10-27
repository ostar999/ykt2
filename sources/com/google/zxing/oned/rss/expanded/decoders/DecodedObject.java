package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes4.dex */
abstract class DecodedObject {
    private final int newPosition;

    public DecodedObject(int i2) {
        this.newPosition = i2;
    }

    public final int getNewPosition() {
        return this.newPosition;
    }
}
