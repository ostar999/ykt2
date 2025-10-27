package org.wrtca.api;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.EglBase;
import org.wrtca.api.GlUtil;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoRenderer;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class EglRenderer implements VideoRenderer.Callbacks, VideoSink {
    private static final long LOG_INTERVAL_SEC = 4;
    private static final String TAG = "EglRenderer";
    private GlTextureFrameBuffer bitmapTextureFramebuffer;
    private RendererCommon.GlDrawer drawer;
    private EglBase eglBase;
    private volatile ErrorCallback errorCallback;
    private int framesDropped;
    private int framesReceived;
    private int framesRendered;
    public boolean isFirstFrameRendered;
    private float layoutAspectRatio;
    private long minRenderPeriodNs;
    private boolean mirror;
    private boolean mirrorOnlyRemote;
    public final String name;
    private long nextFrameTimeNs;
    private VideoFrame pendingFrame;
    private long renderSwapBufferTimeNs;
    private Handler renderThreadHandler;
    private long renderTimeNs;
    public RendererCommon.RendererEvents rendererEvents;
    private long statisticsStartTimeNs;
    private final Object handlerLock = new Object();
    private final ArrayList<FrameListenerAndParams> frameListeners = new ArrayList<>();
    private final Object fpsReductionLock = new Object();
    private final VideoFrameDrawer frameDrawer = new VideoFrameDrawer();
    private final Matrix drawMatrix = new Matrix();
    private final Object frameLock = new Object();
    private final Object layoutLock = new Object();
    private final Object statisticsLock = new Object();
    private final Runnable logStatisticsRunnable = new Runnable() { // from class: org.wrtca.api.EglRenderer.1
        @Override // java.lang.Runnable
        public void run() {
            EglRenderer.this.logStatistics();
            synchronized (EglRenderer.this.handlerLock) {
                if (EglRenderer.this.renderThreadHandler != null) {
                    EglRenderer.this.renderThreadHandler.removeCallbacks(EglRenderer.this.logStatisticsRunnable);
                    EglRenderer.this.renderThreadHandler.postDelayed(EglRenderer.this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4L));
                }
            }
        }
    };
    private final EglSurfaceCreation eglSurfaceCreationRunnable = new EglSurfaceCreation();

    public class EglSurfaceCreation implements Runnable {
        private Object surface;

        private EglSurfaceCreation() {
        }

        @Override // java.lang.Runnable
        public synchronized void run() {
            c.h.a(EglRenderer.TAG, "EglSurfaceCreation: " + EglRenderer.this.name + " eglBase: " + EglRenderer.this.eglBase + "surface: " + this.surface);
            if (EglRenderer.this.eglBase != null) {
                c.h.a(EglRenderer.TAG, " eglBase.hasSurface()" + EglRenderer.this.eglBase.hasSurface());
            }
            if (this.surface == null || EglRenderer.this.eglBase == null || EglRenderer.this.eglBase.hasSurface()) {
                c.h.a(EglRenderer.TAG, "EglSurfaceCreation aborted");
            } else {
                Object obj = this.surface;
                if (obj instanceof Surface) {
                    EglRenderer.this.eglBase.createSurface((Surface) this.surface);
                } else {
                    if (!(obj instanceof SurfaceTexture)) {
                        throw new IllegalStateException("Invalid surface: " + this.surface);
                    }
                    EglRenderer.this.eglBase.createSurface((SurfaceTexture) this.surface);
                }
                EglRenderer.this.eglBase.makeCurrent();
                GLES20.glPixelStorei(R2.attr.srlHeaderMaxDragRate, 1);
                c.h.a(EglRenderer.TAG, "EglSurfaceCreation finished eglBase: " + EglRenderer.this.eglBase + " eglBase.hasSurface() " + EglRenderer.this.eglBase.hasSurface());
            }
        }

        public synchronized void setSurface(Object obj) {
            this.surface = obj;
        }
    }

    public interface ErrorCallback {
        void onGlOutOfMemory();
    }

    public interface FrameListener {
        void onFrame(ByteBuffer byteBuffer, int i2, int i3);
    }

    public static class FrameListenerAndParams {
        public final boolean applyFpsReduction;
        public final RendererCommon.GlDrawer drawer;
        public final FrameListener listener;
        public Bitmap mBitmap;
        public final float scale;

        public FrameListenerAndParams(FrameListener frameListener, float f2, RendererCommon.GlDrawer glDrawer, boolean z2) {
            this.listener = frameListener;
            this.scale = f2;
            this.drawer = glDrawer;
            this.applyFpsReduction = z2;
        }
    }

    public interface RequestEglInit {
        void onRequestEglState(boolean z2, boolean z3);
    }

    public EglRenderer(String str) {
        this.name = str;
    }

    private String averageTimeAsString(long j2, int i2) {
        if (i2 <= 0) {
            return "NA";
        }
        return TimeUnit.NANOSECONDS.toMicros(j2 / i2) + " μs";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: clearSurfaceOnRenderThread, reason: merged with bridge method [inline-methods] */
    public void lambda$clearImage$6(float f2, float f3, float f4, float f5) {
        EglBase eglBase = this.eglBase;
        if (eglBase == null || !eglBase.hasSurface()) {
            return;
        }
        logD("clearSurface");
        GLES20.glClearColor(f2, f3, f4, f5);
        GLES20.glClear(16384);
        this.eglBase.swapBuffers();
    }

    private void createEglSurfaceInternal(Object obj) {
        this.eglSurfaceCreationRunnable.setSurface(obj);
        postToRenderThread(this.eglSurfaceCreationRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addFrameListener$3(RendererCommon.GlDrawer glDrawer, FrameListener frameListener, float f2, boolean z2) {
        if (glDrawer == null) {
            glDrawer = this.drawer;
        }
        this.frameListeners.add(new FrameListenerAndParams(frameListener, f2, glDrawer, z2));
        c.h.a(TAG, "addFrameListener: size:" + this.frameListeners.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(EglBase.Context context, int[] iArr) {
        if (context == null) {
            logD("EglBase10.create context");
            this.eglBase = a.e(iArr);
        } else {
            logD("EglBase.create shared context");
            c.h.a(TAG, "EglBase.create shared context");
            this.eglBase = a.c(context, iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$1(CountDownLatch countDownLatch) {
        RendererCommon.GlDrawer glDrawer = this.drawer;
        if (glDrawer != null) {
            glDrawer.release();
            this.drawer = null;
        }
        this.frameDrawer.release();
        GlTextureFrameBuffer glTextureFrameBuffer = this.bitmapTextureFramebuffer;
        if (glTextureFrameBuffer != null) {
            glTextureFrameBuffer.release();
            this.bitmapTextureFramebuffer = null;
        }
        if (this.eglBase != null) {
            c.h.a(TAG, "eglBase detach and release.");
            logD("eglBase detach and release.");
            this.eglBase.detachCurrent();
            this.eglBase.release();
            this.eglBase = null;
        }
        this.frameListeners.clear();
        countDownLatch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$2(Looper looper) {
        logD("Quitting render thread.");
        c.h.a(TAG, "EglRendererQuitting render thread");
        looper.quit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseEglSurface$5(Runnable runnable) {
        EglBase eglBase = this.eglBase;
        if (eglBase != null) {
            eglBase.detachCurrent();
            c.h.a(TAG, "egl render releaseSurface");
            this.eglBase.releaseSurface();
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeFrameListener$4(CountDownLatch countDownLatch, FrameListener frameListener) {
        countDownLatch.countDown();
        Iterator<FrameListenerAndParams> it = this.frameListeners.iterator();
        while (it.hasNext()) {
            if (it.next().listener == frameListener) {
                it.remove();
            }
        }
    }

    private void logD(String str) {
        Logging.d(TAG, this.name + str);
    }

    private void logE(String str, Throwable th) {
        Logging.e(TAG, this.name + str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logStatistics() {
        long jNanoTime = System.nanoTime();
        synchronized (this.statisticsLock) {
            long j2 = jNanoTime - this.statisticsStartTimeNs;
            if (j2 <= 0) {
                return;
            }
            logD("Duration: " + TimeUnit.NANOSECONDS.toMillis(j2) + " ms. Frames received: " + this.framesReceived + ". Dropped: " + this.framesDropped + ". Rendered: " + this.framesRendered + ". Render fps: " + String.format(Locale.US, "%.1f", Float.valueOf((this.framesRendered * TimeUnit.SECONDS.toNanos(1L)) / j2)) + ". Average render time: " + averageTimeAsString(this.renderTimeNs, this.framesRendered) + ". Average swapBuffer time: " + averageTimeAsString(this.renderSwapBufferTimeNs, this.framesRendered) + StrPool.DOT);
            resetStatistics(jNanoTime);
        }
    }

    private void notifyCallbacks(VideoFrame videoFrame, boolean z2) {
        if (!this.isFirstFrameRendered) {
            c.h.a(TAG, "egl isFirstFrameRendered = true");
            this.isFirstFrameRendered = true;
            if (this.rendererEvents != null) {
                c.h.a(TAG, "EglRendererReporting first rendered frame!!!: " + Thread.currentThread());
                this.rendererEvents.onFirstFrameRendered();
            }
        }
        if (this.frameListeners.isEmpty()) {
            return;
        }
        this.drawMatrix.reset();
        this.drawMatrix.preTranslate(0.5f, 0.5f);
        if (j.d.d().y()) {
            boolean z3 = this.mirror;
            if (z3 && !this.mirrorOnlyRemote) {
                this.drawMatrix.preScale(-1.0f, 1.0f);
            } else if (!z3 && this.mirrorOnlyRemote) {
                this.drawMatrix.preScale(-1.0f, 1.0f);
            }
        }
        this.drawMatrix.preScale(1.0f, -1.0f);
        this.drawMatrix.preTranslate(-0.5f, -0.5f);
        Iterator<FrameListenerAndParams> it = this.frameListeners.iterator();
        while (it.hasNext()) {
            FrameListenerAndParams next = it.next();
            if (z2 || !next.applyFpsReduction) {
                it.remove();
                int rotatedWidth = (int) (next.scale * videoFrame.getRotatedWidth());
                int rotatedHeight = (int) (next.scale * videoFrame.getRotatedHeight());
                if (rotatedWidth == 0 || rotatedHeight == 0) {
                    next.listener.onFrame(null, videoFrame.getRotatedWidth(), videoFrame.getRotatedHeight());
                } else {
                    if (this.bitmapTextureFramebuffer == null) {
                        this.bitmapTextureFramebuffer = new GlTextureFrameBuffer(R2.dimen.dm_200);
                    }
                    this.bitmapTextureFramebuffer.setSize(rotatedWidth, rotatedHeight);
                    GLES20.glBindFramebuffer(36160, this.bitmapTextureFramebuffer.getFrameBufferId());
                    GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, this.bitmapTextureFramebuffer.getTextureId(), 0);
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES20.glClear(16384);
                    this.frameDrawer.drawFrame(videoFrame, next.drawer, this.drawMatrix, 0, 0, rotatedWidth, rotatedHeight);
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(rotatedWidth * rotatedHeight * 4);
                    GLES20.glViewport(0, 0, rotatedWidth, rotatedHeight);
                    GLES20.glReadPixels(0, 0, rotatedWidth, rotatedHeight, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBufferAllocateDirect);
                    GLES20.glBindFramebuffer(36160, 0);
                    GlUtil.checkNoGLES2Error("EglRenderer.notifyCallbacks");
                    c.h.a(TAG, "EglRendereregl listener called screen shot ");
                    next.listener.onFrame(byteBufferAllocateDirect, videoFrame.getRotatedWidth(), videoFrame.getRotatedHeight());
                }
            }
        }
    }

    private void postToRenderThread(Runnable runnable) {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler != null) {
                handler.post(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renderFrameOnRenderThread() {
        boolean z2;
        float f2;
        float f3;
        float f4;
        float f5;
        synchronized (this.frameLock) {
            VideoFrame videoFrame = this.pendingFrame;
            if (videoFrame == null) {
                return;
            }
            this.pendingFrame = null;
            EglBase eglBase = this.eglBase;
            if (eglBase == null || !eglBase.hasSurface()) {
                return;
            }
            synchronized (this.fpsReductionLock) {
                long j2 = this.minRenderPeriodNs;
                if (j2 == Long.MAX_VALUE) {
                    c.h.a(TAG, "name: " + this.name + "shouldRenderFrame = FALSE minRenderPeriodNs == Long.MAX_VALUE");
                } else {
                    if (j2 > 0) {
                        long jNanoTime = System.nanoTime();
                        long j3 = this.nextFrameTimeNs;
                        if (jNanoTime < j3) {
                            logD("Skipping frame rendering - fps reduction is active.");
                            c.h.a(TAG, "name: " + this.name + "Skipping frame rendering - fps reduction is active");
                        } else {
                            long j4 = j3 + this.minRenderPeriodNs;
                            this.nextFrameTimeNs = j4;
                            this.nextFrameTimeNs = Math.max(j4, jNanoTime);
                        }
                    }
                    z2 = true;
                }
                z2 = false;
            }
            long jNanoTime2 = System.nanoTime();
            float rotatedWidth = videoFrame.getRotatedWidth() / videoFrame.getRotatedHeight();
            synchronized (this.layoutLock) {
                f2 = this.layoutAspectRatio;
                f3 = f2 != 0.0f ? f2 : rotatedWidth;
            }
            if (f2 == -100.0f) {
                f4 = 1.0f;
                f5 = 1.0f;
            } else if (rotatedWidth > f3) {
                f5 = f3 / rotatedWidth;
                f4 = 1.0f;
            } else {
                f4 = rotatedWidth / f3;
                f5 = 1.0f;
            }
            this.drawMatrix.reset();
            this.drawMatrix.preTranslate(0.5f, 0.5f);
            if (j.d.d().y()) {
                boolean z3 = this.mirror;
                if (z3 && !this.mirrorOnlyRemote) {
                    this.drawMatrix.preScale(-1.0f, 1.0f);
                } else if (!z3 && this.mirrorOnlyRemote) {
                    this.drawMatrix.preScale(-1.0f, 1.0f);
                }
            }
            this.drawMatrix.preScale(f5, f4);
            this.drawMatrix.preTranslate(-0.5f, -0.5f);
            try {
                if (z2) {
                    try {
                        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        GLES20.glClear(16384);
                        this.frameDrawer.drawFrame(videoFrame, this.drawer, this.drawMatrix, 0, 0, this.eglBase.surfaceWidth(), this.eglBase.surfaceHeight());
                        long jNanoTime3 = System.nanoTime();
                        this.eglBase.swapBuffers();
                        long jNanoTime4 = System.nanoTime();
                        synchronized (this.statisticsLock) {
                            this.framesRendered++;
                            this.renderTimeNs += jNanoTime4 - jNanoTime2;
                            this.renderSwapBufferTimeNs += jNanoTime4 - jNanoTime3;
                        }
                        notifyCallbacks(videoFrame, z2);
                    } catch (GlUtil.GlOutOfMemoryException e2) {
                        logE("Error while drawing frame", e2);
                        c.h.a(TAG, "Error while drawing frame error: " + e2);
                        ErrorCallback errorCallback = this.errorCallback;
                        if (errorCallback != null) {
                            errorCallback.onGlOutOfMemory();
                        }
                        this.drawer.release();
                        this.frameDrawer.release();
                        this.bitmapTextureFramebuffer.release();
                    }
                } else {
                    notifyCallbacks(videoFrame, z2);
                }
            } finally {
                videoFrame.release();
            }
        }
    }

    private void resetStatistics(long j2) {
        synchronized (this.statisticsLock) {
            this.statisticsStartTimeNs = j2;
            this.framesReceived = 0;
            this.framesDropped = 0;
            this.framesRendered = 0;
            this.renderTimeNs = 0L;
            this.renderSwapBufferTimeNs = 0L;
        }
    }

    public void addFrameListener(FrameListener frameListener, float f2) {
        addFrameListener(frameListener, f2, null, false);
    }

    public void clearImage() {
        clearImage(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void createEglSurface(Surface surface) {
        createEglSurfaceInternal(surface);
    }

    public void disableFpsReduction() {
        setFpsReduction(Float.POSITIVE_INFINITY);
    }

    public void init(final EglBase.Context context, final int[] iArr, RendererCommon.GlDrawer glDrawer, RendererCommon.RendererEvents rendererEvents) {
        synchronized (this.handlerLock) {
            this.rendererEvents = rendererEvents;
            if (this.renderThreadHandler == null) {
                logD("Initializing EglRenderer");
                c.h.a(TAG, this.name + "Initializing EglRenderer");
                this.drawer = glDrawer;
                HandlerThread handlerThread = new HandlerThread(this.name + TAG);
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                this.renderThreadHandler = handler;
                ThreadUtils.invokeAtFrontUninterruptibly(handler, new Runnable() { // from class: org.wrtca.api.i
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28116c.lambda$init$0(context, iArr);
                    }
                });
                this.renderThreadHandler.post(this.eglSurfaceCreationRunnable);
                resetStatistics(System.nanoTime());
                this.renderThreadHandler.postDelayed(this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4L));
            } else {
                c.h.a(TAG, this.name + "Already initialized");
            }
        }
    }

    public void isInitEgl(final RequestEglInit requestEglInit) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler != null) {
                postToRenderThread(new Runnable() { // from class: org.wrtca.api.EglRenderer.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EglRenderer.this.eglBase == null || !EglRenderer.this.eglBase.hasSurface()) {
                            requestEglInit.onRequestEglState(true, false);
                            c.h.a(EglRenderer.TAG, "request true false");
                        } else {
                            requestEglInit.onRequestEglState(true, true);
                            c.h.a(EglRenderer.TAG, "request true true");
                        }
                    }
                });
            } else if (requestEglInit != null) {
                c.h.a(TAG, "request false false");
                requestEglInit.onRequestEglState(false, false);
            }
        }
    }

    @Override // org.wrtca.api.VideoSink
    public void onFrame(VideoFrame videoFrame) {
        boolean z2;
        synchronized (this.statisticsLock) {
            this.framesReceived++;
        }
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                logD("Dropping frame - Not initialized or already released.");
                return;
            }
            synchronized (this.frameLock) {
                VideoFrame videoFrame2 = this.pendingFrame;
                z2 = videoFrame2 != null;
                if (z2) {
                    videoFrame2.release();
                }
                this.pendingFrame = videoFrame;
                videoFrame.retain();
                this.renderThreadHandler.post(new Runnable() { // from class: org.wrtca.api.b
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28096c.renderFrameOnRenderThread();
                    }
                });
            }
            if (z2) {
                synchronized (this.statisticsLock) {
                    this.framesDropped++;
                }
            }
        }
    }

    public void pauseVideo() {
        setFpsReduction(0.0f);
    }

    public void printStackTrace() {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            Thread thread = handler == null ? null : handler.getLooper().getThread();
            if (thread != null) {
                StackTraceElement[] stackTrace = thread.getStackTrace();
                if (stackTrace.length > 0) {
                    logD("EglRenderer stack trace:");
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        logD(stackTraceElement.toString());
                    }
                }
            }
        }
    }

    public void release() {
        logD(" Releasing.");
        c.h.a(TAG, "EglRenderer Releasing");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                logD("Already released");
                return;
            }
            handler.removeCallbacks(this.logStatisticsRunnable);
            this.renderThreadHandler.postAtFrontOfQueue(new Runnable() { // from class: org.wrtca.api.d
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28102c.lambda$release$1(countDownLatch);
                }
            });
            final Looper looper = this.renderThreadHandler.getLooper();
            this.renderThreadHandler.post(new Runnable() { // from class: org.wrtca.api.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28104c.lambda$release$2(looper);
                }
            });
            c.h.a(TAG, "EglRendererfree renderThreadHandler");
            this.renderThreadHandler = null;
            this.rendererEvents = null;
            ThreadUtils.awaitUninterruptibly(countDownLatch);
            synchronized (this.frameLock) {
                VideoFrame videoFrame = this.pendingFrame;
                if (videoFrame != null) {
                    videoFrame.release();
                    this.pendingFrame = null;
                }
            }
            logD("Releasing done.");
        }
    }

    public void releaseEglSurface(final Runnable runnable) {
        this.eglSurfaceCreationRunnable.setSurface(null);
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                runnable.run();
            } else {
                handler.removeCallbacks(this.eglSurfaceCreationRunnable);
                this.renderThreadHandler.postAtFrontOfQueue(new Runnable() { // from class: org.wrtca.api.f
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28106c.lambda$releaseEglSurface$5(runnable);
                    }
                });
            }
        }
    }

    public void removeFrameListener(final FrameListener frameListener) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                return;
            }
            if (Thread.currentThread() == this.renderThreadHandler.getLooper().getThread()) {
                throw new RuntimeException("removeFrameListener must not be called on the render thread.");
            }
            postToRenderThread(new Runnable() { // from class: org.wrtca.api.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28108c.lambda$removeFrameListener$4(countDownLatch, frameListener);
                }
            });
            ThreadUtils.awaitUninterruptibly(countDownLatch);
        }
    }

    @Override // org.wrtca.api.VideoRenderer.Callbacks
    public void renderFrame(VideoRenderer.I420Frame i420Frame) {
        VideoFrame videoFrame = i420Frame.toVideoFrame();
        onFrame(videoFrame);
        videoFrame.release();
    }

    public void setErrorCallback(ErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
    }

    public void setFpsReduction(float f2) {
        logD("setFpsReduction: " + f2);
        synchronized (this.fpsReductionLock) {
            long j2 = this.minRenderPeriodNs;
            if (f2 <= 0.0f) {
                this.minRenderPeriodNs = Long.MAX_VALUE;
            } else {
                this.minRenderPeriodNs = (long) (TimeUnit.SECONDS.toNanos(1L) / f2);
            }
            if (this.minRenderPeriodNs != j2) {
                this.nextFrameTimeNs = System.nanoTime();
            }
        }
    }

    public void setLayoutAspectRatio(float f2) {
        logD("setLayoutAspectRatio: " + f2);
        synchronized (this.layoutLock) {
            this.layoutAspectRatio = f2;
        }
    }

    public void setMirror(boolean z2) {
        logD("setMirror: " + z2);
        synchronized (this.layoutLock) {
            this.mirror = z2;
        }
    }

    public void setMirrorOnlyRemote(boolean z2) {
        logD("setMirror: " + z2);
        synchronized (this.layoutLock) {
            this.mirrorOnlyRemote = z2;
            d.b.d(z2);
        }
    }

    public void addFrameListener(FrameListener frameListener, float f2, RendererCommon.GlDrawer glDrawer) {
        addFrameListener(frameListener, f2, glDrawer, false);
    }

    public void clearImage(final float f2, final float f3, final float f4, final float f5) {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                return;
            }
            handler.postAtFrontOfQueue(new Runnable() { // from class: org.wrtca.api.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28111c.lambda$clearImage$6(f2, f3, f4, f5);
                }
            });
        }
    }

    public void createEglSurface(SurfaceTexture surfaceTexture) {
        createEglSurfaceInternal(surfaceTexture);
    }

    public void addFrameListener(final FrameListener frameListener, final float f2, final RendererCommon.GlDrawer glDrawer, final boolean z2) {
        postToRenderThread(new Runnable() { // from class: org.wrtca.api.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f28097c.lambda$addFrameListener$3(glDrawer, frameListener, f2, z2);
            }
        });
    }
}
