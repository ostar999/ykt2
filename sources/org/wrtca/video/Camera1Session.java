package org.wrtca.video;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import c.h;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.DebugKt;
import org.wrtca.api.Camera1Enumerator;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoFrame;
import org.wrtca.log.Logging;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.util.Size;
import org.wrtca.video.CameraSession;

/* loaded from: classes9.dex */
public class Camera1Session implements CameraSession {
    private static final int NUMBER_OF_CAPTURE_BUFFERS = 3;
    private static final String TAG = "Camera1Session";
    public static CameraParamObserver mParamObserver;
    private final Context applicationContext;
    private final Camera camera;
    private final int cameraId;
    private final Handler cameraThreadHandler;
    private final CameraEnumerationAndroid.CaptureFormat captureFormat;
    private final boolean captureToTexture;
    private final long constructionTimeNs;
    private final CameraSession.Events events;
    private boolean firstFrameReported = false;
    private final Camera.CameraInfo info;
    private SessionState state;
    private final SurfaceTextureHelper surfaceTextureHelper;
    private static final Histogram camera1StartTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera1.StartTimeMs", 1, 10000, 50);
    private static final Histogram camera1StopTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera1.StopTimeMs", 1, 10000, 50);
    private static final Histogram camera1ResolutionHistogram = Histogram.createEnumeration("WebRTC.Android.Camera1.Resolution", CameraEnumerationAndroid.COMMON_RESOLUTIONS.size());

    /* renamed from: org.wrtca.video.Camera1Session$3, reason: invalid class name */
    public class AnonymousClass3 implements Camera.PreviewCallback {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreviewFrame$0(byte[] bArr) {
            if (Camera1Session.this.state == SessionState.RUNNING) {
                Camera1Session.this.camera.addCallbackBuffer(bArr);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreviewFrame$1(final byte[] bArr) {
            Camera1Session.this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28138c.lambda$onPreviewFrame$0(bArr);
                }
            });
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(final byte[] bArr, Camera camera) {
            VideoFrame videoFrame;
            Camera1Session.this.checkIsOnCameraThread();
            if (camera != Camera1Session.this.camera) {
                Logging.e(Camera1Session.TAG, "Callback from a different camera. This should never happen.");
                return;
            }
            if (Camera1Session.this.state != SessionState.RUNNING) {
                Logging.d(Camera1Session.TAG, "Bytebuffer frame captured but camera is no longer running.");
                return;
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
            if (!Camera1Session.this.firstFrameReported) {
                Camera1Session.camera1StartTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - Camera1Session.this.constructionTimeNs));
                Camera1Session.this.firstFrameReported = true;
            }
            NV21Buffer nV21Buffer = new NV21Buffer(bArr, Camera1Session.this.captureFormat.width, Camera1Session.this.captureFormat.height, new Runnable() { // from class: org.wrtca.video.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28140c.lambda$onPreviewFrame$1(bArr);
                }
            });
            boolean z2 = false;
            if ((Camera1Session.this.info.facing == 1) && (d.b.s() || d.b.t())) {
                z2 = true;
            }
            int iAbs = Math.abs(Camera1Session.this.getFrameOrientation() - 180);
            if (90 == Camera1Session.this.getDeviceOrientation() || 270 == Camera1Session.this.getDeviceOrientation() || d.b.i() == 1) {
                videoFrame = new VideoFrame(nV21Buffer, Camera1Session.this.getFrameOrientation(), nanos);
            } else {
                if (!z2) {
                    iAbs = Camera1Session.this.getFrameOrientation();
                }
                videoFrame = new VideoFrame(nV21Buffer, iAbs, nanos);
            }
            Camera1Session.this.events.onFrameCaptured(Camera1Session.this, videoFrame);
            videoFrame.release();
        }
    }

    public enum SessionState {
        RUNNING,
        STOPPED
    }

    private Camera1Session(CameraSession.Events events, boolean z2, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, int i2, Camera camera, Camera.CameraInfo cameraInfo, CameraEnumerationAndroid.CaptureFormat captureFormat, long j2) {
        Logging.d(TAG, "Create new camera1 session on camera " + i2);
        this.cameraThreadHandler = new Handler();
        this.events = events;
        this.captureToTexture = z2;
        this.applicationContext = context;
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.cameraId = i2;
        this.camera = camera;
        this.info = cameraInfo;
        this.captureFormat = captureFormat;
        this.constructionTimeNs = j2;
        startCapturing();
        if (mediaRecorder != null) {
            camera.unlock();
            mediaRecorder.setCamera(camera);
        }
    }

    private int adapterUserSelect(int i2) {
        int i3 = d.b.i();
        if (i3 == 1) {
            if (i2 == 0 || i2 == 180) {
                return 90;
            }
            return i2;
        }
        if (i3 != 2) {
            return i2;
        }
        if (i2 == 90 || i2 == 270) {
            return 0;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIsOnCameraThread() {
        if (Thread.currentThread() != this.cameraThreadHandler.getLooper().getThread()) {
            throw new IllegalStateException("Wrong thread");
        }
    }

    public static void create(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, boolean z2, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, int i2, int i3, int i4, int i5, CameraParamObserver cameraParamObserver) throws IOException {
        mParamObserver = cameraParamObserver;
        long jNanoTime = System.nanoTime();
        Logging.d(TAG, "Open camera " + i2);
        events.onCameraOpening();
        try {
            Camera cameraOpen = Camera.open(i2);
            if (cameraOpen == null) {
                createSessionCallback.onFailure(CameraSession.FailureType.ERROR, -1, "android.hardware.Camera.open returned null for camera id = " + i2);
                return;
            }
            try {
                cameraOpen.setPreviewTexture(surfaceTextureHelper.getSurfaceTexture());
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i2, cameraInfo);
                Camera.Parameters parameters = cameraOpen.getParameters();
                CameraEnumerationAndroid.CaptureFormat captureFormatFindClosestCaptureFormat = findClosestCaptureFormat(parameters, i3, i4, i5);
                updateCameraParameters(cameraOpen, parameters, captureFormatFindClosestCaptureFormat, findClosestPictureSize(parameters, i3, i4), z2);
                if (!z2) {
                    int iFrameSize = captureFormatFindClosestCaptureFormat.frameSize();
                    for (int i6 = 0; i6 < 3; i6++) {
                        cameraOpen.addCallbackBuffer(ByteBuffer.allocateDirect(iFrameSize).array());
                    }
                }
                cameraOpen.setDisplayOrientation(0);
                createSessionCallback.onDone(new Camera1Session(events, z2, context, surfaceTextureHelper, mediaRecorder, i2, cameraOpen, cameraInfo, captureFormatFindClosestCaptureFormat, jNanoTime));
            } catch (IOException | RuntimeException e2) {
                cameraOpen.release();
                createSessionCallback.onFailure(CameraSession.FailureType.ERROR, -1, e2.getMessage());
            }
        } catch (RuntimeException e3) {
            createSessionCallback.onFailure(CameraSession.FailureType.ERROR, -1, e3.getMessage());
        }
    }

    private static CameraEnumerationAndroid.CaptureFormat findClosestCaptureFormat(Camera.Parameters parameters, int i2, int i3, int i4) {
        List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> listConvertFramerates = Camera1Enumerator.convertFramerates(parameters.getSupportedPreviewFpsRange());
        Logging.d(TAG, "Available fps ranges: " + listConvertFramerates);
        CameraEnumerationAndroid.CaptureFormat.FramerateRange closestSupportedFramerateRange = CameraEnumerationAndroid.getClosestSupportedFramerateRange(listConvertFramerates, i4);
        Size closestSupportedSize = CameraEnumerationAndroid.getClosestSupportedSize(Camera1Enumerator.convertSizes(parameters.getSupportedPreviewSizes()), i2, i3);
        CameraEnumerationAndroid.reportCameraResolution(camera1ResolutionHistogram, closestSupportedSize);
        return new CameraEnumerationAndroid.CaptureFormat(closestSupportedSize.width, closestSupportedSize.height, closestSupportedFramerateRange);
    }

    private static Size findClosestPictureSize(Camera.Parameters parameters, int i2, int i3) {
        return CameraEnumerationAndroid.getClosestSupportedSize(Camera1Enumerator.convertSizes(parameters.getSupportedPictureSizes()), i2, i3);
    }

    private void listenForBytebufferFrames() {
        this.camera.setPreviewCallbackWithBuffer(new AnonymousClass3());
    }

    private void listenForTextureFrames() {
        this.surfaceTextureHelper.startListening(new SurfaceTextureHelper.OnTextureFrameAvailableListener() { // from class: org.wrtca.video.Camera1Session.2
            @Override // org.wrtca.api.SurfaceTextureHelper.OnTextureFrameAvailableListener
            public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
                Camera1Session.this.checkIsOnCameraThread();
                if (Camera1Session.this.state != SessionState.RUNNING) {
                    Logging.d(Camera1Session.TAG, "Texture frame captured but camera is no longer running.");
                    Camera1Session.this.surfaceTextureHelper.returnTextureFrame();
                    return;
                }
                if (!Camera1Session.this.firstFrameReported) {
                    Camera1Session.camera1StartTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - Camera1Session.this.constructionTimeNs));
                    Camera1Session.this.firstFrameReported = true;
                }
                int frameOrientation = Camera1Session.this.getFrameOrientation();
                if (Camera1Session.this.info.facing == 1) {
                    if (!d.b.s() && !d.b.t()) {
                        fArr = RendererCommon.multiplyMatrices(fArr, RendererCommon.horizontalFlipMatrix());
                    } else if (90 != Camera1Session.this.getDeviceOrientation()) {
                        frameOrientation = Camera1Session.this.getFrameOrientation() - 180;
                    }
                }
                VideoFrame videoFrame = new VideoFrame(Camera1Session.this.surfaceTextureHelper.createTextureBuffer(Camera1Session.this.captureFormat.width, Camera1Session.this.captureFormat.height, RendererCommon.convertMatrixToAndroidGraphicsMatrix(fArr)), frameOrientation, j2);
                Camera1Session.this.events.onFrameCaptured(Camera1Session.this, videoFrame);
                videoFrame.release();
            }
        });
    }

    private void startCapturing() {
        Logging.d(TAG, "Start capturing");
        checkIsOnCameraThread();
        this.state = SessionState.RUNNING;
        this.camera.getParameters().getPictureSize();
        this.camera.setErrorCallback(new Camera.ErrorCallback() { // from class: org.wrtca.video.Camera1Session.1
            @Override // android.hardware.Camera.ErrorCallback
            public void onError(int i2, Camera camera) {
                String str;
                if (i2 == 100) {
                    str = "Camera server died!";
                } else {
                    str = "Camera error: " + i2;
                }
                Logging.e(Camera1Session.TAG, str);
                Camera1Session.this.stopInternal();
                if (i2 == 2) {
                    Camera1Session.this.events.onCameraDisconnected(Camera1Session.this);
                } else {
                    Camera1Session.this.events.onCameraError(Camera1Session.this, str);
                }
            }
        });
        if (this.captureToTexture) {
            listenForTextureFrames();
        } else {
            listenForBytebufferFrames();
        }
        try {
            this.camera.startPreview();
            Camera.Size pictureSize = this.camera.getParameters().getPictureSize();
            this.camera.getParameters().getPreviewFrameRate();
            mParamObserver.reportCameraOpenParam(pictureSize.width, pictureSize.height, getDeviceOrientation(), this.camera.getParameters().getPreviewFrameRate());
        } catch (RuntimeException e2) {
            stopInternal();
            this.events.onCameraError(this, e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopInternal() {
        Logging.d(TAG, "Stop internal");
        checkIsOnCameraThread();
        SessionState sessionState = this.state;
        SessionState sessionState2 = SessionState.STOPPED;
        if (sessionState == sessionState2) {
            Logging.d(TAG, "Camera is already stopped");
            return;
        }
        this.state = sessionState2;
        this.surfaceTextureHelper.stopListening();
        this.camera.stopPreview();
        this.camera.release();
        this.events.onCameraClosed(this);
        Logging.d(TAG, "Stop done");
    }

    private static void updateCameraParameters(Camera camera, Camera.Parameters parameters, CameraEnumerationAndroid.CaptureFormat captureFormat, Size size, boolean z2) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        CameraEnumerationAndroid.CaptureFormat.FramerateRange framerateRange = captureFormat.framerate;
        parameters.setPreviewFpsRange(framerateRange.min, framerateRange.max);
        parameters.setPreviewSize(captureFormat.width, captureFormat.height);
        parameters.setPictureSize(size.width, size.height);
        if (!z2) {
            parameters.setPreviewFormat(17);
        }
        if (parameters.isVideoStabilizationSupported()) {
            parameters.setVideoStabilization(true);
        }
        if (supportedFocusModes.contains("continuous-video")) {
            parameters.setFocusMode("continuous-video");
        }
        camera.setParameters(parameters);
    }

    @Override // org.wrtca.video.CameraSession
    public int getDeviceOrientation() {
        int rotation = ((WindowManager) this.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    @Override // org.wrtca.video.CameraSession
    public int getFrameOrientation() {
        int iAdapterUserSelect = adapterUserSelect(getDeviceOrientation());
        Camera.CameraInfo cameraInfo = this.info;
        if (cameraInfo.facing == 0) {
            iAdapterUserSelect = 360 - iAdapterUserSelect;
        }
        return (cameraInfo.orientation + iAdapterUserSelect) % 360;
    }

    public Camera.CameraInfo getInfo() {
        return this.info;
    }

    @Override // org.wrtca.video.CameraSession
    public SurfaceTextureHelper getSurfaceTextureHelper() {
        return this.surfaceTextureHelper;
    }

    @Override // org.wrtca.video.CameraSession
    public CameraSession.CameraParam requestCamera() {
        CameraSession.CameraParam cameraParam = new CameraSession.CameraParam();
        Camera.CameraInfo cameraInfo = this.info;
        boolean z2 = true;
        if (cameraInfo != null && cameraInfo.facing != 1) {
            z2 = false;
        }
        cameraParam.setFrontCamera(z2);
        cameraParam.setCameraOrientation(this.info.orientation);
        return cameraParam;
    }

    @Override // org.wrtca.video.CameraSession
    public void setFlashLight(boolean z2) {
        h.a(TAG, "Camera1 setFlashLight " + z2);
        checkIsOnCameraThread();
        Camera.Parameters parameters = this.camera.getParameters();
        if (z2) {
            parameters.setFlashMode("torch");
        } else {
            parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        }
        this.camera.setParameters(parameters);
    }

    @Override // org.wrtca.video.CameraSession
    public void stop() {
        Logging.d(TAG, "Stop camera1 session on camera " + this.cameraId);
        checkIsOnCameraThread();
        if (this.state != SessionState.STOPPED) {
            long jNanoTime = System.nanoTime();
            stopInternal();
            camera1StopTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime));
        }
    }
}
