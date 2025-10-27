package org.wrtca.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.view.Surface;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoCapturer;
import org.wrtca.util.ThreadUtils;

@TargetApi(21)
/* loaded from: classes9.dex */
public class ScreenCapturerAndroid implements VideoCapturer, SurfaceTextureHelper.OnTextureFrameAvailableListener {
    private static final int DISPLAY_FLAGS = 3;
    private static final String TAG = "ScreenCapturerAndroid";
    private static int VIRTUAL_DISPLAY_DPI = 400;
    private static ScreenCapturerAndroid instance;
    private VideoCapturer.CapturerObserver capturerObserver;
    private int height;
    private MediaProjection mediaProjection;
    private final MediaProjection.Callback mediaProjectionCallback;
    private MediaProjectionManager mediaProjectionManager;
    private final Intent mediaProjectionPermissionResultData;
    private SurfaceTextureHelper surfaceTextureHelper;
    private VirtualDisplay virtualDisplay;
    private int width;
    private long numCapturedFrames = 0;
    private boolean isDisposed = false;

    public ScreenCapturerAndroid(Intent intent, MediaProjection.Callback callback) {
        this.mediaProjectionPermissionResultData = intent;
        this.mediaProjectionCallback = callback;
    }

    public static void DeleteInstance() {
        if (instance != null) {
            c.h.a(TAG, "getInstance() is not null, deleting!");
            instance = null;
        }
    }

    private void checkNotDisposed() {
        if (this.isDisposed) {
            throw new RuntimeException("capturer is disposed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createVirtualDisplay() {
        c.h.a(TAG, "createVirtualDisplay width: " + this.width + " height: " + this.height + " dpi: " + VIRTUAL_DISPLAY_DPI);
        this.surfaceTextureHelper.getSurfaceTexture().setDefaultBufferSize(this.width, this.height);
        StringBuilder sb = new StringBuilder();
        sb.append("screen surfaceTexture ");
        sb.append(this.surfaceTextureHelper.getSurfaceTexture());
        c.h.a(TAG, sb.toString());
        this.virtualDisplay = this.mediaProjection.createVirtualDisplay("WebRTC_ScreenCapture", this.width, this.height, VIRTUAL_DISPLAY_DPI, 3, new Surface(this.surfaceTextureHelper.getSurfaceTexture()), null, null);
    }

    public static ScreenCapturerAndroid getInstance(Intent intent, MediaProjection.Callback callback) {
        if (instance == null) {
            c.h.a(TAG, "getInstance() is null!");
            instance = new ScreenCapturerAndroid(intent, callback);
        }
        return instance;
    }

    @Override // org.wrtca.api.VideoCapturer
    public synchronized void changeCaptureFormat(int i2, int i3, int i4) {
        checkNotDisposed();
        this.width = i2;
        this.height = i3;
        c.h.a(TAG, "changeCaptureFormat width: " + i2 + " height: " + i3);
        if (this.virtualDisplay == null) {
            return;
        }
        ThreadUtils.invokeAtFrontUninterruptibly(this.surfaceTextureHelper.getHandler(), new Runnable() { // from class: org.wrtca.api.ScreenCapturerAndroid.2
            @Override // java.lang.Runnable
            public void run() {
                ScreenCapturerAndroid.this.virtualDisplay.release();
                ScreenCapturerAndroid.this.createVirtualDisplay();
            }
        });
    }

    @Override // org.wrtca.api.VideoCapturer
    public synchronized void dispose() {
        this.isDisposed = true;
    }

    public long getNumCapturedFrames() {
        return this.numCapturedFrames;
    }

    @Override // org.wrtca.api.VideoCapturer
    public synchronized void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, VideoCapturer.CapturerObserver capturerObserver) {
        checkNotDisposed();
        if (capturerObserver == null) {
            throw new RuntimeException("capturerObserver not set.");
        }
        this.capturerObserver = capturerObserver;
        if (surfaceTextureHelper == null) {
            throw new RuntimeException("surfaceTextureHelper not set.");
        }
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.mediaProjectionManager = (MediaProjectionManager) context.getSystemService("media_projection");
        float f2 = context.getResources().getDisplayMetrics().density;
        c.h.a(TAG, "get density " + f2);
        VIRTUAL_DISPLAY_DPI = ((int) f2) * 160;
        c.h.a(TAG, "SET DPI " + VIRTUAL_DISPLAY_DPI);
    }

    @Override // org.wrtca.api.VideoCapturer
    public boolean isScreencast() {
        return true;
    }

    @Override // org.wrtca.api.SurfaceTextureHelper.OnTextureFrameAvailableListener
    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
        this.numCapturedFrames++;
        VideoFrame videoFrame = new VideoFrame(this.surfaceTextureHelper.createTextureBuffer(this.width, this.height, RendererCommon.convertMatrixToAndroidGraphicsMatrix(fArr)), 0, j2);
        this.capturerObserver.onFrameCaptured(videoFrame);
        videoFrame.release();
    }

    @Override // org.wrtca.api.VideoCapturer
    public synchronized void startCapture(int i2, int i3, int i4) {
        checkNotDisposed();
        c.h.a(TAG, "startCapture width: " + i2 + " height: " + i3);
        this.width = i2;
        this.height = i3;
        MediaProjection mediaProjection = this.mediaProjectionManager.getMediaProjection(-1, this.mediaProjectionPermissionResultData);
        this.mediaProjection = mediaProjection;
        mediaProjection.registerCallback(this.mediaProjectionCallback, this.surfaceTextureHelper.getHandler());
        createVirtualDisplay();
        this.capturerObserver.onCapturerStarted(true);
        this.surfaceTextureHelper.startListening(this);
    }

    @Override // org.wrtca.api.VideoCapturer
    public synchronized void stopCapture() {
        checkNotDisposed();
        ThreadUtils.invokeAtFrontUninterruptibly(this.surfaceTextureHelper.getHandler(), new Runnable() { // from class: org.wrtca.api.ScreenCapturerAndroid.1
            @Override // java.lang.Runnable
            public void run() {
                c.h.a(ScreenCapturerAndroid.TAG, "stopCapture");
                ScreenCapturerAndroid.this.surfaceTextureHelper.stopListening();
                ScreenCapturerAndroid.this.capturerObserver.onCapturerStopped();
                if (ScreenCapturerAndroid.this.virtualDisplay != null) {
                    ScreenCapturerAndroid.this.virtualDisplay.release();
                    ScreenCapturerAndroid.this.virtualDisplay = null;
                }
                if (ScreenCapturerAndroid.this.mediaProjection != null) {
                    ScreenCapturerAndroid.this.mediaProjection.unregisterCallback(ScreenCapturerAndroid.this.mediaProjectionCallback);
                    ScreenCapturerAndroid.this.mediaProjection.stop();
                    ScreenCapturerAndroid.this.mediaProjection = null;
                }
            }
        });
    }

    public synchronized void startCapture(int i2, int i3, int i4, MediaProjection mediaProjection) {
        checkNotDisposed();
        c.h.a(TAG, "startCapture width: " + i2 + " height: " + i3);
        this.width = i2;
        this.height = i3;
        this.mediaProjection = mediaProjection;
        mediaProjection.registerCallback(this.mediaProjectionCallback, this.surfaceTextureHelper.getHandler());
        createVirtualDisplay();
        this.capturerObserver.onCapturerStarted(true);
        this.surfaceTextureHelper.startListening(this);
    }
}
