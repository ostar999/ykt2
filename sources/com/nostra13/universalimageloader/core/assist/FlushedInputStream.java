package com.nostra13.universalimageloader.core.assist;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class FlushedInputStream extends FilterInputStream {
    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        long j3 = 0;
        while (j3 < j2) {
            long jSkip = ((FilterInputStream) this).in.skip(j2 - j3);
            if (jSkip == 0) {
                if (read() < 0) {
                    break;
                }
                jSkip = 1;
            }
            j3 += jSkip;
        }
        return j3;
    }
}
