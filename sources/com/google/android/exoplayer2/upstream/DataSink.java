package com.google.android.exoplayer2.upstream;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface DataSink {

    public interface Factory {
        DataSink createDataSink();
    }

    void close() throws IOException;

    void open(DataSpec dataSpec) throws IOException;

    void write(byte[] bArr, int i2, int i3) throws IOException;
}
