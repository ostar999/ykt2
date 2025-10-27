package com.tencent.liteav.audio;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.audio.impl.Play.TXCMultAudioTrackPlayer;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecord;
import com.tencent.liteav.audio.impl.TXCAudioEngineJNI;
import com.tencent.liteav.audio.impl.earmonitor.HuaweiAudioKit;
import com.tencent.liteav.audio.impl.earmonitor.TXSystemAudioKit;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.StatusBucket;
import com.tencent.liteav.basic.util.TXCBuild;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class TXCAudioEngine implements com.tencent.liteav.audio.impl.b, com.tencent.liteav.audio.impl.earmonitor.a {
    private static final int EVT_AUDIO_DEVICE_RESTART_WHEN_USING_STABLE_SAMPLERATE = 10056;
    private static final int EVT_AUDIO_DEVICE_ROLLBACK_TO_STABLE_SAMPLERATE = 10055;
    private static final String TAG = "AudioEngine :TXCAudioEngine_java";
    private TXSystemAudioKit mAudioKit;
    private static final long SYSTEM_AUDIO_KIT_RESTART_INTERVAL = TimeUnit.SECONDS.toMillis(2);
    static TXCAudioEngine sInstance = new TXCAudioEngine();
    protected static Context mContext = null;
    private static boolean has_trae = false;
    private static WeakReference<f> mAudioCoreDataListener = null;
    protected static final HashMap<String, WeakReference<f>> mJitterDataListenerMap = new HashMap<>();
    private static final Object mJitterDataListenerMapLock = new Object();
    protected static final HashMap<String, WeakReference<d>> mJitterEventListenerMap = new HashMap<>();
    private static final Object mJitterEventListenerMapLock = new Object();
    private static volatile boolean has_init = false;
    private final ArrayList<WeakReference<com.tencent.liteav.basic.b.a>> mCallbackList = new ArrayList<>();
    protected boolean mDeviceIsRecording = false;
    protected boolean mIsCustomRecord = false;
    private final Object mStartStopRemoteAudioMutex = new Object();
    protected boolean mIsCallComed = false;

    private TXCAudioEngine() {
    }

    public static synchronized void CreateInstance(Context context, String str, boolean z2) {
        CreateInstanceWithoutInitDevice(context, str, z2);
        TXCAudioEngineJNI.nativeInitAudioDevice();
    }

    public static synchronized void CreateInstanceWithoutInitDevice(Context context, String str, boolean z2) {
        TXCLog.i(TAG, "CreateInstance: ");
        mContext = context.getApplicationContext();
        if (has_init) {
            TXCLog.i(TAG, "CreateInstance already created~ ");
            return;
        }
        if (TXCAudioEngineJNI.nativeCheckTraeEngine(context)) {
            has_trae = true;
        }
        TXCAudioEngineJNI.nativeUseSysAudioDevice(!has_trae);
        TXCAudioEngineJNI.nativeSetAudioDeviceDSPEnabled(z2);
        if (has_trae) {
            TXCAudioEngineJNI.InitTraeEngineLibrary(context);
            TXCAudioEngineJNI.nativeSetAudioCompatibleConfig(str);
            TXCAudioEngineJNI.nativeInitBeforeEngineCreate(context, getAudioResourceDirectory(mContext));
            com.tencent.liteav.audio.impl.a.a().a(context.getApplicationContext());
            com.tencent.liteav.audio.impl.a.a().a(sInstance);
            if (!z2) {
                TXCAudioEngineJNI.nativeNewAudioSessionDuplicate(mContext);
            }
        } else {
            TXCMultAudioTrackPlayer.getInstance();
            TXCAudioSysRecord.getInstance();
        }
        has_init = true;
    }

    public static synchronized void UninitInstance() {
        TXCLog.i(TAG, "uninit audio engine instance");
        TXCAudioEngineJNI.nativeUninitAudioDevice();
        has_init = false;
    }

    public static String buildTRAEConfig(Context context, Boolean bool, boolean z2, long j2) {
        String string = (((("sharp {\n") + "  os android\n") + "  trae {\n") + "    dev {\n") + "        intf 2\n";
        if (bool != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append("  closeOpensl ");
            sb.append(bool.booleanValue() ? "n" : "y");
            sb.append("\n");
            string = sb.toString();
        }
        boolean z3 = System.currentTimeMillis() - TXCAudioSettings.getInstance().getLong(TXCAudioSettings.TIMESTAMP_ROLLBACK_TO_STABLE_SAMPLERATE, 0L) < j2;
        TXCLog.i(TAG, "low latency samplerate, enable: %b, isBlocked: %b, blockTime: %d", Boolean.valueOf(z2), Boolean.valueOf(z3), Long.valueOf(j2));
        if (!z3 && z2 && getLowLatencySampleRate(context) == 48000) {
            string = ((((((string + "  traemodes 1|2\n") + "  cap {\n") + "    hw_sr 48000\n") + "  }\n") + "  play {\n") + "    hw_sr 48000\n") + "  }";
        }
        return ((string + "    }\n") + "  }\n") + "}";
    }

    private TXSystemAudioKit createManufacturerAudioKit(Context context) {
        if (TXCBuild.Manufacturer().equalsIgnoreCase("huawei")) {
            return new HuaweiAudioKit();
        }
        return null;
    }

    public static void enableAudioEarMonitoring(boolean z2) {
        TXCLog.i(TAG, "enableAudioEarMonitoring: " + z2);
        TXCAudioEngineJNI.nativeEnableAudioEarMonitoring(z2);
    }

    public static boolean enableAudioVolumeEvaluation(boolean z2, int i2) {
        TXCLog.i(TAG, "enableAudioVolumeEvaluation : " + z2 + "interval:" + i2);
        TXCAudioEngineJNI.nativeEnableAudioVolumeEvaluation(z2, i2);
        return true;
    }

    private static String getAudioResourceDirectory(Context context) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        String str = File.separator;
        sb.append(str);
        sb.append("liteav");
        sb.append(str);
        sb.append("audiores");
        String string = sb.toString();
        if (new File(string).mkdirs()) {
            TXCLog.e(TAG, "create audio resource directory failed.");
        }
        return string;
    }

    public static TXCAudioEngine getInstance() {
        return sInstance;
    }

    private static int getLowLatencySampleRate(Context context) {
        AudioManager audioManager;
        if (TXCBuild.VersionInt() < 17 || (audioManager = (AudioManager) context.getSystemService("audio")) == null) {
            return -1;
        }
        try {
            return Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"));
        } catch (NumberFormatException e2) {
            TXCLog.e(TAG, "can't parse low latency samplerate", e2);
            return -1;
        }
    }

    public static int getMixingPlayoutVolumeLevel() {
        return TXCAudioEngineJNI.nativeGetMixingPlayoutVolumeLevel();
    }

    private void handleAudioEvent(String str, int i2, String str2, String str3) {
        if (i2 == 10055) {
            TXCAudioSettings.getInstance().setLong(TXCAudioSettings.TIMESTAMP_ROLLBACK_TO_STABLE_SAMPLERATE, System.currentTimeMillis());
        } else if (i2 == 10056) {
            TXCAudioSettings.getInstance().setLong(TXCAudioSettings.TIMESTAMP_ROLLBACK_TO_STABLE_SAMPLERATE, 0L);
            TXCLog.i(TAG, "audio device restart when using stable samplerate");
        }
    }

    public static boolean hasTrae() {
        return has_trae;
    }

    public static void onAudioJitterBufferNotify(String str, int i2, String str2) {
        d dVar;
        synchronized (mJitterEventListenerMapLock) {
            HashMap<String, WeakReference<d>> map = mJitterEventListenerMap;
            dVar = map.get(str) != null ? map.get(str).get() : null;
        }
        if (dVar != null) {
            TXCLog.i(TAG, "onAudioJitterBufferNotify  cur state " + i2);
            dVar.onAudioJitterBufferNotify(str, i2, str2);
        }
    }

    public static void onAudioPlayPcmData(String str, byte[] bArr, long j2, int i2, int i3, byte[] bArr2) {
        f fVar;
        synchronized (mJitterDataListenerMapLock) {
            HashMap<String, WeakReference<f>> map = mJitterDataListenerMap;
            fVar = map.get(str) != null ? map.get(str).get() : null;
        }
        if (fVar != null) {
            fVar.onAudioPlayPcmData(str, bArr, j2, i2, i3, bArr2);
        }
    }

    public static void onCorePlayPcmData(byte[] bArr, long j2, int i2, int i3) {
        f fVar;
        WeakReference<f> weakReference = mAudioCoreDataListener;
        if (weakReference == null || (fVar = weakReference.get()) == null) {
            return;
        }
        fVar.onAudioPlayPcmData(null, bArr, j2, i2, i3, null);
    }

    public static void setAudioEarMonitoringVolume(int i2) {
        TXCLog.i(TAG, "setAudioEarMonitoringVolume: " + i2);
        TXCAudioEngineJNI.nativeSetAudioEarMonitoringVolume(i2);
    }

    public static void setAudioRoute(int i2) {
        TXCLog.i(TAG, "setAudioRoute: " + i2);
        TXCAudioEngineJNI.nativeSetAudioRoute(i2);
    }

    public static void setPlayoutDataListener(f fVar) {
        mAudioCoreDataListener = new WeakReference<>(fVar);
        TXCAudioEngineJNI.nativeSetPlayoutDataListener(fVar != null);
    }

    public static void setSystemVolumeType(int i2) {
        TXCLog.i(TAG, "setSystemVolumeType: " + i2);
        TXCAudioEngineJNI.nativeSetSystemVolumeType(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSystemAudioKit() {
        Context context;
        if (this.mAudioKit != null || (context = mContext) == null) {
            return;
        }
        TXSystemAudioKit tXSystemAudioKitCreateManufacturerAudioKit = createManufacturerAudioKit(context);
        this.mAudioKit = tXSystemAudioKitCreateManufacturerAudioKit;
        if (tXSystemAudioKitCreateManufacturerAudioKit == null) {
            TXCAudioEngineJNI.nativeSetSystemEarMonitoring(null);
        } else {
            TXCAudioEngineJNI.nativeNotifySystemEarMonitoringInitializing();
            this.mAudioKit.initialize(mContext, this);
        }
    }

    public void EnableMixMode(boolean z2) {
        TXCAudioEngineJNI.nativeEnableMixMode(z2);
    }

    public boolean IsDataCallbackFormatInvalid(int i2, int i3, int i4) {
        return TXCAudioEngineJNI.nativeIsDataCallbackFormatInvalid(i2, i3, i4);
    }

    public void SetAudioCacheParams(int i2, int i3) {
        TXCAudioEngineJNI.nativeSetAudioCacheParams(i2, i3);
    }

    public void addEventCallback(WeakReference<com.tencent.liteav.basic.b.a> weakReference) {
        if (weakReference == null) {
            return;
        }
        synchronized (this.mCallbackList) {
            this.mCallbackList.add(weakReference);
            TXCAudioEngineJNI.nativeSetEventCallbackEnabled(true);
        }
    }

    public void clean() {
        TXCAudioEngineJNI.nativeClean();
    }

    public void enableAutoRestartDevice(boolean z2) {
        TXCAudioEngineJNI.nativeEnableAutoRestartDevice(z2);
    }

    public boolean enableCaptureEOSMode(boolean z2) {
        TXCLog.i(TAG, "enableEosMode " + z2);
        TXCAudioEngineJNI.nativeEnableCaptureEOSMode(z2);
        return true;
    }

    public void enableDeviceAbnormalDetection(boolean z2) {
        TXCAudioEngineJNI.nativeEnableDeviceAbnormalDetection(z2);
    }

    public void enableEncodedDataCallback(boolean z2) {
        TXCAudioEngineJNI.nativeEnableEncodedDataCallback(z2);
    }

    public void enableEncodedDataPackWithTRAEHeaderCallback(boolean z2) {
        TXCAudioEngineJNI.nativeEnableEncodedDataPackWithTRAEHeaderCallback(z2);
    }

    public void enableInbandFEC(boolean z2) {
        TXCAudioEngineJNI.nativeEnableInbandFEC(z2);
    }

    public void enableSoftAEC(boolean z2, int i2) {
        TXCLog.i(TAG, "enableSoftAEC: enable = " + z2 + " level = " + i2);
        if (!z2) {
            i2 = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftAEC(i2);
    }

    public void enableSoftAGC(boolean z2, int i2) {
        TXCLog.i(TAG, "enableSoftAGC: enable = " + z2 + " level = " + i2);
        if (!z2) {
            i2 = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftAGC(i2);
    }

    public void enableSoftANS(boolean z2, int i2) {
        TXCLog.i(TAG, "enableSoftANS: enable = " + z2 + " level = " + i2);
        if (!z2) {
            i2 = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftANS(i2);
    }

    public void forceCallbackMixedPlayAudioFrame(boolean z2) {
        TXCAudioEngineJNI.nativeForceCallbackMixedPlayAudioFrame(z2);
    }

    public int getAECType() {
        return 2;
    }

    public Context getAppContext() {
        return mContext;
    }

    public TXCAudioEncoderConfig getAudioEncoderConfig() {
        return TXCAudioEngineJNI.nativeGetEncoderConfig();
    }

    public int getEncoderChannels() {
        return TXCAudioEngineJNI.nativeGetEncoderChannels();
    }

    public int getEncoderSampleRate() {
        return TXCAudioEngineJNI.nativeGetEncoderSampleRate();
    }

    public int getPlayAECType() {
        return has_trae ? 2 : 0;
    }

    public int getPlayChannels() {
        return 2;
    }

    public int getPlaySampleRate() {
        return 48000;
    }

    public int getRemotePlayoutVolumeLevel(String str) {
        if (str == null) {
            return 0;
        }
        return TXCAudioEngineJNI.nativeGetRemotePlayoutVolumeLevel(str);
    }

    public int getSoftwareCaptureVolumeLevel() {
        return TXCAudioEngineJNI.nativeGetSoftwareCaptureVolumeLevel();
    }

    public StatusBucket getStatus(int i2) {
        return TXCAudioEngineJNI.getStatus(i2);
    }

    public boolean isAudioDeviceCapturing() {
        boolean zNativeIsAudioDeviceCapturing = TXCAudioEngineJNI.nativeIsAudioDeviceCapturing();
        TXCLog.i(TAG, "isRecording: " + zNativeIsAudioDeviceCapturing);
        return zNativeIsAudioDeviceCapturing;
    }

    public boolean isRemoteAudioPlaying(String str) {
        if (str == null) {
            return false;
        }
        return TXCAudioEngineJNI.nativeIsRemoteAudioPlaying(str);
    }

    public boolean muteLocalAudio(boolean z2) {
        TXCLog.i(TAG, "setRecordMute: " + z2);
        TXCAudioEngineJNI.nativeMuteLocalAudio(z2);
        return true;
    }

    public void muteRemoteAudio(String str, boolean z2) {
        if (str == null) {
            return;
        }
        TXCAudioEngineJNI.nativeMuteRemoteAudio(str, z2);
    }

    public void muteRemoteAudioInSpeaker(String str, boolean z2) {
        if (str == null) {
            return;
        }
        TXCAudioEngineJNI.nativeMuteRemoteAudioInSpeaker(str, z2);
    }

    @Override // com.tencent.liteav.audio.impl.earmonitor.a
    public void onAudioKitError(TXSystemAudioKit tXSystemAudioKit) {
        if (this.mAudioKit != tXSystemAudioKit) {
            return;
        }
        TXCLog.i(TAG, "onAudioKitError");
        TXSystemAudioKit tXSystemAudioKit2 = this.mAudioKit;
        if (tXSystemAudioKit2 != null) {
            tXSystemAudioKit2.stopSystemEarMonitoring();
            this.mAudioKit.uninitialize();
            this.mAudioKit = null;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.tencent.liteav.audio.TXCAudioEngine.1
            @Override // java.lang.Runnable
            public void run() {
                TXCAudioEngine.this.startSystemAudioKit();
            }
        }, SYSTEM_AUDIO_KIT_RESTART_INTERVAL);
    }

    @Override // com.tencent.liteav.audio.impl.earmonitor.a
    public void onAudioKitInitFinished(TXSystemAudioKit tXSystemAudioKit, boolean z2) {
        if (this.mAudioKit != tXSystemAudioKit) {
            return;
        }
        TXCLog.i(TAG, "system audio kit init finished, ret: %b.", Boolean.valueOf(z2));
        if (z2) {
            return;
        }
        TXCAudioEngineJNI.nativeSetSystemEarMonitoring(null);
    }

    @Override // com.tencent.liteav.audio.impl.b
    public void onCallStateChanged(int i2) {
        if (i2 == 0) {
            TXCLog.i(TAG, "TelephonyManager.CALL_STATE_IDLE!");
            if (this.mIsCallComed) {
                this.mIsCallComed = false;
                TXCAudioEngineJNI.resumeAudioCapture();
                TXAudioEffectManagerImpl.getInstance().recoverAllMusics();
                TXAudioEffectManagerImpl.getCacheInstance().recoverAllMusics();
                TXAudioEffectManagerImpl.getAutoCacheHolder().recoverAllMusics();
                return;
            }
            return;
        }
        if (i2 == 1) {
            TXCLog.i(TAG, "TelephonyManager.CALL_STATE_RINGING!");
            return;
        }
        if (i2 != 2) {
            return;
        }
        TXCLog.i(TAG, "TelephonyManager.CALL_STATE_OFFHOOK!");
        TXCAudioEngineJNI.pauseAudioCapture(true);
        TXAudioEffectManagerImpl.getInstance().interruptAllMusics();
        TXAudioEffectManagerImpl.getCacheInstance().interruptAllMusics();
        TXAudioEffectManagerImpl.getAutoCacheHolder().interruptAllMusics();
        this.mIsCallComed = true;
    }

    @Override // com.tencent.liteav.audio.impl.earmonitor.a
    public void onEarMonitoringInitialized(TXSystemAudioKit tXSystemAudioKit, boolean z2) {
        if (this.mAudioKit != tXSystemAudioKit) {
            return;
        }
        TXCLog.i(TAG, "onEarMonitoringInitialized result: %b", Boolean.valueOf(z2));
        if (z2) {
            TXCAudioEngineJNI.nativeSetSystemEarMonitoring(this.mAudioKit);
        } else {
            TXCAudioEngineJNI.nativeSetSystemEarMonitoring(null);
        }
    }

    public void onError(String str, int i2, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCallbackList) {
            if (this.mCallbackList.size() <= 0) {
                return;
            }
            Iterator<WeakReference<com.tencent.liteav.basic.b.a>> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                com.tencent.liteav.basic.b.a aVar = it.next().get();
                if (aVar != null) {
                    arrayList.add(aVar);
                } else {
                    it.remove();
                }
            }
            if (this.mCallbackList.size() <= 0) {
                TXCAudioEngineJNI.nativeSetEventCallbackEnabled(false);
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((com.tencent.liteav.basic.b.a) it2.next()).onError(str, i2, str2, str3);
            }
        }
    }

    public void onEvent(String str, int i2, String str2, String str3) {
        handleAudioEvent(str, i2, str2, str3);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCallbackList) {
            if (this.mCallbackList.size() <= 0) {
                return;
            }
            Iterator<WeakReference<com.tencent.liteav.basic.b.a>> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                com.tencent.liteav.basic.b.a aVar = it.next().get();
                if (aVar != null) {
                    arrayList.add(aVar);
                } else {
                    it.remove();
                }
            }
            if (this.mCallbackList.size() <= 0) {
                TXCAudioEngineJNI.nativeSetEventCallbackEnabled(false);
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((com.tencent.liteav.basic.b.a) it2.next()).onEvent(str, i2, str2, str3);
            }
        }
    }

    public void onWarning(String str, int i2, String str2, String str3) {
        handleAudioEvent(str, i2, str2, str3);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCallbackList) {
            if (this.mCallbackList.size() <= 0) {
                return;
            }
            Iterator<WeakReference<com.tencent.liteav.basic.b.a>> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                com.tencent.liteav.basic.b.a aVar = it.next().get();
                if (aVar != null) {
                    arrayList.add(aVar);
                } else {
                    it.remove();
                }
            }
            if (this.mCallbackList.size() <= 0) {
                TXCAudioEngineJNI.nativeSetEventCallbackEnabled(false);
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((com.tencent.liteav.basic.b.a) it2.next()).onWarning(str, i2, str2, str3);
            }
        }
    }

    public int pauseAudioCapture(boolean z2) {
        TXCLog.i(TAG, "pauseAudioCapture: " + z2);
        TXCAudioEngineJNI.pauseAudioCapture(z2);
        return 0;
    }

    public void pauseLocalAudio() {
        TXCAudioEngineJNI.nativePauseLocalAudio();
    }

    public int resumeAudioCapture() {
        TXCLog.i(TAG, "resumeRecord");
        TXCAudioEngineJNI.resumeAudioCapture();
        return 0;
    }

    public void resumeLocalAudio() {
        TXCAudioEngineJNI.nativeResumeLocalAudio();
    }

    public void sendCustomPCMData(byte[] bArr, int i2, int i3) {
        TXCAudioEngineJNI.sendCustomPCMData(bArr, i2, i3);
    }

    public boolean setAudioCaptureDataListener(g gVar) {
        TXCLog.i(TAG, "setRecordListener ");
        if (gVar == null) {
            TXCAudioEngineJNI.setAudioCaptureDataListener(null);
            return true;
        }
        TXCAudioEngineJNI.setAudioCaptureDataListener(new WeakReference(gVar));
        return true;
    }

    public void setAudioDumpingListener(TXCAudioEngineJNI.a aVar) {
        TXCAudioEngineJNI.SetAudioDumpingListener(aVar);
    }

    public boolean setAudioEncoderParam(int i2, int i3) {
        TXCAudioEngineJNI.nativeSetAudioEncoderParam(i2, i3);
        return true;
    }

    public void setAudioFrameExtraData(byte[] bArr) {
        TXCAudioEngineJNI.nativeSetAudioFrameExtraData(bArr);
    }

    public void setAudioQuality(int i2, int i3) {
        TXCAudioEngineJNI.nativeSetAudioQuality(i2, i3);
    }

    public void setCaptureDataCallbackFormat(int i2, int i3, int i4) {
        TXCLog.i(TAG, "setCaptureDataCallbackFormat: sampleRate-" + i2 + " channels-" + i3 + " length-" + i4);
        TXCAudioEngineJNI.nativeSetCaptureDataCallbackFormat(i2, i3, i4);
    }

    public boolean setCaptureEqualizationParam(int i2, int i3) {
        TXCLog.i(TAG, "setCaptureEqualizationParam: bandIndex =" + i2 + " bandGain = " + i3);
        TXCAudioEngineJNI.nativeSetCaptureEqualizationParam(i2, i3);
        return true;
    }

    public boolean setCaptureEqualizationType(int i2) {
        TXCLog.i(TAG, "setCaptureEqualizationType " + i2);
        TXCAudioEngineJNI.nativeSetCaptureEqualizationType(i2);
        return true;
    }

    public void setEncoderChannels(int i2) {
        TXCAudioEngineJNI.nativeSetEncoderChannels(i2);
    }

    public boolean setEncoderFECPercent(float f2) {
        TXCAudioEngineJNI.nativeSetEncoderFECPercent(f2);
        return true;
    }

    public void setEncoderSampleRate(int i2) {
        TXCAudioEngineJNI.nativeSetEncoderSampleRate(i2);
    }

    public void setLocalAudioMuteAction(int i2, int i3) {
        TXCAudioEngineJNI.nativeSetLocalAudioMuteAction(i2, i3);
    }

    public void setLocalProcessedDataCallbackFormat(int i2, int i3, int i4) {
        TXCLog.i(TAG, "setLocalProcessedDataCallbackFormat: sampleRate-" + i2 + " channels-" + i3 + " length-" + i4);
        TXCAudioEngineJNI.nativeSetLocalProcessedDataCallbackFormat(i2, i3, i4);
    }

    public void setMaxSelectedPlayStreams(int i2) {
        TXCAudioEngineJNI.nativeSetMaxSelectedPlayStreams(i2);
    }

    public void setMixedAllDataListener(e eVar) {
        TXCAudioEngineJNI.setMixedAllDataListener(eVar);
    }

    public boolean setMixingPlayoutVolume(float f2) {
        TXCLog.i(TAG, "setPlayoutVolume: " + f2);
        TXCAudioEngineJNI.nativeSetMixingPlayoutVolume(f2);
        return true;
    }

    public void setPlayoutDataCallbackFormat(int i2, int i3, int i4) {
        TXCLog.i(TAG, "setPlayoutDataCallbackFormat: sampleRate-" + i2 + " channels-" + i3 + " length-" + i4);
        TXCAudioEngineJNI.nativeSetPlayoutDataCallbackFormat(i2, i3, i4);
    }

    public void setRemoteAudioCacheParams(String str, boolean z2, int i2, int i3, int i4) {
        TXCAudioEngineJNI.nativeSetRemoteAudioCacheParams(str, z2, i2, i3, i4);
    }

    public void setRemoteAudioStreamEventListener(String str, d dVar) {
        if (str == null) {
            return;
        }
        synchronized (mJitterEventListenerMapLock) {
            mJitterEventListenerMap.put(str, new WeakReference<>(dVar));
        }
    }

    public void setRemotePlayoutVolume(String str, int i2) {
        if (str == null) {
            return;
        }
        TXCAudioEngineJNI.nativeSetRemotePlayoutVolume(str, i2);
    }

    public void setRemoteStreamDataCallbackFormat(String str, int i2, int i3, int i4) {
        TXCLog.i(TAG, "setRemoteStreamDataCallbackFormat: id-" + str + " sampleRate-" + i2 + " channels-" + i3 + " length-" + i4);
        TXCAudioEngineJNI.nativeSetRemoteStreamDataCallbackFormat(str, i2, i3, i4);
    }

    public boolean setReverbParamType(int i2, float f2) {
        TXCLog.i(TAG, "setReverbParamType: reverbParamType =" + i2 + " value = " + f2);
        TXCAudioEngineJNI.nativeSetRecordReverbParam(i2, f2);
        return true;
    }

    public boolean setReverbType(int i2) {
        TXCAudioEngineJNI.nativeSetRecordReverb(i2);
        return true;
    }

    public void setSetAudioEngineRemoteStreamDataListener(String str, f fVar) {
        if (str == null) {
            return;
        }
        synchronized (mJitterDataListenerMapLock) {
            mJitterDataListenerMap.put(str, new WeakReference<>(fVar));
        }
        TXCAudioEngineJNI.nativeSetAudioEngineRemoteStreamDataListener(str, fVar != null);
    }

    public boolean setSoftwareCaptureVolume(float f2) {
        TXCLog.i(TAG, "setRecordVolume: " + f2);
        TXCAudioEngineJNI.nativeSetSoftwareCaptureVolume(f2);
        return true;
    }

    public void setSystemAudioKitEnabled() {
        startSystemAudioKit();
    }

    public boolean setVoiceChangerType(TXAudioEffectManager.TXVoiceChangerType tXVoiceChangerType) {
        TXCLog.i(TAG, "setVoiceChangerType " + tXVoiceChangerType.getNativeValue());
        TXCAudioEngineJNI.nativeSetCaptureVoiceChanger(tXVoiceChangerType.getNativeValue());
        return true;
    }

    public boolean setVoicePitch(double d3) {
        TXCLog.i(TAG, "setVoicePitch: " + d3);
        TXCAudioEngineJNI.nativeSetVoicePitch(d3);
        return true;
    }

    public int startLocalAudio(int i2, boolean z2) {
        TXCLog.i(TAG, "startLocalAudio audioFormat:" + i2);
        Context context = mContext;
        if (context == null) {
            TXCLog.i(TAG, "Please call CreateInstance fisrt!!!");
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        TXCAudioEngineJNI.InitTraeEngineLibrary(context);
        TXCAudioEngineJNI.nativeStartLocalAudio(i2, z2);
        this.mDeviceIsRecording = true;
        return 0;
    }

    public int startLocalAudioDumping(int i2, int i3, int i4, String str) {
        return TXCAudioEngineJNI.nativeStartLocalAudioDumping(i2, i3, i4, str);
    }

    public void startRemoteAudio(String str, boolean z2) {
        synchronized (this.mStartStopRemoteAudioMutex) {
            TXCAudioEngineJNI.nativeStartRemoteAudio(sInstance, z2, str);
        }
        TXCAudioEngineJNI.nativeSetRemoteAudioJitterCycle(str, com.tencent.liteav.basic.c.c.a().a("Audio", "LIVE_JitterCycle"));
        TXCAudioEngineJNI.nativeSetRemoteAudioBlockThreshold(str, com.tencent.liteav.basic.c.c.a().a("Audio", "LoadingThreshold"));
    }

    public int stopLocalAudio() {
        TXCLog.i(TAG, "stopLocalAudio");
        TXCAudioEngineJNI.nativeStopLocalAudio();
        this.mDeviceIsRecording = false;
        return 0;
    }

    public void stopLocalAudioDumping() {
        TXCAudioEngineJNI.nativeStopLocalAudioDumping();
    }

    public void stopRemoteAudio(String str) {
        if (str == null) {
            return;
        }
        synchronized (this.mStartStopRemoteAudioMutex) {
            TXCAudioEngineJNI.nativeStopRemoteAudio(str);
        }
    }

    public void sendCustomPCMData(com.tencent.liteav.basic.structs.a aVar) {
        TXCAudioEngineJNI.sendCustomPCMData(aVar);
    }

    public void setAudioQuality(int i2, int i3, int i4, int i5, int i6, int i7) {
        TXCAudioEngineJNI.nativeSetAudioQualityEx(i2, i3, i4, i5, i6, i7);
    }
}
