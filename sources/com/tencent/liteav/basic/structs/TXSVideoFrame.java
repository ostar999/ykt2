package com.tencent.liteav.basic.structs;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXSVideoFrame {
    public ByteBuffer buffer;
    public byte[] data;
    public Object eglContext;
    public int frameType;
    public int height;
    public long pts;
    public int rotation;
    public int textureId;
    public int width;

    private native void nativeLoadArrayFromBuffer(byte[] bArr, int i2);

    private native void nativeLoadArrayFromGL(byte[] bArr, int i2, int i3);

    private native void nativeLoadBufferFromGL(int i2, int i3);

    private native void nativeLoadNV21BufferFromI420Buffer(int i2, int i3);

    public byte[] I420toNV21(byte[] bArr, byte[] bArr2, int i2, int i3) {
        if (bArr2 == null) {
            bArr2 = new byte[bArr.length];
        }
        int i4 = i2 * i3;
        int i5 = (i4 / 4) + i4;
        System.arraycopy(bArr, 0, bArr2, 0, i4);
        int i6 = i4;
        int i7 = i5;
        while (i4 < i5) {
            bArr2[i6] = bArr[i7];
            bArr2[i6 + 1] = bArr[i4];
            i4++;
            i7++;
            i6 += 2;
        }
        return bArr2;
    }

    public void finalize() throws Throwable {
        release();
        super.finalize();
    }

    public void loadNV21BufferFromI420Buffer() {
        nativeLoadNV21BufferFromI420Buffer(this.width, this.height);
    }

    public void loadYUVArray(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int i2 = this.width;
            int i3 = this.height;
            if (length < ((i2 * i3) * 3) / 2) {
                return;
            }
            if (this.buffer == null) {
                GLES20.glReadPixels(0, 0, i2, (i3 * 3) / 8, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, ByteBuffer.wrap(bArr));
            } else {
                nativeLoadArrayFromBuffer(bArr, ((i2 * i3) * 3) / 2);
            }
        }
    }

    public void loadYUVBufferFromGL() {
        nativeLoadBufferFromGL(this.width, this.height);
    }

    public native void nativeClone(ByteBuffer byteBuffer);

    public native void release();

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public TXSVideoFrame m116clone() {
        TXSVideoFrame tXSVideoFrame = new TXSVideoFrame();
        tXSVideoFrame.width = this.width;
        tXSVideoFrame.height = this.height;
        tXSVideoFrame.frameType = this.frameType;
        tXSVideoFrame.rotation = this.rotation;
        tXSVideoFrame.pts = this.pts;
        tXSVideoFrame.data = this.data;
        tXSVideoFrame.textureId = this.textureId;
        tXSVideoFrame.eglContext = this.eglContext;
        tXSVideoFrame.nativeClone(this.buffer);
        return tXSVideoFrame;
    }
}
