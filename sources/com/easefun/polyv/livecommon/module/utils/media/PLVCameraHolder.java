package com.easefun.polyv.livecommon.module.utils.media;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import com.easefun.polyv.livecommon.module.utils.media.PLVCameraConfiguration;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraHardwareException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraNotSupportException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.DebugKt;

@TargetApi(14)
/* loaded from: classes3.dex */
public class PLVCameraHolder {
    private static final int FOCUS_HEIGHT = 80;
    private static final int FOCUS_WIDTH = 80;
    private static final String TAG = "CameraHolder";
    private static PLVCameraHolder sHolder;
    private PLVCameraData mCameraData;
    private List<PLVCameraData> mCameraDatas;
    private Camera mCameraDevice;
    private SurfaceTexture mTexture;
    private boolean isTouchMode = false;
    private boolean isOpenBackFirst = false;
    private PLVCameraConfiguration mConfiguration = PLVCameraConfiguration.createDefault();
    private State mState = State.INIT;

    public enum State {
        INIT,
        OPENED,
        PREVIEW
    }

    private PLVCameraHolder() {
    }

    public static synchronized PLVCameraHolder instance() {
        if (sHolder == null) {
            sHolder = new PLVCameraHolder();
        }
        return sHolder;
    }

    public float cameraZoom(boolean isBig) {
        Camera camera;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null || this.mCameraData == null) {
            return -1.0f;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (isBig) {
            parameters.setZoom(Math.min(parameters.getZoom() + 1, parameters.getMaxZoom()));
        } else {
            parameters.setZoom(Math.max(parameters.getZoom() - 1, 0));
        }
        this.mCameraDevice.setParameters(parameters);
        return parameters.getZoom() / parameters.getMaxZoom();
    }

    public void changeFocusMode(boolean touchMode) {
        Camera camera;
        PLVCameraData pLVCameraData;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null || (pLVCameraData = this.mCameraData) == null) {
            return;
        }
        this.isTouchMode = touchMode;
        pLVCameraData.touchFocusMode = touchMode;
        if (touchMode) {
            PLVCameraUtils.setTouchFocusMode(camera);
        } else {
            PLVCameraUtils.setAutoFocusMode(camera);
        }
    }

    public boolean doAutofocus(Camera.AutoFocusCallback focusCallback) {
        Camera camera;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null) {
            return false;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.isAutoExposureLockSupported()) {
            parameters.setAutoExposureLock(false);
        }
        if (parameters.isAutoWhiteBalanceLockSupported()) {
            parameters.setAutoWhiteBalanceLock(false);
        }
        this.mCameraDevice.setParameters(parameters);
        this.mCameraDevice.cancelAutoFocus();
        this.mCameraDevice.autoFocus(focusCallback);
        return true;
    }

    public PLVCameraData getCameraData() {
        return this.mCameraData;
    }

    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    public State getState() {
        return this.mState;
    }

    public boolean isLandscape() {
        return this.mConfiguration.orientation != PLVCameraConfiguration.Orientation.PORTRAIT;
    }

    public synchronized Camera openCamera() throws PLVCameraNotSupportException, PLVCameraHardwareException {
        List<PLVCameraData> list = this.mCameraDatas;
        if (list == null || list.size() == 0) {
            this.mCameraDatas = PLVCameraUtils.getAllCamerasData(this.isOpenBackFirst);
        }
        PLVCameraData pLVCameraData = this.mCameraDatas.get(0);
        Camera camera = this.mCameraDevice;
        if (camera != null && this.mCameraData == pLVCameraData) {
            return camera;
        }
        if (camera != null) {
            releaseCamera();
        }
        try {
            Log.d(TAG, "open camera " + pLVCameraData.cameraID);
            Camera cameraOpen = Camera.open(pLVCameraData.cameraID);
            this.mCameraDevice = cameraOpen;
            if (cameraOpen == null) {
                throw new PLVCameraNotSupportException("init camera fail");
            }
            try {
                PLVCameraUtils.initCameraParams(cameraOpen, pLVCameraData, this.isTouchMode, this.mConfiguration);
                this.mCameraData = pLVCameraData;
                this.mState = State.OPENED;
                return this.mCameraDevice;
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mCameraDevice.release();
                this.mCameraDevice = null;
                throw new PLVCameraNotSupportException(e2.getMessage());
            }
        } catch (RuntimeException e3) {
            Log.e(TAG, "fail to connect Camera");
            throw new PLVCameraHardwareException(e3);
        }
    }

    public void release() {
        this.mCameraDatas = null;
        this.mTexture = null;
        this.isTouchMode = false;
        this.isOpenBackFirst = false;
        this.mConfiguration = PLVCameraConfiguration.createDefault();
    }

    public synchronized void releaseCamera() {
        if (this.mState == State.PREVIEW) {
            stopPreview();
        }
        if (this.mState != State.OPENED) {
            return;
        }
        Camera camera = this.mCameraDevice;
        if (camera == null) {
            return;
        }
        camera.release();
        this.mCameraDevice = null;
        this.mCameraData = null;
        this.mState = State.INIT;
    }

    public void setConfiguration(PLVCameraConfiguration configuration) {
        this.isTouchMode = configuration.focusMode != PLVCameraConfiguration.FocusMode.AUTO;
        this.isOpenBackFirst = configuration.facing != PLVCameraConfiguration.Facing.FRONT;
        this.mConfiguration = configuration;
    }

    public void setFocusPoint(int x2, int y2) {
        Camera camera;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null) {
            return;
        }
        if (x2 < -1000 || x2 > 1000 || y2 < -1000 || y2 > 1000) {
            Log.w(TAG, "setFocusPoint: values are not ideal x= " + x2 + " y= " + y2);
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null || parameters.getMaxNumFocusAreas() <= 0) {
            Log.w(TAG, "Not support Touch focus mode");
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Camera.Area(new Rect(x2, y2, x2 + 80, y2 + 80), 1000));
        parameters.setFocusAreas(arrayList);
        try {
            this.mCameraDevice.setParameters(parameters);
        } catch (Exception unused) {
        }
    }

    public void setSurfaceTexture(SurfaceTexture texture) throws IOException {
        Camera camera;
        this.mTexture = texture;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null || texture == null) {
            return;
        }
        try {
            camera.setPreviewTexture(texture);
        } catch (IOException unused) {
            releaseCamera();
        }
    }

    public synchronized void startPreview() {
        startPreview(null);
    }

    public synchronized void stopPreview() {
        if (this.mState != State.PREVIEW) {
            return;
        }
        Camera camera = this.mCameraDevice;
        if (camera == null) {
            return;
        }
        camera.setOneShotPreviewCallback(null);
        this.mCameraDevice.setPreviewCallback(null);
        try {
            Camera.Parameters parameters = this.mCameraDevice.getParameters();
            if (parameters != null && parameters.getFlashMode() != null && !parameters.getFlashMode().equals(DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
            }
            this.mCameraDevice.setParameters(parameters);
            this.mCameraDevice.stopPreview();
            this.mState = State.OPENED;
        } catch (Exception e2) {
            e2.printStackTrace();
            this.mCameraDevice.stopPreview();
            this.mState = State.OPENED;
        }
    }

    public boolean switchCamera() {
        return switchCamera(null);
    }

    public void switchFocusMode() {
        changeFocusMode(!this.isTouchMode);
    }

    public boolean switchLight() {
        Camera camera;
        PLVCameraData pLVCameraData;
        if (this.mState != State.PREVIEW || (camera = this.mCameraDevice) == null || (pLVCameraData = this.mCameraData) == null || !pLVCameraData.hasLight) {
            return false;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.getFlashMode().equals(DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
            parameters.setFlashMode("torch");
        } else {
            parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        }
        try {
            this.mCameraDevice.setParameters(parameters);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public boolean switchCamera(Runnable runnable) {
        if (this.mState != State.PREVIEW) {
            return false;
        }
        try {
            this.mCameraDatas.add(0, this.mCameraDatas.remove(1));
            openCamera();
            startPreview(runnable);
            return true;
        } catch (Exception e2) {
            try {
                this.mCameraDatas.add(0, this.mCameraDatas.remove(1));
                openCamera();
                startPreview(runnable);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            e2.printStackTrace();
            return false;
        }
    }

    public synchronized void startPreview(final Runnable runnable) {
        if (this.mState != State.OPENED) {
            return;
        }
        Camera camera = this.mCameraDevice;
        if (camera == null) {
            return;
        }
        SurfaceTexture surfaceTexture = this.mTexture;
        if (surfaceTexture == null) {
            return;
        }
        try {
            camera.setPreviewTexture(surfaceTexture);
            this.mCameraDevice.setOneShotPreviewCallback(new Camera.PreviewCallback() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVCameraHolder.1
                @Override // android.hardware.Camera.PreviewCallback
                public void onPreviewFrame(byte[] data, Camera camera2) {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            this.mCameraDevice.startPreview();
            this.mState = State.PREVIEW;
        } catch (Exception e2) {
            releaseCamera();
            e2.printStackTrace();
        }
    }
}
