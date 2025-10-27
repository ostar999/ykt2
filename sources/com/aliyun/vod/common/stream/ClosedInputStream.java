package com.aliyun.vod.common.stream;

import java.io.InputStream;

/* loaded from: classes2.dex */
public class ClosedInputStream extends InputStream {
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    @Override // java.io.InputStream
    public int read() {
        return -1;
    }
}
