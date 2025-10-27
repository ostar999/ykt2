package io.agora.rtc.gl;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.gl.RendererCommon;
import io.agora.rtc.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public class EglRenderer {
    private static final long LOG_INTERVAL_SEC = 4;
    private static final int MAX_SURFACE_CLEAR_COUNT = 3;
    private static final String TAG = "EglRenderer";
    private RendererCommon.GlDrawer drawer;
    private EglBase eglBase;
    private int framesDropped;
    private int framesReceived;
    private int framesRendered;
    private float layoutAspectRatio;
    private long minRenderPeriodNs;
    private boolean mirror;
    private final String name;
    private long nextFrameTimeNs;
    private VideoFrame pendingFrame;
    private long renderSwapBufferTimeNs;
    private Handler renderThreadHandler;
    private long renderTimeNs;
    private long statisticsStartTimeNs;
    private final Object handlerLock = new Object();
    private final ArrayList<FrameListenerAndParams> frameListeners = new ArrayList<>();
    private final Object fpsReductionLock = new Object();
    private final VideoFrameDrawer frameDrawer = new VideoFrameDrawer();
    private final Matrix drawMatrix = new Matrix();
    private final Object frameLock = new Object();
    private final Object layoutLock = new Object();
    private final Object statisticsLock = new Object();
    private final Runnable logStatisticsRunnable = new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.1
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
            if (this.surface != null && EglRenderer.this.eglBase != null && !EglRenderer.this.eglBase.hasSurface()) {
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
            }
        }

        public synchronized void setSurface(Object surface) {
            this.surface = surface;
        }
    }

    public interface FrameListener {
        void onFrame(Bitmap frame);
    }

    public static class FrameListenerAndParams {
        public final boolean applyFpsReduction;
        public final RendererCommon.GlDrawer drawer;
        public final FrameListener listener;
        public final float scale;

        public FrameListenerAndParams(FrameListener listener, float scale, RendererCommon.GlDrawer drawer, boolean applyFpsReduction) {
            this.listener = listener;
            this.scale = scale;
            this.drawer = drawer;
            this.applyFpsReduction = applyFpsReduction;
        }
    }

    public EglRenderer(String name) {
        this.name = name;
    }

    private String averageTimeAsString(long sumTimeNs, int count) {
        if (count <= 0) {
            return "NA";
        }
        return TimeUnit.NANOSECONDS.toMicros(sumTimeNs / count) + " μs";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSurfaceOnRenderThread(float r2, float g2, float b3, float a3) {
        EglBase eglBase = this.eglBase;
        if (eglBase == null || !eglBase.hasSurface()) {
            return;
        }
        logD("clearSurface");
        GLES20.glClearColor(r2, g2, b3, a3);
        GLES20.glClear(16384);
        this.eglBase.swapBuffers();
    }

    private void createEglSurfaceInternal(Object surface) {
        this.eglSurfaceCreationRunnable.setSurface(surface);
        postToRenderThread(this.eglSurfaceCreationRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logD(String string) {
        Log.d(TAG, this.name + string);
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
        synchronized (this.frameLock) {
            VideoFrame videoFrame = this.pendingFrame;
            if (videoFrame == null) {
                return;
            }
            this.pendingFrame = null;
            EglBase eglBase = this.eglBase;
            if (eglBase == null || !eglBase.hasSurface()) {
                logD("Dropping frame - No surface");
                videoFrame.release();
                return;
            }
            synchronized (this.fpsReductionLock) {
                long j2 = this.minRenderPeriodNs;
                z2 = false;
                if (j2 != Long.MAX_VALUE) {
                    if (j2 <= 0) {
                        z2 = true;
                    } else {
                        long jNanoTime = System.nanoTime();
                        long j3 = this.nextFrameTimeNs;
                        if (jNanoTime < j3) {
                            logD("Skipping frame rendering - fps reduction is active.");
                        } else {
                            long j4 = j3 + this.minRenderPeriodNs;
                            this.nextFrameTimeNs = j4;
                            this.nextFrameTimeNs = Math.max(j4, jNanoTime);
                            z2 = true;
                        }
                    }
                }
            }
            long jNanoTime2 = System.nanoTime();
            float rotatedWidth = videoFrame.getRotatedWidth() / videoFrame.getRotatedHeight();
            synchronized (this.layoutLock) {
                f2 = this.layoutAspectRatio;
                if (f2 == 0.0f) {
                    f2 = rotatedWidth;
                }
            }
            if (rotatedWidth > f2) {
                f4 = f2 / rotatedWidth;
                f3 = 1.0f;
            } else {
                f3 = rotatedWidth / f2;
                f4 = 1.0f;
            }
            this.drawMatrix.reset();
            this.drawMatrix.preTranslate(0.5f, 0.5f);
            if (this.mirror) {
                this.drawMatrix.preScale(-1.0f, 1.0f);
            }
            this.drawMatrix.preScale(f4, f3);
            this.drawMatrix.preTranslate(-0.5f, -0.5f);
            if (z2) {
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
            }
            videoFrame.release();
        }
    }

    private void resetStatistics(long currentTimeNs) {
        synchronized (this.statisticsLock) {
            this.statisticsStartTimeNs = currentTimeNs;
            this.framesReceived = 0;
            this.framesDropped = 0;
            this.framesRendered = 0;
            this.renderTimeNs = 0L;
            this.renderSwapBufferTimeNs = 0L;
        }
    }

    public void addFrameListener(final FrameListener listener, final float scale) {
        addFrameListener(listener, scale, null, false);
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

    public EglBase.Context getEglContext() {
        return this.eglBase.getEglBaseContext();
    }

    public void init(final EglBase.Context sharedContext, final int[] configAttributes, RendererCommon.GlDrawer drawer) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler != null) {
                throw new IllegalStateException(this.name + "Already initialized");
            }
            logD("Initializing EglRenderer");
            this.drawer = drawer;
            HandlerThread handlerThread = new HandlerThread(this.name + TAG);
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            this.renderThreadHandler = handler;
            ThreadUtils.invokeAtFrontUninterruptibly(handler, new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.2
                @Override // java.lang.Runnable
                public void run() {
                    if (sharedContext == null) {
                        EglRenderer.this.logD("EglBase.create context");
                        EglRenderer.this.eglBase = EglBase.create(sharedContext, configAttributes);
                    } else {
                        EglRenderer.this.logD("EglBase.create shared context");
                        EglRenderer.this.eglBase = EglBase.create(sharedContext, configAttributes);
                    }
                }
            });
            this.renderThreadHandler.post(this.eglSurfaceCreationRunnable);
            resetStatistics(System.nanoTime());
        }
    }

    public void onFrame(VideoFrame frame) {
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
                VideoFrame videoFrame = this.pendingFrame;
                z2 = videoFrame != null;
                if (z2) {
                    videoFrame.release();
                }
                this.pendingFrame = frame;
                frame.retain();
            }
            ThreadUtils.invokeAtFrontUninterruptibly(this.renderThreadHandler, new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.7
                @Override // java.lang.Runnable
                public void run() {
                    EglRenderer.this.renderFrameOnRenderThread();
                }
            });
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
        logD("Releasing.");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                logD("Already released");
                return;
            }
            handler.postAtFrontOfQueue(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.3
                @Override // java.lang.Runnable
                public void run() {
                    if (EglRenderer.this.drawer != null) {
                        EglRenderer.this.drawer.release();
                        EglRenderer.this.drawer = null;
                    }
                    EglRenderer.this.frameDrawer.release();
                    if (EglRenderer.this.eglBase != null) {
                        EglRenderer.this.logD("eglBase detach and release.");
                        EglRenderer.this.eglBase.detachCurrent();
                        EglRenderer.this.eglBase.release();
                        EglRenderer.this.eglBase = null;
                    }
                    countDownLatch.countDown();
                }
            });
            final Looper looper = this.renderThreadHandler.getLooper();
            this.renderThreadHandler.post(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.4
                @Override // java.lang.Runnable
                public void run() {
                    EglRenderer.this.logD("Quitting render thread.");
                    looper.quit();
                }
            });
            this.renderThreadHandler = null;
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

    public void releaseEglSurface(final Runnable completionCallback) {
        this.eglSurfaceCreationRunnable.setSurface(null);
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                completionCallback.run();
            } else {
                handler.removeCallbacks(this.eglSurfaceCreationRunnable);
                this.renderThreadHandler.postAtFrontOfQueue(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.8
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EglRenderer.this.eglBase != null) {
                            EglRenderer.this.eglBase.detachCurrent();
                            EglRenderer.this.eglBase.releaseSurface();
                        }
                        completionCallback.run();
                    }
                });
            }
        }
    }

    public void removeFrameListener(final FrameListener listener) {
        if (Thread.currentThread() == this.renderThreadHandler.getLooper().getThread()) {
            throw new RuntimeException("removeFrameListener must not be called on the render thread.");
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        postToRenderThread(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.6
            @Override // java.lang.Runnable
            public void run() {
                countDownLatch.countDown();
                Iterator it = EglRenderer.this.frameListeners.iterator();
                while (it.hasNext()) {
                    if (((FrameListenerAndParams) it.next()).listener == listener) {
                        it.remove();
                    }
                }
            }
        });
        ThreadUtils.awaitUninterruptibly(countDownLatch);
    }

    public void renderFrame(VideoFrame frame) {
        onFrame(frame);
    }

    public void setFpsReduction(float fps) {
        logD("setFpsReduction: " + fps);
        synchronized (this.fpsReductionLock) {
            long j2 = this.minRenderPeriodNs;
            if (fps <= 0.0f) {
                this.minRenderPeriodNs = Long.MAX_VALUE;
            } else {
                this.minRenderPeriodNs = (long) (TimeUnit.SECONDS.toNanos(1L) / fps);
            }
            if (this.minRenderPeriodNs != j2) {
                this.nextFrameTimeNs = System.nanoTime();
            }
        }
    }

    public void setLayoutAspectRatio(float layoutAspectRatio) {
        logD("setLayoutAspectRatio: " + layoutAspectRatio);
        synchronized (this.layoutLock) {
            this.layoutAspectRatio = layoutAspectRatio;
        }
    }

    public void setMirror(final boolean mirror) {
        logD("setMirror: " + mirror);
        synchronized (this.layoutLock) {
            this.mirror = mirror;
        }
    }

    public void addFrameListener(final FrameListener listener, final float scale, final RendererCommon.GlDrawer drawerParam) {
        addFrameListener(listener, scale, drawerParam, false);
    }

    public void clearImage(final float r2, final float g2, final float b3, final float a3) {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler == null) {
                return;
            }
            handler.postAtFrontOfQueue(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.9
                @Override // java.lang.Runnable
                public void run() {
                    EglRenderer.this.clearSurfaceOnRenderThread(r2, g2, b3, a3);
                }
            });
        }
    }

    public void createEglSurface(SurfaceTexture surfaceTexture) {
        createEglSurfaceInternal(surfaceTexture);
    }

    public void addFrameListener(final FrameListener listener, final float scale, final RendererCommon.GlDrawer drawerParam, final boolean applyFpsReduction) {
        postToRenderThread(new Runnable() { // from class: io.agora.rtc.gl.EglRenderer.5
            @Override // java.lang.Runnable
            public void run() {
                RendererCommon.GlDrawer glDrawer = drawerParam;
                if (glDrawer == null) {
                    glDrawer = EglRenderer.this.drawer;
                }
                EglRenderer.this.frameListeners.add(new FrameListenerAndParams(listener, scale, glDrawer, applyFpsReduction));
            }
        });
    }
}
