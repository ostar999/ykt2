package com.google.android.exoplayer2.decoder;

/* loaded from: classes3.dex */
public class CryptoException extends Exception {
    public final int errorCode;

    public CryptoException(int i2, String str) {
        super(str);
        this.errorCode = i2;
    }
}
