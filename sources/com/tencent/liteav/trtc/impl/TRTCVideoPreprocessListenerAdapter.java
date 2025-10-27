package com.tencent.liteav.trtc.impl;

import android.annotation.SuppressLint;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import com.tencent.liteav.basic.a.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.opengl.e;
import com.tencent.liteav.basic.util.e;
import com.tencent.liteav.beauty.b.k;
import com.tencent.liteav.beauty.b.o;
import com.tencent.liteav.m;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TRTCVideoPreprocessListenerAdapter implements m {
    private static final String TAG = "TRTCVideoPreprocessListenerAdapter";
    private int mBufferType;
    private TRTCCloudListener.TRTCVideoFrameListener mListener;
    private int mPixelFormat;
    private o mRGBAToYUVFilter;
    private k mYUVToRGBAFilter;
    private final e mLastFrameSize = new e(0, 0);
    private final TRTCCloudDef.TRTCVideoFrame mYUVInputFrame = new TRTCCloudDef.TRTCVideoFrame();
    private final TRTCCloudDef.TRTCVideoFrame mYUVOutputFrame = new TRTCCloudDef.TRTCVideoFrame();
    private final TRTCCloudDef.TRTCVideoFrame mShadowInputFrame = new TRTCCloudDef.TRTCVideoFrame();
    private final TRTCCloudDef.TRTCVideoFrame mShadowOutputFrame = new TRTCCloudDef.TRTCVideoFrame();
    private final a mDelayQueue = new a();
    private boolean mHasNotifiedGLContextCreated = false;
    private int mFrameBufferId = -1;

    private void convertTextureToYUV(int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        int i3 = tRTCVideoFrame.width;
        int i4 = tRTCVideoFrame.height;
        o rGBAToYUVFilter = getRGBAToYUVFilter(tRTCVideoFrame.pixelFormat, i3, i4);
        GLES20.glViewport(0, 0, i3, i4);
        rGBAToYUVFilter.a(i3, i4);
        TXCOpenGlUtils.a(rGBAToYUVFilter.b(i2), this.mFrameBufferId);
        e.a aVar = e.a.RGBA;
        int i5 = tRTCVideoFrame.pixelFormat;
        if (i5 == 1) {
            aVar = e.a.I420;
        } else if (i5 == 4) {
            aVar = e.a.NV21;
        }
        GLES20.glBindFramebuffer(36160, this.mFrameBufferId);
        if (tRTCVideoFrame.bufferType == 1) {
            TXCOpenGlUtils.a(aVar, i3, i4, tRTCVideoFrame.buffer);
        } else {
            TXCOpenGlUtils.a(aVar, i3, i4, tRTCVideoFrame.data);
        }
        TXCOpenGlUtils.d(this.mFrameBufferId);
    }

    private void convertYUVToTexture(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, int i2) {
        if (tRTCVideoFrame.data == null && tRTCVideoFrame.buffer == null) {
            return;
        }
        int i3 = tRTCVideoFrame.width;
        int i4 = tRTCVideoFrame.height;
        k yUVToRGBAFilter = getYUVToRGBAFilter(tRTCVideoFrame.pixelFormat, i3, i4);
        TXCOpenGlUtils.a(i2, this.mFrameBufferId);
        GLES20.glViewport(0, 0, i3, i4);
        int i5 = tRTCVideoFrame.bufferType;
        if (i5 == 2) {
            yUVToRGBAFilter.a(tRTCVideoFrame.data);
        } else if (i5 == 1) {
            yUVToRGBAFilter.a(tRTCVideoFrame.buffer);
        }
        GLES20.glBindFramebuffer(36160, this.mFrameBufferId);
        yUVToRGBAFilter.a(-1, this.mFrameBufferId, i2);
        TXCOpenGlUtils.d(this.mFrameBufferId);
    }

    private o getRGBAToYUVFilter(int i2, int i3, int i4) {
        if (this.mRGBAToYUVFilter == null) {
            o oVar = new o(i2 == 1 ? 1 : 3);
            this.mRGBAToYUVFilter = oVar;
            oVar.a(true);
            if (!this.mRGBAToYUVFilter.a()) {
                TXCLog.e(TAG, "init RGBA to YUV filter failed.");
            }
            this.mRGBAToYUVFilter.a(i3, i4);
        }
        return this.mRGBAToYUVFilter;
    }

    private k getYUVToRGBAFilter(int i2, int i3, int i4) {
        if (this.mYUVToRGBAFilter == null) {
            k kVar = new k(i2 != 1 ? 3 : 1);
            this.mYUVToRGBAFilter = kVar;
            if (!kVar.a()) {
                TXCLog.e(TAG, "init YUV to RGBA failed.");
            }
            this.mYUVToRGBAFilter.a(i3, i4);
        }
        return this.mYUVToRGBAFilter;
    }

    @SuppressLint({"NewApi"})
    private static void initVideoFrame(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, int i2, int i3, int i4, int i5) {
        tRTCVideoFrame.width = i2;
        tRTCVideoFrame.height = i3;
        tRTCVideoFrame.pixelFormat = i4;
        tRTCVideoFrame.bufferType = i5;
        if (i4 == 2 && tRTCVideoFrame.texture == null) {
            tRTCVideoFrame.texture = new TRTCCloudDef.TRTCTexture();
            Object objE = TXCOpenGlUtils.e();
            if (objE instanceof EGLContext) {
                tRTCVideoFrame.texture.eglContext14 = (EGLContext) objE;
                return;
            } else {
                tRTCVideoFrame.texture.eglContext10 = (javax.microedition.khronos.egl.EGLContext) objE;
                return;
            }
        }
        if (i4 == 1 || i4 == 4) {
            if (i5 == 2 && tRTCVideoFrame.data == null) {
                tRTCVideoFrame.data = new byte[((i2 * i3) * 3) / 2];
            } else if (i5 == 1 && tRTCVideoFrame.buffer == null) {
                tRTCVideoFrame.buffer = ByteBuffer.allocateDirect(((i2 * i3) * 3) / 2);
            }
        }
    }

    private void notifyGLContextCreated() {
        TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener = this.mListener;
        if (tRTCVideoFrameListener == null || this.mHasNotifiedGLContextCreated) {
            return;
        }
        tRTCVideoFrameListener.onGLContextCreated();
        this.mHasNotifiedGLContextCreated = true;
    }

    private void notifyGLContextDestroy() {
        TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener = this.mListener;
        if (tRTCVideoFrameListener == null || !this.mHasNotifiedGLContextCreated) {
            return;
        }
        tRTCVideoFrameListener.onGLContextDestory();
        this.mHasNotifiedGLContextCreated = false;
    }

    private void releaseOpenGLResources() {
        o oVar = this.mRGBAToYUVFilter;
        if (oVar != null) {
            oVar.d();
            this.mRGBAToYUVFilter = null;
        }
        k kVar = this.mYUVToRGBAFilter;
        if (kVar != null) {
            kVar.d();
            this.mYUVToRGBAFilter = null;
        }
        TXCOpenGlUtils.b(this.mFrameBufferId);
        this.mFrameBufferId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListenerInternal(int i2, int i3, TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener) {
        notifyGLContextDestroy();
        this.mPixelFormat = i2;
        this.mBufferType = i3;
        this.mListener = tRTCVideoFrameListener;
        notifyGLContextCreated();
    }

    private void shadowCopyVideoFrame(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2) {
        tRTCVideoFrame2.width = tRTCVideoFrame.width;
        tRTCVideoFrame2.height = tRTCVideoFrame.height;
        tRTCVideoFrame2.pixelFormat = tRTCVideoFrame.pixelFormat;
        tRTCVideoFrame2.bufferType = tRTCVideoFrame.bufferType;
        tRTCVideoFrame2.texture = tRTCVideoFrame.texture;
        tRTCVideoFrame2.data = tRTCVideoFrame.data;
        tRTCVideoFrame2.buffer = tRTCVideoFrame.buffer;
    }

    public void onDetectFacePoints(float[] fArr) {
    }

    @Override // com.tencent.liteav.m
    public void onGLContextCreated() {
        this.mDelayQueue.a();
        notifyGLContextCreated();
    }

    @Override // com.tencent.liteav.m
    public void onGLContextReadyToDestory() {
        this.mDelayQueue.a();
        notifyGLContextDestroy();
        releaseOpenGLResources();
    }

    @Override // com.tencent.liteav.m
    public int onProcessVideoFrame(int i2, int i3, int i4, int i5) {
        this.mDelayQueue.a();
        if (this.mListener == null) {
            return i2;
        }
        com.tencent.liteav.basic.util.e eVar = this.mLastFrameSize;
        if (eVar.f18712a != i3 || eVar.f18713b != i4) {
            releaseOpenGLResources();
            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame = this.mYUVInputFrame;
            tRTCVideoFrame.data = null;
            tRTCVideoFrame.buffer = null;
            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2 = this.mYUVOutputFrame;
            tRTCVideoFrame2.data = null;
            tRTCVideoFrame2.buffer = null;
            com.tencent.liteav.basic.util.e eVar2 = this.mLastFrameSize;
            eVar2.f18712a = i3;
            eVar2.f18713b = i4;
        }
        initVideoFrame(this.mYUVInputFrame, i3, i4, this.mPixelFormat, this.mBufferType);
        initVideoFrame(this.mYUVOutputFrame, i3, i4, this.mPixelFormat, this.mBufferType);
        if (this.mPixelFormat == 2) {
            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame3 = this.mYUVInputFrame;
            tRTCVideoFrame3.texture.textureId = i2;
            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame4 = this.mYUVOutputFrame;
            tRTCVideoFrame4.texture.textureId = i5;
            this.mListener.onProcessVideoFrame(tRTCVideoFrame3, tRTCVideoFrame4);
            return this.mYUVOutputFrame.texture.textureId;
        }
        if (this.mFrameBufferId == -1) {
            this.mFrameBufferId = TXCOpenGlUtils.d();
        }
        convertTextureToYUV(i2, this.mYUVInputFrame);
        shadowCopyVideoFrame(this.mYUVInputFrame, this.mShadowInputFrame);
        shadowCopyVideoFrame(this.mYUVOutputFrame, this.mShadowOutputFrame);
        this.mListener.onProcessVideoFrame(this.mShadowInputFrame, this.mShadowOutputFrame);
        convertYUVToTexture(this.mShadowOutputFrame, i5);
        return i5;
    }

    public void setListener(final int i2, final int i3, final TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener) {
        this.mDelayQueue.a(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCVideoPreprocessListenerAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                TRTCVideoPreprocessListenerAdapter.this.setListenerInternal(i2, i3, tRTCVideoFrameListener);
            }
        });
    }
}
