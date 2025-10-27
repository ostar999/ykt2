package com.google.zxing;

/* loaded from: classes4.dex */
public final class FormatException extends ReaderException {
    private static final FormatException instance = new FormatException();

    private FormatException() {
    }

    public static FormatException getFormatInstance() {
        return instance;
    }
}
