package io.agora.rtc.video;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.google.android.exoplayer2.C;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.socket.event.PLVEventConstant;
import io.agora.rtc.internal.DeviceUtils;
import io.agora.rtc.internal.Logging;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes8.dex */
public class VideoCaptureCamera extends VideoCapture implements Camera.PreviewCallback, SurfaceHolder.Callback {
    private static final long CAMERA_OPEN_REQUEST_INTERVAL = 2000;
    private static final boolean DEBUG = false;
    private static final String TAG = "CAMERA1";
    private boolean isCaptureRunning;
    private boolean isCaptureStarted;
    private boolean isFaceDetectionStarted;
    private boolean isSurfaceReady;
    protected Camera mCamera;
    private HandlerThread mCameraRecoverHandlerThread;
    private int mCaptureFormat;
    private int mCaptureFps;
    private int mCaptureHeight;
    private ReentrantLock mCaptureLock;
    private int mCaptureWidth;
    private SurfaceTexture mDummySurfaceTexture;
    private int mExpectedFrameSize;
    private Handler mHandler;
    private boolean mIsAutoFaceFocusEnabled;
    private SurfaceHolder mLocalPreview;
    private final int mNumCaptureBuffers;
    private Object mObjectLock;
    private boolean mOwnsBuffers;
    protected ReentrantLock mPreviewBufferLock;
    private Object mRecoverThreadObjectLock;

    public VideoCaptureCamera(Context context, int id, long nativeVideoCaptureDeviceAndroid) {
        super(context, id, nativeVideoCaptureDeviceAndroid);
        this.mPreviewBufferLock = new ReentrantLock();
        this.mCaptureLock = new ReentrantLock();
        this.isCaptureStarted = false;
        this.isCaptureRunning = false;
        this.isSurfaceReady = false;
        this.isFaceDetectionStarted = false;
        this.mLocalPreview = null;
        this.mDummySurfaceTexture = null;
        this.mOwnsBuffers = false;
        this.mNumCaptureBuffers = 3;
        this.mExpectedFrameSize = 0;
        this.mCaptureWidth = -1;
        this.mCaptureHeight = -1;
        this.mCaptureFps = -1;
        this.mCaptureFormat = 17;
        this.mCameraRecoverHandlerThread = null;
        this.mHandler = null;
        this.mRecoverThreadObjectLock = new Object();
        this.mObjectLock = new Object();
        this.mIsAutoFaceFocusEnabled = false;
    }

    private static Rect calculateTapArea(float x2, float y2, float coefficient) {
        int i2 = (int) ((x2 * 2000.0f) - 1000.0f);
        int i3 = (int) ((y2 * 2000.0f) - 1000.0f);
        int iIntValue = Float.valueOf(coefficient * 300.0f).intValue() / 2;
        RectF rectF = new RectF(clamp(i2 - iIntValue, -1000, 1000), clamp(i3 - iIntValue, -1000, 1000), clamp(i2 + iIntValue, -1000, 1000), clamp(i3 + iIntValue, -1000, 1000));
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Camera.CameraInfo getCameraInfo(int id) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        try {
            Camera.getCameraInfo(id, cameraInfo);
            return cameraInfo;
        } catch (RuntimeException e2) {
            Logging.e(TAG, "getCameraInfo: Camera.getCameraInfo: ", e2);
            return null;
        }
    }

    public static String getCaptureName() {
        return "camera1";
    }

    public static String getName(int id) {
        Camera.CameraInfo cameraInfo = getCameraInfo(id);
        if (cameraInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("camera ");
        sb.append(id);
        sb.append(", facing ");
        sb.append(cameraInfo.facing == 1 ? "front" : "back");
        return sb.toString();
    }

    public static int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    public static int getSensorOrientation(int id) {
        Camera.CameraInfo cameraInfo = getCameraInfo(id);
        if (cameraInfo == null) {
            return -1;
        }
        return cameraInfo.orientation;
    }

    private List<Integer> getZoomRatios() {
        if (this.mCamera == null) {
            return null;
        }
        Camera.Parameters cameraParameters = getCameraParameters();
        if (isZoomSupported(cameraParameters)) {
            return cameraParameters.getZoomRatios();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isForeground() {
        Context context = this.mContext;
        if (context != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                Logging.e(TAG, "List of RunningAppProcessInfo is null");
                return false;
            }
            for (int i2 = 0; i2 < runningAppProcesses.size(); i2++) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i2);
                if (runningAppProcessInfo == null) {
                    Logging.e(TAG, "ActivityManager.RunningAppProcessInfo is null");
                } else if (runningAppProcessInfo.processName.equals(this.mContext.getPackageName()) && runningAppProcessInfo.importance == 100) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSupported(String value, List<String> supported) {
        return supported != null && supported.indexOf(value) >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCameraFocusAreaChanged(Rect faceRect) {
        RectF rectFNormalizedFaceRect = CoordinatesTransform.normalizedFaceRect(faceRect, 0, this.mId == 1);
        float f2 = rectFNormalizedFaceRect.left;
        float f3 = rectFNormalizedFaceRect.top;
        float fWidth = rectFNormalizedFaceRect.width();
        float fHeight = rectFNormalizedFaceRect.height();
        Logging.d(TAG, "auto face focus left =" + rectFNormalizedFaceRect.left + " top = " + rectFNormalizedFaceRect.top + " right = " + rectFNormalizedFaceRect.right + " bottom = " + rectFNormalizedFaceRect.bottom);
        NotifyCameraFocusAreaChanged(f2, f3, fWidth, fHeight, this.mNativeVideoCaptureDeviceAndroid);
    }

    private void setAdvancedCameraParameters(Camera.Parameters parameters) {
        if (isSupported(DebugKt.DEBUG_PROPERTY_VALUE_OFF, parameters.getSupportedFlashModes())) {
            Logging.i(TAG, "AgoraVideo set flash mode = FLASH_MODE_OFF");
            parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        }
        if (isSupported("auto", parameters.getSupportedWhiteBalance())) {
            Logging.i(TAG, "AgoraVideo set white blance = WHITE_BALANCE_AUTO");
            parameters.setWhiteBalance("auto");
        }
        if (isSupported("continuous-video", parameters.getSupportedFocusModes())) {
            Logging.i(TAG, "AgoraVideo set Focus mode = FOCUS_MODE_CONTINUOUS_VIDEO");
            parameters.setFocusMode("continuous-video");
        }
        if (isSupported("auto", parameters.getSupportedAntibanding())) {
            Logging.i(TAG, "AgoraVideo set anti-banding = ANTIBANDING_AUTO");
            parameters.setAntibanding("auto");
        }
        if (isSupported("auto", parameters.getSupportedSceneModes())) {
            Logging.i(TAG, "AgoraVideo set sence mode = auto");
            if (parameters.getSceneMode() != "auto") {
                parameters.setSceneMode("auto");
            }
        }
    }

    private void setDeviceSpecificParameters(Camera.Parameters parameters) throws Throwable {
        String deviceId = DeviceUtils.getDeviceId();
        String cpuName = DeviceUtils.getCpuName();
        String cpuABI = DeviceUtils.getCpuABI();
        int numberOfCPUCores = DeviceUtils.getNumberOfCPUCores();
        int cPUMaxFreqKHz = DeviceUtils.getCPUMaxFreqKHz();
        Logging.i(TAG, "Current Device: " + deviceId);
        Logging.i(TAG, "CPU name: " + cpuName + ", with " + numberOfCPUCores + " cores, arch: " + cpuABI + ", max Freq: " + cPUMaxFreqKHz);
        if (deviceId.contains("xiaomi/mi note")) {
            Logging.i(TAG, "set MiNote config");
            parameters.set("scene-detect", DebugKt.DEBUG_PROPERTY_VALUE_ON);
            parameters.set("xiaomi-still-beautify-values", "i:3");
            parameters.set("skinToneEnhancement", "enable");
            parameters.set("auto-exposure", "center-weighted");
        }
        if (deviceId.contains("oppo/r7c/r7c")) {
            Logging.i(TAG, "set oppo r7c config");
            parameters.set("skinToneEnhancement", 1);
            parameters.set("face-beautify", 100);
            parameters.set("auto-exposure", "center-weighted");
        }
    }

    private int tryStartCapture(int width, int height, int frameRate) throws Throwable {
        if (this.mCamera == null) {
            Logging.e(TAG, "Camera not initialized %d" + this.mId);
            return -1;
        }
        Logging.i(TAG, "tryStartCapture: " + width + "*" + height + ", frameRate: " + frameRate + ", isCaptureRunning: " + this.isCaptureRunning + ", isSurfaceReady: " + this.isSurfaceReady + ", isCaptureStarted: " + this.isCaptureStarted);
        if (this.isCaptureRunning || !this.isCaptureStarted) {
            Logging.w(TAG, "tryStartCapture return");
            return 0;
        }
        Camera.Parameters parameters = this.mCamera.getParameters();
        parameters.setPreviewSize(width, height);
        parameters.setPreviewFormat(this.mCaptureFormat);
        parameters.setPreviewFrameRate(frameRate);
        setAdvancedCameraParameters(parameters);
        setDeviceSpecificParameters(parameters);
        this.mCamera.setParameters(parameters);
        int bitsPerPixel = (((width * height) * ImageFormat.getBitsPerPixel(this.mCaptureFormat)) / 8) + 4096;
        for (int i2 = 0; i2 < 3; i2++) {
            this.mCamera.addCallbackBuffer(new byte[bitsPerPixel]);
        }
        this.mCamera.setPreviewCallbackWithBuffer(this);
        this.mOwnsBuffers = true;
        this.mCamera.setErrorCallback(new Camera.ErrorCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera.1
            @Override // android.hardware.Camera.ErrorCallback
            public void onError(int error, Camera camera) {
                Logging.e(VideoCaptureCamera.TAG, "onError: error code" + error);
                if (error == 2 || error == 100 || error == 1) {
                    VideoCaptureCamera videoCaptureCamera = VideoCaptureCamera.this;
                    if (videoCaptureCamera.mCamera != null) {
                        videoCaptureCamera.stopCapture();
                        VideoCaptureCamera.this.mCaptureLock.lock();
                        Camera camera2 = VideoCaptureCamera.this.mCamera;
                        if (camera2 != null) {
                            camera2.release();
                            VideoCaptureCamera.this.mCamera = null;
                        }
                        VideoCaptureCamera.this.mCaptureLock.unlock();
                    }
                    synchronized (VideoCaptureCamera.this.mRecoverThreadObjectLock) {
                        if (VideoCaptureCamera.this.mCameraRecoverHandlerThread == null) {
                            VideoCaptureCamera.this.mCameraRecoverHandlerThread = new HandlerThread("camera-recover-thread");
                            VideoCaptureCamera.this.mCameraRecoverHandlerThread.start();
                            if (VideoCaptureCamera.this.mCameraRecoverHandlerThread != null) {
                                VideoCaptureCamera.this.mHandler = new Handler(VideoCaptureCamera.this.mCameraRecoverHandlerThread.getLooper());
                            }
                        }
                        if (VideoCaptureCamera.this.mHandler != null) {
                            VideoCaptureCamera.this.mHandler.postDelayed(new Runnable() { // from class: io.agora.rtc.video.VideoCaptureCamera.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    synchronized (VideoCaptureCamera.this.mRecoverThreadObjectLock) {
                                        Logging.i(VideoCaptureCamera.TAG, "native handle = " + VideoCaptureCamera.this.mNativeVideoCaptureDeviceAndroid);
                                        if (VideoCaptureCamera.this.isForeground()) {
                                            VideoCaptureCamera videoCaptureCamera2 = VideoCaptureCamera.this;
                                            if (videoCaptureCamera2.mCamera == null && videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid != 0) {
                                                videoCaptureCamera2.allocate();
                                                VideoCaptureCamera videoCaptureCamera3 = VideoCaptureCamera.this;
                                                videoCaptureCamera3.startCapture(videoCaptureCamera3.mCaptureWidth, VideoCaptureCamera.this.mCaptureHeight, VideoCaptureCamera.this.mCaptureFps);
                                                return;
                                            }
                                        }
                                        if (VideoCaptureCamera.this.mHandler != null) {
                                            VideoCaptureCamera.this.mHandler.postDelayed(this, 2000L);
                                        }
                                    }
                                }
                            }, 2000L);
                        }
                    }
                }
            }
        });
        this.mCamera.startPreview();
        if (isAutoFaceFocusSupported()) {
            this.mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() { // from class: io.agora.rtc.video.VideoCaptureCamera.2
                private long mLastFocusedTs;

                @Override // android.hardware.Camera.FaceDetectionListener
                public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                    if (faces == null || faces.length == 0 || camera == null || !VideoCaptureCamera.this.mIsAutoFaceFocusEnabled) {
                        return;
                    }
                    if (System.currentTimeMillis() - this.mLastFocusedTs < C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
                        Camera.Face face = faces[0];
                        if (face.score > 20) {
                            VideoCaptureCamera.this.notifyCameraFocusAreaChanged(face.rect);
                            return;
                        }
                        return;
                    }
                    if (faces[0].score <= 50) {
                        Logging.i(VideoCaptureCamera.TAG, "face score = " + faces[0].score);
                        return;
                    }
                    try {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new Camera.Area(faces[0].rect, 1000));
                        if (camera.getParameters().getMaxNumFocusAreas() > 0) {
                            camera.getParameters().setFocusAreas(arrayList);
                        }
                        if (camera.getParameters().getMaxNumMeteringAreas() > 0) {
                            camera.getParameters().setMeteringAreas(arrayList);
                        }
                        VideoCaptureCamera.this.notifyCameraFocusAreaChanged(faces[0].rect);
                        camera.autoFocus(new Camera.AutoFocusCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera.2.1
                            @Override // android.hardware.Camera.AutoFocusCallback
                            public void onAutoFocus(boolean success, Camera camera2) {
                                Logging.d(VideoCaptureCamera.TAG, "auto face focus called api1 every 3 seconds");
                                if (camera2 != null) {
                                    try {
                                        camera2.cancelAutoFocus();
                                    } catch (RuntimeException e2) {
                                        Logging.w(VideoCaptureCamera.TAG, "Exception in cancelAutoFocus: " + Log.getStackTraceString(e2));
                                    }
                                }
                            }
                        });
                        this.mLastFocusedTs = System.currentTimeMillis();
                    } catch (RuntimeException e2) {
                        Logging.w(VideoCaptureCamera.TAG, "Exception in onFaceDetection callback: " + Log.getStackTraceString(e2));
                    }
                }
            });
            Logging.i(TAG, "enable face detection");
            this.mCamera.startFaceDetection();
            this.isFaceDetectionStarted = true;
        }
        this.mPreviewBufferLock.lock();
        this.mExpectedFrameSize = bitsPerPixel;
        this.isCaptureRunning = true;
        this.mPreviewBufferLock.unlock();
        Logging.e(TAG, "Params: " + this.mCamera.getParameters().flatten());
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int UnRegisterNativeHandle() {
        Logging.d(TAG, "UnRegisterNativeHandle called");
        synchronized (this.mRecoverThreadObjectLock) {
            this.mNativeVideoCaptureDeviceAndroid = 0L;
        }
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int allocate() {
        try {
            this.mCamera = Camera.open(this.mId);
            Camera.CameraInfo cameraInfo = getCameraInfo(this.mId);
            if (cameraInfo == null) {
                this.mCamera.release();
                this.mCamera = null;
                return -2;
            }
            if (VideoCapture.fetchCapability(this.mId, this.mContext, getCaptureName()) == null) {
                createCapabilities();
            }
            this.mCameraNativeOrientation = cameraInfo.orientation;
            long j2 = this.mNativeVideoCaptureDeviceAndroid;
            if (j2 == 0) {
                return 0;
            }
            this.mIsAutoFaceFocusEnabled = isAutoFaceFocusEnabled(j2);
            return 0;
        } catch (RuntimeException e2) {
            Logging.e(TAG, "allocate: Camera.open: ", e2);
            return -1;
        }
    }

    public int createCapabilities() {
        String str;
        Camera.Parameters cameraParameters = getCameraParameters();
        if (cameraParameters != null) {
            String str2 = "\"id\":" + this.mId + ",";
            List<Camera.Size> supportedPreviewSizes = cameraParameters.getSupportedPreviewSizes();
            String str3 = "";
            String str4 = "";
            for (int i2 = 0; i2 < supportedPreviewSizes.size(); i2++) {
                int i3 = supportedPreviewSizes.get(i2).width;
                int i4 = supportedPreviewSizes.get(i2).height;
                if (i3 >= 240 && i4 >= 240 && (i3 >= 320 || i4 >= 320)) {
                    String str5 = "{\"w\":" + i3 + ",\"h\":" + i4 + "}";
                    str4 = str4.isEmpty() ? str5 : str4 + "," + str5;
                }
            }
            List<Integer> supportedPreviewFormats = cameraParameters.getSupportedPreviewFormats();
            if (VideoCapture.isEmulator()) {
                supportedPreviewFormats.remove(Integer.valueOf(IjkMediaPlayer.SDL_FCC_YV12));
            }
            String str6 = "";
            for (int i5 = 0; i5 < supportedPreviewFormats.size(); i5++) {
                int iTranslateToEngineFormat = VideoCapture.translateToEngineFormat(supportedPreviewFormats.get(i5).intValue());
                str6 = i5 != supportedPreviewFormats.size() - 1 ? str6 + iTranslateToEngineFormat + "," : str6 + iTranslateToEngineFormat;
            }
            List<Integer> supportedPreviewFrameRates = cameraParameters.getSupportedPreviewFrameRates();
            for (int i6 = 0; i6 < supportedPreviewFrameRates.size(); i6++) {
                int iIntValue = supportedPreviewFrameRates.get(i6).intValue();
                str3 = i6 != supportedPreviewFrameRates.size() - 1 ? str3 + iIntValue + "," : str3 + iIntValue;
            }
            str = StrPool.DELIM_START + str2 + "\"resolution\":" + StrPool.BRACKET_START + str4 + "],\"format\":" + StrPool.BRACKET_START + str6 + "],\"fps\":" + StrPool.BRACKET_START + str3 + "]}";
        } else {
            str = null;
        }
        VideoCapture.cacheCapability(this.mId, this.mContext, str, getCaptureName());
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public void deallocate() {
        if (this.mCamera == null) {
            return;
        }
        synchronized (this.mRecoverThreadObjectLock) {
            this.mNativeVideoCaptureDeviceAndroid = 0L;
            stopCapture();
            this.mCaptureLock.lock();
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.release();
                this.mCamera = null;
            }
            this.mCaptureLock.unlock();
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = this.mCameraRecoverHandlerThread;
            if (handlerThread != null) {
                handlerThread.quit();
                this.mCameraRecoverHandlerThread = null;
                this.mHandler = null;
            }
        }
    }

    public Camera.Parameters getCameraParameters() {
        try {
            return this.mCamera.getParameters();
        } catch (RuntimeException e2) {
            Logging.e(TAG, "getCameraParameters: Camera.getParameters: ", e2);
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.release();
                this.mCamera = null;
            }
            return null;
        }
    }

    @Override // io.agora.rtc.video.VideoCapture
    public float getMaxZoom() {
        if (this.mCamera == null) {
            return -1.0f;
        }
        Camera.Parameters cameraParameters = getCameraParameters();
        int maxZoom = isZoomSupported(cameraParameters) ? cameraParameters.getMaxZoom() : 0;
        List<Integer> zoomRatios = getZoomRatios();
        if (zoomRatios == null || zoomRatios.size() <= maxZoom) {
            return -1.0f;
        }
        return zoomRatios.get(maxZoom).intValue() / 100.0f;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isAutoFaceFocusSupported() {
        Camera.Parameters cameraParameters;
        return this.mCamera != null && (cameraParameters = getCameraParameters()) != null && cameraParameters.getMaxNumDetectedFaces() > 0 && cameraParameters.getMaxNumFocusAreas() > 0 && isSupported("auto", cameraParameters.getSupportedFocusModes());
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isExposureSupported() {
        Camera.Parameters cameraParameters;
        return (this.mCamera == null || (cameraParameters = getCameraParameters()) == null || cameraParameters.getMaxNumMeteringAreas() <= 0) ? false : true;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isFocusSupported() {
        Camera.Parameters cameraParameters;
        return this.mCamera != null && (cameraParameters = getCameraParameters()) != null && cameraParameters.getMaxNumFocusAreas() > 0 && isSupported("auto", cameraParameters.getSupportedFocusModes());
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isTorchSupported() {
        Camera.Parameters cameraParameters;
        if (this.mCamera == null || (cameraParameters = getCameraParameters()) == null) {
            return false;
        }
        return isSupported("torch", cameraParameters.getSupportedFlashModes());
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isZoomSupported() {
        Camera.Parameters cameraParameters;
        if (this.mCamera == null || (cameraParameters = getCameraParameters()) == null) {
            return false;
        }
        return cameraParameters.isZoomSupported();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001d A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:2:0x0000, B:4:0x0007, B:7:0x000c, B:9:0x0013, B:11:0x0019, B:12:0x001d, B:14:0x0023), top: B:34:0x0000 }] */
    @Override // android.hardware.Camera.PreviewCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreviewFrame(byte[] r7, android.hardware.Camera r8) {
        /*
            r6 = this;
            java.util.concurrent.locks.ReentrantLock r0 = r6.mPreviewBufferLock     // Catch: java.lang.Throwable -> L48
            r0.lock()     // Catch: java.lang.Throwable -> L48
            if (r7 == 0) goto L39
            boolean r0 = r6.isCaptureRunning     // Catch: java.lang.Throwable -> L48
            if (r0 != 0) goto Lc
            goto L39
        Lc:
            int r0 = r7.length     // Catch: java.lang.Throwable -> L48
            int r1 = r6.mExpectedFrameSize     // Catch: java.lang.Throwable -> L48
            r2 = 0
            if (r0 != r1) goto L1d
            long r4 = r6.mNativeVideoCaptureDeviceAndroid     // Catch: java.lang.Throwable -> L48
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 == 0) goto L1d
            r6.ProvideCameraFrame(r7, r1, r4)     // Catch: java.lang.Throwable -> L48
            goto L2a
        L1d:
            long r0 = r6.mNativeVideoCaptureDeviceAndroid     // Catch: java.lang.Throwable -> L48
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L2a
            java.lang.String r0 = "CAMERA1"
            java.lang.String r1 = "warning mNativeVideoCaptureDeviceAndroid = 0, error"
            io.agora.rtc.internal.Logging.w(r0, r1)     // Catch: java.lang.Throwable -> L48
        L2a:
            java.util.concurrent.locks.ReentrantLock r0 = r6.mPreviewBufferLock
            r0.unlock()
            if (r8 == 0) goto L38
            boolean r0 = r6.isCaptureRunning
            if (r0 == 0) goto L38
            r8.addCallbackBuffer(r7)
        L38:
            return
        L39:
            java.util.concurrent.locks.ReentrantLock r0 = r6.mPreviewBufferLock
            r0.unlock()
            if (r8 == 0) goto L47
            boolean r0 = r6.isCaptureRunning
            if (r0 == 0) goto L47
            r8.addCallbackBuffer(r7)
        L47:
            return
        L48:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantLock r1 = r6.mPreviewBufferLock
            r1.unlock()
            if (r8 == 0) goto L57
            boolean r1 = r6.isCaptureRunning
            if (r1 == 0) goto L57
            r8.addCallbackBuffer(r7)
        L57:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.video.VideoCaptureCamera.onPreviewFrame(byte[], android.hardware.Camera):void");
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setAutoFaceFocus(boolean enable) {
        this.mIsAutoFaceFocusEnabled = enable;
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setCaptureFormat(int format) {
        Logging.d(TAG, "setCaptureFormat: " + format);
        int iTranslateToAndroidFormat = VideoCapture.translateToAndroidFormat(format);
        this.mCaptureFormat = iTranslateToAndroidFormat;
        if (iTranslateToAndroidFormat != 0) {
            return 0;
        }
        Logging.e(TAG, "setCaptureFormat failed, unkonwn format: " + format);
        return -1;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setExposure(float x2, float y2, boolean inPreview) {
        Logging.d(TAG, "setExposure called camera api1 x = " + x2 + " y = " + y2);
        if (this.mCamera == null) {
            return -1;
        }
        if (x2 < 0.0f || x2 > 1.0f || y2 < 0.0f || y2 > 1.0f) {
            Logging.e(TAG, "set exposure unreasonable inputs");
            return -1;
        }
        Rect rectCalculateTapArea = calculateTapArea(x2, y2, 1.5f);
        if (this.mCamera != null) {
            Camera.Parameters cameraParameters = getCameraParameters();
            if (cameraParameters == null) {
                return -1;
            }
            if (cameraParameters.getMaxNumMeteringAreas() > 0) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Camera.Area(rectCalculateTapArea, 800));
                cameraParameters.setMeteringAreas(arrayList);
            } else {
                Logging.i(TAG, "metering areas not supported");
            }
            this.mCamera.setParameters(cameraParameters);
            this.mCamera.startPreview();
        }
        long j2 = this.mNativeVideoCaptureDeviceAndroid;
        if (j2 == 0) {
            return 0;
        }
        NotifyCameraExposureAreaChanged(x2, y2, 0.0f, 0.0f, j2);
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setFocus(float x2, float y2, boolean inPreview) {
        Logging.d(TAG, "setFocus called camera api1");
        if (this.mCamera == null) {
            return -1;
        }
        if (x2 < 0.0f || x2 > 1.0f || y2 < 0.0f || y2 > 1.0f) {
            Logging.e(TAG, "set focus unreasonable inputs");
            return -1;
        }
        Rect rectCalculateTapArea = calculateTapArea(x2, y2, 1.0f);
        Rect rectCalculateTapArea2 = calculateTapArea(x2, y2, 1.5f);
        this.mCamera.cancelAutoFocus();
        Camera.Parameters cameraParameters = getCameraParameters();
        if (cameraParameters == null) {
            return -1;
        }
        if (cameraParameters.getMaxNumFocusAreas() > 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Camera.Area(rectCalculateTapArea, 800));
            cameraParameters.setFocusAreas(arrayList);
        } else {
            Logging.i(TAG, "focus areas not supported");
        }
        if (cameraParameters.getMaxNumMeteringAreas() > 0) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new Camera.Area(rectCalculateTapArea2, 800));
            cameraParameters.setMeteringAreas(arrayList2);
        } else {
            Logging.i(TAG, "metering areas not supported");
        }
        final String focusMode = cameraParameters.getFocusMode();
        if (isSupported("macro", cameraParameters.getSupportedFocusModes())) {
            cameraParameters.setFocusMode("macro");
            synchronized (this.mObjectLock) {
                this.mCamera.setParameters(cameraParameters);
            }
        } else {
            Logging.i(PLVEventConstant.Chatroom.SE_FOCUS, "FOCUS_MODE_MACRO is not supported");
        }
        this.mCamera.autoFocus(new Camera.AutoFocusCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera.3
            @Override // android.hardware.Camera.AutoFocusCallback
            public void onAutoFocus(boolean success, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFocusMode(focusMode);
                synchronized (VideoCaptureCamera.this.mObjectLock) {
                    camera.setParameters(parameters);
                }
            }
        });
        long j2 = this.mNativeVideoCaptureDeviceAndroid;
        if (j2 == 0) {
            return 0;
        }
        NotifyCameraFocusAreaChanged(x2, y2, 0.0f, 0.0f, j2);
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setTorchMode(boolean isTorchOn) {
        Camera.Parameters cameraParameters;
        if (this.mCamera == null || (cameraParameters = getCameraParameters()) == null) {
            return -2;
        }
        List<String> supportedFlashModes = cameraParameters.getSupportedFlashModes();
        if (supportedFlashModes == null || !supportedFlashModes.contains("torch")) {
            return -1;
        }
        if (isTorchOn) {
            cameraParameters.setFlashMode("torch");
        } else {
            cameraParameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        }
        this.mCamera.setParameters(cameraParameters);
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setZoom(float zoomValue) {
        int i2 = (int) ((zoomValue * 100.0f) + 0.5f);
        List<Integer> zoomRatios = getZoomRatios();
        if (zoomRatios == null) {
            return -1;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= zoomRatios.size()) {
                i3 = 0;
                break;
            }
            if (i2 <= zoomRatios.get(i3).intValue()) {
                break;
            }
            i3++;
        }
        if (this.mCamera != null) {
            Camera.Parameters cameraParameters = getCameraParameters();
            if (isZoomSupported(cameraParameters)) {
                if (i3 > cameraParameters.getMaxZoom()) {
                    Logging.w(TAG, "zoom value is larger than maxZoom value");
                    return -1;
                }
                cameraParameters.setZoom(i3);
                try {
                    this.mCamera.setParameters(cameraParameters);
                } catch (Exception e2) {
                    Logging.w(TAG, "setParameters failed, zoomLevel: " + i3 + ", " + e2);
                }
            }
        }
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int startCapture(int width, int height, int frameRate) throws IOException {
        int iTryStartCapture = -1;
        if (this.mCamera == null) {
            Logging.e(TAG, "startCapture: camera is null!!");
            return -1;
        }
        SurfaceHolder surfaceHolderGetLocalRenderer = ViERenderer.GetLocalRenderer();
        this.mLocalPreview = surfaceHolderGetLocalRenderer;
        if (surfaceHolderGetLocalRenderer != null) {
            if (surfaceHolderGetLocalRenderer.getSurface() != null && this.mLocalPreview.getSurface().isValid()) {
                surfaceCreated(this.mLocalPreview);
            }
            this.mLocalPreview.addCallback(this);
        } else {
            this.mCaptureLock.lock();
            try {
                SurfaceTexture surfaceTexture = new SurfaceTexture(42);
                this.mDummySurfaceTexture = surfaceTexture;
                this.mCamera.setPreviewTexture(surfaceTexture);
                this.mCaptureLock.unlock();
            } catch (Exception unused) {
                Logging.e(TAG, "failed to startPreview, invalid surfaceTexture!");
                this.mDummySurfaceTexture = null;
                return -1;
            } finally {
            }
        }
        this.mCaptureLock.lock();
        this.isCaptureStarted = true;
        this.mCaptureWidth = width;
        this.mCaptureHeight = height;
        this.mCaptureFps = frameRate;
        try {
            iTryStartCapture = tryStartCapture(width, height, frameRate);
        } finally {
            try {
                return iTryStartCapture;
            } finally {
            }
        }
        return iTryStartCapture;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int stopCapture() {
        if (!this.isCaptureStarted) {
            Logging.w(TAG, "already stop capture");
            return 0;
        }
        try {
            if (this.isFaceDetectionStarted) {
                this.mCamera.stopFaceDetection();
                this.mCamera.setFaceDetectionListener(null);
                this.isFaceDetectionStarted = false;
            }
        } catch (RuntimeException e2) {
            Logging.e(TAG, "Failed to stop face detection", e2);
        }
        try {
            this.mPreviewBufferLock.lock();
            this.isCaptureRunning = false;
            this.mCamera.stopPreview();
            this.mPreviewBufferLock.unlock();
            this.mCamera.setErrorCallback(null);
            this.mCamera.setPreviewCallbackWithBuffer(null);
            this.isCaptureStarted = false;
            return 0;
        } catch (RuntimeException e3) {
            Logging.e(TAG, "Failed to stop camera", e3);
            return -1;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) throws IOException {
        this.mCaptureLock.lock();
        try {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.stopPreview();
                this.mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException e2) {
            Logging.e(TAG, "Failed to set preview surface!", e2);
        } catch (RuntimeException e3) {
            Logging.e(TAG, "Failed to stop preview!", e3);
        }
        this.mCaptureLock.unlock();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) throws IOException {
        this.mCaptureLock.lock();
        try {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.setPreviewDisplay(null);
            }
        } catch (IOException e2) {
            Logging.e(TAG, "Failed to clear preview surface!", e2);
        }
        this.mCaptureLock.unlock();
    }

    private boolean isZoomSupported(Camera.Parameters parameters) {
        if (parameters != null) {
            if (parameters.isZoomSupported()) {
                return true;
            }
            Logging.w(TAG, "camera zoom is not supported ");
        }
        return false;
    }
}
