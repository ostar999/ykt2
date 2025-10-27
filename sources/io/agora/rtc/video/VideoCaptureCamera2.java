package io.agora.rtc.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.psychiatrygarden.utils.Constants;
import io.agora.rtc.internal.Logging;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

@TargetApi(21)
/* loaded from: classes8.dex */
public class VideoCaptureCamera2 extends VideoCapture {
    private static final boolean DEBUG = false;
    private static final float DEFAULT_VALUE = -1.0f;
    private static final String TAG = "CAMERA2";
    private static final MeteringRectangle[] ZERO_WEIGHT_3A_REGION = {new MeteringRectangle(0, 0, 0, 0, 0)};
    private static final float ZOOM_UNSUPPORTED_DEFAULT_VALUE = 1.0f;
    private static final double kNanoSecondsToFps = 1.0E-9d;
    private MeteringRectangle[] mAFAERegions;
    private CameraCaptureSession.CaptureCallback mAfCaptureCallback;
    public CameraManager.AvailabilityCallback mAvailabilityCallback;
    private CameraDevice mCameraDevice;
    private CameraState mCameraState;
    private final Object mCameraStateLock;
    private HandlerThread mCameraStateThread;
    private final CameraCaptureSession.CaptureCallback mCaptureCallback;
    private byte[] mCaptureData;
    private int mCaptureFormat;
    private int mCaptureFps;
    private int mCaptureHeight;
    private CameraCaptureSession mCaptureSession;
    private int mCaptureWidth;
    private float mCurZoomRatio;
    private int mExpectedFrameSize;
    private int mFaceDetectMode;
    private boolean mFaceDetectSupported;
    private ImageReader mImageReader;
    private boolean mIsAutoFaceFocusEnabled;
    private float mLastZoomRatio;
    private CameraManager mManager;
    private float mMaxZoom;
    private CaptureRequest.Builder mPreviewBuilder;
    private HandlerThread mPreviewThread;
    private Rect mSensorRect;
    private Handler mStateHandler;

    public enum CameraState {
        OPENING,
        STARTED,
        EVICTED,
        STOPPED
    }

    public class CaptureSessionListener extends CameraCaptureSession.StateCallback {
        private CaptureSessionListener() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            Logging.e(VideoCaptureCamera2.TAG, "onConfigureFailed");
            if (VideoCaptureCamera2.this.mCameraState != CameraState.EVICTED) {
                VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.STOPPED);
            }
            VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
            long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
            if (j2 != 0) {
                videoCaptureCamera2.onCameraError(j2, "Camera session configuration error");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            VideoCaptureCamera2.this.mCaptureSession = cameraCaptureSession;
            if (VideoCaptureCamera2.this.createCaptureRequest() == 0) {
                VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.STARTED);
                return;
            }
            VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.STOPPED);
            VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
            long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
            if (j2 != 0) {
                videoCaptureCamera2.onCameraError(j2, "Fail to setup capture session");
            }
        }
    }

    public class CrStateListener extends CameraDevice.StateCallback {
        private CrStateListener() {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            if (VideoCaptureCamera2.this.mCameraState != CameraState.STOPPED) {
                Logging.w(VideoCaptureCamera2.TAG, "camera client is evicted by other application");
                VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
                long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
                if (j2 != 0) {
                    videoCaptureCamera2.onCameraError(j2, "Camera device evicted by other application");
                }
                Logging.i(VideoCaptureCamera2.TAG, "Camera device enter state: EVICTED");
                if (VideoCaptureCamera2.this.mCameraDevice != null) {
                    VideoCaptureCamera2.this.mCameraDevice.close();
                    VideoCaptureCamera2.this.mCameraDevice = null;
                }
                VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.EVICTED);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int error) {
            if (VideoCaptureCamera2.this.mCameraState == CameraState.EVICTED) {
                return;
            }
            if (VideoCaptureCamera2.this.mCameraDevice != null) {
                VideoCaptureCamera2.this.mCameraDevice.close();
                VideoCaptureCamera2.this.mCameraDevice = null;
            }
            VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.STOPPED);
            Logging.e(VideoCaptureCamera2.TAG, "CameraDevice Error :" + Integer.toString(error));
            VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
            long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
            if (j2 != 0) {
                videoCaptureCamera2.onCameraError(j2, "Camera device error" + Integer.toString(error));
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            VideoCaptureCamera2.this.mCameraDevice = cameraDevice;
            if (VideoCaptureCamera2.this.doStartCapture() < 0) {
                VideoCaptureCamera2.this.doStopCapture();
                if (VideoCaptureCamera2.this.mCameraState != CameraState.EVICTED) {
                    VideoCaptureCamera2.this.changeCameraStateAndNotify(CameraState.STOPPED);
                }
                Logging.e(VideoCaptureCamera2.TAG, "Camera startCapture failed!!");
                VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
                long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
                if (j2 != 0) {
                    videoCaptureCamera2.onCameraError(j2, "Error configuring camera");
                }
            }
        }
    }

    public class ImageReaderListener implements ImageReader.OnImageAvailableListener {
        private ImageReaderListener() {
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader) {
            Image image = null;
            try {
                try {
                    synchronized (VideoCaptureCamera2.this.mCameraStateLock) {
                        if (VideoCaptureCamera2.this.mCameraState == CameraState.STARTED && reader != null) {
                            Image imageAcquireLatestImage = reader.acquireLatestImage();
                            if (imageAcquireLatestImage == null) {
                                if (imageAcquireLatestImage != null) {
                                    imageAcquireLatestImage.close();
                                    return;
                                }
                                return;
                            }
                            if (imageAcquireLatestImage.getFormat() == 35 && imageAcquireLatestImage.getPlanes().length == 3) {
                                if (reader.getWidth() == imageAcquireLatestImage.getWidth() && reader.getHeight() == imageAcquireLatestImage.getHeight()) {
                                    VideoCaptureCamera2.readImageIntoBuffer(imageAcquireLatestImage, VideoCaptureCamera2.this.mCaptureData);
                                    VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
                                    if (videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid != 0) {
                                        videoCaptureCamera2.ProvideCameraFrame(videoCaptureCamera2.mCaptureData, VideoCaptureCamera2.this.mExpectedFrameSize, VideoCaptureCamera2.this.mNativeVideoCaptureDeviceAndroid);
                                    } else {
                                        Logging.w(VideoCaptureCamera2.TAG, "warning mNativeVideoCaptureDeviceAndroid = 0, error");
                                    }
                                    imageAcquireLatestImage.close();
                                    return;
                                }
                                throw new IllegalStateException("ImageReader size " + reader.getWidth() + "x" + reader.getHeight() + " did not match Image size: " + imageAcquireLatestImage.getWidth() + "x" + imageAcquireLatestImage.getHeight());
                            }
                            Logging.e(VideoCaptureCamera2.TAG, "Unexpected image format: " + imageAcquireLatestImage.getFormat() + "or #planes:" + imageAcquireLatestImage.getPlanes().length);
                            imageAcquireLatestImage.close();
                        }
                    }
                } catch (IllegalStateException e2) {
                    Logging.e(VideoCaptureCamera2.TAG, "acquireLastest Image():", e2);
                    if (0 != 0) {
                        image.close();
                    }
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    image.close();
                }
                throw th;
            }
        }
    }

    public VideoCaptureCamera2(Context context, int id, long nativeVideoCaptureDeviceAndroid) {
        super(context, id, nativeVideoCaptureDeviceAndroid);
        this.mCameraDevice = null;
        this.mPreviewBuilder = null;
        this.mCaptureSession = null;
        this.mImageReader = null;
        this.mCameraState = CameraState.STOPPED;
        this.mManager = null;
        this.mStateHandler = null;
        this.mCameraStateThread = null;
        this.mPreviewThread = null;
        this.mCameraStateLock = new Object();
        this.mExpectedFrameSize = 0;
        this.mCaptureWidth = -1;
        this.mCaptureHeight = -1;
        this.mCaptureFps = -1;
        this.mCaptureFormat = 35;
        this.mIsAutoFaceFocusEnabled = false;
        this.mAFAERegions = ZERO_WEIGHT_3A_REGION;
        this.mLastZoomRatio = -1.0f;
        this.mCurZoomRatio = 1.0f;
        this.mMaxZoom = -1.0f;
        this.mSensorRect = null;
        this.mAvailabilityCallback = new CameraManager.AvailabilityCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera2.1
            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public synchronized void onCameraAvailable(String cameraId) {
                super.onCameraAvailable(cameraId);
                if (VideoCaptureCamera2.this.mCameraState == CameraState.EVICTED && VideoCaptureCamera2.this.tryOpenCamera() != 0) {
                    Logging.e(VideoCaptureCamera2.TAG, "start capture failed");
                }
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public synchronized void onCameraUnavailable(String cameraId) {
                super.onCameraUnavailable(cameraId);
                Logging.e(VideoCaptureCamera2.TAG, "Camera " + cameraId + " unavailable");
            }
        };
        this.mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera2.2
            private long mLastFocusedTs;

            private void notifyCameraFocusAreaChanged(Rect cropRegion, Rect faceRect) {
                Rect rectSensorToNormalizedPreview = CoordinatesTransform.sensorToNormalizedPreview(faceRect, VideoCaptureCamera2.this.mCaptureWidth, VideoCaptureCamera2.this.mCaptureHeight, cropRegion);
                Logging.d(VideoCaptureCamera2.TAG, "face bound = " + faceRect.toString());
                Logging.d(VideoCaptureCamera2.TAG, "rect (-1000, 1000) = " + rectSensorToNormalizedPreview.toString());
                boolean z2 = VideoCaptureCamera2.this.mId == 1;
                RectF rectFNormalizedFaceRect = CoordinatesTransform.normalizedFaceRect(rectSensorToNormalizedPreview, 0, z2);
                Logging.d(VideoCaptureCamera2.TAG, "preview size width = " + VideoCaptureCamera2.this.mCaptureWidth + " height = " + VideoCaptureCamera2.this.mCaptureHeight);
                Logging.d(VideoCaptureCamera2.TAG, "auto face focus left =" + rectFNormalizedFaceRect.left + " top = " + rectFNormalizedFaceRect.top + " right = " + rectFNormalizedFaceRect.right + " bottom = " + rectFNormalizedFaceRect.bottom + "isMirror =" + z2);
                float f2 = rectFNormalizedFaceRect.left;
                float f3 = rectFNormalizedFaceRect.top;
                float fWidth = rectFNormalizedFaceRect.width();
                float fHeight = rectFNormalizedFaceRect.height();
                VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
                long j2 = videoCaptureCamera2.mNativeVideoCaptureDeviceAndroid;
                if (j2 != 0) {
                    videoCaptureCamera2.NotifyCameraFocusAreaChanged(f2, f3, fWidth, fHeight, j2);
                }
            }

            private void process(CaptureResult result) throws CameraAccessException {
                Face[] faceArr = (Face[]) result.get(CaptureResult.STATISTICS_FACES);
                if (faceArr == null || faceArr.length <= 0) {
                    VideoCaptureCamera2.this.mAFAERegions = VideoCaptureCamera2.ZERO_WEIGHT_3A_REGION;
                    return;
                }
                if (System.currentTimeMillis() - this.mLastFocusedTs < C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
                    if (faceArr[0].getScore() > 20) {
                        notifyCameraFocusAreaChanged((Rect) result.get(CaptureResult.SCALER_CROP_REGION), faceArr[0].getBounds());
                        return;
                    }
                    return;
                }
                if (faceArr[0].getScore() <= 50) {
                    return;
                }
                VideoCaptureCamera2.this.mAFAERegions = new MeteringRectangle[]{new MeteringRectangle(faceArr[0].getBounds(), 1000)};
                if (VideoCaptureCamera2.this.mPreviewBuilder == null) {
                    return;
                }
                VideoCaptureCamera2 videoCaptureCamera2 = VideoCaptureCamera2.this;
                videoCaptureCamera2.addRegionsToCaptureRequestBuilder(videoCaptureCamera2.mPreviewBuilder);
                if (VideoCaptureCamera2.this.mCameraState != CameraState.STARTED) {
                    return;
                }
                try {
                    Rect rect = (Rect) result.get(CaptureResult.SCALER_CROP_REGION);
                    Logging.d(VideoCaptureCamera2.TAG, "cropRegion = " + rect.toString());
                    Logging.d(VideoCaptureCamera2.TAG, "capture size wxh = " + VideoCaptureCamera2.this.mCaptureWidth + " x " + VideoCaptureCamera2.this.mCaptureHeight);
                    notifyCameraFocusAreaChanged(rect, faceArr[0].getBounds());
                    VideoCaptureCamera2.this.mCaptureSession.capture(VideoCaptureCamera2.this.mPreviewBuilder.build(), VideoCaptureCamera2.this.mCaptureCallback, null);
                    VideoCaptureCamera2.this.createCaptureRequest();
                    this.mLastFocusedTs = System.currentTimeMillis();
                } catch (Exception e2) {
                    Logging.e(VideoCaptureCamera2.TAG, "capture: " + e2);
                }
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) throws CameraAccessException {
                if (VideoCaptureCamera2.this.mIsAutoFaceFocusEnabled && VideoCaptureCamera2.this.isAutoFaceFocusSupported()) {
                    process(result);
                }
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
            }
        };
        this.mAfCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: io.agora.rtc.video.VideoCaptureCamera2.3
            private void process(CaptureResult result) throws CameraAccessException {
                Integer num = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
                if (num == null || VideoCaptureCamera2.this.mPreviewBuilder == null) {
                    return;
                }
                if (4 == num.intValue() || 5 == num.intValue()) {
                    VideoCaptureCamera2.this.mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                    VideoCaptureCamera2.this.startNormalPreview();
                }
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) throws CameraAccessException {
                process(result);
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) throws CameraAccessException {
                process(partialResult);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRegionsToCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
        builder.set(CaptureRequest.CONTROL_AE_REGIONS, this.mAFAERegions);
        builder.set(CaptureRequest.CONTROL_AF_REGIONS, this.mAFAERegions);
        builder.set(CaptureRequest.CONTROL_AF_MODE, 1);
        builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
        builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeCameraStateAndNotify(CameraState state) {
        synchronized (this.mCameraStateLock) {
            this.mCameraState = state;
            this.mCameraStateLock.notifyAll();
        }
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int createCapabilities(int id, Context context) {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(context, id);
        if (cameraCharacteristics == null) {
            return -1;
        }
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            Logging.e(TAG, "Failed to create capabilities");
            return -1;
        }
        try {
            Logging.i(TAG, "dump configuration map:" + streamConfigurationMap.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(streamConfigurationMap.getOutputSizes(35)));
        if ("SM-G9300".equals(Build.MODEL)) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (((Size) arrayList.get(i2)).getHeight() >= 720) {
                    arrayList2.add(arrayList.get(i2));
                }
            }
            arrayList = arrayList2;
        }
        String str = "\"id\":" + id + ",";
        String str2 = "";
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            int width = ((Size) arrayList.get(i3)).getWidth();
            int height = ((Size) arrayList.get(i3)).getHeight();
            if (width >= 240 && height >= 240 && (width >= 320 || height >= 320)) {
                String str3 = "{\"w\":" + width + ",\"h\":" + height + "}";
                str2 = str2.isEmpty() ? str3 : str2 + "," + str3;
            }
        }
        VideoCapture.cacheCapability(id, context, StrPool.DELIM_START + str + "\"resolution\":" + StrPool.BRACKET_START + str2 + "],\"format\":" + StrPool.BRACKET_START + ("" + VideoCapture.translateToEngineFormat(35)) + "],\"fps\":" + StrPool.BRACKET_START + "30]}", getCaptureName());
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int createCaptureRequest() throws CameraAccessException {
        CaptureRequest.Builder builder = this.mPreviewBuilder;
        if (builder == null) {
            return -1;
        }
        try {
            this.mCaptureSession.setRepeatingRequest(builder.build(), this.mCaptureCallback, null);
            return 0;
        } catch (CameraAccessException e2) {
            Logging.e(TAG, "setRepeatingRequest: ", e2);
            return -1;
        } catch (IllegalArgumentException e3) {
            Logging.e(TAG, "setRepeatingRequest: ", e3);
            return -2;
        } catch (IllegalStateException e4) {
            Logging.e(TAG, "capture:" + e4);
            return -4;
        } catch (SecurityException e5) {
            Logging.e(TAG, "setRepeatingRequest: ", e5);
            return -3;
        }
    }

    private Rect cropRegionForZoom(float ratio) {
        int iWidth = this.mSensorRect.width() / 2;
        int iHeight = this.mSensorRect.height() / 2;
        int iWidth2 = (int) ((this.mSensorRect.width() * 0.5f) / ratio);
        int iHeight2 = (int) ((this.mSensorRect.height() * 0.5f) / ratio);
        return new Rect(iWidth - iWidth2, iHeight - iHeight2, iWidth + iWidth2, iHeight + iHeight2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int doStartCapture() throws CameraAccessException {
        int bitsPerPixel = ((this.mCaptureWidth * this.mCaptureHeight) * ImageFormat.getBitsPerPixel(this.mCaptureFormat)) / 8;
        this.mExpectedFrameSize = bitsPerPixel;
        this.mCaptureData = new byte[bitsPerPixel];
        this.mImageReader = ImageReader.newInstance(this.mCaptureWidth, this.mCaptureHeight, this.mCaptureFormat, 2);
        if (this.mPreviewThread == null) {
            HandlerThread handlerThread = new HandlerThread("CameraPreview");
            this.mPreviewThread = handlerThread;
            handlerThread.start();
        }
        Handler handler = new Handler(this.mPreviewThread.getLooper());
        this.mImageReader.setOnImageAvailableListener(new ImageReaderListener(), handler);
        try {
            CaptureRequest.Builder builderCreateCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
            this.mPreviewBuilder = builderCreateCaptureRequest;
            if (builderCreateCaptureRequest == null) {
                Logging.e(TAG, "mPreviewBuilder error");
                return -4;
            }
            builderCreateCaptureRequest.addTarget(this.mImageReader.getSurface());
            this.mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, 1);
            this.mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, 3);
            this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
            setFaceDetect(this.mPreviewBuilder, this.mFaceDetectMode);
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(this.mImageReader.getSurface());
            try {
                this.mCameraDevice.createCaptureSession(arrayList, new CaptureSessionListener(), null);
                return 0;
            } catch (CameraAccessException e2) {
                Logging.e(TAG, "createCaptureSession :", e2);
                return -1;
            } catch (IllegalArgumentException e3) {
                Logging.e(TAG, "createCaptureSession :", e3);
                return -2;
            } catch (SecurityException e4) {
                Logging.e(TAG, "createCaptureSession :", e4);
                return -3;
            }
        } catch (CameraAccessException e5) {
            Logging.e(TAG, "createCaptureRequest: ", e5);
            return -1;
        } catch (IllegalArgumentException e6) {
            Logging.e(TAG, "createCaptureRequest: ", e6);
            return -2;
        } catch (SecurityException e7) {
            Logging.e(TAG, "createCaptureRequest ", e7);
            return -3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int doStopCapture() {
        HandlerThread handlerThread = this.mPreviewThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mPreviewThread = null;
        }
        try {
            try {
                try {
                    try {
                        try {
                            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
                            if (cameraCaptureSession != null) {
                                cameraCaptureSession.abortCaptures();
                                this.mCaptureSession = null;
                            }
                            ImageReader imageReader = this.mImageReader;
                            if (imageReader != null) {
                                imageReader.setOnImageAvailableListener(null, null);
                                this.mImageReader.close();
                                this.mImageReader = null;
                            }
                            CameraDevice cameraDevice = this.mCameraDevice;
                            if (cameraDevice == null) {
                                return 0;
                            }
                            cameraDevice.close();
                            this.mCameraDevice = null;
                            return 0;
                        } catch (CameraAccessException e2) {
                            Logging.e(TAG, "abortCaptures: ", e2);
                            ImageReader imageReader2 = this.mImageReader;
                            if (imageReader2 != null) {
                                imageReader2.setOnImageAvailableListener(null, null);
                                this.mImageReader.close();
                                this.mImageReader = null;
                            }
                            CameraDevice cameraDevice2 = this.mCameraDevice;
                            if (cameraDevice2 != null) {
                                cameraDevice2.close();
                                this.mCameraDevice = null;
                            }
                            return -1;
                        }
                    } catch (Exception e3) {
                        Logging.e(TAG, "abortCaptures: ", e3);
                        ImageReader imageReader3 = this.mImageReader;
                        if (imageReader3 != null) {
                            imageReader3.setOnImageAvailableListener(null, null);
                            this.mImageReader.close();
                            this.mImageReader = null;
                        }
                        CameraDevice cameraDevice3 = this.mCameraDevice;
                        if (cameraDevice3 != null) {
                            cameraDevice3.close();
                            this.mCameraDevice = null;
                        }
                        return -1;
                    }
                } catch (IllegalStateException e4) {
                    Logging.e(TAG, "abortCaptures: ", e4);
                    ImageReader imageReader4 = this.mImageReader;
                    if (imageReader4 != null) {
                        imageReader4.setOnImageAvailableListener(null, null);
                        this.mImageReader.close();
                        this.mImageReader = null;
                    }
                    CameraDevice cameraDevice4 = this.mCameraDevice;
                    if (cameraDevice4 != null) {
                        cameraDevice4.close();
                        this.mCameraDevice = null;
                    }
                    return -1;
                }
            } catch (IllegalArgumentException e5) {
                Logging.e(TAG, "abortCaptures: ", e5);
                ImageReader imageReader5 = this.mImageReader;
                if (imageReader5 != null) {
                    imageReader5.setOnImageAvailableListener(null, null);
                    this.mImageReader.close();
                    this.mImageReader = null;
                }
                CameraDevice cameraDevice5 = this.mCameraDevice;
                if (cameraDevice5 != null) {
                    cameraDevice5.close();
                    this.mCameraDevice = null;
                }
                return -1;
            }
        } catch (Throwable th) {
            ImageReader imageReader6 = this.mImageReader;
            if (imageReader6 != null) {
                imageReader6.setOnImageAvailableListener(null, null);
                this.mImageReader.close();
                this.mImageReader = null;
            }
            CameraDevice cameraDevice6 = this.mCameraDevice;
            if (cameraDevice6 != null) {
                cameraDevice6.close();
                this.mCameraDevice = null;
            }
            throw th;
        }
    }

    private static CameraCharacteristics getCameraCharacteristics(Context appContext, int id) {
        try {
            return ((CameraManager) appContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA)).getCameraCharacteristics(Integer.toString(id));
        } catch (CameraAccessException e2) {
            Logging.i(TAG, "getNumberOfCameras: getCameraIdList(): " + e2);
            return null;
        } catch (Exception e3) {
            Logging.i(TAG, "getNumberOfCameras: got exception: " + e3);
            return null;
        }
    }

    public static String getCaptureName() {
        return "camera2";
    }

    public static String getName(int id, Context appContext) {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(appContext, id);
        if (cameraCharacteristics == null) {
            return null;
        }
        int iIntValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("camera2 ");
        sb.append(id);
        sb.append(", facing ");
        sb.append(iIntValue == 0 ? "front" : "back");
        return sb.toString();
    }

    public static int getNumberOfCameras(Context appContext) {
        try {
            return ((CameraManager) appContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA)).getCameraIdList().length;
        } catch (Exception e2) {
            Logging.e(TAG, "getNumberOfCameras: getCameraIdList(): ", e2);
            return 0;
        }
    }

    public static int getSensorOrientation(int id, Context appContext) {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(appContext, id);
        if (cameraCharacteristics == null) {
            return -1;
        }
        return ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
    }

    public static boolean isLegacyDevice(Context appContext, int id) {
        try {
            CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(appContext, id);
            if (cameraCharacteristics != null) {
                if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                    return true;
                }
            }
            return false;
        } catch (Throwable unused) {
            Logging.w(TAG, "this is a legacy camera device");
            return true;
        }
    }

    private boolean isMeteringAreaAFSupported() {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics != null) {
            return ((Integer) cameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() >= 1;
        }
        Logging.w(TAG, "warning cameraCharacteristics is null");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readImageIntoBuffer(Image image, byte[] data) {
        int width = image.getWidth();
        int height = image.getHeight();
        Image.Plane[] planes = image.getPlanes();
        int i2 = 0;
        int i3 = 0;
        while (i2 < planes.length) {
            ByteBuffer buffer = planes[i2].getBuffer();
            if (buffer == null) {
                Logging.e(TAG, "plane " + i2 + " buffer is null ");
                return;
            }
            int rowStride = planes[i2].getRowStride();
            int pixelStride = planes[i2].getPixelStride();
            int i4 = i2 == 0 ? width : width / 2;
            int i5 = i2 == 0 ? height : height / 2;
            if (pixelStride == 1 && rowStride == i4) {
                int i6 = i4 * i5;
                buffer.get(data, i3, i6);
                i3 += i6;
            } else {
                byte[] bArr = new byte[rowStride];
                for (int i7 = 0; i7 < i5 - 1; i7++) {
                    buffer.get(bArr, 0, rowStride);
                    int i8 = 0;
                    while (i8 < i4) {
                        data[i3] = bArr[i8 * pixelStride];
                        i8++;
                        i3++;
                    }
                }
                buffer.get(bArr, 0, Math.min(rowStride, buffer.remaining()));
                int i9 = 0;
                while (i9 < i4) {
                    data[i3] = bArr[i9 * pixelStride];
                    i9++;
                    i3++;
                }
            }
            i2++;
        }
    }

    private void setFaceDetect(CaptureRequest.Builder requestBuilder, int faceDetectMode) {
        if (this.mFaceDetectSupported) {
            requestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, Integer.valueOf(faceDetectMode));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNormalPreview() throws CameraAccessException {
        CaptureRequest.Builder builder = this.mPreviewBuilder;
        if (builder == null) {
            return;
        }
        builder.set(CaptureRequest.CONTROL_AF_MODE, 3);
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
        try {
            this.mCaptureSession.setRepeatingRequest(this.mPreviewBuilder.build(), this.mCaptureCallback, new Handler(this.mPreviewThread.getLooper()));
        } catch (CameraAccessException e2) {
            Logging.e(TAG, "setRepeatingRequest failed, error message : " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int tryOpenCamera() throws CameraAccessException {
        try {
            this.mManager.openCamera(Integer.toString(this.mId), new CrStateListener(), this.mStateHandler);
            return 0;
        } catch (CameraAccessException e2) {
            Logging.e(TAG, "allocate: manager.openCamera: ", e2);
            return -1;
        } catch (IllegalArgumentException e3) {
            Logging.e(TAG, "allocate: manager.openCamera: ", e3);
            return -2;
        } catch (SecurityException e4) {
            Logging.e(TAG, "allocate: manager.openCamera: ", e4);
            return -3;
        } catch (Exception e5) {
            Logging.e(TAG, "unknown error", e5);
            return -4;
        }
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int UnRegisterNativeHandle() {
        this.mNativeVideoCaptureDeviceAndroid = 0L;
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int allocate() {
        synchronized (this.mCameraStateLock) {
            if (this.mCameraState == CameraState.OPENING) {
                Logging.e(TAG, "allocate() invoked while Camera is busy opening/configuring");
                return -1;
            }
            CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
            if (cameraCharacteristics == null) {
                return -1;
            }
            if (VideoCapture.fetchCapability(this.mId, this.mContext, getCaptureName()) == null) {
                createCapabilities(this.mId, this.mContext);
            }
            long j2 = this.mNativeVideoCaptureDeviceAndroid;
            if (j2 != 0) {
                this.mIsAutoFaceFocusEnabled = isAutoFaceFocusEnabled(j2);
            }
            this.mCameraNativeOrientation = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
            this.mManager = (CameraManager) this.mContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
            int[] iArr = (int[]) cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
            int iIntValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT)).intValue();
            if (iArr.length > 1 && iIntValue > 0) {
                this.mFaceDetectSupported = true;
                int i2 = 0;
                for (int i3 : iArr) {
                    i2 += i3;
                }
                if (i2 % 2 != 0) {
                    this.mFaceDetectMode = 1;
                } else {
                    this.mFaceDetectMode = 2;
                }
            }
            Logging.i(TAG, "allocate() face detection: " + this.mFaceDetectMode + " " + iIntValue + " " + this.mFaceDetectSupported);
            if (this.mCameraStateThread == null) {
                HandlerThread handlerThread = new HandlerThread("CameraCallbackThread");
                this.mCameraStateThread = handlerThread;
                handlerThread.start();
                this.mStateHandler = new Handler(this.mCameraStateThread.getLooper());
            }
            this.mManager.registerAvailabilityCallback(this.mAvailabilityCallback, this.mStateHandler);
            return 0;
        }
    }

    @Override // io.agora.rtc.video.VideoCapture
    public void deallocate() {
        CameraManager cameraManager = this.mManager;
        if (cameraManager != null) {
            cameraManager.unregisterAvailabilityCallback(this.mAvailabilityCallback);
            HandlerThread handlerThread = this.mCameraStateThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.mCameraStateThread = null;
                this.mStateHandler = null;
            }
        }
    }

    @Override // io.agora.rtc.video.VideoCapture
    public float getMaxZoom() {
        if (this.mMaxZoom <= 0.0f) {
            CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
            if (cameraCharacteristics == null) {
                Logging.w(TAG, "warning cameraCharacteristics is null");
                return -1.0f;
            }
            this.mMaxZoom = ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
        }
        return this.mMaxZoom;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isAutoFaceFocusSupported() {
        if (!isFocusSupported()) {
            return false;
        }
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics != null) {
            return ((Integer) cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT)).intValue() > 0;
        }
        Logging.w(TAG, "warning cameraCharacteristics is null");
        return false;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isExposureSupported() {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics == null) {
            Logging.w(TAG, "warning cameraCharacteristics is null");
            return false;
        }
        int[] iArr = (int[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
        if (iArr != null) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                Logging.d(TAG, "isExposureSupported AE mode = " + iArr[i2]);
                if (1 == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isFocusSupported() {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics == null) {
            Logging.w(TAG, "warning cameraCharacteristics is null");
            return false;
        }
        int[] iArr = (int[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        if (iArr != null) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (1 == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isTorchSupported() {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics == null) {
            Logging.w(TAG, "warning cameraCharacteristics is null");
            return false;
        }
        Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // io.agora.rtc.video.VideoCapture
    public boolean isZoomSupported() {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics != null) {
            return ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue() > 1.0f;
        }
        Logging.w(TAG, "warning cameraCharacteristics is null");
        return false;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setAutoFaceFocus(boolean enable) {
        this.mIsAutoFaceFocusEnabled = enable;
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setCaptureFormat(int format) {
        if (VideoCapture.translateToAndroidFormat(format) == this.mCaptureFormat) {
            return 0;
        }
        Logging.e(TAG, "For camera2 api, only YUV_420_888 format are supported");
        return -1;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setExposure(float valX, float valY, boolean inPreview) throws CameraAccessException {
        int i2;
        int i3;
        Logging.d(TAG, "setExposure called camera api2");
        if (valX < 0.0f || valX > 1.0f || valY < 0.0f || valY > 1.0f) {
            Logging.e(TAG, "set exposure unreasonable inputs");
            return -1;
        }
        CaptureRequest.Builder builder = this.mPreviewBuilder;
        if (builder == null) {
            Logging.d(TAG, "setExposure mPreviewBuilder is null");
            return -1;
        }
        double d3 = valX;
        double d4 = valY;
        Rect rect = (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION);
        if (rect == null) {
            return -1;
        }
        int iWidth = rect.width();
        int iHeight = rect.height();
        Logging.d(TAG, "crop width = " + iWidth + " crop height = " + iHeight + " capture width = " + this.mCaptureWidth + " capture height = " + this.mCaptureHeight);
        int i4 = this.mCaptureHeight;
        int i5 = iWidth * i4;
        int i6 = this.mCaptureWidth;
        if (i5 > iHeight * i6) {
            i3 = (int) (((iWidth - r12) / 2.0f) + (d3 * ((i6 * iHeight) / i4)));
            i2 = (int) (d4 * iHeight);
        } else {
            int i7 = (int) (d3 * iWidth);
            i2 = (int) (((iHeight - r2) / 2.0f) + (d4 * ((i4 * iWidth) / i6)));
            i3 = i7;
        }
        Rect rect2 = new Rect();
        double d5 = i3;
        double d6 = iWidth * 0.05d;
        rect2.left = clamp((int) (d5 - d6), 0, iWidth);
        rect2.right = clamp((int) (d5 + d6), 0, iWidth);
        double d7 = i2;
        double d8 = iHeight * 0.05d;
        rect2.top = clamp((int) (d7 - d8), 0, iHeight);
        rect2.bottom = clamp((int) (d7 + d8), 0, iHeight);
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect2, 1000)});
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
        CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
        if (cameraCaptureSession != null) {
            try {
                cameraCaptureSession.setRepeatingRequest(this.mPreviewBuilder.build(), null, null);
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
                return -1;
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
                return -1;
            }
        }
        long j2 = this.mNativeVideoCaptureDeviceAndroid;
        if (j2 != 0) {
            NotifyCameraExposureAreaChanged(valX, valY, 0.0f, 0.0f, j2);
        }
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setFocus(float valX, float valY, boolean inPreview) throws CameraAccessException {
        Rect rect;
        int i2;
        int i3;
        if (valX < 0.0f || valX > 1.0f || valY < 0.0f || valY > 1.0f) {
            Logging.e(TAG, "set focus unreasonable inputs");
            return -1;
        }
        CaptureRequest.Builder builder = this.mPreviewBuilder;
        if (builder == null) {
            Logging.d(TAG, "setFocus mPreviewBuilder is null");
            return -1;
        }
        double d3 = valX;
        double d4 = valY;
        if (builder == null || (rect = (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION)) == null) {
            return -1;
        }
        int iWidth = rect.width();
        int iHeight = rect.height();
        Log.d(Constants.ANSWER_MODE.TEST_MODE, "crop width = " + iWidth + " crop height = " + iHeight + " capture width = " + this.mCaptureWidth + " capture height = " + this.mCaptureHeight);
        int i4 = this.mCaptureHeight;
        int i5 = iWidth * i4;
        int i6 = this.mCaptureWidth;
        if (i5 > iHeight * i6) {
            i3 = (int) (((iWidth - r12) / 2.0f) + (d3 * ((i6 * iHeight) / i4)));
            i2 = (int) (d4 * iHeight);
        } else {
            int i7 = (int) (d3 * iWidth);
            i2 = (int) (((iHeight - r4) / 2.0f) + (d4 * ((i4 * iWidth) / i6)));
            i3 = i7;
        }
        Rect rect2 = new Rect();
        double d5 = i3;
        double d6 = iWidth * 0.05d;
        rect2.left = clamp((int) (d5 - d6), 0, iWidth);
        rect2.right = clamp((int) (d5 + d6), 0, iWidth);
        double d7 = i2;
        double d8 = iHeight * 0.05d;
        rect2.top = clamp((int) (d7 - d8), 0, iHeight);
        rect2.bottom = clamp((int) (d7 + d8), 0, iHeight);
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect2, 1000)});
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect2, 1000)});
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, 1);
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
        this.mPreviewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
        if (this.mPreviewThread != null) {
            Handler handler = new Handler(this.mPreviewThread.getLooper());
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.setRepeatingRequest(this.mPreviewBuilder.build(), this.mAfCaptureCallback, handler);
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                    return -1;
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                    return -1;
                }
            }
            long j2 = this.mNativeVideoCaptureDeviceAndroid;
            if (j2 != 0) {
                NotifyCameraFocusAreaChanged(valX, valY, 0.0f, 0.0f, j2);
            }
        }
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setTorchMode(boolean isTorchOn) throws CameraAccessException {
        Log.d("flash", "setFlashMode isTorchOn " + isTorchOn);
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
        if (cameraCharacteristics == null) {
            Logging.w(TAG, "warning cameraCharacteristics is null");
            return -1;
        }
        if (this.mPreviewBuilder == null) {
            return -1;
        }
        Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if (!(bool == null ? false : bool.booleanValue())) {
            Logging.w(TAG, "flash is not supported");
        } else if (this.mPreviewThread != null && this.mPreviewBuilder != null) {
            Handler handler = new Handler(this.mPreviewThread.getLooper());
            if (isTorchOn) {
                this.mPreviewBuilder.set(CaptureRequest.FLASH_MODE, 2);
            } else {
                this.mPreviewBuilder.set(CaptureRequest.FLASH_MODE, 0);
            }
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.setRepeatingRequest(this.mPreviewBuilder.build(), null, handler);
                    return 0;
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return -1;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int setZoom(float zoomValue) throws CameraAccessException {
        Log.d("zoom", "setCameraZoom api2 called zoomValue =" + zoomValue);
        if (this.mPreviewBuilder == null) {
            Logging.d(TAG, "setZoom mPreviewBuilder is null");
            return -1;
        }
        if (this.mSensorRect == null) {
            CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(this.mContext, this.mId);
            if (cameraCharacteristics == null) {
                Logging.w(TAG, "warning cameraCharacteristics is null");
                return -1;
            }
            this.mSensorRect = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            this.mMaxZoom = ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
        }
        if (Math.abs(this.mMaxZoom - 1.0f) < 0.001f) {
            Logging.w(TAG, "Camera " + this.mId + " does not support camera zoom");
            return -1;
        }
        this.mCurZoomRatio = zoomValue;
        if (!(zoomValue > 1.0f && zoomValue <= this.mMaxZoom && zoomValue != this.mLastZoomRatio)) {
            return -2;
        }
        Rect rectCropRegionForZoom = cropRegionForZoom(zoomValue);
        CaptureRequest.Builder builder = this.mPreviewBuilder;
        if (builder == null) {
            return -1;
        }
        builder.set(CaptureRequest.SCALER_CROP_REGION, rectCropRegionForZoom);
        this.mLastZoomRatio = this.mCurZoomRatio;
        if (this.mPreviewThread != null) {
            Handler handler = new Handler(this.mPreviewThread.getLooper());
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.setRepeatingRequest(this.mPreviewBuilder.build(), this.mCaptureCallback, handler);
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                    return -3;
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                    return -4;
                }
            }
        }
        return 0;
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int startCapture(int width, int height, int frameRate) throws CameraAccessException {
        CameraState cameraState;
        CameraState cameraState2;
        Logging.d(TAG, "startCapture, w=" + width + ", h=" + height + ", fps=" + frameRate);
        this.mCaptureWidth = width;
        this.mCaptureHeight = height;
        this.mCaptureFps = frameRate;
        synchronized (this.mCameraStateLock) {
            while (true) {
                cameraState = this.mCameraState;
                cameraState2 = CameraState.STARTED;
                if (cameraState == cameraState2 || cameraState == CameraState.EVICTED || cameraState == CameraState.STOPPED) {
                    break;
                }
                try {
                    this.mCameraStateLock.wait();
                } catch (InterruptedException e2) {
                    Logging.e(TAG, "CaptureStartedEvent: ", e2);
                }
            }
            if (cameraState == cameraState2) {
                return 0;
            }
            changeCameraStateAndNotify(CameraState.OPENING);
            int iTryOpenCamera = tryOpenCamera();
            if (iTryOpenCamera != 0) {
                changeCameraStateAndNotify(CameraState.STOPPED);
            }
            return iTryOpenCamera;
        }
    }

    @Override // io.agora.rtc.video.VideoCapture
    public int stopCapture() {
        CameraState cameraState;
        synchronized (this.mCameraStateLock) {
            while (true) {
                cameraState = this.mCameraState;
                if (cameraState == CameraState.STARTED || cameraState == CameraState.EVICTED || cameraState == CameraState.STOPPED) {
                    break;
                }
                try {
                    this.mCameraStateLock.wait();
                } catch (InterruptedException e2) {
                    Logging.e(TAG, "CaptureStartedEvent: ", e2);
                }
            }
            if (cameraState == CameraState.EVICTED) {
                this.mCameraState = CameraState.STOPPED;
            }
            CameraState cameraState2 = this.mCameraState;
            CameraState cameraState3 = CameraState.STOPPED;
            if (cameraState2 == cameraState3) {
                return 0;
            }
            doStopCapture();
            this.mCameraState = cameraState3;
            this.mCameraStateLock.notifyAll();
            return 0;
        }
    }
}
