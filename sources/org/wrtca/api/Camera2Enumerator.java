package org.wrtca.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.SystemClock;
import android.util.AndroidException;
import android.util.Range;
import cn.hutool.core.text.StrPool;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.log.Logging;
import org.wrtca.util.Size;

@TargetApi(21)
/* loaded from: classes9.dex */
public class Camera2Enumerator implements CameraEnumerator {
    private static final double NANO_SECONDS_PER_SECOND = 1.0E9d;
    private static final String TAG = "Camera2Enumerator";
    private static final Map<String, List<CameraEnumerationAndroid.CaptureFormat>> cachedSupportedFormats = new HashMap();
    public final CameraManager cameraManager;
    public final Context context;

    public Camera2Enumerator(Context context) {
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
    }

    public static List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> convertFramerates(Range<Integer>[] rangeArr, int i2) {
        ArrayList arrayList = new ArrayList();
        for (Range<Integer> range : rangeArr) {
            arrayList.add(new CameraEnumerationAndroid.CaptureFormat.FramerateRange(((Integer) range.getLower()).intValue() * i2, ((Integer) range.getUpper()).intValue() * i2));
        }
        return arrayList;
    }

    private static List<Size> convertSizes(android.util.Size[] sizeArr) {
        ArrayList arrayList = new ArrayList();
        for (android.util.Size size : sizeArr) {
            arrayList.add(new Size(size.getWidth(), size.getHeight()));
        }
        return arrayList;
    }

    private CameraCharacteristics getCameraCharacteristics(String str) {
        try {
            return this.cameraManager.getCameraCharacteristics(str);
        } catch (AndroidException e2) {
            Logging.e(TAG, "Camera access exception: " + e2);
            return null;
        }
    }

    public static int getFpsUnitFactor(Range<Integer>[] rangeArr) {
        return (rangeArr.length != 0 && ((Integer) rangeArr[0].getUpper()).intValue() >= 1000) ? 1 : 1000;
    }

    public static List<Size> getSupportedPictureSizes(CameraCharacteristics cameraCharacteristics) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        ((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue();
        return convertSizes(streamConfigurationMap.getOutputSizes(256));
    }

    public static List<Size> getSupportedSizes(CameraCharacteristics cameraCharacteristics) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        ((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue();
        c.h.a(TAG, "getSupportedSizes class " + SurfaceTexture.class);
        android.util.Size[] outputSizes = streamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        c.h.a(TAG, "getSupportedSizes nativeSize " + outputSizes);
        if (outputSizes == null) {
            return new ArrayList();
        }
        c.h.a(TAG, "getSupportedSizes nativeSize length: " + outputSizes.length);
        return convertSizes(outputSizes);
    }

    public static boolean isSupported(Context context) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
        try {
            for (String str : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                c.h.a(TAG, "id is: " + str + " characteristics.get is: " + cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL));
                if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                    return false;
                }
            }
            return true;
        } catch (AndroidException e2) {
            Logging.e(TAG, "Camera access exception: " + e2);
            c.h.c(TAG, "Camera access exception: " + e2);
            return false;
        }
    }

    @Override // org.wrtca.api.CameraEnumerator
    public CameraVideoCapturer createCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler) {
        return new Camera2Capturer(this.context, str, cameraEventsHandler);
    }

    @Override // org.wrtca.api.CameraEnumerator
    public String[] getDeviceNames() {
        try {
            return this.cameraManager.getCameraIdList();
        } catch (AndroidException e2) {
            Logging.e(TAG, "Camera access exception: " + e2);
            return new String[0];
        }
    }

    @Override // org.wrtca.api.CameraEnumerator
    public int getFacingIndex(String str) {
        return ((Integer) getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING)).intValue();
    }

    @Override // org.wrtca.api.CameraEnumerator
    public List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(String str) {
        return getSupportedFormats(this.context, str);
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isBackFacing(String str) {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        return cameraCharacteristics != null && ((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 1;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isFrontFacing(String str) {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        return cameraCharacteristics != null && ((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
    }

    public static List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(Context context, String str) {
        return getSupportedFormats((CameraManager) context.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA), str);
    }

    public static List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(CameraManager cameraManager, String str) {
        long outputMinFrameDuration;
        Map<String, List<CameraEnumerationAndroid.CaptureFormat>> map = cachedSupportedFormats;
        synchronized (map) {
            if (map.containsKey(str)) {
                return map.get(str);
            }
            Logging.d(TAG, "Get supported formats for camera index " + str + StrPool.DOT);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            try {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> listConvertFramerates = convertFramerates(rangeArr, getFpsUnitFactor(rangeArr));
                List<Size> supportedSizes = getSupportedSizes(cameraCharacteristics);
                Iterator<CameraEnumerationAndroid.CaptureFormat.FramerateRange> it = listConvertFramerates.iterator();
                int iMax = 0;
                while (it.hasNext()) {
                    iMax = Math.max(iMax, it.next().max);
                }
                ArrayList arrayList = new ArrayList();
                for (Size size : supportedSizes) {
                    try {
                        outputMinFrameDuration = streamConfigurationMap.getOutputMinFrameDuration(SurfaceTexture.class, new android.util.Size(size.width, size.height));
                    } catch (Exception unused) {
                        outputMinFrameDuration = 0;
                    }
                    int iRound = outputMinFrameDuration == 0 ? iMax : ((int) Math.round(NANO_SECONDS_PER_SECOND / outputMinFrameDuration)) * 1000;
                    arrayList.add(new CameraEnumerationAndroid.CaptureFormat(size.width, size.height, 0, iRound));
                    Logging.d(TAG, "Format: " + size.width + "x" + size.height + "@" + iRound);
                }
                cachedSupportedFormats.put(str, arrayList);
                Logging.d(TAG, "Get supported formats for camera index " + str + " done. Time spent: " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + " ms.");
                return arrayList;
            } catch (Exception e2) {
                Logging.e(TAG, "getCameraCharacteristics(): " + e2);
                return new ArrayList();
            }
        }
    }
}
