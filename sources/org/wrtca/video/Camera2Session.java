package org.wrtca.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Range;
import android.view.Surface;
import android.view.WindowManager;
import c.h;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.Camera2Enumerator;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoFrame;
import org.wrtca.log.Logging;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.util.Size;
import org.wrtca.video.CameraSession;

@TargetApi(21)
/* loaded from: classes9.dex */
public class Camera2Session implements CameraSession {
    private static final String TAG = "Camera2Session";
    private final Context applicationContext;
    private final CameraSession.CreateSessionCallback callback;
    private CameraCharacteristics cameraCharacteristics;
    private CameraDevice cameraDevice;
    private final String cameraId;
    private final CameraManager cameraManager;
    private int cameraOrientation;
    private final Handler cameraThreadHandler;
    private CameraEnumerationAndroid.CaptureFormat captureFormat;
    private CaptureRequest.Builder captureRequest;
    private CameraCaptureSession captureSession;
    private final long constructionTimeNs;
    private final CameraSession.Events events;
    private int fpsUnitFactor;
    private final int framerate;
    private final int height;
    private boolean isCameraFrontFacing;
    private CameraParamObserver mRTCRecordObserver;
    private final Surface mediaRecorderSurface;
    private Surface surface;
    private final SurfaceTextureHelper surfaceTextureHelper;
    private final int width;
    private static final Histogram camera2StartTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera2.StartTimeMs", 1, 10000, 50);
    private static final Histogram camera2StopTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera2.StopTimeMs", 1, 10000, 50);
    private static final Histogram camera2ResolutionHistogram = Histogram.createEnumeration("WebRTC.Android.Camera2.Resolution", CameraEnumerationAndroid.COMMON_RESOLUTIONS.size());
    private SessionState state = SessionState.RUNNING;
    private boolean firstFrameReported = false;

    public static class CameraCaptureCallback extends CameraCaptureSession.CaptureCallback {
        private CameraCaptureCallback() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            Logging.d(Camera2Session.TAG, "Capture failed: " + captureFailure);
            h.a(Camera2Session.TAG, "Capture failed: " + captureFailure);
        }
    }

    public class CameraStateCallback extends CameraDevice.StateCallback {
        private CameraStateCallback() {
        }

        private String getErrorDescription(int i2) {
            if (i2 == 1) {
                return "Camera device is in use already.";
            }
            if (i2 == 2) {
                return "Camera device could not be opened because there are too many other open camera devices.";
            }
            if (i2 == 3) {
                return "Camera device could not be opened due to a device policy.";
            }
            if (i2 == 4) {
                return "Camera device has encountered a fatal error.";
            }
            if (i2 == 5) {
                return "Camera service has encountered a fatal error.";
            }
            return "Unknown camera error: " + i2;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(CameraDevice cameraDevice) {
            Camera2Session.this.checkIsOnCameraThread();
            Logging.d(Camera2Session.TAG, "Camera device closed.");
            h.a(Camera2Session.TAG, "Camera device closed.");
            Camera2Session.this.events.onCameraClosed(Camera2Session.this);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            Camera2Session.this.checkIsOnCameraThread();
            h.a(Camera2Session.TAG, "onDisconnected: ");
            boolean z2 = Camera2Session.this.captureSession == null && Camera2Session.this.state != SessionState.STOPPED;
            Camera2Session.this.state = SessionState.STOPPED;
            Camera2Session.this.stopInternal();
            if (z2) {
                Camera2Session.this.callback.onFailure(CameraSession.FailureType.DISCONNECTED, -2, "Camera disconnected / evicted.");
            } else {
                Camera2Session.this.events.onCameraDisconnected(Camera2Session.this);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i2) {
            Camera2Session.this.checkIsOnCameraThread();
            h.a(Camera2Session.TAG, "onError: " + getErrorDescription(i2));
            Camera2Session.this.reportError(i2, getErrorDescription(i2));
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) throws CameraAccessException {
            Camera2Session.this.checkIsOnCameraThread();
            Logging.d(Camera2Session.TAG, "Camera opened.");
            h.a(Camera2Session.TAG, "Camera opened.");
            Camera2Session.this.cameraDevice = cameraDevice;
            SurfaceTexture surfaceTexture = Camera2Session.this.surfaceTextureHelper.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(Camera2Session.this.captureFormat.width, Camera2Session.this.captureFormat.height);
            if (Camera2Session.this.mRTCRecordObserver != null) {
                h.a(Camera2Session.TAG, "Camera2SessiongetDeviceOrientation: " + Camera2Session.this.getDeviceOrientation());
                h.a(Camera2Session.TAG, "Camera2SessiongetFrameOrientation: " + Camera2Session.this.getFrameOrientation());
                Camera2Session.this.mRTCRecordObserver.reportCameraOpenParam(Camera2Session.this.captureFormat.width, Camera2Session.this.captureFormat.height, Camera2Session.this.getDeviceOrientation(), Camera2Session.this.framerate);
            }
            Camera2Session.this.surface = new Surface(surfaceTexture);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Camera2Session.this.surface);
            if (Camera2Session.this.mediaRecorderSurface != null) {
                Logging.d(Camera2Session.TAG, "Add MediaRecorder surface to capture session.");
                arrayList.add(Camera2Session.this.mediaRecorderSurface);
            }
            try {
                cameraDevice.createCaptureSession(arrayList, new CaptureSessionCallback(), Camera2Session.this.cameraThreadHandler);
            } catch (CameraAccessException e2) {
                Camera2Session.this.reportError("Failed to create capture session. " + e2);
            }
        }
    }

    public class CaptureSessionCallback extends CameraCaptureSession.StateCallback {
        private CaptureSessionCallback() {
        }

        private void chooseFocusMode(CaptureRequest.Builder builder) {
            for (int i2 : (int[]) Camera2Session.this.cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)) {
                if (i2 == 3) {
                    builder.set(CaptureRequest.CONTROL_AF_MODE, 3);
                    Logging.d(Camera2Session.TAG, "Using continuous video auto-focus.");
                    h.a(Camera2Session.TAG, "Using continuous video auto-focus.");
                    return;
                }
            }
            Logging.d(Camera2Session.TAG, "Auto-focus is not available.");
            h.a(Camera2Session.TAG, "Auto-focus is not available.");
        }

        private void chooseStabilizationMode(CaptureRequest.Builder builder) {
            int[] iArr = (int[]) Camera2Session.this.cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION);
            if (iArr != null) {
                for (int i2 : iArr) {
                    if (i2 == 1) {
                        builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, 1);
                        builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 0);
                        Logging.d(Camera2Session.TAG, "Using optical stabilization.");
                        return;
                    }
                }
            }
            for (int i3 : (int[]) Camera2Session.this.cameraCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)) {
                if (i3 == 1) {
                    builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 1);
                    builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, 0);
                    Logging.d(Camera2Session.TAG, "Using video stabilization.");
                    return;
                }
            }
            Logging.d(Camera2Session.TAG, "Stabilization not available.");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            Camera2Session.this.checkIsOnCameraThread();
            cameraCaptureSession.close();
            h.a(Camera2Session.TAG, "Failed to configure capture session.");
            Camera2Session.this.reportError("Failed to configure capture session.");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) throws CameraAccessException {
            Camera2Session.this.checkIsOnCameraThread();
            Logging.d(Camera2Session.TAG, "Camera capture session configured.");
            h.a(Camera2Session.TAG, "Camera capture session configured.");
            Camera2Session.this.captureSession = cameraCaptureSession;
            try {
                CaptureRequest.Builder builderCreateCaptureRequest = Camera2Session.this.cameraDevice.createCaptureRequest(3);
                builderCreateCaptureRequest.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, new Range(Integer.valueOf(Camera2Session.this.captureFormat.framerate.min / Camera2Session.this.fpsUnitFactor), Integer.valueOf(Camera2Session.this.captureFormat.framerate.max / Camera2Session.this.fpsUnitFactor)));
                builderCreateCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, 1);
                builderCreateCaptureRequest.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.FALSE);
                chooseStabilizationMode(builderCreateCaptureRequest);
                chooseFocusMode(builderCreateCaptureRequest);
                builderCreateCaptureRequest.addTarget(Camera2Session.this.surface);
                if (Camera2Session.this.mediaRecorderSurface != null) {
                    Logging.d(Camera2Session.TAG, "Add MediaRecorder surface to CaptureRequest.Builder");
                    builderCreateCaptureRequest.addTarget(Camera2Session.this.mediaRecorderSurface);
                }
                cameraCaptureSession.setRepeatingRequest(builderCreateCaptureRequest.build(), new CameraCaptureCallback(), Camera2Session.this.cameraThreadHandler);
                Camera2Session.this.captureRequest = builderCreateCaptureRequest;
                Camera2Session.this.surfaceTextureHelper.startListening(new SurfaceTextureHelper.OnTextureFrameAvailableListener() { // from class: org.wrtca.video.Camera2Session.CaptureSessionCallback.1
                    @Override // org.wrtca.api.SurfaceTextureHelper.OnTextureFrameAvailableListener
                    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
                        Camera2Session.this.checkIsOnCameraThread();
                        RendererCommon.convertMatrixToAndroidGraphicsMatrix(fArr);
                        RendererCommon.identityMatrix();
                        if (Camera2Session.this.state != SessionState.RUNNING) {
                            Logging.d(Camera2Session.TAG, "Texture frame captured but camera is no longer running.");
                            Camera2Session.this.surfaceTextureHelper.returnTextureFrame();
                            return;
                        }
                        if (!Camera2Session.this.firstFrameReported) {
                            Camera2Session.this.firstFrameReported = true;
                            Camera2Session.camera2StartTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - Camera2Session.this.constructionTimeNs));
                        }
                        int frameOrientation = Camera2Session.this.getFrameOrientation();
                        float[] fArrRotateTextureMatrix = Camera2Session.this.isCameraFrontFacing ? (d.b.s() || d.b.t()) ? (Camera2Session.this.getDeviceOrientation() != 0 || d.b.i() == 1) ? RendererCommon.rotateTextureMatrix(fArr, Camera2Session.this.cameraOrientation) : RendererCommon.rotateTextureMatrix(fArr, -Camera2Session.this.cameraOrientation) : RendererCommon.rotateTextureMatrix(RendererCommon.multiplyMatrices(fArr, RendererCommon.horizontalFlipMatrix()), -Camera2Session.this.cameraOrientation) : RendererCommon.rotateTextureMatrix(fArr, -Camera2Session.this.cameraOrientation);
                        VideoFrame videoFrame = new VideoFrame(Camera2Session.this.surfaceTextureHelper.createTextureBuffer(Camera2Session.this.captureFormat.width, Camera2Session.this.captureFormat.height, RendererCommon.convertMatrixToAndroidGraphicsMatrix(fArrRotateTextureMatrix)), frameOrientation, j2);
                        Camera2Session.this.events.onFrameCaptured(Camera2Session.this, videoFrame);
                        videoFrame.release();
                    }
                });
                Logging.d(Camera2Session.TAG, "Camera device successfully started.");
                h.a(Camera2Session.TAG, "Camera device successfully started.");
                Camera2Session.this.callback.onDone(Camera2Session.this);
            } catch (CameraAccessException e2) {
                Camera2Session.this.reportError("Failed to start capture request. " + e2);
                h.a(Camera2Session.TAG, "Failed to start capture request. " + e2);
            }
        }
    }

    public enum SessionState {
        RUNNING,
        STOPPED
    }

    private Camera2Session(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, CameraManager cameraManager, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4) throws CameraAccessException {
        Logging.d(TAG, "Create new camera2 session on camera " + str);
        h.a(TAG, "Create new camera2 session on camera " + str);
        this.constructionTimeNs = System.nanoTime();
        this.cameraThreadHandler = new Handler();
        this.callback = createSessionCallback;
        this.events = events;
        this.applicationContext = context;
        this.cameraManager = cameraManager;
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.mediaRecorderSurface = mediaRecorder != null ? mediaRecorder.getSurface() : null;
        this.cameraId = str;
        this.width = i2;
        this.height = i3;
        this.framerate = i4;
        start();
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

    public static void create(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, CameraManager cameraManager, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4, CameraParamObserver cameraParamObserver) {
        new Camera2Session(createSessionCallback, events, context, cameraManager, surfaceTextureHelper, mediaRecorder, str, i2, i3, i4).setRTCRecordObserver(cameraParamObserver);
    }

    private void findCaptureFormat() {
        checkIsOnCameraThread();
        Range[] rangeArr = (Range[]) this.cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        this.fpsUnitFactor = Camera2Enumerator.getFpsUnitFactor(rangeArr);
        List<Size> supportedSizes = Camera2Enumerator.getSupportedSizes(this.cameraCharacteristics);
        Logging.d(TAG, "Available preview sizes: " + supportedSizes);
        h.a(TAG, "Available preview sizes: " + supportedSizes);
        List<Size> supportedPictureSizes = Camera2Enumerator.getSupportedPictureSizes(this.cameraCharacteristics);
        Logging.d(TAG, "Available picture sizes: " + supportedPictureSizes);
        h.a(TAG, "Available picture sizes: " + supportedPictureSizes);
        List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> listConvertFramerates = Camera2Enumerator.convertFramerates(rangeArr, this.fpsUnitFactor);
        Logging.d(TAG, "Available fps ranges: " + listConvertFramerates);
        h.a(TAG, "Available fps ranges: " + listConvertFramerates);
        if (listConvertFramerates.isEmpty() || supportedPictureSizes.isEmpty()) {
            reportError("No supported capture formats.");
            h.a(TAG, "No supported capture formats.");
            return;
        }
        CameraEnumerationAndroid.CaptureFormat.FramerateRange closestSupportedFramerateRange = CameraEnumerationAndroid.getClosestSupportedFramerateRange(listConvertFramerates, this.framerate);
        Size closestSupportedSize = CameraEnumerationAndroid.getClosestSupportedSize(supportedPictureSizes, this.width, this.height);
        CameraEnumerationAndroid.reportCameraResolution(camera2ResolutionHistogram, closestSupportedSize);
        this.captureFormat = new CameraEnumerationAndroid.CaptureFormat(closestSupportedSize.width, closestSupportedSize.height, closestSupportedFramerateRange);
        Logging.d(TAG, "Using capture format: " + this.captureFormat);
        h.a(TAG, "Using capture format: " + this.captureFormat);
    }

    private void openCamera() throws CameraAccessException {
        checkIsOnCameraThread();
        Logging.d(TAG, "Opening camera " + this.cameraId);
        h.a(TAG, "Opening camera " + this.cameraId);
        this.events.onCameraOpening();
        try {
            this.cameraManager.openCamera(this.cameraId, new CameraStateCallback(), this.cameraThreadHandler);
        } catch (CameraAccessException e2) {
            reportError("Failed to open camera: " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportError(int i2, String str) {
        checkIsOnCameraThread();
        Logging.e(TAG, "Error: " + str);
        boolean z2 = this.captureSession == null && this.state != SessionState.STOPPED;
        this.state = SessionState.STOPPED;
        stopInternal();
        if (z2) {
            this.callback.onFailure(CameraSession.FailureType.ERROR, i2, str);
        } else {
            this.events.onCameraError(this, str);
        }
    }

    private void start() throws CameraAccessException {
        checkIsOnCameraThread();
        Logging.d(TAG, "start");
        h.a(TAG, "start");
        try {
            CameraCharacteristics cameraCharacteristics = this.cameraManager.getCameraCharacteristics(this.cameraId);
            this.cameraCharacteristics = cameraCharacteristics;
            this.cameraOrientation = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
            this.isCameraFrontFacing = ((Integer) this.cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
            findCaptureFormat();
            openCamera();
        } catch (CameraAccessException e2) {
            reportError("getCameraCharacteristics(): " + e2.getMessage());
            h.a(TAG, "getCameraCharacteristics(): " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopInternal() {
        Logging.d(TAG, "Stop internal");
        h.a(TAG, "Stop internal");
        checkIsOnCameraThread();
        this.surfaceTextureHelper.stopListening();
        h.a(TAG, "surfaceTextureHelper stopListening end");
        CameraCaptureSession cameraCaptureSession = this.captureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.captureSession = null;
            h.a(TAG, "captureSession closed");
        }
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
            this.surface = null;
            h.a(TAG, "surface released");
        }
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.cameraDevice = null;
            h.a(TAG, "cameraDevice closed");
        }
        Logging.d(TAG, "Stop done");
        h.a(TAG, "Stop done");
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
        if (!this.isCameraFrontFacing) {
            iAdapterUserSelect = 360 - iAdapterUserSelect;
        }
        return (this.cameraOrientation + iAdapterUserSelect) % 360;
    }

    @Override // org.wrtca.video.CameraSession
    public SurfaceTextureHelper getSurfaceTextureHelper() {
        return this.surfaceTextureHelper;
    }

    @Override // org.wrtca.video.CameraSession
    public CameraSession.CameraParam requestCamera() {
        CameraSession.CameraParam cameraParam = new CameraSession.CameraParam();
        cameraParam.setFrontCamera(this.isCameraFrontFacing);
        cameraParam.setCameraOrientation(this.cameraOrientation);
        return cameraParam;
    }

    @Override // org.wrtca.video.CameraSession
    public void setFlashLight(boolean z2) throws CameraAccessException {
        h.a(TAG, "Camera2 setFlashLight " + z2);
        checkIsOnCameraThread();
        try {
            if (z2) {
                this.captureRequest.set(CaptureRequest.FLASH_MODE, 2);
            } else {
                this.captureRequest.set(CaptureRequest.FLASH_MODE, 0);
            }
            this.captureSession.setRepeatingRequest(this.captureRequest.build(), null, null);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        }
    }

    public void setRTCRecordObserver(CameraParamObserver cameraParamObserver) {
        this.mRTCRecordObserver = cameraParamObserver;
    }

    @Override // org.wrtca.video.CameraSession
    public void stop() {
        Logging.d(TAG, "Stop camera2 session on camera " + this.cameraId);
        h.a(TAG, "Stop camera2 session on camera " + this.cameraId);
        checkIsOnCameraThread();
        SessionState sessionState = this.state;
        SessionState sessionState2 = SessionState.STOPPED;
        if (sessionState != sessionState2) {
            long jNanoTime = System.nanoTime();
            this.state = sessionState2;
            stopInternal();
            camera2StopTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportError(String str) {
        reportError(-1, str);
    }
}
