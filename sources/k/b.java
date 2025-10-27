package k;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import c.h;
import com.hjq.permissions.Permission;
import java.util.List;
import java.util.Set;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes8.dex */
public class b {

    /* renamed from: m, reason: collision with root package name */
    public static final String f27562m = "BluetoothManager";

    /* renamed from: n, reason: collision with root package name */
    public static final int f27563n = 4000;

    /* renamed from: o, reason: collision with root package name */
    public static final int f27564o = 2;

    /* renamed from: a, reason: collision with root package name */
    public final Context f27565a;

    /* renamed from: b, reason: collision with root package name */
    public final k.a f27566b;

    /* renamed from: c, reason: collision with root package name */
    public final AudioManager f27567c;

    /* renamed from: d, reason: collision with root package name */
    public final Handler f27568d;

    /* renamed from: e, reason: collision with root package name */
    public final BluetoothProfile.ServiceListener f27569e;

    /* renamed from: f, reason: collision with root package name */
    public final BroadcastReceiver f27570f;

    /* renamed from: g, reason: collision with root package name */
    public int f27571g;

    /* renamed from: h, reason: collision with root package name */
    public d f27572h;

    /* renamed from: i, reason: collision with root package name */
    public BluetoothAdapter f27573i;

    /* renamed from: j, reason: collision with root package name */
    public BluetoothHeadset f27574j;

    /* renamed from: k, reason: collision with root package name */
    public BluetoothDevice f27575k;

    /* renamed from: l, reason: collision with root package name */
    public final Runnable f27576l = new a();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            b.this.a();
        }
    }

    /* renamed from: k.b$b, reason: collision with other inner class name */
    public class C0467b extends BroadcastReceiver {
        public C0467b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (b.this.f27572h == d.UNINITIALIZED) {
                return;
            }
            String action = intent.getAction();
            if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                h.a(b.f27562m, "BluetoothHeadsetBroadcastReceiver.onReceive: a=ACTION_CONNECTION_STATE_CHANGED, s=" + b.this.a(intExtra) + ", sb=" + isInitialStickyBroadcast() + ", BT state: " + b.this.f27572h);
                if (intExtra == 2) {
                    b bVar = b.this;
                    bVar.f27571g = 0;
                    bVar.j();
                } else if (intExtra != 1 && intExtra != 3 && intExtra == 0) {
                    b.this.i();
                    b.this.j();
                }
            } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                h.a(b.f27562m, "BluetoothHeadsetBroadcastReceiver.onReceive: a=ACTION_AUDIO_STATE_CHANGED, s=" + b.this.a(intExtra2) + ", sb=" + isInitialStickyBroadcast() + ", BT state: " + b.this.f27572h);
                if (intExtra2 == 12) {
                    b.this.b();
                    if (b.this.f27572h == d.SCO_CONNECTING) {
                        h.a(b.f27562m, "+++ Bluetooth audio SCO is now connected");
                        b.this.f27572h = d.SCO_CONNECTED;
                        b bVar2 = b.this;
                        bVar2.f27571g = 0;
                        bVar2.j();
                    } else {
                        Log.w(b.f27562m, "Unexpected state BluetoothHeadset.STATE_AUDIO_CONNECTED");
                    }
                } else if (intExtra2 == 11) {
                    h.a(b.f27562m, "+++ Bluetooth audio SCO is now connecting...");
                } else if (intExtra2 == 10) {
                    h.a(b.f27562m, "+++ Bluetooth audio SCO is now disconnected");
                    if (isInitialStickyBroadcast()) {
                        h.a(b.f27562m, "Ignore STATE_AUDIO_DISCONNECTED initial sticky broadcast.");
                        return;
                    }
                    b.this.j();
                }
            }
            h.a(b.f27562m, "onReceive done: BT state=" + b.this.f27572h);
        }

        public /* synthetic */ C0467b(b bVar, a aVar) {
            this();
        }
    }

    public class c implements BluetoothProfile.ServiceListener {
        public c() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            if (i2 != 1 || b.this.f27572h == d.UNINITIALIZED) {
                return;
            }
            h.a(b.f27562m, "BluetoothServiceListener.onServiceConnected: BT state=" + b.this.f27572h);
            b.this.f27574j = (BluetoothHeadset) bluetoothProfile;
            b.this.j();
            h.a(b.f27562m, "onServiceConnected done: BT state=" + b.this.f27572h);
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i2) {
            if (i2 != 1 || b.this.f27572h == d.UNINITIALIZED) {
                return;
            }
            h.a(b.f27562m, "BluetoothServiceListener.onServiceDisconnected: BT state=" + b.this.f27572h);
            b.this.i();
            b.this.f27574j = null;
            b.this.f27575k = null;
            b.this.f27572h = d.HEADSET_UNAVAILABLE;
            b.this.j();
            h.a(b.f27562m, "onServiceDisconnected done: BT state=" + b.this.f27572h);
        }

        public /* synthetic */ c(b bVar, a aVar) {
            this();
        }
    }

    public enum d {
        UNINITIALIZED,
        ERROR,
        HEADSET_UNAVAILABLE,
        HEADSET_AVAILABLE,
        SCO_DISCONNECTING,
        SCO_CONNECTING,
        SCO_CONNECTED
    }

    public b(Context context, k.a aVar) {
        h.a(f27562m, "ctor");
        ThreadUtils.checkIsOnMainThread();
        this.f27565a = context;
        this.f27566b = aVar;
        this.f27567c = a(context);
        this.f27572h = d.UNINITIALIZED;
        a aVar2 = null;
        this.f27569e = new c(this, aVar2);
        this.f27570f = new C0467b(this, aVar2);
        this.f27568d = new Handler(Looper.getMainLooper());
    }

    public final String a(int i2) {
        if (i2 == 0) {
            return "DISCONNECTED";
        }
        if (i2 == 1) {
            return "CONNECTING";
        }
        if (i2 == 2) {
            return "CONNECTED";
        }
        if (i2 == 3) {
            return "DISCONNECTING";
        }
        switch (i2) {
            case 10:
                return "OFF";
            case 11:
                return "TURNING_ON";
            case 12:
                return "ON";
            case 13:
                return "TURNING_OFF";
            default:
                return "INVALID";
        }
    }

    public void e() {
        try {
            ThreadUtils.checkIsOnMainThread();
            h.a(f27562m, "start ");
            if (Build.VERSION.SDK_INT >= 31) {
                h.a(f27562m, "start blue tooth component: >= 31");
                if (!a(this.f27565a, Permission.BLUETOOTH_SCAN) || !a(this.f27565a, Permission.BLUETOOTH_CONNECT)) {
                    Log.w(f27562m, "Process (pid=" + Process.myPid() + ") lacks BLUETOOTH BLUETOOTH_SCAN or BLUETOOTH_CONNECT permission");
                    return;
                }
            } else {
                h.a(f27562m, "start blue tooth component: < 31");
                if (!a(this.f27565a, "android.permission.BLUETOOTH")) {
                    Log.w(f27562m, "Process (pid=" + Process.myPid() + ") lacks BLUETOOTH permission");
                    return;
                }
            }
            if (this.f27572h != d.UNINITIALIZED) {
                Log.w(f27562m, "Invalid BT state");
                return;
            }
            this.f27574j = null;
            this.f27575k = null;
            this.f27571g = 0;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.f27573i = defaultAdapter;
            if (defaultAdapter == null) {
                Log.w(f27562m, "Device does not support Bluetooth");
                return;
            }
            if (!this.f27567c.isBluetoothScoAvailableOffCall()) {
                Log.e(f27562m, "Bluetooth SCO audio is not available off call");
                return;
            }
            a(this.f27573i);
            if (!a(this.f27565a, this.f27569e, 1)) {
                Log.e(f27562m, "BluetoothAdapter.getProfileProxy(HEADSET) failed");
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
            a(this.f27570f, intentFilter);
            h.a(f27562m, "HEADSET profile state: " + a(this.f27573i.getProfileConnectionState(1)));
            h.a(f27562m, "Bluetooth proxy for headset profile has started");
            this.f27572h = d.HEADSET_UNAVAILABLE;
            h.a(f27562m, "start done: BT state=" + this.f27572h);
        } catch (Exception e2) {
            h.a(f27562m, "start bluetooth failed for " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    public boolean f() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "startSco: BT state=" + this.f27572h + ", attempts: " + this.f27571g + ", SCO is on: " + d());
        if (this.f27571g >= 2) {
            Log.e(f27562m, "BT SCO connection fails - no more attempts");
            return false;
        }
        if (this.f27572h != d.HEADSET_AVAILABLE) {
            Log.e(f27562m, "BT SCO connection fails - no headset available");
            return false;
        }
        h.a(f27562m, "Starting Bluetooth SCO and waits for ACTION_AUDIO_STATE_CHANGED...");
        this.f27572h = d.SCO_CONNECTING;
        this.f27567c.startBluetoothSco();
        this.f27567c.setBluetoothScoOn(true);
        this.f27571g++;
        g();
        h.a(f27562m, "startScoAudio done: BT state=" + this.f27572h + ", SCO is on: " + d());
        return true;
    }

    public final void g() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "startTimer");
        this.f27568d.postDelayed(this.f27576l, 4000L);
    }

    public void h() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "stop: BT state=" + this.f27572h + "bluetoothAdapter: " + this.f27573i);
        if (this.f27573i == null) {
            return;
        }
        i();
        d dVar = this.f27572h;
        d dVar2 = d.UNINITIALIZED;
        if (dVar == dVar2) {
            return;
        }
        a(this.f27570f);
        b();
        BluetoothHeadset bluetoothHeadset = this.f27574j;
        if (bluetoothHeadset != null) {
            this.f27573i.closeProfileProxy(1, bluetoothHeadset);
            this.f27574j = null;
        }
        this.f27573i = null;
        this.f27575k = null;
        this.f27572h = dVar2;
        h.a(f27562m, "stop done: BT state=" + this.f27572h);
    }

    public void i() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "stopScoAudio: BT state=" + this.f27572h + ", SCO is on: " + d());
        d dVar = this.f27572h;
        if (dVar == d.SCO_CONNECTING || dVar == d.SCO_CONNECTED) {
            b();
            this.f27567c.stopBluetoothSco();
            this.f27567c.setBluetoothScoOn(false);
            this.f27572h = d.SCO_DISCONNECTING;
            h.a(f27562m, "stopScoAudio done: BT state=" + this.f27572h + ", SCO is on: " + d());
        }
    }

    public final void j() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "updateAudioDeviceState");
        this.f27566b.i();
    }

    public void k() {
        if (Build.VERSION.SDK_INT >= 31) {
            h.a(f27562m, "updateDevice blue tooth : >= 31");
            if (!a(this.f27565a, Permission.BLUETOOTH_SCAN) || !a(this.f27565a, Permission.BLUETOOTH_CONNECT)) {
                Log.w(f27562m, "Process (pid=" + Process.myPid() + ") lacks BLUETOOTH BLUETOOTH_SCAN or BLUETOOTH_CONNECT permission");
                return;
            }
        } else {
            h.a(f27562m, "updateDevice blue tooth component: < 31");
            if (!a(this.f27565a, "android.permission.BLUETOOTH")) {
                Log.w(f27562m, "Process (pid=" + Process.myPid() + ") lacks BLUETOOTH permission");
                return;
            }
        }
        if (this.f27572h == d.UNINITIALIZED || this.f27574j == null) {
            return;
        }
        h.a(f27562m, "updateDevice");
        List<BluetoothDevice> connectedDevices = this.f27574j.getConnectedDevices();
        if (connectedDevices.isEmpty()) {
            this.f27575k = null;
            this.f27572h = d.HEADSET_UNAVAILABLE;
            h.a(f27562m, "No connected bluetooth headset");
        } else {
            this.f27575k = connectedDevices.get(0);
            this.f27572h = d.HEADSET_AVAILABLE;
            h.a(f27562m, "Connected bluetooth headset: name=" + this.f27575k.getName() + ", state=" + a(this.f27574j.getConnectionState(this.f27575k)) + ", SCO audio=" + this.f27574j.isAudioConnected(this.f27575k));
        }
        h.a(f27562m, "updateDevice done: BT state=" + this.f27572h);
    }

    public final void b() {
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "cancelTimer");
        this.f27568d.removeCallbacks(this.f27576l);
    }

    public d c() {
        ThreadUtils.checkIsOnMainThread();
        return this.f27572h;
    }

    public final boolean d() {
        return this.f27567c.isBluetoothScoOn();
    }

    public static b a(Context context, k.a aVar) {
        h.a(f27562m, "create" + h.b.a());
        return new b(context, aVar);
    }

    public AudioManager a(Context context) {
        return (AudioManager) context.getSystemService("audio");
    }

    public void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.f27565a.registerReceiver(broadcastReceiver, intentFilter);
    }

    public void a(BroadcastReceiver broadcastReceiver) {
        this.f27565a.unregisterReceiver(broadcastReceiver);
    }

    public boolean a(Context context, BluetoothProfile.ServiceListener serviceListener, int i2) {
        return this.f27573i.getProfileProxy(context, serviceListener, i2);
    }

    public boolean a(Context context, String str) {
        return this.f27565a.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    @SuppressLint({"HardwareIds"})
    public void a(BluetoothAdapter bluetoothAdapter) {
        h.a(f27562m, "BluetoothAdapter: enabled=" + bluetoothAdapter.isEnabled() + ", state=" + a(bluetoothAdapter.getState()) + ", name=" + bluetoothAdapter.getName() + ", address=" + bluetoothAdapter.getAddress());
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            return;
        }
        h.a(f27562m, "paired devices:");
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice != null) {
                h.a(f27562m, " name=" + bluetoothDevice.getName() + ", address=" + bluetoothDevice.getAddress());
            }
        }
    }

    public final void a() {
        boolean z2;
        ThreadUtils.checkIsOnMainThread();
        h.a(f27562m, "bluetoothTimeout bluetoothState: " + this.f27572h);
        if (this.f27572h == d.UNINITIALIZED || this.f27574j == null) {
            return;
        }
        h.a(f27562m, "bluetoothTimeout: BT state=" + this.f27572h + ", attempts: " + this.f27571g + ", SCO is on: " + d());
        if (this.f27572h != d.SCO_CONNECTING) {
            return;
        }
        List<BluetoothDevice> connectedDevices = this.f27574j.getConnectedDevices();
        if (connectedDevices.size() > 0) {
            BluetoothDevice bluetoothDevice = connectedDevices.get(0);
            this.f27575k = bluetoothDevice;
            if (this.f27574j.isAudioConnected(bluetoothDevice)) {
                h.a(f27562m, "SCO connected with " + this.f27575k.getName());
                z2 = true;
            } else {
                h.a(f27562m, "SCO is not connected with " + this.f27575k.getName());
                z2 = false;
            }
        } else {
            z2 = false;
        }
        if (z2) {
            this.f27572h = d.SCO_CONNECTED;
            this.f27571g = 0;
        } else {
            Log.w(f27562m, "BT failed to connect after timeout");
            i();
        }
        j();
        h.a(f27562m, "bluetoothTimeout done: BT state=" + this.f27572h);
    }
}
