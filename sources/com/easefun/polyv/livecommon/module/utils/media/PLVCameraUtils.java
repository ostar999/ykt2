package com.easefun.polyv.livecommon.module.utils.media;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import com.easefun.polyv.livecommon.module.utils.media.PLVCameraConfiguration;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraDisabledException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraNotSupportException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVNoCameraException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@TargetApi(14)
/* loaded from: classes3.dex */
public class PLVCameraUtils {

    public static class CameraSizeComparator implements Comparator<Camera.Size> {
        private CameraSizeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            int i2 = lhs.width;
            int i3 = rhs.width;
            if (i2 == i3) {
                return 0;
            }
            return i2 > i3 ? 1 : -1;
        }
    }

    private static int[] adaptPreviewFps(int expectedFps, List<int[]> fpsRanges) {
        int iAbs;
        int i2 = expectedFps * 1000;
        int[] iArr = fpsRanges.get(0);
        int iAbs2 = Math.abs(iArr[0] - i2) + Math.abs(iArr[1] - i2);
        for (int[] iArr2 : fpsRanges) {
            int i3 = iArr2[0];
            if (i3 <= i2 && iArr2[1] >= i2 && (iAbs = Math.abs(i3 - i2) + Math.abs(iArr2[1] - i2)) < iAbs2) {
                iArr = iArr2;
                iAbs2 = iAbs;
            }
        }
        return iArr;
    }

    public static void checkCameraService(Context context) throws PLVNoCameraException, PLVCameraDisabledException {
        if (((DevicePolicyManager) context.getSystemService("device_policy")).getCameraDisabled(null)) {
            throw new PLVCameraDisabledException("camera disabled");
        }
        if (getAllCamerasData(false).size() == 0) {
            throw new PLVNoCameraException("no camera");
        }
    }

    private static boolean equalRate(Camera.Size s2, float rate) {
        return ((double) Math.abs((((float) s2.width) / ((float) s2.height)) - rate)) <= 0.2d;
    }

    public static List<PLVCameraData> getAllCamerasData(boolean isBackFirst) {
        ArrayList arrayList = new ArrayList();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            int i3 = cameraInfo.facing;
            if (i3 == 1) {
                PLVCameraData pLVCameraData = new PLVCameraData(i2, 1);
                if (isBackFirst) {
                    arrayList.add(pLVCameraData);
                } else {
                    arrayList.add(0, pLVCameraData);
                }
            } else if (i3 == 0) {
                PLVCameraData pLVCameraData2 = new PLVCameraData(i2, 2);
                if (isBackFirst) {
                    arrayList.add(0, pLVCameraData2);
                } else {
                    arrayList.add(pLVCameraData2);
                }
            }
        }
        return arrayList;
    }

    public static int getDisplayOrientation(int cameraId) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        return cameraInfo.facing == 1 ? (360 - (cameraInfo.orientation % 360)) % 360 : (cameraInfo.orientation + 360) % 360;
    }

    public static Camera.Size getOptimalPreviewSize(Camera camera, int width, int height) {
        List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
        Camera.Size size = null;
        if (supportedPreviewSizes == null) {
            return null;
        }
        double dAbs = Double.MAX_VALUE;
        double dAbs2 = Double.MAX_VALUE;
        for (Camera.Size size2 : supportedPreviewSizes) {
            if (Math.abs(size2.width - width) < dAbs2) {
                dAbs2 = Math.abs(size2.width - width);
            }
        }
        for (Camera.Size size3 : supportedPreviewSizes) {
            if (Math.abs(size3.width - width) == dAbs2 && Math.abs(size3.height - height) < dAbs) {
                dAbs = Math.abs(size3.height - height);
                size = size3;
            }
        }
        return size;
    }

    private static Camera.Size getPreviewSize(Camera camera, int i2, int i3) {
        List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
        Camera.Size size = null;
        byte b3 = 0;
        if (supportedPreviewSizes == null) {
            return null;
        }
        Collections.sort(supportedPreviewSizes, new CameraSizeComparator());
        float f2 = i2 / i3;
        float fAbs = Float.MAX_VALUE;
        for (Camera.Size size2 : supportedPreviewSizes) {
            float f3 = f2 - (size2.width / size2.height);
            if (Math.abs(f3) <= fAbs || Math.abs(f3) <= 0.2d) {
                fAbs = Math.abs(f3);
                size = size2;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("最终设置预览尺寸:w = ");
        sb.append(size == null ? "" : Integer.valueOf(size.width));
        sb.append("h = ");
        sb.append(size != null ? Integer.valueOf(size.height) : "");
        Log.d("PLVCameraUtils", sb.toString());
        return size;
    }

    public static void initCameraParams(Camera camera, PLVCameraData cameraData, boolean isTouchMode, PLVCameraConfiguration configuration) throws PLVCameraNotSupportException {
        boolean z2 = configuration.orientation != PLVCameraConfiguration.Orientation.PORTRAIT;
        int iMax = Math.max(configuration.height, configuration.width);
        int iMin = Math.min(configuration.height, configuration.width);
        Camera.Parameters parameters = camera.getParameters();
        setPreviewFormat(camera, parameters);
        setPreviewFps(camera, configuration.fps, parameters);
        setPreviewSize(camera, cameraData, iMax, iMin, parameters);
        cameraData.hasLight = supportFlash(camera);
        setOrientation(cameraData, z2, camera);
        setFocusMode(camera, cameraData, isTouchMode);
    }

    public static void setAutoFocusMode(Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (supportedFocusModes.size() > 0 && supportedFocusModes.contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
                camera.setParameters(parameters);
            } else if (supportedFocusModes.size() > 0) {
                parameters.setFocusMode(supportedFocusModes.get(0));
                camera.setParameters(parameters);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setFocusMode(Camera camera, PLVCameraData cameraData, boolean isTouchMode) {
        boolean zSupportTouchFocus = supportTouchFocus(camera);
        cameraData.supportTouchFocus = zSupportTouchFocus;
        if (!zSupportTouchFocus) {
            setAutoFocusMode(camera);
        } else if (isTouchMode) {
            cameraData.touchFocusMode = true;
        } else {
            cameraData.touchFocusMode = false;
            setAutoFocusMode(camera);
        }
    }

    private static void setOrientation(PLVCameraData cameraData, boolean isLandscape, Camera camera) {
        int displayOrientation = getDisplayOrientation(cameraData.cameraID);
        if (isLandscape) {
            displayOrientation -= 90;
        }
        try {
            camera.setDisplayOrientation(displayOrientation);
        } catch (Exception unused) {
            camera.setDisplayOrientation(getDisplayOrientation(cameraData.cameraID));
        }
    }

    public static void setPreviewFormat(Camera camera, Camera.Parameters parameters) throws PLVCameraNotSupportException {
        try {
            parameters.setPreviewFormat(17);
            camera.setParameters(parameters);
        } catch (Exception e2) {
            throw new PLVCameraNotSupportException(e2.getMessage());
        }
    }

    public static void setPreviewFps(Camera camera, int fps, Camera.Parameters parameters) {
        if (PLVBlackListHelper.deviceInFpsBlacklisted()) {
            Log.d("PLVCameraUtils", "Device in fps setting black list, so set the camera fps 15");
            fps = 15;
        }
        try {
            parameters.setPreviewFrameRate(fps);
            camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int[] iArrAdaptPreviewFps = adaptPreviewFps(fps, parameters.getSupportedPreviewFpsRange());
        try {
            parameters.setPreviewFpsRange(iArrAdaptPreviewFps[0], iArrAdaptPreviewFps[1]);
            camera.setParameters(parameters);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static void setPreviewSize(Camera camera, PLVCameraData cameraData, int width, int height, Camera.Parameters parameters) throws PLVCameraNotSupportException {
        Camera.Size previewSize = getPreviewSize(camera, width, height);
        if (previewSize == null) {
            throw new PLVCameraNotSupportException("camera no support preview");
        }
        cameraData.cameraWidth = previewSize.width;
        cameraData.cameraHeight = previewSize.height;
        Log.d("PLVCameraUtils", "Camera Width: " + previewSize.width + "    Height: " + previewSize.height);
        try {
            parameters.setPreviewSize(cameraData.cameraWidth, cameraData.cameraHeight);
            camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void setTouchFocusMode(Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (supportedFocusModes.size() > 0 && supportedFocusModes.contains("auto")) {
                parameters.setFocusMode("auto");
                camera.setParameters(parameters);
            } else if (supportedFocusModes.size() > 0) {
                parameters.setFocusMode(supportedFocusModes.get(0));
                camera.setParameters(parameters);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean supportFlash(Camera camera) {
        List<String> supportedFlashModes = camera.getParameters().getSupportedFlashModes();
        if (supportedFlashModes == null) {
            return false;
        }
        Iterator<String> it = supportedFlashModes.iterator();
        while (it.hasNext()) {
            if ("torch".equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean supportTouchFocus(Camera camera) {
        return (camera == null || camera.getParameters().getMaxNumFocusAreas() == 0) ? false : true;
    }
}
