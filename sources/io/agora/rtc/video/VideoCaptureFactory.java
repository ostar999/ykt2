package io.agora.rtc.video;

import android.content.Context;
import android.os.Build;
import io.agora.rtc.internal.Logging;

/* loaded from: classes8.dex */
public class VideoCaptureFactory {
    private static final String TAG = "CAM-FACTORY";

    public static class AndroidCameraInfo {
        private static int sNumberOfSystemCameras = -1;

        /* JADX INFO: Access modifiers changed from: private */
        public static int getNumberOfCameras(Context appContext) {
            if (sNumberOfSystemCameras == -1) {
                sNumberOfSystemCameras = 0;
                if (VideoCaptureFactory.isLReleaseOrLater()) {
                    sNumberOfSystemCameras = VideoCaptureCamera2.getNumberOfCameras(appContext);
                }
                if (sNumberOfSystemCameras == 0) {
                    sNumberOfSystemCameras = VideoCaptureCamera.getNumberOfCameras();
                }
            }
            return sNumberOfSystemCameras;
        }
    }

    public static VideoCapture createVideoCapture(int id, Context context, long nativeVideoCaptureDeviceAndroid) {
        return (!isLReleaseOrLater() || VideoCaptureCamera2.isLegacyDevice(context, id)) ? new VideoCaptureCamera(context, id, nativeVideoCaptureDeviceAndroid) : new VideoCaptureCamera2(context, id, nativeVideoCaptureDeviceAndroid);
    }

    public static String getCapabilities(int id, Context appContext) {
        String strFetchCapability = (!isLReleaseOrLater() || VideoCaptureCamera2.isLegacyDevice(appContext, id)) ? VideoCapture.fetchCapability(id, appContext, VideoCaptureCamera.getCaptureName()) : VideoCapture.fetchCapability(id, appContext, VideoCaptureCamera2.getCaptureName());
        if (strFetchCapability == null) {
            Logging.e(TAG, "Capability hasn't been created");
        } else {
            printCameraInfo(strFetchCapability);
        }
        return strFetchCapability;
    }

    public static String getDeviceName(int id, Context appContext) {
        return (!isLReleaseOrLater() || VideoCaptureCamera2.isLegacyDevice(appContext, id)) ? VideoCaptureCamera.getName(id) : VideoCaptureCamera2.getName(id, appContext);
    }

    public static int getDeviceOrientation(int id, Context appContext) {
        return (!isLReleaseOrLater() || VideoCaptureCamera2.isLegacyDevice(appContext, id)) ? VideoCaptureCamera.getSensorOrientation(id) : VideoCaptureCamera2.getSensorOrientation(id, appContext);
    }

    public static int getNumberOfCameras(Context appContext) {
        return AndroidCameraInfo.getNumberOfCameras(appContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isLReleaseOrLater() {
        String str = Build.DEVICE;
        if ("ocean".equalsIgnoreCase(str) && "oe106".equalsIgnoreCase(Build.MODEL)) {
            return false;
        }
        if ("trident".equalsIgnoreCase(str) && "de106".equalsIgnoreCase(Build.MODEL)) {
            return false;
        }
        if (("shark".equalsIgnoreCase(str) && "skr-a0".equalsIgnoreCase(Build.MODEL)) || "hnnem-h".equalsIgnoreCase(str)) {
            return false;
        }
        if ((!"on7xelte".equals(str) || !"SM-G610F".equals(Build.MODEL)) && !"m2c".equals(str)) {
            String str2 = Build.MODEL;
            if (!"M578CA".equals(str2)) {
                String str3 = Build.MANUFACTURER;
                return (("samsung".equalsIgnoreCase(str3) && str2 != null && (str2.contains("SM-G930") || str2.contains("SM-G935") || str2.contains("SM-G950") || str2.contains("SM-G955") || "SC-02H".equals(str2) || "SCV33".equals(str2) || "SC-02J".equals(str2) || "SCV36".equals(str2) || "SM-G892A".equals(str2) || "SM-G892U".equals(str2) || "SC-03J".equals(str2) || "SCV35".equals(str2))) || "oneplus".equalsIgnoreCase(str3)) ? false : true;
            }
        }
        return false;
    }

    public static int printCameraInfo(String cap) {
        int length = (cap.length() / 150) + 1;
        for (int i2 = 0; i2 < length; i2++) {
            try {
                String str = "lines = " + length + ":";
                Logging.d("CameraInfo", i2 == length - 1 ? str + cap.substring(i2 * 150, cap.length()) : str + cap.substring(i2 * 150, (i2 + 1) * 150));
            } catch (IndexOutOfBoundsException e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }
}
