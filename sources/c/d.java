package c;

import android.content.Context;
import com.hjq.permissions.Permission;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f2232a = "CameraPermissionCheckUtils";

    /* JADX WARN: Removed duplicated region for block: B:12:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a() {
        /*
            int r0 = d.b.d()
            r1 = 1
            if (r0 != 0) goto L20
            r0 = 0
            android.hardware.Camera r2 = android.hardware.Camera.open(r0)     // Catch: java.lang.Exception -> L12
            r3 = 90
            r2.setDisplayOrientation(r3)     // Catch: java.lang.Exception -> L13
            goto L1b
        L12:
            r2 = 0
        L13:
            java.lang.String r1 = "CameraPermissionCheckUtils"
            java.lang.String r3 = " checkCameraPermission failed!"
            c.h.a(r1, r3)
            r1 = r0
        L1b:
            if (r1 == 0) goto L20
            r2.release()
        L20:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: c.d.a():boolean");
    }

    public static boolean a(Context context) {
        return context.checkSelfPermission(Permission.CAMERA) == 0;
    }
}
