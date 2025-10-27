package org.wrtca.api;

import android.hardware.Camera;
import android.os.SystemClock;
import cn.hutool.core.text.StrPool;
import java.util.ArrayList;
import java.util.List;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.log.Logging;
import org.wrtca.util.Size;

/* loaded from: classes9.dex */
public class Camera1Enumerator implements CameraEnumerator {
    private static final String TAG = "Camera1Enumerator";
    private static List<List<CameraEnumerationAndroid.CaptureFormat>> cachedSupportedFormats;
    private final boolean captureToTexture;

    public Camera1Enumerator() {
        this(true);
    }

    public static List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> convertFramerates(List<int[]> list) {
        ArrayList arrayList = new ArrayList();
        for (int[] iArr : list) {
            arrayList.add(new CameraEnumerationAndroid.CaptureFormat.FramerateRange(iArr[0], iArr[1]));
        }
        return arrayList;
    }

    public static List<Size> convertSizes(List<Camera.Size> list) {
        ArrayList arrayList = new ArrayList();
        for (Camera.Size size : list) {
            arrayList.add(new Size(size.width, size.height));
        }
        return arrayList;
    }

    private static List<CameraEnumerationAndroid.CaptureFormat> enumerateFormats(int i2) {
        int i3;
        Logging.d(TAG, "Get supported formats for camera index " + i2 + StrPool.DOT);
        c.h.a(TAG, "Get supported formats for camera index " + i2 + StrPool.DOT);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Camera cameraOpen = null;
        try {
            try {
                Logging.d(TAG, "Opening camera with index " + i2);
                c.h.a(TAG, "Opening camera with index " + i2);
                cameraOpen = Camera.open(i2);
                Camera.Parameters parameters = cameraOpen.getParameters();
                c.h.a(TAG, "open camera: " + i2 + " error.");
                c.h.a(TAG, "camera index is : " + i2 + " release.");
                cameraOpen.release();
                c.h.a(TAG, "start to get formatList");
                ArrayList arrayList = new ArrayList();
                try {
                    List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                    int i4 = 0;
                    if (supportedPreviewFpsRange != null) {
                        c.h.a(TAG, "entering set listFpsRange");
                        int[] iArr = supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1);
                        i4 = iArr[0];
                        i3 = iArr[1];
                    } else {
                        i3 = 0;
                    }
                    for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                        c.h.a(TAG, "entering add formatList");
                        arrayList.add(new CameraEnumerationAndroid.CaptureFormat(size.width, size.height, i4, i3));
                    }
                } catch (Exception e2) {
                    Logging.e(TAG, "getSupportedFormats() failed on camera index " + i2, e2);
                    c.h.a(TAG, "getSupportedFormats() failed on camera index " + i2 + " errorMsg is: " + e2.toString());
                }
                long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                StringBuilder sb = new StringBuilder();
                sb.append("Get supported formats for camera index ");
                sb.append(i2);
                sb.append(" done. Time spent: ");
                long j2 = jElapsedRealtime2 - jElapsedRealtime;
                sb.append(j2);
                sb.append(" ms.");
                Logging.d(TAG, sb.toString());
                c.h.a(TAG, "Get supported formats for camera index " + i2 + " done. Time spent: " + j2 + " ms.");
                return arrayList;
            } catch (RuntimeException e3) {
                Logging.e(TAG, "Open camera failed on camera index " + i2, e3);
                c.h.a(TAG, "Open camera failed on camera index " + i2 + " errorMsg: " + e3.toString());
                ArrayList arrayList2 = new ArrayList();
                c.h.a(TAG, "open camera: " + i2 + " error.");
                if (cameraOpen != null) {
                    c.h.a(TAG, "camera index is : " + i2 + " release.");
                    cameraOpen.release();
                }
                return arrayList2;
            }
        } catch (Throwable th) {
            c.h.a(TAG, "open camera: " + i2 + " error.");
            if (cameraOpen != null) {
                c.h.a(TAG, "camera index is : " + i2 + " release.");
                cameraOpen.release();
            }
            throw th;
        }
    }

    public static int getCameraIndex(String str) {
        Logging.d(TAG, "getCameraIndex: " + str);
        c.h.a(TAG, "getCameraName: " + str);
        for (int i2 = 0; i2 < Camera.getNumberOfCameras(); i2++) {
            c.h.a(TAG, "Index is: " + i2 + " and compare deviceName is:" + getDeviceName(i2));
            if (str.equals(getDeviceName(i2))) {
                c.h.a(TAG, "getCameraIndex: " + i2);
                return i2;
            }
        }
        throw new IllegalArgumentException("No such camera: " + str);
    }

    private static Camera.CameraInfo getCameraInfo(int i2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        try {
            Camera.getCameraInfo(i2, cameraInfo);
            return cameraInfo;
        } catch (Exception e2) {
            Logging.e(TAG, "getCameraInfo failed on index " + i2, e2);
            return null;
        }
    }

    public static String getDeviceName(int i2) {
        Camera.CameraInfo cameraInfo = getCameraInfo(i2);
        if (cameraInfo == null) {
            return null;
        }
        return "Camera " + i2 + ", Facing " + (cameraInfo.facing == 1 ? "front" : "back") + ", Orientation " + cameraInfo.orientation;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public CameraVideoCapturer createCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler) {
        return new Camera1Capturer(str, cameraEventsHandler, this.captureToTexture);
    }

    @Override // org.wrtca.api.CameraEnumerator
    public String[] getDeviceNames() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < Camera.getNumberOfCameras(); i2++) {
            String deviceName = getDeviceName(i2);
            if (deviceName != null) {
                arrayList.add(deviceName);
                Logging.d(TAG, "Index: " + i2 + ". " + deviceName);
            } else {
                Logging.e(TAG, "Index: " + i2 + ". Failed to query camera name.");
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // org.wrtca.api.CameraEnumerator
    public int getFacingIndex(String str) {
        return getCameraInfo(getCameraIndex(str)).facing;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(String str) {
        return getSupportedFormats(getCameraIndex(str));
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isBackFacing(String str) {
        Camera.CameraInfo cameraInfo = getCameraInfo(getCameraIndex(str));
        return cameraInfo != null && cameraInfo.facing == 0;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isFrontFacing(String str) {
        Camera.CameraInfo cameraInfo = getCameraInfo(getCameraIndex(str));
        return cameraInfo != null && cameraInfo.facing == 1;
    }

    public Camera1Enumerator(boolean z2) {
        this.captureToTexture = z2;
    }

    public static synchronized List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(int i2) {
        if (cachedSupportedFormats == null) {
            c.h.a(TAG, "cachedSupportedFormats == null ");
            cachedSupportedFormats = new ArrayList();
            for (int i3 = 0; i3 < Camera.getNumberOfCameras(); i3++) {
                cachedSupportedFormats.add(enumerateFormats(i3));
            }
        }
        c.h.a(TAG, "cachedSupportedFormats.get cameraId is: " + i2);
        return cachedSupportedFormats.get(i2);
    }
}
