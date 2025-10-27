package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public interface EbmlProcessor {
    public static final int ELEMENT_TYPE_BINARY = 4;
    public static final int ELEMENT_TYPE_FLOAT = 5;
    public static final int ELEMENT_TYPE_MASTER = 1;
    public static final int ELEMENT_TYPE_STRING = 3;
    public static final int ELEMENT_TYPE_UNKNOWN = 0;
    public static final int ELEMENT_TYPE_UNSIGNED_INT = 2;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ElementType {
    }

    void binaryElement(int i2, int i3, ExtractorInput extractorInput) throws IOException;

    void endMasterElement(int i2) throws ParserException;

    void floatElement(int i2, double d3) throws ParserException;

    int getElementType(int i2);

    void integerElement(int i2, long j2) throws ParserException;

    boolean isLevel1Element(int i2);

    void startMasterElement(int i2, long j2, long j3) throws ParserException;

    void stringElement(int i2, String str) throws ParserException;
}
