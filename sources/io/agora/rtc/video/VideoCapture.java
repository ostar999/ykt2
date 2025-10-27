package io.agora.rtc.video;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.hjq.permissions.Permission;
import io.agora.rtc.internal.Logging;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes8.dex */
public abstract class VideoCapture {
    private static final int kVideoI420 = 0;
    private static final int kVideoNV12 = 11;
    private static final int kVideoNV21 = 12;
    private static final int kVideoUnknown = 99;
    private static final int kVideoYUY2 = 2;
    private static final int kVideoYV12 = 1;
    protected int mCameraNativeOrientation;
    protected final Context mContext;
    protected final int mId;
    private int mLastRotation = -1;
    protected long mNativeVideoCaptureDeviceAndroid;

    public VideoCapture(Context context, int id, long nativeVideoCaptureDeviceAndroid) {
        this.mContext = context;
        this.mId = id;
        this.mNativeVideoCaptureDeviceAndroid = nativeVideoCaptureDeviceAndroid;
    }

    public static void cacheCapability(int id, Context appContext, String cap, String captureName) {
        SharedPreferences.Editor editorEdit = appContext.getSharedPreferences("CamCaps2", 0).edit();
        editorEdit.putString("Cam_" + id, cap);
        editorEdit.putString("CaptureName", captureName);
        editorEdit.commit();
    }

    private int checkOrientation() {
        Display defaultDisplay;
        Context context = this.mContext;
        if (context == null || context.getSystemService("window") == null || (defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay()) == null) {
            return this.mLastRotation;
        }
        try {
            return defaultDisplay.getRotation();
        } catch (RuntimeException unused) {
            Logging.e("VideoCapture", "video capture checkOrientation display getRotation throwout exception");
            return this.mLastRotation;
        }
    }

    public static boolean checkVideoPermission(Context context) {
        return context != null && context.checkCallingOrSelfPermission(Permission.CAMERA) == 0;
    }

    public static String fetchCapability(int id, Context appContext, String captureName) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("CamCaps2", 0);
        String string = sharedPreferences.getString("CaptureName", null);
        if (string == null || !string.equals(captureName)) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.commit();
            return null;
        }
        return sharedPreferences.getString("Cam_" + id, null);
    }

    public static boolean isEmulator() throws IOException {
        if ("nokia".equalsIgnoreCase(Build.MANUFACTURER) && ("Nokia_N1".equalsIgnoreCase(Build.DEVICE) || "N1".equalsIgnoreCase(Build.MODEL))) {
            return false;
        }
        try {
            Process processStart = new ProcessBuilder("/system/bin/cat", "/proc/cpuinfo").start();
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream(), "utf-8"));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            }
            bufferedReader.close();
            String lowerCase = stringBuffer.toString().toLowerCase();
            return lowerCase.contains("intel") || lowerCase.contains("amd");
        } catch (IOException unused) {
            return false;
        }
    }

    public static int translateToAndroidFormat(int fmt) {
        if (fmt == 0) {
            return 35;
        }
        if (fmt == 1) {
            return IjkMediaPlayer.SDL_FCC_YV12;
        }
        if (fmt != 2) {
            return fmt != 12 ? 0 : 17;
        }
        return 20;
    }

    public static int translateToEngineFormat(int fmt) {
        if (fmt == 17) {
            return 12;
        }
        if (fmt == 20) {
            return 2;
        }
        if (fmt != 35) {
            return fmt != 842094169 ? 99 : 1;
        }
        return 0;
    }

    public native void NotifyCameraExposureAreaChanged(float x2, float y2, float width, float height, long nativeVideoCaptureDeviceAndroid);

    public native void NotifyCameraFocusAreaChanged(float x2, float y2, float width, float height, long nativeVideoCaptureDeviceAndroid);

    public native void ProvideCameraFrame(byte[] data, int length, long nativeVideoCaptureDeviceAndroid);

    public native void ProvideCameraTexture(byte[] data, int textureID, long nativeVideoCaptureDeviceAndroid);

    public abstract int UnRegisterNativeHandle();

    public abstract int allocate();

    public abstract void deallocate();

    public abstract float getMaxZoom();

    public native boolean isAutoFaceFocusEnabled(long nativeVideoCaptureDeviceAndroid);

    public abstract boolean isAutoFaceFocusSupported();

    public abstract boolean isExposureSupported();

    public abstract boolean isFocusSupported();

    public abstract boolean isTorchSupported();

    public abstract boolean isZoomSupported();

    public native void onCameraError(long nativeVideoCaptureDeviceAndroid, String message);

    public abstract int setAutoFaceFocus(boolean enable);

    public abstract int setCaptureFormat(int format);

    public abstract int setExposure(float x2, float y2, boolean inPreview);

    public abstract int setFocus(float x2, float y2, boolean inPreview);

    public abstract int setTorchMode(boolean isTorchOn);

    public abstract int setZoom(float zoomValue);

    public abstract int startCapture(int width, int height, int frameRate);

    public abstract int stopCapture();
}
