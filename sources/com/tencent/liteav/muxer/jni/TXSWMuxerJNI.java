package com.tencent.liteav.muxer.jni;

import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXSWMuxerJNI {

    /* renamed from: a, reason: collision with root package name */
    private long f19449a;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f19450b = true;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f19451c;

    public static class AVOptions {
        public int videoWidth = 960;
        public int videoHeight = R2.attr.bl_checked_gradient_centerY;
        public int videoGOP = 12;
        public int audioSampleRate = 0;
        public int audioChannels = 0;
    }

    public TXSWMuxerJNI() {
        this.f19449a = -1L;
        this.f19449a = init();
    }

    private native long init();

    private native void release(long j2);

    private native void setAVParams(long j2, AVOptions aVOptions);

    private native void setAudioCSD(long j2, byte[] bArr);

    private native void setDstPath(long j2, String str);

    private native void setVideoCSD(long j2, byte[] bArr, byte[] bArr2);

    private native int start(long j2);

    private native int stop(long j2);

    private native int writeFrame(long j2, byte[] bArr, int i2, int i3, int i4, int i5, long j3);

    public void a(AVOptions aVOptions) {
        if (this.f19450b) {
            setAVParams(this.f19449a, aVOptions);
        } else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    public int b() {
        if (!this.f19450b) {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
            return -1;
        }
        if (!this.f19451c) {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't start yet!");
            return -1;
        }
        this.f19451c = false;
        int iStop = stop(this.f19449a);
        if (iStop != 0) {
            TXCLog.e("TXSWMuxerJNI", "Stop Muxer Error!!!");
        }
        return iStop;
    }

    public void c() {
        if (!this.f19450b) {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
            return;
        }
        release(this.f19449a);
        this.f19450b = false;
        this.f19451c = false;
    }

    public void a(String str) {
        if (this.f19450b) {
            setDstPath(this.f19449a, str);
        } else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    public void a(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3) {
        if (this.f19450b) {
            setVideoCSD(this.f19449a, b(byteBuffer, i2), b(byteBuffer2, i3));
        } else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    private byte[] b(ByteBuffer byteBuffer, int i2) {
        if (byteBuffer == null) {
            return null;
        }
        byte[] bArr = new byte[i2];
        byteBuffer.get(bArr);
        return bArr;
    }

    public void a(ByteBuffer byteBuffer, int i2) {
        if (this.f19450b) {
            setAudioCSD(this.f19449a, b(byteBuffer, i2));
        } else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    public int a(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, long j2) {
        if (this.f19450b) {
            if (this.f19451c) {
                int iWriteFrame = writeFrame(this.f19449a, b(byteBuffer, i4), i2, i3, i4, i5, j2);
                if (iWriteFrame != 0) {
                    TXCLog.e("TXSWMuxerJNI", "Muxer write frame error!");
                }
                return iWriteFrame;
            }
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't start yet!");
            return -1;
        }
        TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }

    public int a() {
        if (this.f19450b) {
            int iStart = start(this.f19449a);
            if (iStart == 0) {
                this.f19451c = true;
            } else {
                TXCLog.e("TXSWMuxerJNI", "Start Muxer Error!!!");
            }
            return iStart;
        }
        TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }
}
