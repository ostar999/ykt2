package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import com.tencent.liteav.audio.impl.TXCAudioEngineJNI;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.sharp.jni.TraeAudioSession;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class AudioSessionDuplicate {
    private static final String TAG = "AudioSessionDuplicate";
    private static TraeAudioSession _as;
    private static boolean _preDone;
    private static Condition _precon;
    private static ReentrantLock _prelock;
    private static String[] mDeviceList;
    private static int playoutDeviceType;
    private static boolean usingJava;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        _prelock = reentrantLock;
        _precon = reentrantLock.newCondition();
        _preDone = false;
        usingJava = true;
        mDeviceList = null;
        playoutDeviceType = 0;
    }

    public static void DeleteAudioSessionDuplicate() {
        TXCLog.i(TAG, " DeleteAudioSessionDuplicate...");
        TraeAudioSession traeAudioSession = _as;
        if (traeAudioSession != null) {
            traeAudioSession.voiceCallPostprocess();
            _as.release();
            _as = null;
        }
    }

    public static void NewAudioSessionDuplicate(Context context) {
        TXCLog.i(TAG, " NewAudioSessionDuplicate...");
        if (_as == null) {
            _as = new TraeAudioSession(context, new TraeAudioSession.ITraeAudioCallback() { // from class: com.tencent.rtmp.sharp.jni.AudioSessionDuplicate.1
                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchEnd(String str, long j2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchStart(String str, String str2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onConnectDeviceRes(int i2, String str, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceChangabledUpdate(boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceListUpdate(String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioSessionDuplicate.mDeviceList = strArr;
                    if (AudioSessionDuplicate.usingJava) {
                        AudioSessionDuplicate.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectedDeviceRes(int i2, String str) {
                    if (i2 == 0) {
                        AudioSessionDuplicate.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectingDeviceRes(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetDeviceListRes(int i2, String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioSessionDuplicate.mDeviceList = strArr;
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetStreamTypeRes(int i2, int i3) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onIsDeviceChangabledRes(int i2, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onRingCompletion(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onServiceStateUpdate(boolean z2) {
                    if (z2) {
                        return;
                    }
                    try {
                        AudioSessionDuplicate._prelock.lock();
                        boolean unused = AudioSessionDuplicate._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "onServiceStateUpdate signalAll");
                        }
                        AudioSessionDuplicate._precon.signalAll();
                        AudioSessionDuplicate._prelock.unlock();
                    } catch (Exception unused2) {
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onStreamTypeUpdate(int i2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onVoicecallPreprocessRes(int i2) {
                    try {
                        AudioSessionDuplicate._prelock.lock();
                        boolean unused = AudioSessionDuplicate._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "onVoicecallPreprocessRes signalAll");
                        }
                        AudioSessionDuplicate._precon.signalAll();
                        AudioSessionDuplicate._prelock.unlock();
                    } catch (Exception unused2) {
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onOutputChanage(String str) {
        TXCLog.i(TAG, "device: " + str);
        if (str.equals(TraeAudioManager.DEVICE_EARPHONE)) {
            playoutDeviceType = 1;
        } else if (str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
            playoutDeviceType = 2;
        } else if (str.equals(TraeAudioManager.DEVICE_WIREDHEADSET)) {
            playoutDeviceType = 3;
        } else if (str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
            playoutDeviceType = 4;
        } else {
            playoutDeviceType = 0;
        }
        TXCAudioEngineJNI.nativeSetPlayoutDevice(playoutDeviceType);
    }
}
