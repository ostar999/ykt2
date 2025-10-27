package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes4.dex */
final class DecodedInformation extends DecodedObject {
    private final String newString;
    private final boolean remaining;
    private final int remainingValue;

    public DecodedInformation(int i2, String str) {
        super(i2);
        this.newString = str;
        this.remaining = false;
        this.remainingValue = 0;
    }

    public String getNewString() {
        return this.newString;
    }

    public int getRemainingValue() {
        return this.remainingValue;
    }

    public boolean isRemaining() {
        return this.remaining;
    }

    public DecodedInformation(int i2, String str, int i3) {
        super(i2);
        this.remaining = true;
        this.remainingValue = i3;
        this.newString = str;
    }
}
