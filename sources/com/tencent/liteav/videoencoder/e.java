package com.tencent.liteav.videoencoder;

import android.media.MediaFormat;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class e extends com.tencent.liteav.basic.module.a {
    protected j mEncodeFilter;
    protected boolean mInit;
    protected j mInputFilter;
    protected f mListener = null;
    protected int mInputWidth = 0;
    protected int mInputHeight = 0;
    protected int mOutputWidth = 0;
    protected int mOutputHeight = 0;
    protected int mInputTextureID = -1;
    protected Object mGLContextExternal = null;
    private long mVideoGOPEncode = 0;
    private boolean mEncodeFirstGOP = false;
    protected int mStreamType = 2;
    protected int mRotation = 0;
    protected JSONArray mEncFmt = null;
    protected boolean mEnableXMirror = false;

    public void callDelegate(int i2) {
        callDelegate(new TXSNALPacket(), i2);
    }

    public void enableNearestRPS(int i2) {
    }

    public int getEncodeCost() {
        return 0;
    }

    public long getRealBitrate() {
        return 0L;
    }

    public double getRealFPS() {
        return 0.0d;
    }

    public int getVideoHeight() {
        return this.mOutputHeight;
    }

    public int getVideoWidth() {
        return this.mOutputWidth;
    }

    public boolean isH265Encoder() {
        return false;
    }

    public void onEncodeFinished(int i2, long j2, long j3) {
        f fVar = this.mListener;
        if (fVar != null) {
            fVar.a(i2, j2, j3);
        }
    }

    public long pushVideoFrame(int i2, int i3, int i4, long j2) {
        return 10000002L;
    }

    public long pushVideoFrameAsync(int i2, int i3, int i4, long j2) {
        return 10000002L;
    }

    public long pushVideoFrameSync(int i2, int i3, int i4, long j2) {
        return 10000002L;
    }

    public void restartIDR() {
    }

    public void setBitrate(int i2) {
    }

    public void setBitrateFromQos(int i2, int i3) {
    }

    public void setEncodeIdrFpsFromQos(int i2) {
    }

    public void setFPS(int i2) {
    }

    public void setGLFinishedTextureNeed(boolean z2) {
    }

    public void setListener(f fVar) {
        this.mListener = fVar;
    }

    public void setRPSRefBitmap(int i2, int i3, long j2) {
    }

    public void setRotation(int i2) {
        this.mRotation = i2;
    }

    public void setXMirror(boolean z2) {
        this.mEnableXMirror = z2;
    }

    public void signalEOSAndFlush() {
    }

    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        if (tXSVideoEncoderParam != null) {
            int i2 = tXSVideoEncoderParam.width;
            this.mOutputWidth = i2;
            int i3 = tXSVideoEncoderParam.height;
            this.mOutputHeight = i3;
            this.mInputWidth = i2;
            this.mInputHeight = i3;
            this.mGLContextExternal = tXSVideoEncoderParam.glContext;
            this.mStreamType = tXSVideoEncoderParam.streamType;
            this.mEncFmt = tXSVideoEncoderParam.encFmt;
        }
        this.mVideoGOPEncode = 0L;
        this.mEncodeFirstGOP = false;
        return 10000002;
    }

    public void stop() {
    }

    public void callDelegate(TXSNALPacket tXSNALPacket, int i2) {
        f fVar = this.mListener;
        if (fVar != null) {
            tXSNALPacket.streamType = this.mStreamType;
            fVar.a(tXSNALPacket, i2);
            if (tXSNALPacket.nalType == 0) {
                long j2 = this.mVideoGOPEncode;
                if (j2 != 0) {
                    this.mEncodeFirstGOP = true;
                    setStatusValue(4006, Long.valueOf(j2));
                }
                this.mVideoGOPEncode = 1L;
                return;
            }
            long j3 = this.mVideoGOPEncode + 1;
            this.mVideoGOPEncode = j3;
            if (this.mEncodeFirstGOP) {
                return;
            }
            setStatusValue(4006, Long.valueOf(j3));
        }
    }

    public void callDelegate(MediaFormat mediaFormat) {
        f fVar = this.mListener;
        if (fVar != null) {
            fVar.a(mediaFormat);
        }
    }
}
