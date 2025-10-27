package com.tencent.ugc;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXAudioFrame {
    private ByteBuffer mByteBuffer;
    private int mLength;

    public ByteBuffer getByteBuffer() {
        return this.mByteBuffer;
    }

    public int getLength() {
        return this.mLength;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.mByteBuffer = byteBuffer;
    }

    public void setLength(int i2) {
        this.mLength = i2;
    }
}
