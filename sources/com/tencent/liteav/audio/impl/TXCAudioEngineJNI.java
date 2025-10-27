package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import com.tencent.liteav.audio.TXCAudioEncoderConfig;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.audio.g;
import com.tencent.liteav.audio.impl.earmonitor.TXSystemAudioKit;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.StatusBucket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.h;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class TXCAudioEngineJNI {
    private static final String TAG = "TXCAudioEngineJNI";
    public static final int kInvalidCacheSize = Integer.MAX_VALUE;
    private static WeakReference<g> mAudioCaptureDataListener;
    private static a mAudioDumpingListener;
    private static WeakReference<e> mMixedAllDataListener;
    private static AudioManager sAudioManager;

    public interface a {
        void onLocalAudioWriteFailed();
    }

    static {
        h.d();
        nativeCacheClassForNative();
        mAudioDumpingListener = null;
        mAudioCaptureDataListener = null;
        mMixedAllDataListener = new WeakReference<>(null);
    }

    public static void InitTraeEngineLibrary(Context context) {
        if (context == null) {
            TXCLog.e(TAG, "InitTraeEngineLibrary failed, context is null!");
            return;
        }
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String str = applicationInfo.nativeLibraryDir;
            String str2 = applicationInfo.dataDir + "/lib";
            String str3 = "/data/data/" + applicationInfo.packageName + "/lib";
            String strE = h.e();
            if (strE == null) {
                strE = "";
            }
            nativeAppendLibraryPath("add_libpath:" + str);
            nativeAppendLibraryPath("add_libpath:" + str2);
            nativeAppendLibraryPath("add_libpath:" + str3);
            nativeAppendLibraryPath("add_libpath:" + strE);
        } catch (UnsatisfiedLinkError e2) {
            TXCLog.e(TAG, "init trae engine library failed.", e2);
        }
    }

    public static void SetAudioDumpingListener(a aVar) {
        mAudioDumpingListener = aVar;
    }

    private static AudioManager getAudioManager() {
        if (sAudioManager == null) {
            sAudioManager = (AudioManager) TXCAudioEngine.getInstance().getAppContext().getSystemService("audio");
        }
        return sAudioManager;
    }

    public static int getAudioMode() {
        try {
            AudioManager audioManager = getAudioManager();
            if (audioManager != null) {
                return audioManager.getMode();
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public static StatusBucket getStatus(int i2) {
        return nativeGetStatus(i2);
    }

    public static int getSystemVolume() {
        try {
            int i2 = getAudioMode() == 0 ? 3 : 0;
            AudioManager audioManager = getAudioManager();
            if (audioManager != null) {
                return audioManager.getStreamVolume(i2);
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public static boolean isAppInBackground() {
        return h.a(TXCAudioEngine.getInstance().getAppContext());
    }

    public static native void nativeAppendLibraryPath(String str);

    public static native void nativeCacheClassForNative();

    /* JADX WARN: Removed duplicated region for block: B:15:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean nativeCheckTraeEngine(android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.audio.impl.TXCAudioEngineJNI.nativeCheckTraeEngine(android.content.Context):boolean");
    }

    public static native void nativeClean();

    public static native void nativeCloseAudioTunnel(int i2);

    public static native void nativeDeleteAudioSessionDuplicate();

    public static native void nativeEnableAudioEarMonitoring(boolean z2);

    public static native void nativeEnableAudioVolumeEvaluation(boolean z2, int i2);

    public static native void nativeEnableAutoRestartDevice(boolean z2);

    public static native void nativeEnableCaptureEOSMode(boolean z2);

    public static native void nativeEnableCustomAudioRendering(boolean z2);

    public static native void nativeEnableDeviceAbnormalDetection(boolean z2);

    public static native void nativeEnableEncodedDataCallback(boolean z2);

    public static native void nativeEnableEncodedDataPackWithTRAEHeaderCallback(boolean z2);

    public static native void nativeEnableInbandFEC(boolean z2);

    public static native void nativeEnableMixMode(boolean z2);

    public static native void nativeForceCallbackMixedPlayAudioFrame(boolean z2);

    public static native void nativeGetCustomAudioRenderingFrame(byte[] bArr, int i2, int i3);

    public static native int nativeGetEncoderChannels();

    public static native TXCAudioEncoderConfig nativeGetEncoderConfig();

    public static native int nativeGetEncoderSampleRate();

    public static native int nativeGetMixingPlayoutVolumeLevel();

    public static native int nativeGetRemotePlayoutVolumeLevel(String str);

    public static native int nativeGetSoftwareCaptureVolumeLevel();

    public static native StatusBucket nativeGetStatus(int i2);

    public static native void nativeInitAudioDevice();

    public static native void nativeInitBeforeEngineCreate(Context context, String str);

    public static native boolean nativeIsAudioDeviceCapturing();

    public static native boolean nativeIsAudioDevicePlaying();

    public static native boolean nativeIsDataCallbackFormatInvalid(int i2, int i3, int i4);

    public static native boolean nativeIsRemoteAudioPlaying(String str);

    public static native void nativeMuteLocalAudio(boolean z2);

    public static native void nativeMuteRemoteAudio(String str, boolean z2);

    public static native void nativeMuteRemoteAudioInSpeaker(String str, boolean z2);

    public static native void nativeNewAudioSessionDuplicate(Context context);

    public static native void nativeNotifySystemEarMonitoringInitializing();

    public static native int nativeOpenAudioTunnel(boolean z2);

    public static native void nativePauseAudioCapture(boolean z2);

    public static native void nativePauseLocalAudio();

    public static native void nativeResumeAudioCapture();

    public static native void nativeResumeLocalAudio();

    public static native void nativeSendCustomPCMData(byte[] bArr, int i2, long j2, int i3, int i4);

    public static native void nativeSetAudioCacheParams(int i2, int i3);

    public static native void nativeSetAudioCompatibleConfig(String str);

    public static native void nativeSetAudioDeviceDSPEnabled(boolean z2);

    public static native void nativeSetAudioEarMonitoringVolume(int i2);

    public static native void nativeSetAudioEncoderParam(int i2, int i3);

    public static native void nativeSetAudioEngineCaptureDataCallback(boolean z2);

    public static native void nativeSetAudioEngineCaptureRawDataCallback(boolean z2);

    public static native void nativeSetAudioEngineEncodedDataCallback(boolean z2);

    public static native void nativeSetAudioEngineMixedAllDataCallback(boolean z2);

    public static native void nativeSetAudioEngineRemoteStreamDataListener(String str, boolean z2);

    public static native void nativeSetAudioFrameExtraData(byte[] bArr);

    public static native void nativeSetAudioPlayoutTunnelEnabled(boolean z2);

    public static native void nativeSetAudioQuality(int i2, int i3);

    public static native void nativeSetAudioQualityEx(int i2, int i3, int i4, int i5, int i6, int i7);

    public static native void nativeSetAudioRoute(int i2);

    public static native void nativeSetCaptureDataCallbackFormat(int i2, int i3, int i4);

    public static native void nativeSetCaptureEqualizationParam(int i2, int i3);

    public static native void nativeSetCaptureEqualizationType(int i2);

    public static native void nativeSetCaptureVoiceChanger(int i2);

    public static native void nativeSetEncoderChannels(int i2);

    public static native void nativeSetEncoderFECPercent(float f2);

    public static native void nativeSetEncoderSampleRate(int i2);

    public static native void nativeSetEventCallbackEnabled(boolean z2);

    public static native void nativeSetLocalAudioMuteAction(int i2, int i3);

    public static native void nativeSetLocalProcessedDataCallbackFormat(int i2, int i3, int i4);

    public static native void nativeSetMaxSelectedPlayStreams(int i2);

    public static native void nativeSetMixingPlayoutVolume(float f2);

    public static native void nativeSetPlayoutDataCallbackFormat(int i2, int i3, int i4);

    public static native void nativeSetPlayoutDataListener(boolean z2);

    public static native void nativeSetPlayoutDevice(int i2);

    public static native void nativeSetRecordReverb(int i2);

    public static native void nativeSetRecordReverbParam(int i2, float f2);

    public static native void nativeSetRemoteAudioBlockThreshold(String str, long j2);

    public static native void nativeSetRemoteAudioCacheParams(String str, boolean z2, int i2, int i3, int i4);

    public static native void nativeSetRemoteAudioJitterCycle(String str, long j2);

    public static native void nativeSetRemotePlayoutVolume(String str, int i2);

    public static native void nativeSetRemoteStreamDataCallbackFormat(String str, int i2, int i3, int i4);

    public static native void nativeSetSoftAEC(int i2);

    public static native void nativeSetSoftAGC(int i2);

    public static native void nativeSetSoftANS(int i2);

    public static native void nativeSetSoftwareCaptureVolume(float f2);

    public static native void nativeSetSystemEarMonitoring(TXSystemAudioKit tXSystemAudioKit);

    public static native void nativeSetSystemVolumeType(int i2);

    public static native void nativeSetVoicePitch(double d3);

    public static native void nativeSetVolumeToTunnel(int i2, int i3);

    public static native void nativeStartLocalAudio(int i2, boolean z2);

    public static native int nativeStartLocalAudioDumping(int i2, int i3, int i4, String str);

    public static native String nativeStartRemoteAudio(TXCAudioEngine tXCAudioEngine, boolean z2, String str);

    public static native void nativeStopLocalAudio();

    public static native void nativeStopLocalAudioDumping();

    public static native void nativeStopRemoteAudio(String str);

    public static native void nativeUninitAudioDevice();

    public static native void nativeUseSysAudioDevice(boolean z2);

    public static native int nativeWriteDataToTunnel(int i2, int i3, int i4, int i5, byte[] bArr);

    public static void onError(String str, int i2, String str2, String str3) {
        TXCAudioEngine.getInstance().onError(str, i2, str2, str3);
    }

    public static void onEvent(String str, int i2, String str2, String str3) {
        TXCAudioEngine.getInstance().onEvent(str, i2, str2, str3);
    }

    public static void onLocalAudioWriteFail() {
        a aVar = mAudioDumpingListener;
        if (aVar != null) {
            aVar.onLocalAudioWriteFailed();
        }
    }

    public static void onMixedAllData(byte[] bArr, int i2, int i3) {
        e eVar = mMixedAllDataListener.get();
        if (eVar != null) {
            eVar.onMixedAllData(bArr, i2, i3);
        }
    }

    public static void onRecordEncData(byte[] bArr, long j2, int i2, int i3) {
        g gVar;
        WeakReference<g> weakReference = mAudioCaptureDataListener;
        if (weakReference == null || (gVar = weakReference.get()) == null) {
            return;
        }
        gVar.onRecordEncData(bArr, j2, i2, i3, 16);
    }

    public static void onRecordError(int i2, String str) {
        g gVar;
        TXCLog.e(TAG, "onRecordError: " + i2 + ", " + str);
        WeakReference<g> weakReference = mAudioCaptureDataListener;
        if (weakReference == null || (gVar = weakReference.get()) == null) {
            return;
        }
        gVar.onRecordError(i2, str);
    }

    public static void onRecordPcmData(byte[] bArr, long j2, int i2, int i3, int i4) {
        g gVar;
        WeakReference<g> weakReference = mAudioCaptureDataListener;
        if (weakReference == null || (gVar = weakReference.get()) == null) {
            return;
        }
        gVar.onRecordPcmData(bArr, j2, i2, i3, i4);
    }

    public static void onRecordRawPcmData(byte[] bArr, long j2, int i2, int i3, int i4) {
        g gVar;
        WeakReference<g> weakReference = mAudioCaptureDataListener;
        if (weakReference == null || (gVar = weakReference.get()) == null) {
            return;
        }
        gVar.onRecordRawPcmData(bArr, j2, i2, i3, i4, false);
    }

    public static void onWarning(String str, int i2, String str2, String str3) {
        TXCAudioEngine.getInstance().onWarning(str, i2, str2, str3);
    }

    public static void pauseAudioCapture(boolean z2) {
        nativePauseAudioCapture(z2);
    }

    public static void resumeAudioCapture() {
        nativeResumeAudioCapture();
    }

    public static void sendCustomPCMData(byte[] bArr, int i2, int i3) {
        nativeSendCustomPCMData(bArr, bArr.length, TXCTimeUtil.generatePtsMS(), i2, i3);
    }

    public static void setAudioCaptureDataListener(WeakReference<g> weakReference) {
        mAudioCaptureDataListener = weakReference;
        nativeSetAudioEngineCaptureDataCallback(weakReference != null);
        nativeSetAudioEngineCaptureRawDataCallback(mAudioCaptureDataListener != null);
        nativeSetAudioEngineEncodedDataCallback(mAudioCaptureDataListener != null);
    }

    public static void setMixedAllDataListener(e eVar) {
        mMixedAllDataListener = new WeakReference<>(eVar);
        nativeSetAudioEngineMixedAllDataCallback(eVar != null);
    }

    public static void sendCustomPCMData(com.tencent.liteav.basic.structs.a aVar) {
        byte[] bArr = aVar.f18649f;
        nativeSendCustomPCMData(bArr, bArr.length, aVar.f18648e, aVar.f18644a, aVar.f18645b);
    }
}
