package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* loaded from: classes4.dex */
abstract class Token {
    static final Token EMPTY = new SimpleToken(null, 0, 0);
    private final Token previous;

    public Token(Token token) {
        this.previous = token;
    }

    public final Token add(int i2, int i3) {
        return new SimpleToken(this, i2, i3);
    }

    public final Token addBinaryShift(int i2, int i3) {
        return new BinaryShiftToken(this, i2, i3);
    }

    public abstract void appendTo(BitArray bitArray, byte[] bArr);

    public final Token getPrevious() {
        return this.previous;
    }
}
