package com.uuzuche.lib_zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.regex.Pattern;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
final class CameraConfigurationManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final int DESIRED_SHARPNESS = 30;
    private static final String TAG = "CameraConfigurationManager";
    private static final int TEN_DESIRED_ZOOM = 27;
    private int cameraDisplayOrientation = 90;
    private Point cameraResolution;
    private final Context context;
    private int previewFormat;
    private String previewFormatString;
    private Point screenResolution;

    public CameraConfigurationManager(Context context) {
        this.context = context;
    }

    private static int findBestMotZoomValue(CharSequence charSequence, int i2) throws NumberFormatException {
        int i3 = 0;
        for (String str : COMMA_PATTERN.split(charSequence)) {
            try {
                double d3 = Double.parseDouble(str.trim());
                int i4 = (int) (10.0d * d3);
                if (Math.abs(i2 - d3) < Math.abs(i2 - i3)) {
                    i3 = i4;
                }
            } catch (NumberFormatException unused) {
                return i2;
            }
        }
        return i3;
    }

    private static Point findBestPreviewSizeValue(CharSequence charSequence, Point point) throws NumberFormatException {
        String[] strArrSplit = COMMA_PATTERN.split(charSequence);
        int length = strArrSplit.length;
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            String strTrim = strArrSplit[i3].trim();
            int iIndexOf = strTrim.indexOf(120);
            if (iIndexOf < 0) {
                Log.w(TAG, "Bad preview-size: " + strTrim);
            } else {
                try {
                    int i6 = Integer.parseInt(strTrim.substring(0, iIndexOf));
                    int i7 = Integer.parseInt(strTrim.substring(iIndexOf + 1));
                    int iAbs = Math.abs(i6 - point.x) + Math.abs(i7 - point.y);
                    if (iAbs == 0) {
                        i5 = i7;
                        i4 = i6;
                        break;
                    }
                    if (iAbs < i2) {
                        i5 = i7;
                        i2 = iAbs;
                        i4 = i6;
                    }
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Bad preview-size: " + strTrim);
                }
            }
            i3++;
        }
        if (i4 <= 0 || i5 <= 0) {
            return null;
        }
        return new Point(i4, i5);
    }

    public static int getDesiredSharpness() {
        return 30;
    }

    private void setFlash(Camera.Parameters parameters) {
        if (Build.MODEL.contains("Behold II") && CameraManager.SDK_INT == 3) {
            parameters.set("flash-value", 1);
        } else {
            parameters.set("flash-value", 2);
        }
        parameters.set("flash-mode", DebugKt.DEBUG_PROPERTY_VALUE_OFF);
    }

    private void setZoom(Camera.Parameters parameters) throws NumberFormatException {
        String str = parameters.get("zoom-supported");
        if (str == null || Boolean.parseBoolean(str)) {
            String str2 = parameters.get("max-zoom");
            int iFindBestMotZoomValue = 27;
            if (str2 != null) {
                try {
                    int i2 = (int) (Double.parseDouble(str2) * 10.0d);
                    if (27 > i2) {
                        iFindBestMotZoomValue = i2;
                    }
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Bad max-zoom: " + str2);
                }
            }
            String str3 = parameters.get("taking-picture-zoom-max");
            if (str3 != null) {
                try {
                    int i3 = Integer.parseInt(str3);
                    if (iFindBestMotZoomValue > i3) {
                        iFindBestMotZoomValue = i3;
                    }
                } catch (NumberFormatException unused2) {
                    Log.w(TAG, "Bad taking-picture-zoom-max: " + str3);
                }
            }
            String str4 = parameters.get("mot-zoom-values");
            if (str4 != null) {
                iFindBestMotZoomValue = findBestMotZoomValue(str4, iFindBestMotZoomValue);
            }
            String str5 = parameters.get("mot-zoom-step");
            if (str5 != null) {
                try {
                    int i4 = (int) (Double.parseDouble(str5.trim()) * 10.0d);
                    if (i4 > 1) {
                        iFindBestMotZoomValue -= iFindBestMotZoomValue % i4;
                    }
                } catch (NumberFormatException unused3) {
                }
            }
            if (str2 != null || str4 != null) {
                parameters.set("zoom", String.valueOf(iFindBestMotZoomValue / 10.0d));
            }
            if (str3 != null) {
                parameters.set("taking-picture-zoom", iFindBestMotZoomValue);
            }
        }
    }

    public int getCameraDisplayOrientation() {
        return this.cameraDisplayOrientation;
    }

    public Point getCameraResolution() {
        return this.cameraResolution;
    }

    public int getPreviewFormat() {
        return this.previewFormat;
    }

    public String getPreviewFormatString() {
        return this.previewFormatString;
    }

    public Point getScreenResolution() {
        return this.screenResolution;
    }

    public void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        this.previewFormat = parameters.getPreviewFormat();
        this.previewFormatString = parameters.get("preview-format");
        String str = TAG;
        Log.d(str, "Default preview format: " + this.previewFormat + '/' + this.previewFormatString);
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        this.screenResolution = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());
        Log.d(str, "Screen resolution: " + this.screenResolution);
        Point point = new Point();
        Point point2 = this.screenResolution;
        point.x = point2.x;
        point.y = point2.y;
        int i2 = point2.x;
        int i3 = point2.y;
        if (i2 < i3) {
            point.x = i3;
            point.y = point2.x;
        }
        Log.i("#########", "screenX:" + point.x + "   screenY:" + point.y);
        this.cameraResolution = getCameraResolution(parameters, point);
        StringBuilder sb = new StringBuilder();
        sb.append("Camera resolution: ");
        sb.append(this.screenResolution);
        Log.d(str, sb.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0061 A[Catch: all -> 0x006d, TRY_LEAVE, TryCatch #1 {all -> 0x006d, blocks: (B:15:0x0055, B:17:0x0061), top: B:33:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0079 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:3:0x002d, B:21:0x006d, B:23:0x0079, B:25:0x008a, B:24:0x0083), top: B:31:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0083 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:3:0x002d, B:21:0x006d, B:23:0x0079, B:25:0x008a, B:24:0x0083), top: B:31:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006d A[EDGE_INSN: B:35:0x006d->B:21:0x006d BREAK  A[LOOP:0: B:16:0x005f->B:20:0x006a], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setDesiredCameraParameters(android.hardware.Camera r10) {
        /*
            r9 = this;
            android.hardware.Camera$Parameters r0 = r10.getParameters()
            java.lang.String r1 = com.uuzuche.lib_zxing.camera.CameraConfigurationManager.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Setting preview size: "
            r2.append(r3)
            android.graphics.Point r3 = r9.cameraResolution
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            android.graphics.Point r1 = r9.cameraResolution
            int r2 = r1.x
            int r1 = r1.y
            r0.setPreviewSize(r2, r1)
            r9.setFlash(r0)
            r9.setZoom(r0)
            r1 = 90
            android.content.Context r2 = r9.context     // Catch: java.lang.Throwable -> L90
            java.lang.String r3 = "window"
            java.lang.Object r2 = r2.getSystemService(r3)     // Catch: java.lang.Throwable -> L90
            android.view.WindowManager r2 = (android.view.WindowManager) r2     // Catch: java.lang.Throwable -> L90
            android.view.Display r2 = r2.getDefaultDisplay()     // Catch: java.lang.Throwable -> L90
            int r2 = r2.getRotation()     // Catch: java.lang.Throwable -> L90
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L4c
            if (r2 == r3) goto L54
            r5 = 2
            if (r2 == r5) goto L51
            r5 = 3
            if (r2 == r5) goto L4e
        L4c:
            r2 = r4
            goto L55
        L4e:
            r2 = 270(0x10e, float:3.78E-43)
            goto L55
        L51:
            r2 = 180(0xb4, float:2.52E-43)
            goto L55
        L54:
            r2 = r1
        L55:
            int r5 = android.hardware.Camera.getNumberOfCameras()     // Catch: java.lang.Throwable -> L6d
            android.hardware.Camera$CameraInfo r6 = new android.hardware.Camera$CameraInfo     // Catch: java.lang.Throwable -> L6d
            r6.<init>()     // Catch: java.lang.Throwable -> L6d
            r7 = r4
        L5f:
            if (r7 >= r5) goto L6d
            android.hardware.Camera.getCameraInfo(r7, r6)     // Catch: java.lang.Throwable -> L6d
            int r8 = r6.facing     // Catch: java.lang.Throwable -> L6d
            if (r8 != 0) goto L6a
            r4 = r7
            goto L6d
        L6a:
            int r7 = r7 + 1
            goto L5f
        L6d:
            android.hardware.Camera$CameraInfo r5 = new android.hardware.Camera$CameraInfo     // Catch: java.lang.Throwable -> L90
            r5.<init>()     // Catch: java.lang.Throwable -> L90
            android.hardware.Camera.getCameraInfo(r4, r5)     // Catch: java.lang.Throwable -> L90
            int r4 = r5.facing     // Catch: java.lang.Throwable -> L90
            if (r4 != r3) goto L83
            int r3 = r5.orientation     // Catch: java.lang.Throwable -> L90
            int r3 = r3 + r2
            int r3 = r3 % 360
            int r2 = 360 - r3
            int r2 = r2 % 360
            goto L8a
        L83:
            int r3 = r5.orientation     // Catch: java.lang.Throwable -> L90
            int r3 = r3 - r2
            int r3 = r3 + 360
            int r2 = r3 % 360
        L8a:
            r9.cameraDisplayOrientation = r2     // Catch: java.lang.Throwable -> L90
            r10.setDisplayOrientation(r2)     // Catch: java.lang.Throwable -> L90
            goto L95
        L90:
            r9.cameraDisplayOrientation = r1
            r10.setDisplayOrientation(r1)
        L95:
            r10.setParameters(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uuzuche.lib_zxing.camera.CameraConfigurationManager.setDesiredCameraParameters(android.hardware.Camera):void");
    }

    private static Point getCameraResolution(Camera.Parameters parameters, Point point) throws NumberFormatException {
        Point pointFindBestPreviewSizeValue;
        String str = parameters.get("preview-size-values");
        if (str == null) {
            str = parameters.get("preview-size-value");
        }
        if (str != null) {
            Log.d(TAG, "preview-size-values parameter: " + str);
            pointFindBestPreviewSizeValue = findBestPreviewSizeValue(str, point);
        } else {
            pointFindBestPreviewSizeValue = null;
        }
        return pointFindBestPreviewSizeValue == null ? new Point((point.x >> 3) << 3, (point.y >> 3) << 3) : pointFindBestPreviewSizeValue;
    }
}
