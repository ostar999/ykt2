package io.agora.rtc.video;

import android.hardware.Camera;
import io.agora.rtc.internal.Logging;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes8.dex */
public class CameraHelper {
    private static final String TAG = "CameraHelper";

    public static class Capability {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public int facing;
        public int height;
        public int id;
        public int maxFps;
        public int width;

        public Capability(int id, int facing, int w2, int h2, int fps) {
            this.id = id;
            this.facing = facing;
            this.width = w2;
            this.height = h2;
            this.maxFps = fps;
        }
    }

    public static boolean checkPermission() {
        return true;
    }

    public static Capability createCapability(int id, int facing, Camera.Parameters param) {
        List<Camera.Size> supportedPreviewSizes = param.getSupportedPreviewSizes();
        List<int[]> supportedPreviewFpsRange = param.getSupportedPreviewFpsRange();
        if (supportedPreviewSizes.isEmpty() || supportedPreviewFpsRange.isEmpty()) {
            Logging.e(TAG, "failed get preview size/fps, parameters = " + param.flatten());
            throw new IllegalArgumentException(param.flatten());
        }
        Camera.Size size = supportedPreviewSizes.get(0);
        for (Camera.Size size2 : supportedPreviewSizes) {
            if (size2.width * size2.height > size.width * size.height) {
                size = size2;
            }
        }
        int i2 = supportedPreviewFpsRange.get(0)[1] / 1000;
        Logging.d(TAG, "creaet capability for camera " + id + " : width: " + size.width + " , height: " + size.height + " max fps: " + i2);
        return new Capability(id, facing, size.width, size.height, i2);
    }

    public static synchronized List<Capability> getCameraCapability() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras < 1) {
            throw new RuntimeException("no camera device");
        }
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i2, cameraInfo);
            try {
                Camera cameraOpen = Camera.open(i2);
                arrayList.add(createCapability(i2, cameraInfo.facing, cameraOpen.getParameters()));
                cameraOpen.release();
            } catch (RuntimeException e2) {
                throw e2;
            }
        }
        return arrayList;
    }
}
