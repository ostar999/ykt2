package com.tencent.liteav.audio.impl.route;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Process;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.sharp.jni.TraeAudioManager;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
public class f extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private static int f18295a;

    /* renamed from: b, reason: collision with root package name */
    private final long f18296b = a();

    /* renamed from: c, reason: collision with root package name */
    private final Context f18297c;

    /* renamed from: d, reason: collision with root package name */
    private final e f18298d;

    /* renamed from: e, reason: collision with root package name */
    private a f18299e;

    public static abstract class a {
        public void a(int i2) {
        }

        public void a(int i2, String str) {
        }

        public void a(int i2, String str, boolean z2) {
        }

        public void a(int i2, boolean z2) {
        }

        public void a(int i2, String[] strArr, String str, String str2, String str3) {
        }

        public void a(String str, long j2) {
        }

        public void a(String str, String str2) {
        }

        public void a(boolean z2) {
        }

        public void a(String[] strArr, String str, String str2, String str3) {
        }

        public void b(int i2, String str) {
        }

        public void b(boolean z2) {
        }
    }

    public f(Context context, e eVar, a aVar) {
        this.f18299e = null;
        this.f18299e = aVar;
        this.f18297c = context;
        this.f18298d = eVar;
        if (context == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("AudioSession | Invalid parameters: ctx = ");
            sb.append(context == null ? "null" : "{object}");
            sb.append("; cb = ");
            sb.append(aVar != null ? "{object}" : "null");
            TXCLog.w("TXCAudioSession", sb.toString());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_RES");
        intentFilter.addAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_NOTIFY");
        if (context != null) {
            try {
                com.tencent.liteav.basic.a.b.a(context).a(this, intentFilter);
            } catch (Exception e2) {
                TXCLog.e("TXCAudioSession", "registerReceiver Exception: " + e2.getMessage());
            }
        }
        a(this, true);
        TXCLog.i("TXCAudioSession", "TraeAudioSession create, mSessionId: " + this.f18296b);
    }

    public static long a() {
        long jMyPid = Process.myPid() << 32;
        int i2 = f18295a + 1;
        f18295a = i2;
        return jMyPid + i2;
    }

    public void b() {
        TXCLog.i("TXCAudioSession", "TraeAudioSession release, mSessionId: " + this.f18296b);
        Context context = this.f18297c;
        if (context != null) {
            try {
                context.unregisterReceiver(this);
            } catch (Exception unused) {
            }
            com.tencent.liteav.basic.a.b.a(this.f18297c).a(this);
        }
        a(this, false);
        this.f18299e = null;
    }

    public int c() {
        return this.f18298d.a(TraeAudioManager.OPERATION_STOPSERVICE, this.f18296b);
    }

    public void d() {
        this.f18298d.c(TraeAudioManager.OPERATION_GETCONNECTEDDEVICE, this.f18296b);
    }

    public void e() {
        this.f18298d.d(TraeAudioManager.OPERATION_VOICECALL_POSTPROCESS, this.f18296b);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String str;
        try {
            if (intent == null) {
                TXCLog.w("TXCAudioSession", "[ERROR] intent = null!!");
                return;
            }
            long longExtra = intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE);
            String stringExtra = intent.getStringExtra(TraeAudioManager.PARAM_OPERATION);
            int intExtra = intent.getIntExtra(TraeAudioManager.PARAM_RES_ERRCODE, 0);
            if ("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_NOTIFY".equals(intent.getAction())) {
                if (TraeAudioManager.NOTIFY_SERVICE_STATE.equals(stringExtra)) {
                    boolean booleanExtra = intent.getBooleanExtra(TraeAudioManager.NOTIFY_SERVICE_STATE_DATE, false);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[onServiceStateUpdate]");
                    sb.append(booleanExtra ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                    TXCLog.i("TXCAudioSession", sb.toString());
                    a aVar = this.f18299e;
                    if (aVar != null) {
                        aVar.a(booleanExtra);
                        return;
                    }
                    return;
                }
                if (TraeAudioManager.NOTIFY_DEVICELIST_UPDATE.equals(stringExtra)) {
                    String[] stringArrayExtra = intent.getStringArrayExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST);
                    String stringExtra2 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE);
                    String stringExtra3 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE);
                    String stringExtra4 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME);
                    StringBuilder sb2 = new StringBuilder("\n");
                    for (int i2 = 0; i2 < stringArrayExtra.length; i2++) {
                        sb2.append("    ");
                        sb2.append(i2);
                        sb2.append(" ");
                        sb2.append(stringArrayExtra[i2]);
                        sb2.append("\n");
                    }
                    sb2.append("\n");
                    TXCLog.i("TXCAudioSession", "[onDeviceListUpdate]  connected:" + stringExtra2 + " prevConnected:" + stringExtra3 + " bt:" + stringExtra4 + " Num:" + stringArrayExtra.length + ((Object) sb2));
                    a aVar2 = this.f18299e;
                    if (aVar2 != null) {
                        aVar2.a(stringArrayExtra, stringExtra2, stringExtra3, stringExtra4);
                        return;
                    }
                    return;
                }
                if (TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE.equals(stringExtra)) {
                    boolean booleanExtra2 = intent.getBooleanExtra(TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE_DATE, true);
                    TXCLog.i("TXCAudioSession", "[onDeviceChangabledUpdate]" + booleanExtra2);
                    a aVar3 = this.f18299e;
                    if (aVar3 != null) {
                        aVar3.b(booleanExtra2);
                        return;
                    }
                    return;
                }
                if (TraeAudioManager.NOTIFY_ROUTESWITCHSTART.equals(stringExtra)) {
                    String stringExtra5 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_ROUTESWITCHSTART_FROM);
                    String stringExtra6 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_ROUTESWITCHSTART_TO);
                    a aVar4 = this.f18299e;
                    if (aVar4 == null || stringExtra5 == null || stringExtra6 == null) {
                        return;
                    }
                    aVar4.a(stringExtra5, stringExtra6);
                    return;
                }
                if (TraeAudioManager.NOTIFY_ROUTESWITCHEND.equals(stringExtra)) {
                    String stringExtra7 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_ROUTESWITCHEND_DEV);
                    long longExtra2 = intent.getLongExtra(TraeAudioManager.EXTRA_DATA_ROUTESWITCHEND_TIME, -1L);
                    a aVar5 = this.f18299e;
                    if (aVar5 == null || stringExtra7 == null || longExtra2 == -1) {
                        return;
                    }
                    aVar5.a(stringExtra7, longExtra2);
                    return;
                }
                return;
            }
            if ("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_RES".equals(intent.getAction())) {
                str = "TXCAudioSession";
                try {
                    if (this.f18296b != longExtra) {
                        return;
                    }
                    if (TraeAudioManager.OPERATION_GETDEVICELIST.equals(stringExtra)) {
                        String[] stringArrayExtra2 = intent.getStringArrayExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST);
                        String stringExtra8 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE);
                        String stringExtra9 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE);
                        String stringExtra10 = intent.getStringExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME);
                        StringBuilder sb3 = new StringBuilder("\n");
                        for (int i3 = 0; i3 < stringArrayExtra2.length; i3++) {
                            sb3.append("    ");
                            sb3.append(i3);
                            sb3.append(" ");
                            sb3.append(stringArrayExtra2[i3]);
                            sb3.append("\n");
                        }
                        sb3.append("\n");
                        TXCLog.i(str, "[onGetDeviceListRes] err:" + intExtra + " connected:" + stringExtra8 + " prevConnected:" + stringExtra9 + " bt:" + stringExtra10 + " Num:" + stringArrayExtra2.length + ((Object) sb3));
                        a aVar6 = this.f18299e;
                        if (aVar6 != null) {
                            aVar6.a(intExtra, stringArrayExtra2, stringExtra8, stringExtra9, stringExtra10);
                            return;
                        }
                        return;
                    }
                    if (TraeAudioManager.OPERATION_CONNECTDEVICE.equals(stringExtra)) {
                        String stringExtra11 = intent.getStringExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME);
                        TXCLog.i(str, "[onConnectDeviceRes] err:" + intExtra + " dev:" + stringExtra11);
                        a aVar7 = this.f18299e;
                        if (aVar7 != null) {
                            aVar7.a(intExtra, stringExtra11, intExtra == 0);
                            return;
                        }
                        return;
                    }
                    if (TraeAudioManager.OPERATION_ISDEVICECHANGABLED.equals(stringExtra)) {
                        boolean booleanExtra3 = intent.getBooleanExtra(TraeAudioManager.ISDEVICECHANGABLED_RESULT_ISCHANGABLED, false);
                        TXCLog.i(str, "[onIsDeviceChangabledRes] err:" + intExtra + " Changabled:" + booleanExtra3);
                        a aVar8 = this.f18299e;
                        if (aVar8 != null) {
                            aVar8.a(intExtra, booleanExtra3);
                            return;
                        }
                        return;
                    }
                    if (TraeAudioManager.OPERATION_GETCONNECTEDDEVICE.equals(stringExtra)) {
                        String stringExtra12 = intent.getStringExtra(TraeAudioManager.GETCONNECTEDDEVICE_RESULT_LIST);
                        TXCLog.i(str, "[onGetConnectedDeviceRes] err:" + intExtra + " dev:" + stringExtra12);
                        a aVar9 = this.f18299e;
                        if (aVar9 != null) {
                            aVar9.a(intExtra, stringExtra12);
                            return;
                        }
                        return;
                    }
                    if (!TraeAudioManager.OPERATION_GETCONNECTINGDEVICE.equals(stringExtra)) {
                        if (TraeAudioManager.OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                            TXCLog.i(str, "[onVoicecallPreprocess] err:" + intExtra);
                            a aVar10 = this.f18299e;
                            if (aVar10 != null) {
                                aVar10.a(intExtra);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    String stringExtra13 = intent.getStringExtra(TraeAudioManager.GETCONNECTINGDEVICE_RESULT_LIST);
                    TXCLog.i(str, "[onGetConnectingDeviceRes] err:" + intExtra + " dev:" + stringExtra13);
                    a aVar11 = this.f18299e;
                    if (aVar11 != null) {
                        aVar11.b(intExtra, stringExtra13);
                    }
                } catch (Exception e2) {
                    e = e2;
                    TXCLog.e(str, " nSessinId = " + this.f18296b + " onReceive::intent:" + intent.toString() + " intent.getAction():" + intent.getAction() + " Exception:" + e.getMessage());
                }
            }
        } catch (Exception e3) {
            e = e3;
            str = "TXCAudioSession";
        }
    }

    private void a(f fVar, boolean z2) {
        if (this.f18297c == null) {
            return;
        }
        this.f18298d.a(fVar, z2, this.f18296b);
    }

    public int a(String str) {
        if (str == null || str.length() <= 0) {
            str = "internal_disable_dev_switch";
        }
        return this.f18298d.a(TraeAudioManager.OPERATION_STARTSERVICE, this.f18296b, str);
    }

    public void a(int i2) {
        this.f18298d.a(TraeAudioManager.OPERATION_VOICECALL_PREPROCESS, this.f18296b, i2);
    }

    public void a(Intent intent) {
        try {
            if (intent == null) {
                TXCLog.w("TXCAudioSession", "[ERROR] intent = null!!");
                return;
            }
            long longExtra = intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE);
            String stringExtra = intent.getStringExtra(TraeAudioManager.PARAM_OPERATION);
            int intExtra = intent.getIntExtra(TraeAudioManager.PARAM_RES_ERRCODE, 0);
            if ("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_RES".equals(intent.getAction()) && this.f18296b == longExtra && TraeAudioManager.OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                TXCLog.w("TXCAudioSession", "[onReceiveCallback onVoicecallPreprocess] err:" + intExtra);
                a aVar = this.f18299e;
                if (aVar != null) {
                    aVar.a(intExtra);
                }
            }
        } catch (Exception e2) {
            TXCLog.e("TXCAudioSession", " nSessinId = " + this.f18296b + " onReceive::intent:" + intent.toString() + " intent.getAction():" + intent.getAction() + " Exception:" + e2.getMessage());
        }
    }

    public void b(String str) {
        this.f18298d.b(TraeAudioManager.OPERATION_CONNECTDEVICE, this.f18296b, str);
    }
}
