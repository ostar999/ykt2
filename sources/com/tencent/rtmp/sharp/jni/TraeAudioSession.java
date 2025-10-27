package com.tencent.rtmp.sharp.jni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Process;
import com.tencent.liteav.basic.a.b;

/* loaded from: classes6.dex */
public class TraeAudioSession extends BroadcastReceiver {
    static int s_nSessionIdAllocator;
    private ITraeAudioCallback mCallback;
    private Context mContext;
    private long mSessionId;
    private String _connectedDev = TraeAudioManager.DEVICE_NONE;
    private boolean _canSwtich2Earphone = true;
    final String TRAE_ACTION_PHONE_STATE = "android.intent.action.PHONE_STATE";

    public interface ITraeAudioCallback {
        void onAudioRouteSwitchEnd(String str, long j2);

        void onAudioRouteSwitchStart(String str, String str2);

        void onConnectDeviceRes(int i2, String str, boolean z2);

        void onDeviceChangabledUpdate(boolean z2);

        void onDeviceListUpdate(String[] strArr, String str, String str2, String str3);

        void onGetConnectedDeviceRes(int i2, String str);

        void onGetConnectingDeviceRes(int i2, String str);

        void onGetDeviceListRes(int i2, String[] strArr, String str, String str2, String str3);

        void onGetStreamTypeRes(int i2, int i3);

        void onIsDeviceChangabledRes(int i2, boolean z2);

        void onRingCompletion(int i2, String str);

        void onServiceStateUpdate(boolean z2);

        void onStreamTypeUpdate(int i2);

        void onVoicecallPreprocessRes(int i2);
    }

    public TraeAudioSession(Context context, ITraeAudioCallback iTraeAudioCallback) {
        this.mSessionId = Long.MIN_VALUE;
        this.mSessionId = requestSessionId();
        this.mCallback = iTraeAudioCallback;
        this.mContext = context;
        if (context == null && QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("AudioSession | Invalid parameters: ctx = ");
            sb.append(context == null ? "null" : "{object}");
            sb.append("; cb = ");
            sb.append(iTraeAudioCallback != null ? "{object}" : "null");
            QLog.w("TRAE", 2, sb.toString());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_RES);
        intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
        if (context != null) {
            try {
                b.a(context).a(this, intentFilter);
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "registerReceiver Exception: " + e2.getMessage());
                }
            }
        }
        registerAudioSession(this, true);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioSession create, mSessionId: " + this.mSessionId);
        }
    }

    public static void ExConnectDevice(Context context, String str) {
        if (context == null || str == null || str.length() <= 0) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
        intent.putExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE);
        intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.OPERATION_CONNECTDEVICE);
        intent.putExtra(TraeAudioManager.CONNECTDEVICE_DEVICENAME, str);
        b.a(context).a(intent);
    }

    private int registerAudioSession(TraeAudioSession traeAudioSession, boolean z2) {
        Context context = this.mContext;
        if (context == null) {
            return -1;
        }
        return TraeAudioManager.registerAudioSession(traeAudioSession, z2, this.mSessionId, context);
    }

    public static long requestSessionId() {
        long jMyPid = Process.myPid() << 32;
        int i2 = s_nSessionIdAllocator + 1;
        s_nSessionIdAllocator = i2;
        return jMyPid + i2;
    }

    public int EarAction(int i2) {
        return TraeAudioManager.earAction(TraeAudioManager.OPERATION_EARACTION, this.mSessionId, true, i2);
    }

    public int connectDevice(String str) {
        return TraeAudioManager.connectDevice(TraeAudioManager.OPERATION_CONNECTDEVICE, this.mSessionId, true, str);
    }

    public int connectHighestPriorityDevice() {
        return TraeAudioManager.connectHighestPriorityDevice(TraeAudioManager.OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE, this.mSessionId, true);
    }

    public int disableDeviceSwitch() {
        return TraeAudioManager.disableDeviceSwitch();
    }

    public int getConnectedDevice() {
        return TraeAudioManager.getConnectedDevice(TraeAudioManager.OPERATION_GETCONNECTEDDEVICE, this.mSessionId, true);
    }

    public int getConnectingDevice() {
        return TraeAudioManager.getConnectingDevice(TraeAudioManager.OPERATION_GETCONNECTINGDEVICE, this.mSessionId, true);
    }

    public int getDeviceList() {
        return TraeAudioManager.getDeviceList(TraeAudioManager.OPERATION_GETDEVICELIST, this.mSessionId, true);
    }

    public int getStreamType() {
        return TraeAudioManager.getStreamType(TraeAudioManager.OPERATION_GETSTREAMTYPE, this.mSessionId, true);
    }

    public int isDeviceChangabled() {
        return TraeAudioManager.isDeviceChangabled(TraeAudioManager.OPERATION_ISDEVICECHANGABLED, this.mSessionId, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:190:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:244:? A[RETURN, SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onReceive(android.content.Context r25, android.content.Intent r26) {
        /*
            Method dump skipped, instructions count: 1254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.TraeAudioSession.onReceive(android.content.Context, android.content.Intent):void");
    }

    public void onReceiveCallback(Intent intent) {
        try {
            if (intent == null) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "[ERROR] intent = null!!");
                    return;
                }
                return;
            }
            long longExtra = intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE);
            String stringExtra = intent.getStringExtra(TraeAudioManager.PARAM_OPERATION);
            int intExtra = intent.getIntExtra(TraeAudioManager.PARAM_RES_ERRCODE, 0);
            if (TraeAudioManager.ACTION_TRAEAUDIOMANAGER_RES.equals(intent.getAction()) && this.mSessionId == longExtra && TraeAudioManager.OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "AudioSession|[onReceiveCallback onVoicecallPreprocess] err:" + intExtra);
                }
                ITraeAudioCallback iTraeAudioCallback = this.mCallback;
                if (iTraeAudioCallback != null) {
                    iTraeAudioCallback.onVoicecallPreprocessRes(intExtra);
                }
            }
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "AudioSession| nSessinId = " + this.mSessionId + " onReceive::intent:" + intent.toString() + " intent.getAction():" + intent.getAction() + " Exception:" + e2.getMessage());
            }
        }
    }

    public int recoverAudioFocus() {
        return TraeAudioManager.recoverAudioFocus(TraeAudioManager.OPERATION_RECOVER_AUDIO_FOCUS, this.mSessionId, true);
    }

    public void release() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioSession release, mSessionId: " + this.mSessionId);
        }
        Context context = this.mContext;
        if (context != null) {
            try {
                context.unregisterReceiver(this);
            } catch (Exception unused) {
            }
            b.a(this.mContext).a(this);
        }
        registerAudioSession(this, false);
        this.mContext = null;
        this.mCallback = null;
    }

    public int requestReleaseAudioFocus() {
        return TraeAudioManager.requestReleaseAudioFocus(TraeAudioManager.OPERATION_REQUEST_RELEASE_AUDIO_FOCUS, this.mSessionId, true);
    }

    public void setCallback(ITraeAudioCallback iTraeAudioCallback) {
        this.mCallback = iTraeAudioCallback;
    }

    public int startRing(int i2, int i3, Uri uri, String str, boolean z2) {
        return TraeAudioManager.startRing(TraeAudioManager.OPERATION_STARTRING, this.mSessionId, true, i2, i3, uri, str, z2, 1, "normal-ring", false);
    }

    public int startService(String str) {
        if (str == null || str.length() <= 0) {
            str = "internal_disable_dev_switch";
        }
        return TraeAudioManager.startService(TraeAudioManager.OPERATION_STARTSERVICE, this.mSessionId, true, str);
    }

    public int stopRing() {
        return TraeAudioManager.stopRing(TraeAudioManager.OPERATION_STOPRING, this.mSessionId, true);
    }

    public int stopService() {
        return TraeAudioManager.stopService(TraeAudioManager.OPERATION_STOPSERVICE, this.mSessionId, true);
    }

    public int voiceCallAudioParamChanged(int i2, int i3) {
        return TraeAudioManager.voiceCallAudioParamChanged(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST, this.mSessionId, true, i2, i3);
    }

    public int voiceCallPostprocess() {
        return TraeAudioManager.voicecallPostprocess(TraeAudioManager.OPERATION_VOICECALL_POSTPROCESS, this.mSessionId, true);
    }

    public int voiceCallPreprocess(int i2, int i3) {
        return TraeAudioManager.voicecallPreprocess(TraeAudioManager.OPERATION_VOICECALL_PREPROCESS, this.mSessionId, true, i2, i3);
    }

    public int startRing(int i2, int i3, Uri uri, String str, boolean z2, int i4, String str2) {
        return TraeAudioManager.startRing(TraeAudioManager.OPERATION_STARTRING, this.mSessionId, true, i2, i3, uri, str, z2, i4, str2, false);
    }

    public int startRing(int i2, int i3, Uri uri, String str, boolean z2, int i4, String str2, boolean z3) {
        return TraeAudioManager.startRing(TraeAudioManager.OPERATION_STARTRING, this.mSessionId, true, i2, i3, uri, str, z2, i4, str2, z3);
    }
}
