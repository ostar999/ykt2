package com.aliyun.player.source;

/* loaded from: classes2.dex */
public class BitStreamSource extends SourceBase {
    public static final int EINVAL = 22;
    public static final int EIO = 5;
    public static final int SEEK_CUR = 1;
    public static final int SEEK_END = 2;
    public static final int SEEK_SET = 0;
    public static final int SEEK_SIZE = 65536;
    private ReadCallback mReadCallback = null;
    private SeekCallback mSeekCallback = null;

    public interface ReadCallback {
        int read(byte[] bArr);
    }

    public interface SeekCallback {
        long seek(long j2, int i2);
    }

    public BitStreamSource() {
        this.mQuality = "AUTO";
        this.mForceQuality = true;
    }

    public ReadCallback getReadCallback() {
        return this.mReadCallback;
    }

    public SeekCallback getSeekCallback() {
        return this.mSeekCallback;
    }

    public void setReadCallback(ReadCallback readCallback) {
        this.mReadCallback = readCallback;
    }

    public void setSeekCallback(SeekCallback seekCallback) {
        this.mSeekCallback = seekCallback;
    }
}
