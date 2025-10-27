package io.agora.rtc.utils;

import android.content.Context;
import android.view.WindowManager;
import com.hjq.permissions.Permission;
import io.agora.rtc.internal.RtcEngineImpl;

/* loaded from: classes8.dex */
public class AgoraUtils {
    private static final String TAG = "AgoraUtils";

    public static boolean ensureNativeLibsInitialized() {
        return RtcEngineImpl.initializeNativeLibs();
    }

    public static String getAppStorageDir(Context context) {
        if (context == null || context.checkCallingOrSelfPermission(Permission.READ_EXTERNAL_STORAGE) != 0) {
            return null;
        }
        return "/sdcard/" + context.getApplicationInfo().packageName;
    }

    public static int getDisplayRotation(Context context) {
        int rotation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    public static int getFrameOrientation(int displayRotation, int sensorOrientation, boolean isFrontFacing, boolean compensateForMirroring) {
        if (!isFrontFacing) {
            return ((sensorOrientation - displayRotation) + 360) % 360;
        }
        int i2 = (sensorOrientation + displayRotation) % 360;
        return compensateForMirroring ? (360 - i2) % 360 : i2;
    }
}
