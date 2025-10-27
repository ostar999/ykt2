package io.agora.rtc.mediaio;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.WindowManager;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import io.agora.rtc.internal.Logging;
import io.agora.rtc.mediaio.MediaIO;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
/* loaded from: classes8.dex */
public class AgoraBufferedCamera2 extends CameraSource {
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final String TAG = "AgoraBufferedCamera2";
    private int cameraOrientation;
    private boolean isCameraFrontFacing;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private byte[] mBufferArrayData;
    private ByteBuffer mByteBufferData;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private CameraCaptureSession mCaptureSession;
    private CameraCharacteristics mCharacteristics;
    private Context mContext;
    private boolean mFlashSupported;
    private ImageReader mImageReader;
    private CaptureParameters mParameters;
    private CaptureRequest mPreviewRequest;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private int mState = 0;
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: io.agora.rtc.mediaio.AgoraBufferedCamera2.1
        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader) {
            Image image = null;
            try {
                try {
                    Image imageAcquireLatestImage = reader.acquireLatestImage();
                    if (imageAcquireLatestImage == null) {
                        if (imageAcquireLatestImage != null) {
                            imageAcquireLatestImage.close();
                            return;
                        }
                        return;
                    }
                    if (imageAcquireLatestImage.getFormat() == 35 && imageAcquireLatestImage.getPlanes().length == 3) {
                        if (reader.getWidth() != imageAcquireLatestImage.getWidth() || reader.getHeight() != imageAcquireLatestImage.getHeight()) {
                            throw new IllegalStateException("ImageReader size " + reader.getWidth() + "x" + reader.getHeight() + " did not match Image size: " + imageAcquireLatestImage.getWidth() + "x" + imageAcquireLatestImage.getHeight());
                        }
                        AgoraBufferedCamera2.readImageIntoBuffer(imageAcquireLatestImage, AgoraBufferedCamera2.this.mBufferArrayData);
                        int frameOrientation = AgoraBufferedCamera2.this.getFrameOrientation();
                        AgoraBufferedCamera2 agoraBufferedCamera2 = AgoraBufferedCamera2.this;
                        if (agoraBufferedCamera2.consumer == null || agoraBufferedCamera2.mParameters.bufferType != MediaIO.BufferType.BYTE_ARRAY.intValue()) {
                            AgoraBufferedCamera2 agoraBufferedCamera22 = AgoraBufferedCamera2.this;
                            if (agoraBufferedCamera22.consumer != null && agoraBufferedCamera22.mParameters.bufferType == MediaIO.BufferType.BYTE_BUFFER.intValue()) {
                                AgoraBufferedCamera2.this.mByteBufferData.rewind();
                                AgoraBufferedCamera2.this.mByteBufferData.put(AgoraBufferedCamera2.this.mBufferArrayData, 0, AgoraBufferedCamera2.this.mBufferArrayData.length);
                                AgoraBufferedCamera2 agoraBufferedCamera23 = AgoraBufferedCamera2.this;
                                agoraBufferedCamera23.consumer.consumeByteBufferFrame(agoraBufferedCamera23.mByteBufferData, AgoraBufferedCamera2.this.mParameters.pixelFormat, imageAcquireLatestImage.getWidth(), imageAcquireLatestImage.getHeight(), frameOrientation, System.currentTimeMillis());
                            }
                        } else {
                            AgoraBufferedCamera2 agoraBufferedCamera24 = AgoraBufferedCamera2.this;
                            agoraBufferedCamera24.consumer.consumeByteArrayFrame(agoraBufferedCamera24.mBufferArrayData, AgoraBufferedCamera2.this.mParameters.pixelFormat, imageAcquireLatestImage.getWidth(), imageAcquireLatestImage.getHeight(), frameOrientation, System.currentTimeMillis());
                        }
                        imageAcquireLatestImage.close();
                        return;
                    }
                    Logging.e(AgoraBufferedCamera2.TAG, "Unexpected image format: " + imageAcquireLatestImage.getFormat() + "or #planes:" + imageAcquireLatestImage.getPlanes().length);
                    imageAcquireLatestImage.close();
                } catch (IllegalStateException e2) {
                    Log.e(AgoraBufferedCamera2.TAG, "acquireLastest Image():", e2);
                    if (0 != 0) {
                        image.close();
                    }
                } catch (Exception unused) {
                    Log.e(AgoraBufferedCamera2.TAG, "fetch image failed...");
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
    };
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() { // from class: io.agora.rtc.mediaio.AgoraBufferedCamera2.2
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            AgoraBufferedCamera2.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            AgoraBufferedCamera2.this.mCameraDevice = null;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int error) {
            AgoraBufferedCamera2.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            AgoraBufferedCamera2.this.mCameraDevice = null;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) throws CameraAccessException {
            AgoraBufferedCamera2.this.mCameraOpenCloseLock.release();
            AgoraBufferedCamera2.this.mCameraDevice = cameraDevice;
            AgoraBufferedCamera2.this.createCameraPreviewSession();
        }
    };
    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: io.agora.rtc.mediaio.AgoraBufferedCamera2.3
        private void process(CaptureResult result) {
            Integer num;
            Integer num2;
            int i2 = AgoraBufferedCamera2.this.mState;
            if (i2 == 1) {
                Integer num3 = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
                if (num3 == null) {
                    return;
                }
                if ((4 == num3.intValue() || 5 == num3.intValue()) && (num = (Integer) result.get(CaptureResult.CONTROL_AE_STATE)) != null) {
                    num.intValue();
                    return;
                }
                return;
            }
            if (i2 != 2) {
                if (i2 == 3 && (num2 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE)) != null) {
                    num2.intValue();
                    return;
                }
                return;
            }
            Integer num4 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
            if (num4 == null || num4.intValue() == 5 || num4.intValue() == 4) {
                AgoraBufferedCamera2.this.mState = 3;
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            process(result);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
            process(partialResult);
        }
    };

    public static class CompareSizesByArea implements Comparator<Size> {
        @Override // java.util.Comparator
        public int compare(Size lhs, Size rhs) {
            return Long.signum((lhs.getWidth() * lhs.getHeight()) - (rhs.getWidth() * rhs.getHeight()));
        }
    }

    public AgoraBufferedCamera2(Context context) {
        this.mContext = context;
        CaptureParameters captureParameters = new CaptureParameters();
        this.mParameters = captureParameters;
        captureParameters.width = 640;
        captureParameters.height = 480;
        captureParameters.fps = 15;
        captureParameters.pixelFormat = MediaIO.PixelFormat.I420.intValue();
        this.mParameters.bufferType = MediaIO.BufferType.BYTE_BUFFER.intValue();
    }

    private void allocateBuffer(int pixelFormat) {
        int bitsPerPixel;
        if (pixelFormat == MediaIO.PixelFormat.I420.intValue()) {
            CaptureParameters captureParameters = this.mParameters;
            bitsPerPixel = ((captureParameters.width * captureParameters.height) * ImageFormat.getBitsPerPixel(35)) / 8;
        } else {
            bitsPerPixel = 0;
        }
        if (this.mParameters.bufferType == MediaIO.BufferType.BYTE_ARRAY.intValue()) {
            this.mBufferArrayData = new byte[bitsPerPixel];
        } else if (this.mParameters.bufferType == MediaIO.BufferType.BYTE_BUFFER.intValue()) {
            this.mBufferArrayData = new byte[bitsPerPixel];
            this.mByteBufferData = ByteBuffer.allocateDirect(bitsPerPixel);
        }
    }

    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int width = aspectRatio.getWidth();
        int height = aspectRatio.getHeight();
        for (Size size : choices) {
            if (size.getWidth() <= maxWidth && size.getHeight() <= maxHeight && size.getHeight() == (size.getWidth() * height) / width) {
                if (size.getWidth() < textureViewWidth || size.getHeight() < textureViewHeight) {
                    arrayList2.add(size);
                } else {
                    arrayList.add(size);
                }
            }
        }
        if (arrayList.size() > 0) {
            return (Size) Collections.min(arrayList, new CompareSizesByArea());
        }
        if (arrayList2.size() > 0) {
            return (Size) Collections.max(arrayList2, new CompareSizesByArea());
        }
        Log.e(TAG, "Couldn't find any suitable preview size");
        return choices[0];
    }

    private void closeCamera() {
        try {
            try {
                this.mCameraOpenCloseLock.acquire();
                CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
                if (cameraCaptureSession != null) {
                    cameraCaptureSession.close();
                    this.mCaptureSession = null;
                }
                CameraDevice cameraDevice = this.mCameraDevice;
                if (cameraDevice != null) {
                    cameraDevice.close();
                    this.mCameraDevice = null;
                }
                ImageReader imageReader = this.mImageReader;
                if (imageReader != null) {
                    imageReader.close();
                    this.mImageReader = null;
                }
            } catch (InterruptedException e2) {
                throw new RuntimeException("Interrupted while trying to lock camera closing.", e2);
            }
        } finally {
            this.mCameraOpenCloseLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createCameraPreviewSession() throws CameraAccessException {
        try {
            CaptureRequest.Builder builderCreateCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
            this.mPreviewRequestBuilder = builderCreateCaptureRequest;
            builderCreateCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, 1);
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 3);
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.FALSE);
            if (this.mFlashSupported) {
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 2);
            }
            this.mPreviewRequestBuilder.addTarget(this.mImageReader.getSurface());
            this.mCameraDevice.createCaptureSession(Arrays.asList(this.mImageReader.getSurface()), new CameraCaptureSession.StateCallback() { // from class: io.agora.rtc.mediaio.AgoraBufferedCamera2.4
                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Log.e(AgoraBufferedCamera2.TAG, "Configure camera failed");
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(CameraCaptureSession cameraCaptureSession) throws CameraAccessException {
                    if (AgoraBufferedCamera2.this.mCameraDevice == null) {
                        return;
                    }
                    AgoraBufferedCamera2.this.mCaptureSession = cameraCaptureSession;
                    try {
                        AgoraBufferedCamera2 agoraBufferedCamera2 = AgoraBufferedCamera2.this;
                        agoraBufferedCamera2.mPreviewRequest = agoraBufferedCamera2.mPreviewRequestBuilder.build();
                        AgoraBufferedCamera2.this.mCaptureSession.setRepeatingRequest(AgoraBufferedCamera2.this.mPreviewRequest, AgoraBufferedCamera2.this.mCaptureCallback, AgoraBufferedCamera2.this.mBackgroundHandler);
                    } catch (CameraAccessException | IllegalStateException e2) {
                        e2.printStackTrace();
                    }
                }
            }, null);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        }
    }

    private void doStop() throws InterruptedException {
        closeCamera();
        stopBackgroundThread();
    }

    private int getAndroidImageFormat(int mediaioFormat) {
        return mediaioFormat == MediaIO.PixelFormat.I420.intValue() ? 35 : 0;
    }

    private int getDeviceOrientation() {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getFrameOrientation() {
        int deviceOrientation = getDeviceOrientation();
        if (!this.isCameraFrontFacing) {
            deviceOrientation = 360 - deviceOrientation;
        }
        return (this.cameraOrientation + deviceOrientation) % 360;
    }

    private void openCamera(int width, int height) throws CameraAccessException {
        setUpCameraOutputs(width, height);
        CameraManager cameraManager = (CameraManager) this.mContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
        try {
            if (!this.mCameraOpenCloseLock.tryAcquire(2500L, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            cameraManager.openCamera(this.mCameraId, this.mStateCallback, this.mBackgroundHandler);
        } catch (CameraAccessException e2) {
            Log.e(TAG, e2.toString());
        } catch (InterruptedException e3) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e3);
        } catch (SecurityException e4) {
            Log.e(TAG, e4.toString());
        }
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

    private void setUpCameraOutputs(int width, int height) throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) this.mContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
        try {
            for (String str : cameraManager.getCameraIdList()) {
                this.mCameraId = str;
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                this.mCharacteristics = cameraCharacteristics;
                this.cameraOrientation = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
                Boolean bool = (Boolean) this.mCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                this.mFlashSupported = bool == null ? false : bool.booleanValue();
                if (!this.isCameraFrontFacing || ((Integer) this.mCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
                    break;
                }
            }
            CaptureParameters captureParameters = this.mParameters;
            ImageReader imageReaderNewInstance = ImageReader.newInstance(captureParameters.width, captureParameters.height, getAndroidImageFormat(captureParameters.pixelFormat), 2);
            this.mImageReader = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(this.mOnImageAvailableListener, this.mBackgroundHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        } catch (NullPointerException unused) {
        }
    }

    private void startBackgroundThread() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() throws InterruptedException {
        HandlerThread handlerThread = this.mBackgroundThread;
        if (handlerThread == null) {
            return;
        }
        handlerThread.quitSafely();
        try {
            this.mBackgroundThread.join();
            this.mBackgroundThread = null;
            this.mBackgroundHandler = null;
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public int getBufferType() {
        return this.mParameters.bufferType;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onDispose() throws InterruptedException {
        doStop();
        this.mBufferArrayData = null;
        this.mByteBufferData = null;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onInitialize(IVideoFrameConsumer consumer) {
        this.consumer = consumer;
        allocateBuffer(this.mParameters.pixelFormat);
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onStart() throws CameraAccessException {
        startBackgroundThread();
        CaptureParameters captureParameters = this.mParameters;
        openCamera(captureParameters.width, captureParameters.height);
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onStop() throws InterruptedException {
        doStop();
    }

    public void useFrontCamera(boolean front) {
        this.isCameraFrontFacing = front;
    }

    public AgoraBufferedCamera2(Context context, CaptureParameters parameters) {
        this.mContext = context;
        if (parameters != null) {
            CaptureParameters captureParameters = new CaptureParameters();
            this.mParameters = captureParameters;
            captureParameters.width = parameters.width;
            captureParameters.height = parameters.height;
            captureParameters.fps = parameters.fps;
            captureParameters.pixelFormat = parameters.pixelFormat;
            captureParameters.bufferType = parameters.bufferType;
        }
    }
}
