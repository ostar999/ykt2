package io.agora.rtc.video;

/* loaded from: classes8.dex */
public class CameraUtil {
    private static final String TAG = "CAMERA_UTIL";

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getCameraDisplayOrientation(android.content.Context r3, int r4) {
        /*
            android.hardware.Camera$CameraInfo r0 = new android.hardware.Camera$CameraInfo
            r0.<init>()
            android.hardware.Camera.getCameraInfo(r4, r0)
            r4 = 90
            if (r3 == 0) goto L54
            java.lang.String r1 = "window"
            java.lang.Object r2 = r3.getSystemService(r1)
            if (r2 == 0) goto L54
            java.lang.Object r3 = r3.getSystemService(r1)
            android.view.WindowManager r3 = (android.view.WindowManager) r3
            android.view.Display r3 = r3.getDefaultDisplay()
            if (r3 != 0) goto L28
            java.lang.String r3 = "CAMERA_UTIL"
            java.lang.String r0 = "display is null"
            io.agora.rtc.internal.Logging.e(r3, r0)
            return r4
        L28:
            int r3 = r3.getRotation()
            r1 = 1
            r2 = 0
            if (r3 == 0) goto L38
            if (r3 == r1) goto L3f
            r4 = 2
            if (r3 == r4) goto L3d
            r4 = 3
            if (r3 == r4) goto L3a
        L38:
            r4 = r2
            goto L3f
        L3a:
            r4 = 270(0x10e, float:3.78E-43)
            goto L3f
        L3d:
            r4 = 180(0xb4, float:2.52E-43)
        L3f:
            int r3 = r0.facing
            if (r3 != r1) goto L4d
            int r3 = r0.orientation
            int r3 = r3 + r4
            int r3 = r3 % 360
            int r3 = 360 - r3
            int r4 = r3 % 360
            goto L54
        L4d:
            int r3 = r0.orientation
            int r3 = r3 - r4
            int r3 = r3 + 360
            int r4 = r3 % 360
        L54:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.video.CameraUtil.getCameraDisplayOrientation(android.content.Context, int):int");
    }
}
