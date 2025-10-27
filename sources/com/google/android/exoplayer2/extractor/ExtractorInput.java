package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import java.io.IOException;

/* loaded from: classes3.dex */
public interface ExtractorInput extends DataReader {
    void advancePeekPosition(int i2) throws IOException;

    boolean advancePeekPosition(int i2, boolean z2) throws IOException;

    long getLength();

    long getPeekPosition();

    long getPosition();

    int peek(byte[] bArr, int i2, int i3) throws IOException;

    void peekFully(byte[] bArr, int i2, int i3) throws IOException;

    boolean peekFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException;

    @Override // com.google.android.exoplayer2.upstream.DataReader
    int read(byte[] bArr, int i2, int i3) throws IOException;

    void readFully(byte[] bArr, int i2, int i3) throws IOException;

    boolean readFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException;

    void resetPeekPosition();

    <E extends Throwable> void setRetryPosition(long j2, E e2) throws Throwable;

    int skip(int i2) throws IOException;

    void skipFully(int i2) throws IOException;

    boolean skipFully(int i2, boolean z2) throws IOException;
}
