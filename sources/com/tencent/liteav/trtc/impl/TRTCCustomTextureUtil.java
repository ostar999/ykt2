package com.tencent.liteav.trtc.impl;

import android.opengl.GLES20;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.i;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.c;
import com.tencent.liteav.beauty.b.k;
import com.tencent.liteav.d;
import com.tencent.trtc.TRTCCloudDef;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class TRTCCustomTextureUtil {
    private static final String TAG = "TRTCCustomTextureUtil";
    private d mCaptureAndEnc;
    private int mGLSyncMode;
    private long mLastGLThreadId;
    private j mRotateFilter;
    private i mGLThreadHandler = null;
    private HandlerThread mEGLThread = null;
    private Object mEGLContext = null;
    private k mI4202RGBAFilter = null;
    private long mLastCaptureCalculateTS = 0;
    private long mCaptureFrameCount = 0;
    private long mLastCaptureFrameCount = 0;
    private double mCurrentFps = 0.0d;
    private final c mFpsMeter = new c("send-custom-texture", (int) TimeUnit.SECONDS.toMillis(5));

    public TRTCCustomTextureUtil(d dVar, int i2) {
        this.mGLSyncMode = 0;
        this.mCaptureAndEnc = dVar;
        this.mGLSyncMode = i2;
        apiLog("TRTCCustomTextureUtil: glMode:" + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void apiLog(String str) {
        TXCLog.i(TAG, "trtc_api " + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003f A[PHI: r2
      0x003f: PHI (r2v5 boolean) = (r2v4 boolean), (r2v6 boolean) binds: [B:7:0x0015, B:14:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkEGLContext(com.tencent.trtc.TRTCCloudDef.TRTCVideoFrame r5) throws java.lang.InterruptedException {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            long r0 = r4.mLastGLThreadId
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            long r2 = r2.getId()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            if (r0 != 0) goto L41
            com.tencent.trtc.TRTCCloudDef$TRTCTexture r0 = r5.texture
            r2 = 0
            if (r0 == 0) goto L3f
            javax.microedition.khronos.egl.EGLContext r0 = r0.eglContext10
            if (r0 == 0) goto L2a
            java.lang.Object r2 = r4.mEGLContext
            boolean r0 = r0.equals(r2)
            r2 = r0 ^ 1
            if (r2 == 0) goto L2a
            java.lang.String r0 = "CustomCapture egl10Context change!"
            r4.apiLog(r0)
        L2a:
            com.tencent.trtc.TRTCCloudDef$TRTCTexture r0 = r5.texture
            android.opengl.EGLContext r0 = r0.eglContext14
            if (r0 == 0) goto L3f
            java.lang.Object r2 = r4.mEGLContext
            boolean r0 = r0.equals(r2)
            r1 = r1 ^ r0
            if (r1 == 0) goto L46
            java.lang.String r0 = "CustomCapture egl14Context change!"
            r4.apiLog(r0)
            goto L46
        L3f:
            r1 = r2
            goto L46
        L41:
            java.lang.String r0 = "CustomCapture eglContext's thread change!"
            r4.apiLog(r0)
        L46:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            long r2 = r0.getId()
            r4.mLastGLThreadId = r2
            com.tencent.trtc.TRTCCloudDef$TRTCTexture r0 = r5.texture
            if (r0 == 0) goto L5f
            javax.microedition.khronos.egl.EGLContext r2 = r0.eglContext10
            if (r2 == 0) goto L5b
            r4.mEGLContext = r2
            goto L5f
        L5b:
            android.opengl.EGLContext r0 = r0.eglContext14
            r4.mEGLContext = r0
        L5f:
            if (r1 == 0) goto L67
            r4.stopThread()
            r4.startThread(r5)
        L67:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCustomTextureUtil.checkEGLContext(com.tencent.trtc.TRTCCloudDef$TRTCVideoFrame):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkRotate(int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        int i3;
        if (tRTCVideoFrame != null && (i3 = tRTCVideoFrame.rotation) != 0) {
            int i4 = i3 * 90;
            if (this.mRotateFilter == null) {
                j jVar = new j();
                jVar.a();
                jVar.a(true);
                jVar.a(tRTCVideoFrame.width, tRTCVideoFrame.height);
                this.mRotateFilter = jVar;
            }
            j jVar2 = this.mRotateFilter;
            if (jVar2 != null) {
                GLES20.glViewport(0, 0, tRTCVideoFrame.width, tRTCVideoFrame.height);
                int i5 = (720 - i4) % 360;
                jVar2.a(tRTCVideoFrame.width, tRTCVideoFrame.height);
                int i6 = tRTCVideoFrame.width;
                int i7 = tRTCVideoFrame.height;
                jVar2.a(i6, i7, i5, null, i6 / i7, false, false);
                jVar2.b(i2);
                i2 = jVar2.l();
                int i8 = (i5 == 90 || i5 == 270) ? tRTCVideoFrame.height : tRTCVideoFrame.width;
                int i9 = (i5 == 90 || i5 == 270) ? tRTCVideoFrame.width : tRTCVideoFrame.height;
                tRTCVideoFrame.width = i8;
                tRTCVideoFrame.height = i9;
            }
        }
        return i2;
    }

    private void sendCustomTextureInternal(final TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        synchronized (this) {
            if (this.mGLThreadHandler != null) {
                if (this.mGLSyncMode == 0) {
                    GLES20.glFinish();
                }
                final i iVar = this.mGLThreadHandler;
                iVar.post(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCustomTextureUtil.1
                    @Override // java.lang.Runnable
                    public void run() {
                        iVar.d();
                        if (iVar.f18583d) {
                            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2 = tRTCVideoFrame;
                            TRTCCloudDef.TRTCTexture tRTCTexture = tRTCVideoFrame2.texture;
                            if (tRTCTexture != null) {
                                tRTCTexture.textureId = TRTCCustomTextureUtil.this.checkRotate(tRTCTexture.textureId, tRTCVideoFrame2);
                                if (iVar.f18584e != null) {
                                    if (TRTCCustomTextureUtil.this.mGLSyncMode == 0) {
                                        d dVar = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                        TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame3 = tRTCVideoFrame;
                                        dVar.b(tRTCVideoFrame3.texture.textureId, tRTCVideoFrame3.width, tRTCVideoFrame3.height, iVar.f18584e.f(), tRTCVideoFrame.timestamp);
                                        return;
                                    } else {
                                        d dVar2 = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                        TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame4 = tRTCVideoFrame;
                                        dVar2.a(tRTCVideoFrame4.texture.textureId, tRTCVideoFrame4.width, tRTCVideoFrame4.height, iVar.f18584e.f(), tRTCVideoFrame.timestamp);
                                        return;
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame5 = tRTCVideoFrame;
                        TRTCCloudDef.TRTCTexture tRTCTexture2 = tRTCVideoFrame5.texture;
                        if (tRTCTexture2 != null) {
                            tRTCTexture2.textureId = TRTCCustomTextureUtil.this.checkRotate(tRTCTexture2.textureId, tRTCVideoFrame5);
                            if (iVar.f18586g != null) {
                                if (TRTCCustomTextureUtil.this.mGLSyncMode == 0) {
                                    d dVar3 = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                    TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame6 = tRTCVideoFrame;
                                    dVar3.b(tRTCVideoFrame6.texture.textureId, tRTCVideoFrame6.width, tRTCVideoFrame6.height, iVar.f18586g.d(), tRTCVideoFrame.timestamp);
                                    return;
                                } else {
                                    d dVar4 = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                    TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame7 = tRTCVideoFrame;
                                    dVar4.a(tRTCVideoFrame7.texture.textureId, tRTCVideoFrame7.width, tRTCVideoFrame7.height, iVar.f18586g.d(), tRTCVideoFrame.timestamp);
                                    return;
                                }
                            }
                            return;
                        }
                        int i2 = tRTCVideoFrame5.pixelFormat == 1 ? 1 : 3;
                        if (TRTCCustomTextureUtil.this.mI4202RGBAFilter == null) {
                            k kVar = new k(i2);
                            kVar.a(true);
                            if (!kVar.a()) {
                                TXCLog.e(TRTCCustomTextureUtil.TAG, "mI4202RGBAFilter init failed!!, break init");
                            }
                            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame8 = tRTCVideoFrame;
                            kVar.a(tRTCVideoFrame8.width, tRTCVideoFrame8.height);
                            TRTCCustomTextureUtil.this.mI4202RGBAFilter = kVar;
                        }
                        k kVar2 = TRTCCustomTextureUtil.this.mI4202RGBAFilter;
                        if (kVar2 != null) {
                            TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame9 = tRTCVideoFrame;
                            GLES20.glViewport(0, 0, tRTCVideoFrame9.width, tRTCVideoFrame9.height);
                            kVar2.a(tRTCVideoFrame.data);
                            int iCheckRotate = TRTCCustomTextureUtil.this.checkRotate(kVar2.r(), tRTCVideoFrame);
                            if (iVar.f18586g != null) {
                                if (TRTCCustomTextureUtil.this.mGLSyncMode == 0) {
                                    d dVar5 = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                    TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame10 = tRTCVideoFrame;
                                    dVar5.b(iCheckRotate, tRTCVideoFrame10.width, tRTCVideoFrame10.height, iVar.f18586g.d(), tRTCVideoFrame.timestamp);
                                } else {
                                    d dVar6 = TRTCCustomTextureUtil.this.mCaptureAndEnc;
                                    TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame11 = tRTCVideoFrame;
                                    dVar6.a(iCheckRotate, tRTCVideoFrame11.width, tRTCVideoFrame11.height, iVar.f18586g.d(), tRTCVideoFrame.timestamp);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void startThread(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) throws InterruptedException {
        if (tRTCVideoFrame == null) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this) {
            if (this.mEGLThread == null) {
                HandlerThread handlerThread = new HandlerThread("customCaptureGLThread");
                this.mEGLThread = handlerThread;
                handlerThread.start();
                this.mGLThreadHandler = new i(this.mEGLThread.getLooper());
                TRTCCloudDef.TRTCTexture tRTCTexture = tRTCVideoFrame.texture;
                if (tRTCTexture == null) {
                    apiLog("CustomCapture buffer start egl10 thread");
                    i iVar = this.mGLThreadHandler;
                    iVar.f18583d = false;
                    iVar.f18587h = null;
                    iVar.f18580a = 1280;
                    iVar.f18581b = 720;
                    iVar.sendEmptyMessage(100);
                } else if (tRTCTexture.eglContext10 != null) {
                    apiLog("CustomCapture texture start egl10 thread");
                    i iVar2 = this.mGLThreadHandler;
                    iVar2.f18583d = false;
                    iVar2.f18587h = tRTCVideoFrame.texture.eglContext10;
                    iVar2.f18580a = 1280;
                    iVar2.f18581b = 720;
                    iVar2.sendEmptyMessage(100);
                } else if (tRTCTexture.eglContext14 != null && TXCBuild.VersionInt() >= 17) {
                    apiLog("CustomCapture texture start egl14 thread");
                    i iVar3 = this.mGLThreadHandler;
                    iVar3.f18583d = true;
                    iVar3.f18585f = tRTCVideoFrame.texture.eglContext14;
                    iVar3.f18580a = 1280;
                    iVar3.f18581b = 720;
                    iVar3.sendEmptyMessage(100);
                }
                this.mGLThreadHandler.post(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCustomTextureUtil.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCLog.i(TRTCCustomTextureUtil.TAG, "GLContext create finished!");
                        countDownLatch.countDown();
                    }
                });
            } else {
                countDownLatch.countDown();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void stopThread() {
        i iVar = this.mGLThreadHandler;
        if (iVar != null) {
            final j jVar = this.mRotateFilter;
            this.mRotateFilter = null;
            final k kVar = this.mI4202RGBAFilter;
            this.mI4202RGBAFilter = null;
            iVar.post(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCustomTextureUtil.3
                @Override // java.lang.Runnable
                public void run() {
                    j jVar2 = jVar;
                    if (jVar2 != null) {
                        jVar2.d();
                    }
                    k kVar2 = kVar;
                    if (kVar2 != null) {
                        kVar2.d();
                    }
                    if (TRTCCustomTextureUtil.this.mCaptureAndEnc != null) {
                        TRTCCustomTextureUtil.this.apiLog("CustomCapture release");
                        TRTCCustomTextureUtil.this.mCaptureAndEnc.q();
                    }
                }
            });
            i.a(this.mGLThreadHandler, this.mEGLThread);
            apiLog("CustomCapture destroy egl thread");
        }
        this.mGLThreadHandler = null;
        this.mEGLThread = null;
    }

    public double getCurrentFPS() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = jElapsedRealtime - this.mLastCaptureCalculateTS;
        if (j2 >= 1000) {
            long j3 = this.mCaptureFrameCount;
            this.mCurrentFps = ((j3 - this.mLastCaptureFrameCount) * 1000.0d) / j2;
            this.mLastCaptureFrameCount = j3;
            this.mLastCaptureCalculateTS = jElapsedRealtime;
        }
        return this.mCurrentFps;
    }

    public void release() {
        stopThread();
    }

    public void sendCustomTexture(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) throws InterruptedException {
        this.mFpsMeter.a();
        checkEGLContext(tRTCVideoFrame);
        sendCustomTextureInternal(tRTCVideoFrame);
        if (this.mLastCaptureCalculateTS != 0) {
            this.mCaptureFrameCount++;
            return;
        }
        this.mLastCaptureCalculateTS = SystemClock.elapsedRealtime();
        this.mLastCaptureFrameCount = 0L;
        this.mCaptureFrameCount = 0L;
    }
}
