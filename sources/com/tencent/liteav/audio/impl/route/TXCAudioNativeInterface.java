package com.tencent.liteav.audio.impl.route;

import android.content.Context;
import android.media.AudioManager;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.audio.impl.TXCAudioEngineJNI;
import com.tencent.liteav.audio.impl.route.a;
import com.tencent.liteav.audio.impl.route.f;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.rtmp.sharp.jni.TraeAudioManager;

/* loaded from: classes6.dex */
public class TXCAudioNativeInterface {
    private static final int OUTPUT_MODE_HEADSET = 0;
    private static final int OUTPUT_MODE_SPEAKER = 1;
    private static final String TAG = "AudioNativeInterface";
    private final e mAudioRouteManager;
    private final Context mContext;
    private String[] mDeviceList = null;
    private AudioManager mAudioManager = null;
    private f mAudioSession = null;

    public TXCAudioNativeInterface(Context context) {
        this.mContext = context;
        this.mAudioRouteManager = new e(context);
        TXCLog.i(TAG, "AudioDeviceInterface SDK_INT: %d, MANUFACTURE: %s, MODEL: %s", Integer.valueOf(TXCBuild.VersionInt()), TXCBuild.Manufacturer(), TXCBuild.Model());
    }

    public static void LogTraceEntry(String str) {
        TXCLog.i(TAG, getTraceInfo() + " entry:" + str);
    }

    public static void LogTraceExit() {
        TXCLog.i(TAG, getTraceInfo() + " exit");
    }

    private AudioManager getAudioManager() {
        Context context;
        if (this.mAudioManager == null && (context = this.mContext) != null) {
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
        }
        return this.mAudioManager;
    }

    private static String getTraceInfo() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        sb.append(stackTrace[2].getClassName());
        sb.append(StrPool.DOT);
        sb.append(stackTrace[2].getMethodName());
        sb.append(": ");
        sb.append(stackTrace[2].getLineNumber());
        return sb.toString();
    }

    private a.EnumC0325a nativeValueToAudioIOScene(int i2) {
        a.EnumC0325a enumC0325a = a.EnumC0325a.STOPPED;
        return (i2 < 0 || i2 >= 7) ? enumC0325a : new a.EnumC0325a[]{enumC0325a, enumC0325a, a.EnumC0325a.VOICE_CHAT, a.EnumC0325a.MEDIA_PLAY_AND_RECORD, a.EnumC0325a.MEDIA_PLAYBACK, a.EnumC0325a.VOICE_PLAYBACK, a.EnumC0325a.IDLE}[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPlayoutDeviceChanged(String str) {
        TXCLog.i(TAG, "device: " + str);
        TXCAudioEngineJNI.nativeSetPlayoutDevice(str.equals(TraeAudioManager.DEVICE_EARPHONE) ? 1 : str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE) ? 2 : str.equals(TraeAudioManager.DEVICE_WIREDHEADSET) ? 3 : str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET) ? 4 : 0);
    }

    private void updateVolumeTypeForMedia() {
        LogTraceEntry("");
        AudioManager audioManager = getAudioManager();
        if (audioManager != null && audioManager.getMode() != 0) {
            audioManager.setMode(0);
        }
        this.mAudioSession.d();
        LogTraceExit();
    }

    private void updateVolumeTypeForVoIP(a.EnumC0325a enumC0325a) {
        LogTraceEntry("");
        this.mAudioSession.a(a.a(enumC0325a));
        this.mAudioSession.d();
        LogTraceExit();
    }

    private void waitAMomentIfInPhoneCall() throws InterruptedException {
        if (getAudioManager() == null) {
            return;
        }
        int i2 = 5;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0 || this.mAudioManager.getMode() != 2) {
                return;
            }
            TXCLog.e(TAG, "waiting.  mode:" + this.mAudioManager.getMode());
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            i2 = i3;
        }
    }

    public void initAudioRouteManager() {
        if (this.mContext == null) {
            return;
        }
        TXCLog.i(TAG, "initAudioManager, TXCAudioSession create");
        if (this.mAudioSession == null) {
            this.mAudioSession = new f(this.mContext, this.mAudioRouteManager, new f.a() { // from class: com.tencent.liteav.audio.impl.route.TXCAudioNativeInterface.1
                @Override // com.tencent.liteav.audio.impl.route.f.a
                public void a(String[] strArr, String str, String str2, String str3) {
                    TXCAudioNativeInterface.this.mDeviceList = strArr;
                    TXCAudioNativeInterface.this.notifyPlayoutDeviceChanged(str);
                }

                @Override // com.tencent.liteav.audio.impl.route.f.a
                public void a(int i2, String[] strArr, String str, String str2, String str3) {
                    TXCAudioNativeInterface.this.mDeviceList = strArr;
                }

                @Override // com.tencent.liteav.audio.impl.route.f.a
                public void a(int i2, String str) {
                    if (i2 == 0) {
                        TXCAudioNativeInterface.this.notifyPlayoutDeviceChanged(str);
                    }
                }
            });
        }
        this.mAudioSession.a(TraeAudioManager.VIDEO_CONFIG);
    }

    public void notifyAudioIOSceneChanged(int i2, int i3) throws InterruptedException {
        a.EnumC0325a enumC0325aNativeValueToAudioIOScene = nativeValueToAudioIOScene(i2);
        a.EnumC0325a enumC0325aNativeValueToAudioIOScene2 = nativeValueToAudioIOScene(i3);
        TXCLog.i(TAG, "notify audio io scene changed, %s -> %s", enumC0325aNativeValueToAudioIOScene, enumC0325aNativeValueToAudioIOScene2);
        if (!enumC0325aNativeValueToAudioIOScene.a()) {
            this.mAudioSession.e();
        }
        this.mAudioRouteManager.a(enumC0325aNativeValueToAudioIOScene2);
        if (enumC0325aNativeValueToAudioIOScene2 != a.EnumC0325a.STOPPED) {
            AudioManager audioManager = getAudioManager();
            if (audioManager != null) {
                waitAMomentIfInPhoneCall();
                if (audioManager.isMicrophoneMute()) {
                    audioManager.setMicrophoneMute(false);
                    TXCLog.i(TAG, "setMicrophoneMute false when ioscene changed");
                }
            }
            if (enumC0325aNativeValueToAudioIOScene2.a()) {
                updateVolumeTypeForMedia();
            } else {
                updateVolumeTypeForVoIP(enumC0325aNativeValueToAudioIOScene2);
            }
        }
    }

    public int setAudioOutputMode(int i2) {
        f fVar;
        TXCLog.w(TAG, "TXCAudioSession SetAudioOutputMode: " + i2);
        if (i2 != 0) {
            if (1 != i2 || (fVar = this.mAudioSession) == null) {
                return -1;
            }
            fVar.b(TraeAudioManager.DEVICE_SPEAKERPHONE);
            return 0;
        }
        if (this.mDeviceList == null || this.mAudioSession == null) {
            return -1;
        }
        boolean z2 = false;
        do {
            int i3 = 0;
            while (true) {
                String[] strArr = this.mDeviceList;
                if (i3 >= strArr.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_WIREDHEADSET.equals(strArr[i3])) {
                    this.mAudioSession.b(TraeAudioManager.DEVICE_WIREDHEADSET);
                    z2 = true;
                }
                i3++;
            }
            int i4 = 0;
            while (true) {
                String[] strArr2 = this.mDeviceList;
                if (i4 >= strArr2.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(strArr2[i4])) {
                    this.mAudioSession.b(TraeAudioManager.DEVICE_BLUETOOTHHEADSET);
                    z2 = true;
                }
                i4++;
            }
            int i5 = 0;
            while (true) {
                String[] strArr3 = this.mDeviceList;
                if (i5 >= strArr3.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_EARPHONE.equals(strArr3[i5])) {
                    this.mAudioSession.b(TraeAudioManager.DEVICE_EARPHONE);
                    z2 = true;
                }
                i5++;
            }
        } while (!z2);
        return 0;
    }

    public int startService(String str) {
        TXCLog.i(TAG, "startService: " + this.mAudioSession + " deviceConfig:" + str);
        f fVar = this.mAudioSession;
        if (fVar != null) {
            return fVar.a(str);
        }
        return -1;
    }

    public int stopService() {
        TXCLog.i(TAG, "stopService: " + this.mAudioSession);
        f fVar = this.mAudioSession;
        if (fVar != null) {
            return fVar.c();
        }
        return -1;
    }

    public void uninitAudioRouteManager() {
        if (this.mContext == null) {
            TXCLog.w(TAG, "uninitTXCAudioManager , context null");
            return;
        }
        TXCLog.w(TAG, "uninitTXCAudioManager , stopService");
        f fVar = this.mAudioSession;
        if (fVar != null) {
            fVar.c();
            this.mAudioSession.b();
            this.mAudioSession = null;
        }
    }
}
