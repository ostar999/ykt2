package com.tencent.liteav.audio.impl.route;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.C;
import com.hjq.permissions.Permission;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.tencent.liteav.audio.impl.route.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.rtmp.sharp.jni.TraeAudioManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint({"NewApi"})
/* loaded from: classes6.dex */
public class e extends BroadcastReceiver {

    /* renamed from: b, reason: collision with root package name */
    private Context f18231b;

    /* renamed from: n, reason: collision with root package name */
    private i f18243n;

    /* renamed from: a, reason: collision with root package name */
    private AudioManager f18230a = null;

    /* renamed from: c, reason: collision with root package name */
    private int f18232c = 0;

    /* renamed from: d, reason: collision with root package name */
    private int f18233d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f18234e = -1;

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.audio.impl.route.d f18235f = com.tencent.liteav.audio.impl.route.d.HEADSET_AVAILABLE;

    /* renamed from: g, reason: collision with root package name */
    private final boolean[] f18236g = {false};

    /* renamed from: h, reason: collision with root package name */
    private a.EnumC0325a f18237h = a.EnumC0325a.STOPPED;

    /* renamed from: i, reason: collision with root package name */
    private boolean f18238i = false;

    /* renamed from: j, reason: collision with root package name */
    private final com.tencent.liteav.audio.impl.route.g f18239j = new com.tencent.liteav.audio.impl.route.g();

    /* renamed from: k, reason: collision with root package name */
    private final com.tencent.liteav.audio.impl.route.h f18240k = new com.tencent.liteav.audio.impl.route.h();

    /* renamed from: l, reason: collision with root package name */
    private com.tencent.liteav.audio.impl.route.b f18241l = null;

    /* renamed from: m, reason: collision with root package name */
    private String f18242m = TraeAudioManager.DEVICE_NONE;

    /* renamed from: o, reason: collision with root package name */
    private h f18244o = null;

    /* renamed from: p, reason: collision with root package name */
    private final Object f18245p = new Object();

    /* renamed from: com.tencent.liteav.audio.impl.route.e$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18255a;

        static {
            int[] iArr = new int[a.values().length];
            f18255a = iArr;
            try {
                iArr[a.MESSAGE_DISABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18255a[a.MESSAGE_GETDEVICELIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18255a[a.MESSAGE_ISDEVICECHANGABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f18255a[a.MESSAGE_GETCONNECTEDDEVICE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f18255a[a.MESSAGE_GETCONNECTINGDEVICE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f18255a[a.MESSAGE_VOICECALLPREPROCESS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f18255a[a.MESSAGE_VOICECALLPOSTPROCESS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f18255a[a.MESSAGE_CONNECTDEVICE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f18255a[a.MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f18255a[a.MESSAGE_AUTO_DEVICELIST_UPDATE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f18255a[a.MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f18255a[a.MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f18255a[a.MESSAGE_NOTIFY_DEVICELIST_UPDATE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public enum a {
        MESSAGE_ENABLE,
        MESSAGE_DISABLE,
        MESSAGE_GETDEVICELIST,
        MESSAGE_CONNECTDEVICE,
        MESSAGE_ISDEVICECHANGABLED,
        MESSAGE_GETCONNECTEDDEVICE,
        MESSAGE_GETCONNECTINGDEVICE,
        MESSAGE_VOICECALLPREPROCESS,
        MESSAGE_VOICECALLPOSTPROCESS,
        MESSAGE_AUTO_DEVICELIST_UPDATE,
        MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE,
        MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE,
        MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE,
        MESSAGE_NOTIFY_DEVICELIST_UPDATE
    }

    public class c extends h {
        public c() {
            super();
        }

        private void f() {
            e.this.f18230a.setBluetoothScoOn(true);
            e.this.f18230a.startBluetoothSco();
        }

        private void g() {
            try {
                e.this.f18230a.stopBluetoothSco();
                e.this.f18230a.setBluetoothScoOn(false);
            } catch (Exception unused) {
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public void a() throws InterruptedException {
            int i2 = 1;
            if (e.this.c() || e.this.f18237h == a.EnumC0325a.STOPPED) {
                TXCLog.i("TXCAudioRouteManager", "connect bluetoothHeadset: do nothing, IsMusicScene: %b, scene: %s", Boolean.valueOf(e.this.c()), e.this.f18237h);
                d();
                return;
            }
            try {
                if (e.this.f18235f == com.tencent.liteav.audio.impl.route.d.SCO_DISCONNECTED) {
                    TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread SCO_DISCONNECTED sleep 5000");
                    Thread.sleep(5000L);
                } else {
                    TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread sleep 1000");
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException unused) {
            }
            e.this.f18235f = com.tencent.liteav.audio.impl.route.d.HEADSET_AVAILABLE;
            if (this.f18282b) {
                e.this.f18235f = com.tencent.liteav.audio.impl.route.d.SCO_CONNECTING;
                synchronized (e.this.f18236g) {
                    e.this.f18236g[0] = false;
                }
                f();
                TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread _startBluetoothSco");
            }
            while (true) {
                if (!this.f18282b) {
                    break;
                }
                TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread i:" + i2 + " is bluetooth sco on:" + e.this.f18230a.isBluetoothScoOn() + " bluetooth name:" + e.this.f18240k.d());
                if (e.this.f18235f == com.tencent.liteav.audio.impl.route.d.SCO_CONNECTED) {
                    TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread bluetoothState ==  BluetoothState.SCO_CONNECTED 1");
                    d();
                    break;
                }
                synchronized (e.this.f18236g) {
                    if (!e.this.f18236g[0]) {
                        try {
                            e.this.f18236g.wait(4000L);
                        } catch (InterruptedException unused2) {
                        }
                    }
                }
                if (e.this.f18235f == com.tencent.liteav.audio.impl.route.d.SCO_CONNECTED) {
                    TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread bluetoothState == BluetoothState.SCO_CONNECTED 2");
                    d();
                    break;
                }
                int i3 = i2 + 1;
                if (i2 > 3) {
                    break;
                }
                g();
                try {
                    Thread.sleep(4000L);
                } catch (InterruptedException unused3) {
                }
                e.this.f18235f = com.tencent.liteav.audio.impl.route.d.SCO_CONNECTING;
                synchronized (e.this.f18236g) {
                    e.this.f18236g[0] = false;
                }
                f();
                TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread retry start sco");
                i2 = i3;
            }
            if (e.this.f18235f != com.tencent.liteav.audio.impl.route.d.SCO_CONNECTED) {
                TXCLog.e("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread sco fail,remove btheadset");
                e.this.f18240k.a(b(), false);
                e.this.f18240k.j();
                a(10);
                e.this.a(false);
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public String b() {
            return TraeAudioManager.DEVICE_BLUETOOTHHEADSET;
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public void c() {
            if (e.this.f18230a == null) {
                return;
            }
            TXCLog.i("TXCAudioRouteManager", "bluetoothHeadsetSwitchThread _quit _stopBluetoothSco");
            g();
        }
    }

    public class d extends h {
        public d() {
            super();
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public void a() throws InterruptedException {
            d();
            int i2 = 0;
            while (this.f18282b) {
                if (e.this.c() || e.this.f18237h == a.EnumC0325a.STOPPED) {
                    TXCLog.d("TXCAudioRouteManager", "connect earphone: do nothing");
                } else if (e.this.f18230a.isSpeakerphoneOn()) {
                    e eVar = e.this;
                    eVar.a(eVar.f18231b, false);
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (Exception unused) {
                }
                i2++;
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public String b() {
            return TraeAudioManager.DEVICE_EARPHONE;
        }
    }

    /* renamed from: com.tencent.liteav.audio.impl.route.e$e, reason: collision with other inner class name */
    public class C0326e extends h {
        public C0326e() {
            super();
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public void a() throws InterruptedException {
            d();
            int i2 = 0;
            while (this.f18282b) {
                if (e.this.c() || e.this.f18237h == a.EnumC0325a.STOPPED) {
                    TXCLog.d("TXCAudioRouteManager", "connect headset: do nothing");
                } else if (e.this.f18230a.isSpeakerphoneOn()) {
                    e.this.f18230a.setWiredHeadsetOn(true);
                    e eVar = e.this;
                    eVar.a(eVar.f18231b, false);
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (InterruptedException unused) {
                }
                i2++;
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public String b() {
            return TraeAudioManager.DEVICE_WIREDHEADSET;
        }
    }

    public static class f extends Handler {

        /* renamed from: a, reason: collision with root package name */
        private final WeakReference<i> f18280a;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            i iVar = this.f18280a.get();
            if (iVar != null) {
                iVar.a(message);
            }
        }

        private f(i iVar) {
            this.f18280a = new WeakReference<>(iVar);
        }
    }

    public class g extends h {
        public g() {
            super();
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public void a() throws InterruptedException {
            d();
            TXCLog.i("TXCAudioRouteManager", "run:" + b() + " _running:" + this.f18282b);
            int i2 = 0;
            while (this.f18282b) {
                if (e.this.c() || e.this.f18237h == a.EnumC0325a.STOPPED) {
                    TXCLog.d("TXCAudioRouteManager", "connect speakerPhone: do nothing, mCurrentIOScene: %s", e.this.f18237h);
                } else if (!e.this.f18230a.isSpeakerphoneOn()) {
                    e eVar = e.this;
                    eVar.a(eVar.f18231b, true);
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (InterruptedException unused) {
                }
                i2++;
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.e.h
        public String b() {
            return TraeAudioManager.DEVICE_SPEAKERPHONE;
        }
    }

    public abstract class h extends Thread {

        /* renamed from: b, reason: collision with root package name */
        protected boolean f18282b = true;

        /* renamed from: c, reason: collision with root package name */
        protected final boolean[] f18283c = {false};

        /* renamed from: d, reason: collision with root package name */
        protected HashMap<String, Object> f18284d = null;

        public h() {
            TXCLog.i("TXCAudioRouteManager", "construct SwitchThread: " + b());
        }

        public abstract void a();

        public void a(HashMap<String, Object> map) {
            this.f18284d = map;
        }

        public abstract String b();

        public void c() {
        }

        public void d() {
            e.this.f18240k.e(b());
            a(0);
        }

        public void e() {
            TXCAudioNativeInterface.LogTraceEntry(b());
            this.f18282b = false;
            TXCLog.i("TXCAudioRouteManager", "quit:" + b() + " isRunning:" + this.f18282b);
            interrupt();
            c();
            synchronized (this.f18283c) {
                boolean[] zArr = this.f18283c;
                if (!zArr[0]) {
                    try {
                        zArr.wait(com.heytap.mcssdk.constant.a.f7153q);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            TXCAudioNativeInterface.LogTraceEntry(b());
            e.this.f18240k.d(b());
            e.this.f();
            a();
            synchronized (this.f18283c) {
                boolean[] zArr = this.f18283c;
                zArr[0] = true;
                zArr.notifyAll();
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        public void a(int i2) {
            TXCAudioNativeInterface.LogTraceEntry(b() + " err:" + i2);
            e.this.f();
            if (this.f18284d == null) {
                e.this.b();
                return;
            }
            e eVar = e.this;
            eVar.f18242m = eVar.f18240k.h();
            Long l2 = (Long) this.f18284d.get(TraeAudioManager.PARAM_SESSIONID);
            TXCLog.i("TXCAudioRouteManager", "sessonID:" + l2);
            if (l2 == null || l2.longValue() == Long.MIN_VALUE) {
                e.this.b();
                TXCLog.w("TXCAudioRouteManager", "processDeviceConnectRes sid null,don't send res");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME, (String) this.f18284d.get(TraeAudioManager.PARAM_DEVICE));
            if (e.this.a(intent, this.f18284d, i2) == 0) {
                e.this.b();
            }
            TXCAudioNativeInterface.LogTraceExit();
        }
    }

    public e(Context context) {
        this.f18231b = null;
        this.f18243n = null;
        TXCAudioNativeInterface.LogTraceEntry(" context:" + context);
        if (context == null) {
            return;
        }
        this.f18231b = context;
        this.f18243n = new i(this);
        TXCAudioNativeInterface.LogTraceExit();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            TXCLog.d("TXCAudioRouteManager", "onReceive intent or context is null!");
            return;
        }
        try {
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra(TraeAudioManager.PARAM_OPERATION);
            TXCLog.i("TXCAudioRouteManager", "receive Action: " + intent.getAction());
            com.tencent.liteav.audio.impl.route.h hVar = this.f18240k;
            if (hVar == null) {
                TXCLog.d("TXCAudioRouteManager", "mDeviceConfigManager is null!");
                return;
            }
            boolean zC = hVar.c(TraeAudioManager.DEVICE_WIREDHEADSET);
            boolean zC2 = this.f18240k.c(TraeAudioManager.DEVICE_BLUETOOTHHEADSET);
            if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                a(intent);
                if (!zC && this.f18240k.c(TraeAudioManager.DEVICE_WIREDHEADSET)) {
                    a(TraeAudioManager.DEVICE_WIREDHEADSET, true);
                }
                if (!zC || this.f18240k.c(TraeAudioManager.DEVICE_WIREDHEADSET)) {
                    return;
                }
                a(TraeAudioManager.DEVICE_WIREDHEADSET, false);
                return;
            }
            if (!"com.tencent.sharp.ACTION_TXCAUDIOMANAGER_REQUEST".equals(action)) {
                com.tencent.liteav.audio.impl.route.h hVar2 = this.f18240k;
                if (hVar2 != null) {
                    com.tencent.liteav.audio.impl.route.b bVar = this.f18241l;
                    if (bVar != null) {
                        bVar.a(context, intent, hVar2);
                    }
                    if (!zC2 && this.f18240k.c(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
                        a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    }
                    if (!zC2 || this.f18240k.c(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
                        return;
                    }
                    a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                    return;
                }
                return;
            }
            TXCLog.i("TXCAudioRouteManager", "operation: " + stringExtra);
            if (TraeAudioManager.OPERATION_STARTSERVICE.equals(stringExtra)) {
                a(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE), intent.getStringExtra(TraeAudioManager.EXTRA_DATA_DEVICECONFIG));
                return;
            }
            if (TraeAudioManager.OPERATION_STOPSERVICE.equals(stringExtra)) {
                a(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_GETDEVICELIST.equals(stringExtra)) {
                e(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_CONNECTDEVICE.equals(stringExtra)) {
                b(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE), intent.getStringExtra(TraeAudioManager.CONNECTDEVICE_DEVICENAME));
                return;
            }
            if (TraeAudioManager.OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE.equals(stringExtra)) {
                b(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_ISDEVICECHANGABLED.equals(stringExtra)) {
                f(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_GETCONNECTEDDEVICE.equals(stringExtra)) {
                c(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_GETCONNECTINGDEVICE.equals(stringExtra)) {
                g(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
                return;
            }
            if (TraeAudioManager.OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                a(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE), intent.getIntExtra(TraeAudioManager.PARAM_MODEPOLICY, -1));
            } else if (TraeAudioManager.OPERATION_VOICECALL_POSTPROCESS.equals(stringExtra)) {
                d(stringExtra, intent.getLongExtra(TraeAudioManager.PARAM_SESSIONID, Long.MIN_VALUE));
            }
        } catch (Exception e2) {
            TXCLog.e("TXCAudioRouteManager", "deal with receiver failed." + e2.getMessage());
        }
    }

    private void e() {
        if (this.f18240k.c(TraeAudioManager.DEVICE_WIREDHEADSET) || this.f18240k.c(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
            TXCLog.i("TXCAudioRouteManager", "detected headset plugin,so disable earphone");
            this.f18240k.a(TraeAudioManager.DEVICE_EARPHONE, false);
        } else {
            TXCLog.i("TXCAudioRouteManager", "detected headset plugout,so enable earphone");
            this.f18240k.a(TraeAudioManager.DEVICE_EARPHONE, true);
        }
    }

    private void f(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_ISDEVICECHANGABLED, map);
    }

    private void g(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_GETCONNECTINGDEVICE, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        return this.f18237h.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean zA;
        String[] strArr = {TraeAudioManager.DEVICE_BLUETOOTHHEADSET, TraeAudioManager.DEVICE_WIREDHEADSET, TraeAudioManager.DEVICE_SPEAKERPHONE};
        for (int i2 = 0; i2 < 3; i2++) {
            String str = strArr[i2];
            if (str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
                com.tencent.liteav.audio.impl.route.b bVar = this.f18241l;
                if (bVar == null) {
                    zA = this.f18240k.a(str, false);
                } else {
                    zA = this.f18240k.a(str, bVar.c());
                }
            } else if (str.equals(TraeAudioManager.DEVICE_WIREDHEADSET)) {
                zA = this.f18240k.a(str, this.f18230a.isWiredHeadsetOn());
            } else {
                if (str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                    this.f18240k.a(str, true);
                }
                zA = false;
            }
            if (zA) {
                TXCLog.i("TXCAudioRouteManager", "update visibility success, dev: %s, visible: %b", str, Boolean.valueOf(this.f18240k.c(str)));
            }
        }
        a(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b(int i2) {
        if (c()) {
            return 0;
        }
        if (i2 >= 0) {
            TXCLog.i("TXCAudioRouteManager", "[Config] getCallAudioMode modePolicy:" + i2 + " mode:" + i2);
            return i2;
        }
        TXCLog.w("TXCAudioRouteManager", "[Config] getCallAudioMode _modePolicy:" + i2 + " mode:3facturer:" + TXCBuild.Manufacturer() + " model:" + TXCBuild.Model());
        return 3;
    }

    public void c(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_GETCONNECTEDDEVICE, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (this.f18231b == null) {
            return;
        }
        final boolean zA = a();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.route.e.3
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_NOTIFY");
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE);
                intent.putExtra(TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE_DATE, zA);
                if (e.this.f18231b != null) {
                    com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(intent);
                }
            }
        });
    }

    private void e(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_GETDEVICELIST, map);
    }

    public class i extends Thread {

        /* renamed from: c, reason: collision with root package name */
        private final boolean[] f18288c;

        /* renamed from: e, reason: collision with root package name */
        private final e f18290e;

        /* renamed from: b, reason: collision with root package name */
        private Handler f18287b = null;

        /* renamed from: d, reason: collision with root package name */
        private boolean f18289d = false;

        /* renamed from: f, reason: collision with root package name */
        private boolean f18291f = true;

        /* renamed from: g, reason: collision with root package name */
        private String f18292g = "";

        /* renamed from: h, reason: collision with root package name */
        private long f18293h = -1;

        /* renamed from: i, reason: collision with root package name */
        private String f18294i = "";

        public i(e eVar) {
            boolean[] zArr = {false};
            this.f18288c = zArr;
            this.f18290e = eVar;
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            TXCLog.i("TXCAudioRouteManager", "TraeAudioManagerLooper start...");
            start();
            synchronized (zArr) {
                if (!zArr[0]) {
                    try {
                        zArr.wait(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            TXCLog.i("TXCAudioRouteManager", "start used:" + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
        }

        private void b() {
            TXCAudioNativeInterface.LogTraceEntry("");
            try {
                e eVar = e.this;
                eVar.f18230a = (AudioManager) eVar.f18231b.getSystemService("audio");
                e eVar2 = e.this;
                eVar2.f18241l = eVar2.a(eVar2.f18231b, e.this.f18240k);
                IntentFilter intentFilter = new IntentFilter();
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                e.this.f18241l.a(intentFilter, intentFilter2);
                e.this.f18231b.registerReceiver(this.f18290e, intentFilter);
                intentFilter2.addAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_REQUEST");
                com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e, intentFilter2);
                TXCLog.i("TXCAudioRouteManager", "register receiver in _init");
            } catch (Exception e2) {
                TXCLog.e("TXCAudioRouteManager", "init failed, " + e2.getMessage());
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        private void c() {
            TXCAudioNativeInterface.LogTraceEntry("");
            try {
                e eVar = e.this;
                eVar.f18230a = (AudioManager) eVar.f18231b.getSystemService("audio");
                if (e.this.f18241l == null) {
                    e eVar2 = e.this;
                    eVar2.f18241l = eVar2.a(eVar2.f18231b, e.this.f18240k);
                }
                try {
                    e.this.f18231b.unregisterReceiver(this.f18290e);
                } catch (Exception unused) {
                }
                TXCLog.i("TXCAudioRouteManager", "unregister receiver in _prev_startService");
                com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e);
                IntentFilter intentFilter = new IntentFilter();
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                e.this.f18241l.a(intentFilter, intentFilter2);
                intentFilter2.addAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_REQUEST");
                e.this.f18231b.registerReceiver(this.f18290e, intentFilter);
                TXCLog.i("TXCAudioRouteManager", "register receiver in _prev_startService");
                com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e, intentFilter2);
            } catch (Exception e2) {
                TXCLog.w("TXCAudioRouteManager", "prev start service failed." + e2.getMessage());
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        private void d() {
            TXCAudioNativeInterface.LogTraceEntry("");
            try {
                if (e.this.f18241l != null) {
                    e.this.f18241l.b();
                }
                e.this.f18241l = null;
                if (e.this.f18231b != null) {
                    try {
                        e.this.f18231b.unregisterReceiver(this.f18290e);
                    } catch (Exception unused) {
                    }
                    TXCLog.i("TXCAudioRouteManager", "unregister receiver in _post_stopService");
                    com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e);
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_REQUEST");
                    com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e, intentFilter);
                }
            } catch (Exception e2) {
                TXCLog.e("TXCAudioRouteManager", "stop service failed." + e2.getMessage());
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        private void e() {
            TXCAudioNativeInterface.LogTraceEntry("");
            try {
                a();
                if (e.this.f18241l != null) {
                    e.this.f18241l.b();
                    e.this.f18241l = null;
                }
                if (e.this.f18231b != null) {
                    try {
                        e.this.f18231b.unregisterReceiver(this.f18290e);
                    } catch (Exception unused) {
                    }
                    TXCLog.i("TXCAudioRouteManager", "unregister receiver in _uninit");
                    com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(this.f18290e);
                    e.this.f18231b = null;
                }
                if (e.this.f18240k != null) {
                    e.this.f18240k.a();
                }
            } catch (Exception e2) {
                TXCLog.e("TXCAudioRouteManager", "uninit failed." + e2.getMessage());
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        private void f() {
            TXCAudioNativeInterface.LogTraceEntry(" activeMode:" + e.this.f18232c);
            if (e.this.f18230a == null) {
                TXCLog.e("TXCAudioRouteManager", "AudioManager is null!");
                return;
            }
            if (e.this.f18232c != 1) {
                TXCLog.e("TXCAudioRouteManager", "active mode is not AUDIO_MANAGER_ACTIVE_VOICECALL!");
                return;
            }
            e.this.f18232c = 0;
            if (e.this.f18233d != -1) {
                e eVar = e.this;
                eVar.a(eVar.f18233d);
            }
            HashMap map = new HashMap();
            map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this.f18293h));
            map.put(TraeAudioManager.PARAM_OPERATION, this.f18294i);
            e.this.a(new Intent(), (HashMap<String, Object>) map, 6);
            TXCAudioNativeInterface.LogTraceExit();
        }

        public int a(a aVar, HashMap<String, Object> map) {
            Handler handler = this.f18287b;
            if (handler != null) {
                Message messageObtain = Message.obtain(handler, aVar.ordinal(), map);
                if (aVar != a.MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE || e.this.a()) {
                    return this.f18287b.sendMessage(messageObtain) ? 0 : -1;
                }
                TXCLog.w("TXCAudioRouteManager", "sendMessageDelayed, device is connecting, plugin need delay 1 second");
                return this.f18287b.sendMessageDelayed(messageObtain, 1000L) ? 0 : -1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(" fail mMsgHandler==null _enabled:");
            sb.append(this.f18289d ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(e.this.f18232c);
            sb.append(" msg:");
            sb.append(aVar);
            TXCAudioNativeInterface.LogTraceEntry(sb.toString());
            return -1;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            TXCAudioNativeInterface.LogTraceEntry("");
            Looper.prepare();
            this.f18287b = new f(this);
            b();
            synchronized (this.f18288c) {
                boolean[] zArr = this.f18288c;
                zArr[0] = true;
                zArr.notifyAll();
            }
            Looper.loop();
            e();
            synchronized (this.f18288c) {
                boolean[] zArr2 = this.f18288c;
                zArr2[0] = false;
                zArr2.notifyAll();
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        public void a(HashMap<String, Object> map) {
            TXCAudioNativeInterface.LogTraceEntry("");
            if (e.this.f18231b == null) {
                return;
            }
            String str = (String) map.get(TraeAudioManager.EXTRA_DATA_DEVICECONFIG);
            TXCLog.i("TXCAudioRouteManager", "startService cfg:%s, enabled: %s, activeMode: %d", str, Boolean.valueOf(this.f18289d), Integer.valueOf(e.this.f18232c));
            if ((this.f18289d && this.f18292g.equals(str)) || e.this.f18232c != 0) {
                e.this.a(true);
                TXCLog.i("TXCAudioRouteManager", "service is started.");
                return;
            }
            if (this.f18289d) {
                a();
            }
            c();
            e.this.f18240k.a();
            e.this.f18240k.a(str);
            this.f18292g = str;
            this.f18289d = true;
            e.this.f18238i = false;
            e.this.d();
            a(this.f18289d);
            TXCAudioNativeInterface.LogTraceExit();
        }

        private void d(HashMap<String, Object> map) {
            TXCAudioNativeInterface.LogTraceEntry(" activeMode:" + e.this.f18232c);
            if (e.this.f18230a != null) {
                if (e.this.f18232c == 1) {
                    e.this.f18232c = 0;
                    TXCAudioNativeInterface.LogTraceExit();
                    return;
                } else {
                    TXCLog.e("TXCAudioRouteManager", "active mode is not ACTIVE_VOICECALL!");
                    e.this.a(new Intent(), map, 3);
                    return;
                }
            }
            TXCLog.e("TXCAudioRouteManager", "InternalVoicecallPostprocess AudioManager is null!");
        }

        private void b(HashMap<String, Object> map) {
            Intent intent = new Intent();
            synchronized (e.this.f18240k) {
                ArrayList<String> arrayListK = e.this.f18240k.k();
                String strH = e.this.f18240k.h();
                String strI = e.this.f18240k.i();
                intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) arrayListK.toArray(new String[0]));
                intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, strH);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, strI);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, e.this.f18240k.d());
            }
            e.this.a(intent, map, 0);
        }

        private void c(HashMap<String, Object> map) {
            TXCAudioNativeInterface.LogTraceEntry(" activeMode:" + e.this.f18232c);
            if (map == null || e.this.f18240k == null) {
                return;
            }
            if (e.this.f18230a != null) {
                if (e.this.f18232c == 1) {
                    e.this.a(new Intent(), map, 2);
                    return;
                }
                this.f18293h = ((Long) map.get(TraeAudioManager.PARAM_SESSIONID)).longValue();
                this.f18294i = (String) map.get(TraeAudioManager.PARAM_OPERATION);
                e.this.f18232c = 1;
                e eVar = e.this;
                eVar.f18233d = eVar.f18230a.getMode();
                Integer num = (Integer) map.get(TraeAudioManager.PARAM_MODEPOLICY);
                if (num == null) {
                    TXCLog.e("TXCAudioRouteManager", "params.get(PARAM_MODEPOLICY)==null!!");
                    e.this.f18234e = -1;
                } else {
                    e.this.f18234e = num.intValue();
                }
                TXCLog.i("TXCAudioRouteManager", "audio mode policy:" + e.this.f18234e);
                e eVar2 = e.this;
                eVar2.a(eVar2.b(eVar2.f18234e));
                e.this.a(new Intent(), map, 0);
                TXCAudioNativeInterface.LogTraceExit();
                return;
            }
            TXCLog.e("TXCAudioRouteManager", " InternalVoicecallPreprocess am==null!!");
        }

        private void a() {
            StringBuilder sb = new StringBuilder();
            sb.append(" _enabled:");
            sb.append(this.f18289d ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(e.this.f18232c);
            TXCAudioNativeInterface.LogTraceEntry(sb.toString());
            if (this.f18289d) {
                e.this.f18238i = true;
                if (e.this.f18232c == 1) {
                    f();
                }
                synchronized (e.this.f18245p) {
                    if (e.this.f18244o != null) {
                        TXCLog.w("TXCAudioRouteManager", "switchThread:" + e.this.f18244o.b());
                        e.this.f18244o.e();
                        e.this.f18244o = null;
                    }
                }
                this.f18289d = false;
                a(false);
                if (e.this.f18230a != null && e.this.f18231b != null) {
                    try {
                        e.this.a(0);
                    } catch (Exception e2) {
                        TXCLog.e("TXCAudioRouteManager", "set mode failed." + e2.getMessage());
                    }
                }
                d();
                TXCAudioNativeInterface.LogTraceExit();
            }
        }

        private void a(boolean z2) {
            if (e.this.f18231b == null) {
                return;
            }
            Intent intent = new Intent();
            intent.setAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_NOTIFY");
            intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_SERVICE_STATE);
            intent.putExtra(TraeAudioManager.NOTIFY_SERVICE_STATE_DATE, z2);
            com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(intent);
        }

        public void a(Message message) {
            HashMap<String, Object> map;
            int i2 = message.what;
            if (i2 < 0 || i2 >= a.values().length) {
                TXCLog.e("TXCAudioRouteManager", "invalid message: %d", Integer.valueOf(message.what));
                return;
            }
            a aVar = a.values()[message.what];
            try {
                map = (HashMap) message.obj;
            } catch (Exception unused) {
                TXCLog.e("TXCAudioRouteManager", "");
                map = null;
            }
            TXCLog.i("TXCAudioRouteManager", "handle message: " + message.what + " _enabled:" + this.f18289d);
            if (aVar == a.MESSAGE_ENABLE) {
                a(map);
            }
            if (!this.f18289d) {
                TXCLog.w("TXCAudioRouteManager", "service has been disabled, skip.");
                e.this.a(new Intent(), map, 1);
                return;
            }
            switch (AnonymousClass4.f18255a[aVar.ordinal()]) {
                case 1:
                    a();
                    break;
                case 2:
                    b(map);
                    break;
                case 3:
                    e.this.b(map);
                    break;
                case 4:
                    e.this.c(map);
                    break;
                case 5:
                    e.this.d(map);
                    break;
                case 6:
                    c(map);
                    break;
                case 7:
                    d(map);
                    break;
                case 8:
                    e.this.a(map);
                    break;
                case 9:
                case 10:
                    String strF = e.this.f18240k.f();
                    String strH = e.this.f18240k.h();
                    TXCLog.i("TXCAudioRouteManager", "MESSAGE_AUTO_DEVICELIST_UPDATE connectedDev:" + strH + " highestDev" + strF);
                    if (e.this.f18237h != a.EnumC0325a.STOPPED && this.f18291f) {
                        this.f18291f = false;
                        e.this.a(strF, (HashMap<String, Object>) null, true);
                        break;
                    } else if (TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(strH) || !strF.equals(strH)) {
                        e.this.a(strF, (HashMap<String, Object>) null, false);
                        break;
                    } else {
                        e.this.b();
                        break;
                    }
                    break;
                case 11:
                    String str = (String) map.get(TraeAudioManager.PARAM_DEVICE);
                    if (e.this.a(str, (HashMap<String, Object>) null, false) != 0) {
                        TXCLog.i("TXCAudioRouteManager", "device plug in: " + str + ", sessionConnectedDev:" + e.this.f18242m + " connected fail,auto switch!");
                        e eVar = e.this;
                        eVar.a(eVar.f18240k.f(), (HashMap<String, Object>) null, false);
                        break;
                    }
                    break;
                case 12:
                    e eVar2 = e.this;
                    if (eVar2.a(eVar2.f18242m, (HashMap<String, Object>) null, false) != 0) {
                        TXCLog.i("TXCAudioRouteManager", "device plug out:" + ((String) map.get(TraeAudioManager.PARAM_DEVICE)) + " sessionConnectedDev:" + e.this.f18242m + ", connected fail, auto switch!");
                        e eVar3 = e.this;
                        eVar3.a(eVar3.f18240k.f(), (HashMap<String, Object>) null, false);
                        break;
                    }
                    break;
                case 13:
                    e.this.b();
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(TraeAudioManager.GETCONNECTEDDEVICE_RESULT_LIST, this.f18240k.h());
        a(intent, map, 0);
    }

    @TargetApi(11)
    public class b extends com.tencent.liteav.audio.impl.route.b implements BluetoothProfile.ServiceListener {

        /* renamed from: b, reason: collision with root package name */
        private Context f18272b = null;

        /* renamed from: c, reason: collision with root package name */
        private com.tencent.liteav.audio.impl.route.h f18273c = null;

        /* renamed from: d, reason: collision with root package name */
        private BluetoothAdapter f18274d = null;

        /* renamed from: e, reason: collision with root package name */
        private BluetoothProfile f18275e = null;

        /* renamed from: f, reason: collision with root package name */
        private final Object f18276f = new Object();

        public b() {
        }

        private List<BluetoothDevice> d() {
            if (this.f18275e == null) {
                return null;
            }
            try {
                boolean z2 = true;
                if (e.this.f18231b != null && TXCBuild.VersionInt() >= 31 && e.this.f18231b.checkPermission(Permission.BLUETOOTH_CONNECT, Process.myPid(), Process.myUid()) != 0) {
                    z2 = false;
                }
                if (z2) {
                    return this.f18275e.getConnectedDevices();
                }
                return null;
            } catch (Exception e2) {
                TXCLog.e("TXCAudioRouteManager", "get connected devices failed." + e2.getMessage());
                return null;
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        public String a() {
            return "BluetoohHeadsetCheck";
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        @TargetApi(11)
        public boolean a(Context context, com.tencent.liteav.audio.impl.route.h hVar) {
            TXCAudioNativeInterface.LogTraceEntry("");
            if (context == null || hVar == null) {
                TXCLog.e("TXCAudioRouteManager", "err ctx is null or devCfg is null");
                return false;
            }
            this.f18272b = context;
            this.f18273c = hVar;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.f18274d = defaultAdapter;
            if (defaultAdapter == null) {
                TXCLog.e("TXCAudioRouteManager", "getDefaultAdapter return null!");
                return false;
            }
            synchronized (this.f18276f) {
                if (this.f18274d.isEnabled() && this.f18275e == null && !this.f18274d.getProfileProxy(this.f18272b, this, 1)) {
                    TXCLog.e("TXCAudioRouteManager", "BluetoohHeadsetCheck: getProfileProxy HEADSET fail!");
                    return false;
                }
                TXCAudioNativeInterface.LogTraceExit();
                return true;
            }
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        public void b() {
            BluetoothAdapter bluetoothAdapter;
            TXCAudioNativeInterface.LogTraceEntry(" profile:" + this.f18275e);
            synchronized (this.f18276f) {
                try {
                    bluetoothAdapter = this.f18274d;
                } catch (Exception e2) {
                    TXCLog.e("TXCAudioRouteManager", "closeProfileProxy failed.", e2);
                }
                if (bluetoothAdapter != null) {
                    BluetoothProfile bluetoothProfile = this.f18275e;
                    if (bluetoothProfile != null) {
                        bluetoothAdapter.closeProfileProxy(1, bluetoothProfile);
                    }
                    this.f18275e = null;
                }
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        public boolean c() {
            synchronized (this.f18276f) {
                List<BluetoothDevice> listD = d();
                if (listD == null) {
                    return false;
                }
                return listD.size() > 0;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            BluetoothProfile bluetoothProfile2;
            TXCAudioNativeInterface.LogTraceEntry(" profile:" + this.f18275e + " profile:" + i2 + " proxy:" + bluetoothProfile);
            if (i2 != 1) {
                return;
            }
            synchronized (this.f18276f) {
                BluetoothProfile bluetoothProfile3 = this.f18275e;
                if (bluetoothProfile3 != null && bluetoothProfile3 != bluetoothProfile) {
                    TXCLog.i("TXCAudioRouteManager", "BluetoohHeadsetCheck: HEADSET Connected proxy:" + bluetoothProfile + ", profile:" + this.f18275e);
                    this.f18274d.closeProfileProxy(1, this.f18275e);
                    this.f18275e = null;
                }
                this.f18275e = bluetoothProfile;
                List<BluetoothDevice> listD = d();
                if (listD != null && this.f18275e != null) {
                    TXCLog.i("TXCAudioRouteManager", "HEADSET Connected devs:" + listD.size() + ", profile:" + this.f18275e);
                    for (int i3 = 0; i3 < listD.size(); i3++) {
                        BluetoothDevice bluetoothDevice = listD.get(i3);
                        try {
                            bluetoothProfile2 = this.f18275e;
                        } catch (Exception e2) {
                            TXCLog.e("TXCAudioRouteManager", "get bluetooth connection state failed." + e2.getMessage());
                        }
                        int connectionState = bluetoothProfile2 != null ? bluetoothProfile2.getConnectionState(bluetoothDevice) : 0;
                        if (connectionState == 2) {
                            this.f18273c.b(bluetoothDevice.getName());
                        }
                        TXCLog.i("TXCAudioRouteManager", "bluetooth state, device: %s, state: %s", bluetoothDevice.getName(), com.tencent.liteav.audio.impl.route.b.b(connectionState));
                    }
                }
            }
            if (this.f18273c != null) {
                if (!TextUtils.isEmpty(e.this.f18240k.d()) && c()) {
                    this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    e.this.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                } else {
                    this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        public void onServiceDisconnected(int i2) {
            TXCAudioNativeInterface.LogTraceEntry("_profile:" + this.f18275e + " profile:" + i2);
            if (i2 == 1) {
                TXCLog.i("TXCAudioRouteManager", "TRAEBluetoohProxy: HEADSET Disconnected");
                if (c()) {
                    e.this.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
                synchronized (this.f18276f) {
                    BluetoothProfile bluetoothProfile = this.f18275e;
                    if (bluetoothProfile != null) {
                        this.f18274d.closeProfileProxy(1, bluetoothProfile);
                        this.f18275e = null;
                    }
                }
            }
            TXCAudioNativeInterface.LogTraceExit();
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        public void b(IntentFilter intentFilter, IntentFilter intentFilter2) {
            TXCLog.i("TXCAudioRouteManager", "add extra action " + a());
            intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        }

        @Override // com.tencent.liteav.audio.impl.route.b
        public void a(Context context, Intent intent) {
            if ("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                if (intExtra != 12) {
                    if (intExtra == 10) {
                        e.this.a(true);
                        TXCLog.i("TXCAudioRouteManager", "receive bluetooth audio state changed to STATE_AUDIO_DISCONNECTED");
                        return;
                    }
                    return;
                }
                if (e.this.f18235f == com.tencent.liteav.audio.impl.route.d.SCO_CONNECTING) {
                    TXCLog.i("TXCAudioRouteManager", "receive bluetooth audio state changed to SCO_CONNECTED");
                    e.this.f18235f = com.tencent.liteav.audio.impl.route.d.SCO_CONNECTED;
                    synchronized (e.this.f18236g) {
                        e.this.f18236g[0] = true;
                        e.this.f18236g.notifyAll();
                    }
                    return;
                }
                return;
            }
            if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                TXCLog.i("TXCAudioRouteManager", "receive bluetooth headset connection state changed: %s", com.tencent.liteav.audio.impl.route.b.c(intExtra2));
                if (intExtra2 == 0) {
                    e.this.f18235f = com.tencent.liteav.audio.impl.route.d.HEADSET_UNAVAILABLE;
                    this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                    return;
                } else {
                    if (intExtra2 != 2) {
                        return;
                    }
                    e.this.f18235f = com.tencent.liteav.audio.impl.route.d.HEADSET_AVAILABLE;
                    this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    TXCLog.i("TXCAudioRouteManager", "ACTION_CONNECTION_STATE_CHANGED BluetoothProfile.STATE_CONNECTED");
                    e.this.a(false);
                    return;
                }
            }
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra3 = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                int intExtra4 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE", -1);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                StringBuilder sb = new StringBuilder();
                sb.append("receive bluetooth connection state changed, EXTRA_CONNECTION_STATE ");
                sb.append(com.tencent.liteav.audio.impl.route.b.b(intExtra3));
                sb.append(", EXTRA_PREVIOUS_CONNECTION_STATE ");
                sb.append(com.tencent.liteav.audio.impl.route.b.b(intExtra4));
                sb.append(", EXTRA_DEVICE ");
                sb.append(bluetoothDevice != null ? bluetoothDevice.getName() : "unknown name");
                TXCLog.i("TXCAudioRouteManager", sb.toString());
                if (intExtra3 != 2) {
                    if (intExtra3 == 0) {
                        this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                    }
                } else {
                    String name = bluetoothDevice != null ? bluetoothDevice.getName() : "unkown";
                    if (!name.contains("FreeBuds")) {
                        this.f18273c.a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    }
                    this.f18273c.b(name);
                }
            }
        }
    }

    public void b(String str, long j2, String str2) {
        if (str2 == null) {
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        map.put(TraeAudioManager.CONNECTDEVICE_DEVICENAME, str2);
        map.put(TraeAudioManager.PARAM_DEVICE, str2);
        a(a.MESSAGE_CONNECTDEVICE, map);
    }

    public synchronized void a(com.tencent.liteav.audio.impl.route.f fVar, boolean z2, long j2) {
        if (z2) {
            this.f18239j.a(fVar, j2);
            TXCLog.d("TXCAudioRouteManager", "[register] add AudioSession: " + j2);
        } else {
            this.f18239j.b(j2);
            TXCLog.d("TXCAudioRouteManager", "[register] remove AudioSession: " + j2);
        }
    }

    public void b(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE, map);
    }

    public void d(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        a(a.MESSAGE_VOICECALLPOSTPROCESS, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(TraeAudioManager.ISDEVICECHANGABLED_RESULT_ISCHANGABLED, a());
        a(intent, map, 0);
    }

    public void a(a.EnumC0325a enumC0325a) {
        this.f18237h = enumC0325a;
        TXCLog.i("TXCAudioRouteManager", "set audio io scene to %s", enumC0325a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(TraeAudioManager.GETCONNECTINGDEVICE_RESULT_LIST, this.f18240k.g());
        a(intent, map, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        if (z2 || this.f18240k.b()) {
            TXCLog.i("TXCAudioRouteManager", "checkAutoDeviceListUpdate got update!");
            e();
            this.f18240k.c();
            a(a.MESSAGE_AUTO_DEVICELIST_UPDATE, new HashMap<>());
        }
    }

    public void b() {
        TXCAudioNativeInterface.LogTraceEntry("");
        if (this.f18231b == null) {
            return;
        }
        final Intent intent = new Intent();
        synchronized (this) {
            ArrayList<String> arrayListK = this.f18240k.k();
            String strH = this.f18240k.h();
            String strI = this.f18240k.i();
            String strD = this.f18240k.d();
            intent.setAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_NOTIFY");
            intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICELIST_UPDATE);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) arrayListK.toArray(new String[0]));
            intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, strH);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, strI);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, strD);
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.route.e.2
            @Override // java.lang.Runnable
            public void run() {
                if (e.this.f18231b != null) {
                    com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(intent);
                }
            }
        });
        TXCAudioNativeInterface.LogTraceExit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z2) {
        if (this.f18240k.b()) {
            StringBuilder sb = new StringBuilder();
            sb.append("checkDevicePlug got update dev:");
            sb.append(str);
            sb.append(z2 ? " piugin" : " plugout");
            sb.append(", connectedDev:");
            sb.append(this.f18240k.h());
            TXCLog.i("TXCAudioRouteManager", sb.toString());
            e();
            this.f18240k.c();
            if (z2) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(TraeAudioManager.PARAM_DEVICE, str);
                a(a.MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE, map);
                return;
            }
            String strH = this.f18240k.h();
            if (!strH.equals(str) && !strH.equals(TraeAudioManager.DEVICE_NONE)) {
                TXCLog.i("TXCAudioRouteManager", "No switch,plugout:" + str + " connectedDev:" + strH);
                a(a.MESSAGE_NOTIFY_DEVICELIST_UPDATE, new HashMap<>());
                return;
            }
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put(TraeAudioManager.PARAM_DEVICE, str);
            a(a.MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE, map2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, boolean z2) {
        if (context == null) {
            TXCLog.e("TXCAudioRouteManager", "Could not InternalSetSpeaker, context is null.");
            return;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            TXCLog.e("TXCAudioRouteManager", "Could not InternalSetSpeaker AudioManager is null.");
            return;
        }
        TXCLog.i("TXCAudioRouteManager", "InternalSetSpeaker entry, speaker on: " + audioManager.isSpeakerphoneOn() + "-->:" + z2);
        try {
            if (audioManager.isSpeakerphoneOn() != z2) {
                audioManager.setSpeakerphoneOn(z2);
            }
        } catch (Exception e2) {
            TXCLog.e("TXCAudioRouteManager", "setSpeakerphoneOn failed with " + e2.getMessage());
        }
        TXCLog.i("TXCAudioRouteManager", "InternalSetSpeaker exit:" + z2 + " res:" + (audioManager.isSpeakerphoneOn() == z2 ? 0 : -1) + " mode:" + audioManager.getMode());
    }

    public void a(int i2) {
        TXCLog.i("TXCAudioRouteManager", "set audio mode to " + i2);
        AudioManager audioManager = this.f18230a;
        if (audioManager == null) {
            TXCLog.w("TXCAudioRouteManager", "setMode:" + i2 + " fail because AudioManager is null.");
            return;
        }
        audioManager.setMode(i2);
        StringBuilder sb = new StringBuilder();
        sb.append("setMode:");
        sb.append(i2);
        sb.append(this.f18230a.getMode() != i2 ? "fail" : "success");
        TXCLog.i("TXCAudioRouteManager", sb.toString());
    }

    private void a(Intent intent) {
        String stringExtra = intent.getStringExtra("name");
        if (stringExtra == null) {
            stringExtra = "unkonw";
        }
        String string = " [" + stringExtra + "] ";
        int intExtra = intent.getIntExtra("state", -1);
        if (intExtra != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(intExtra == 0 ? "unplugged" : "plugged");
            string = sb.toString();
        }
        String string2 = string + " mic:";
        int intExtra2 = intent.getIntExtra(PLVPPTAuthentic.PermissionType.MICROPHONE, -1);
        if (intExtra2 != -1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(string2);
            sb2.append(intExtra2 == 1 ? "Y" : "unkown");
            string2 = sb2.toString();
        }
        TXCLog.i("TXCAudioRouteManager", "onHeadsetPlug:: " + string2);
        com.tencent.liteav.audio.impl.route.h hVar = this.f18240k;
        if (hVar != null) {
            hVar.a(TraeAudioManager.DEVICE_WIREDHEADSET, 1 == intExtra);
        }
    }

    private int a(a aVar, HashMap<String, Object> map) {
        i iVar = this.f18243n;
        if (iVar != null) {
            return iVar.a(aVar, map);
        }
        return -1;
    }

    public int a(String str, long j2, String str2) {
        if (str2.length() <= 0) {
            return -1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        map.put(TraeAudioManager.EXTRA_DATA_DEVICECONFIG, str2);
        return a(a.MESSAGE_ENABLE, map);
    }

    public int a(String str, long j2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        return a(a.MESSAGE_DISABLE, map);
    }

    public void a(String str, long j2, int i2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(j2));
        map.put(TraeAudioManager.PARAM_OPERATION, str);
        map.put(TraeAudioManager.PARAM_MODEPOLICY, Integer.valueOf(i2));
        a(a.MESSAGE_VOICECALLPREPROCESS, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HashMap<String, Object> map) {
        int i2;
        TXCAudioNativeInterface.LogTraceEntry("");
        if (map == null || this.f18231b == null) {
            return;
        }
        if (c()) {
            TXCLog.w("TXCAudioRouteManager", "InternalSessionConnectDevice failed, because it's in music scene");
            return;
        }
        String str = (String) map.get(TraeAudioManager.PARAM_DEVICE);
        Log.i("TXCAudioRouteManager", "ConnectDevice: " + str);
        if (!this.f18237h.b() && str.equals(TraeAudioManager.DEVICE_EARPHONE)) {
            TXCLog.e("TXCAudioRouteManager", "InternalSessionConnectDevice IsEarPhoneSupported = false, Connect device:" + str + " failed");
            return;
        }
        boolean zA = a();
        if (com.tencent.liteav.audio.impl.route.h.f(str)) {
            i2 = !this.f18240k.c(str) ? 8 : !zA ? 9 : 0;
        } else {
            i2 = 7;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("sessonID:");
        sb.append((Long) map.get(TraeAudioManager.PARAM_SESSIONID));
        sb.append(" devName:");
        sb.append(str);
        sb.append(" bChangabled:");
        sb.append(zA ? "Y" : "N");
        sb.append(" err:");
        sb.append(i2);
        TXCLog.i("TXCAudioRouteManager", sb.toString());
        if (i2 != 0) {
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME, (String) map.get(TraeAudioManager.PARAM_DEVICE));
            a(intent, map, i2);
            return;
        }
        if (str.equals(this.f18240k.h())) {
            TXCLog.e("TXCAudioRouteManager", str + " has connected!");
            Intent intent2 = new Intent();
            intent2.putExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME, (String) map.get(TraeAudioManager.PARAM_DEVICE));
            a(intent2, map, i2);
            return;
        }
        TXCLog.i("TXCAudioRouteManager", str + "is connecting.");
        a(str, map, false);
        TXCAudioNativeInterface.LogTraceExit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public int a(String str, HashMap<String, Object> map, boolean z2) {
        TXCAudioNativeInterface.LogTraceEntry(" devName:" + str);
        char c3 = 65535;
        if (str == null) {
            return -1;
        }
        if (!z2 && !TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(str) && !this.f18240k.h().equals(TraeAudioManager.DEVICE_NONE) && str.equals(this.f18240k.h())) {
            return 0;
        }
        if (com.tencent.liteav.audio.impl.route.h.f(str) && this.f18240k.c(str)) {
            if (!a()) {
                TXCLog.e("TXCAudioRouteManager", "InternalIsDeviceChangeable fail");
                return -1;
            }
            if (this.f18238i) {
                TXCLog.e("TXCAudioRouteManager", "InternalConnectDevice fail,ready to stopService");
                return -1;
            }
            synchronized (this.f18245p) {
                if (!this.f18238i) {
                    this.f18240k.d(str);
                    if (this.f18244o != null) {
                        TXCLog.i("TXCAudioRouteManager", "_switchThread:" + this.f18244o.b());
                        this.f18244o.e();
                        this.f18244o = null;
                    }
                    switch (str.hashCode()) {
                        case -1261879272:
                            if (str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                                c3 = 1;
                                break;
                            }
                            break;
                        case -463197279:
                            if (str.equals(TraeAudioManager.DEVICE_EARPHONE)) {
                                c3 = 0;
                                break;
                            }
                            break;
                        case 715243628:
                            if (str.equals(TraeAudioManager.DEVICE_WIREDHEADSET)) {
                                c3 = 2;
                                break;
                            }
                            break;
                        case 770344669:
                            if (str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
                                c3 = 3;
                                break;
                            }
                            break;
                    }
                    if (c3 == 0) {
                        this.f18244o = new d();
                    } else if (c3 == 1) {
                        this.f18244o = new g();
                    } else if (c3 == 2) {
                        this.f18244o = new C0326e();
                    } else if (c3 == 3) {
                        this.f18244o = new c();
                    }
                    h hVar = this.f18244o;
                    if (hVar != null) {
                        hVar.a(map);
                        this.f18244o.start();
                    }
                }
            }
            TXCAudioNativeInterface.LogTraceExit();
            return 0;
        }
        TXCLog.e("TXCAudioRouteManager", "checkDevName fail");
        return -1;
    }

    public boolean a() {
        String strG = this.f18240k.g();
        return strG == null || strG.equals(TraeAudioManager.DEVICE_NONE) || strG.equals("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(final Intent intent, HashMap<String, Object> map, final int i2) {
        if (this.f18231b == null) {
            return -1;
        }
        Long l2 = (Long) map.get(TraeAudioManager.PARAM_SESSIONID);
        TXCLog.i("TXCAudioRouteManager", " sessonID:" + l2 + " " + ((String) map.get(TraeAudioManager.PARAM_OPERATION)));
        if (l2 != null && l2.longValue() != Long.MIN_VALUE) {
            final Long l3 = (Long) map.get(TraeAudioManager.PARAM_SESSIONID);
            final String str = (String) map.get(TraeAudioManager.PARAM_OPERATION);
            if (TraeAudioManager.OPERATION_VOICECALL_PREPROCESS.equals(str)) {
                intent.setAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_RES");
                intent.putExtra(TraeAudioManager.PARAM_SESSIONID, l3);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, str);
                intent.putExtra(TraeAudioManager.PARAM_RES_ERRCODE, i2);
                com.tencent.liteav.audio.impl.route.g gVar = this.f18239j;
                if (gVar == null) {
                    return 0;
                }
                gVar.a(intent);
                return 0;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.route.e.1
                @Override // java.lang.Runnable
                public void run() {
                    intent.setAction("com.tencent.sharp.ACTION_TXCAUDIOMANAGER_RES");
                    intent.putExtra(TraeAudioManager.PARAM_SESSIONID, l3);
                    intent.putExtra(TraeAudioManager.PARAM_OPERATION, str);
                    intent.putExtra(TraeAudioManager.PARAM_RES_ERRCODE, i2);
                    if (e.this.f18231b != null) {
                        com.tencent.liteav.basic.a.b.a(e.this.f18231b).a(intent);
                    }
                }
            });
            return 0;
        }
        b();
        TXCLog.e("TXCAudioRouteManager", "sendResBroadcast sid null,don't send res");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.tencent.liteav.audio.impl.route.b a(Context context, com.tencent.liteav.audio.impl.route.h hVar) {
        com.tencent.liteav.audio.impl.route.b bVar = new b();
        if (!bVar.a(context, hVar)) {
            bVar = new com.tencent.liteav.audio.impl.route.c();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CreateBluetoothCheck:");
        sb.append(bVar.a());
        sb.append(" skip android4.3: ");
        sb.append(TXCBuild.VersionInt() == 18);
        TXCLog.i("TXCAudioRouteManager", sb.toString());
        return bVar;
    }
}
