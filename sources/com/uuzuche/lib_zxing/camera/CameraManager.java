package com.uuzuche.lib_zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.view.SurfaceHolder;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class CameraManager {
    public static int FRAME_HEIGHT = -1;
    public static int FRAME_MARGINTOP = -1;
    public static int FRAME_WIDTH = -1;
    static final int SDK_INT;
    private static final String TAG = "CameraManager";
    private static CameraManager cameraManager;
    private final AutoFocusCallback autoFocusCallback;
    private Camera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;

    static {
        int i2;
        try {
            i2 = Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException unused) {
            i2 = 10000;
        }
        SDK_INT = i2;
    }

    private CameraManager(Context context) {
        this.context = context;
        CameraConfigurationManager cameraConfigurationManager = new CameraConfigurationManager(context);
        this.configManager = cameraConfigurationManager;
        boolean z2 = Integer.parseInt(Build.VERSION.SDK) > 3;
        this.useOneShotPreviewCallback = z2;
        this.previewCallback = new PreviewCallback(cameraConfigurationManager, z2);
        this.autoFocusCallback = new AutoFocusCallback();
    }

    public static CameraManager get() {
        return cameraManager;
    }

    public static void init(Context context) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context);
        }
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] bArr, int i2, int i3) {
        Rect framingRectInPreview = getFramingRectInPreview();
        int previewFormat = this.configManager.getPreviewFormat();
        String previewFormatString = this.configManager.getPreviewFormatString();
        if (previewFormat == 16 || previewFormat == 17) {
            return new PlanarYUVLuminanceSource(bArr, i2, i3, framingRectInPreview.left, framingRectInPreview.top, framingRectInPreview.width(), framingRectInPreview.height());
        }
        if ("yuv420p".equals(previewFormatString)) {
            return new PlanarYUVLuminanceSource(bArr, i2, i3, framingRectInPreview.left, framingRectInPreview.top, framingRectInPreview.width(), framingRectInPreview.height());
        }
        throw new IllegalArgumentException("Unsupported picture format: " + previewFormat + '/' + previewFormatString);
    }

    public void closeDriver() {
        if (this.camera != null) {
            FlashlightManager.disableFlashlight();
            this.camera.release();
            this.camera = null;
        }
    }

    public AutoFocusCallback getAutoFocusCallback() {
        return this.autoFocusCallback;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Context getContext() {
        return this.context;
    }

    public Rect getFramingRect() {
        try {
            Point screenResolution = this.configManager.getScreenResolution();
            if (this.camera == null) {
                return null;
            }
            int i2 = FRAME_WIDTH;
            int i3 = FRAME_HEIGHT;
            if (i2 <= 0) {
                i2 = (screenResolution.x * 3) / 4;
            }
            if (i3 <= 0) {
                i3 = (screenResolution.y * 3) / 4;
            }
            int iMin = Math.min(i2, screenResolution.x - 2);
            int iMin2 = Math.min(i3, screenResolution.y - 2);
            int i4 = (screenResolution.x - iMin) / 2;
            int i5 = FRAME_MARGINTOP;
            if (i5 == -1) {
                i5 = (screenResolution.y - iMin2) / 2;
            }
            int iMax = Math.max(1, i4);
            int iMax2 = Math.max(1, i5);
            Rect rect = new Rect(iMax, iMax2, Math.min(screenResolution.x - 1, iMin + iMax), Math.min(screenResolution.y - 1, iMin2 + iMax2));
            this.framingRect = rect;
            return rect;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect rect = new Rect(getFramingRect());
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            boolean z2 = this.configManager.getCameraDisplayOrientation() % 180 != 0;
            if (z2) {
                int i2 = rect.left;
                int i3 = cameraResolution.y;
                int i4 = screenResolution.x;
                rect.left = (i2 * i3) / i4;
                rect.right = (rect.right * i3) / i4;
                int i5 = rect.top;
                int i6 = cameraResolution.x;
                int i7 = screenResolution.y;
                rect.top = (i5 * i6) / i7;
                rect.bottom = (rect.bottom * i6) / i7;
            } else {
                int i8 = rect.left;
                int i9 = cameraResolution.x;
                int i10 = screenResolution.x;
                rect.left = (i8 * i9) / i10;
                rect.right = (rect.right * i9) / i10;
                int i11 = rect.top;
                int i12 = cameraResolution.y;
                int i13 = screenResolution.y;
                rect.top = (i11 * i12) / i13;
                rect.bottom = (rect.bottom * i12) / i13;
            }
            int i14 = z2 ? cameraResolution.y : cameraResolution.x;
            int i15 = z2 ? cameraResolution.x : cameraResolution.y;
            rect.left = Math.max(0, Math.min(rect.left, i14 - 1));
            rect.top = Math.max(0, Math.min(rect.top, i15 - 1));
            rect.right = Math.max(rect.left + 1, Math.min(rect.right, i14));
            rect.bottom = Math.max(rect.top + 1, Math.min(rect.bottom, i15));
            this.framingRectInPreview = rect;
        }
        return this.framingRectInPreview;
    }

    public PreviewCallback getPreviewCallback() {
        return this.previewCallback;
    }

    public boolean isPreviewing() {
        return this.previewing;
    }

    public boolean isUseOneShotPreviewCallback() {
        return this.useOneShotPreviewCallback;
    }

    public void openDriver(SurfaceHolder surfaceHolder) throws IOException {
        if (this.camera == null) {
            Camera cameraOpen = Camera.open();
            this.camera = cameraOpen;
            if (cameraOpen == null) {
                throw new IOException();
            }
            cameraOpen.setPreviewDisplay(surfaceHolder);
            if (!this.initialized) {
                this.initialized = true;
                this.configManager.initFromCameraParameters(this.camera);
            }
            this.configManager.setDesiredCameraParameters(this.camera);
            FlashlightManager.enableFlashlight();
        }
    }

    public void requestAutoFocus(Handler handler, int i2) {
        if (this.camera == null || !this.previewing) {
            return;
        }
        this.autoFocusCallback.setHandler(handler, i2);
        this.camera.autoFocus(this.autoFocusCallback);
    }

    public void requestPreviewFrame(Handler handler, int i2) {
        if (this.camera == null || !this.previewing) {
            return;
        }
        this.previewCallback.setHandler(handler, i2);
        if (this.useOneShotPreviewCallback) {
            this.camera.setOneShotPreviewCallback(this.previewCallback);
        } else {
            this.camera.setPreviewCallback(this.previewCallback);
        }
    }

    public void setPreviewing(boolean z2) {
        this.previewing = z2;
    }

    public void startPreview() {
        Camera camera = this.camera;
        if (camera == null || this.previewing) {
            return;
        }
        camera.startPreview();
        this.previewing = true;
    }

    public void stopPreview() {
        Camera camera = this.camera;
        if (camera == null || !this.previewing) {
            return;
        }
        if (!this.useOneShotPreviewCallback) {
            camera.setPreviewCallback(null);
        }
        this.camera.stopPreview();
        this.previewCallback.setHandler(null, 0);
        this.autoFocusCallback.setHandler(null, 0);
        this.previewing = false;
    }
}
