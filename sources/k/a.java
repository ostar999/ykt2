package k;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import c.h;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import h.b;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import k.b;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes8.dex */
public class a {

    /* renamed from: s, reason: collision with root package name */
    public static final String f27521s = "AudioManager";

    /* renamed from: t, reason: collision with root package name */
    public static final String f27522t = "auto";

    /* renamed from: u, reason: collision with root package name */
    public static final String f27523u = "true";

    /* renamed from: v, reason: collision with root package name */
    public static final String f27524v = "false";

    /* renamed from: a, reason: collision with root package name */
    public final Context f27525a;

    /* renamed from: b, reason: collision with root package name */
    public AudioManager f27526b;

    /* renamed from: c, reason: collision with root package name */
    public d f27527c;

    /* renamed from: d, reason: collision with root package name */
    public e f27528d;

    /* renamed from: i, reason: collision with root package name */
    public c f27533i;

    /* renamed from: j, reason: collision with root package name */
    public c f27534j;

    /* renamed from: k, reason: collision with root package name */
    public c f27535k;

    /* renamed from: l, reason: collision with root package name */
    public final String f27536l;

    /* renamed from: m, reason: collision with root package name */
    public b.a f27537m;

    /* renamed from: n, reason: collision with root package name */
    public final k.b f27538n;

    /* renamed from: p, reason: collision with root package name */
    public BroadcastReceiver f27540p;

    /* renamed from: q, reason: collision with root package name */
    public AudioManager.OnAudioFocusChangeListener f27541q;

    /* renamed from: e, reason: collision with root package name */
    public int f27529e = -2;

    /* renamed from: f, reason: collision with root package name */
    public boolean f27530f = false;

    /* renamed from: g, reason: collision with root package name */
    public boolean f27531g = false;

    /* renamed from: h, reason: collision with root package name */
    public boolean f27532h = false;

    /* renamed from: o, reason: collision with root package name */
    public Set<c> f27539o = new HashSet();

    /* renamed from: r, reason: collision with root package name */
    public boolean f27542r = true;

    /* renamed from: k.a$a, reason: collision with other inner class name */
    public class C0465a implements AudioManager.OnAudioFocusChangeListener {

        /* renamed from: k.a$a$a, reason: collision with other inner class name */
        public class RunnableC0466a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ int f27544a;

            public RunnableC0466a(int i2) {
                this.f27544a = i2;
            }

            @Override // java.lang.Runnable
            public void run() {
                String str;
                h.a(a.f27521s, "onAudioFocusChange focusChange is: " + this.f27544a + " bluetooth state is: " + a.this.f27538n.c() + " audioManager state is: " + a.this.f27526b.getMode());
                int i2 = this.f27544a;
                if (i2 == -3) {
                    str = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
                } else if (i2 == -2) {
                    if (a.this.f27538n.c() == b.d.SCO_CONNECTED && a.this.f27526b.getMode() == 3) {
                        a.this.f27538n.i();
                        a.this.f27538n.k();
                    }
                    str = "AUDIOFOCUS_LOSS_TRANSIENT";
                } else if (i2 == -1) {
                    str = "AUDIOFOCUS_LOSS";
                } else if (i2 != 1) {
                    str = i2 != 2 ? i2 != 3 ? i2 != 4 ? "AUDIOFOCUS_INVALID" : "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE" : "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK" : "AUDIOFOCUS_GAIN_TRANSIENT";
                } else {
                    if (a.this.f27538n.c() == b.d.HEADSET_AVAILABLE && a.this.f27526b.getMode() == 3) {
                        a.this.f27538n.f();
                        a.this.f27538n.k();
                    }
                    str = "AUDIOFOCUS_GAIN";
                }
                h.a(a.f27521s, "onAudioFocusChange: " + str);
            }
        }

        public C0465a() {
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i2) {
            new Handler(Looper.getMainLooper()).post(new RunnableC0466a(i2));
        }
    }

    public static /* synthetic */ class b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f27546a;

        static {
            int[] iArr = new int[c.values().length];
            f27546a = iArr;
            try {
                iArr[c.SPEAKER_PHONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f27546a[c.EARPIECE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f27546a[c.WIRED_HEADSET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f27546a[c.BLUETOOTH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum c {
        SPEAKER_PHONE,
        WIRED_HEADSET,
        EARPIECE,
        BLUETOOTH,
        NONE
    }

    public interface d {
        void a(c cVar, Set<c> set);
    }

    public enum e {
        UNINITIALIZED,
        PREINITIALIZED,
        RUNNING
    }

    public class f extends BroadcastReceiver {

        /* renamed from: b, reason: collision with root package name */
        public static final int f27557b = 0;

        /* renamed from: c, reason: collision with root package name */
        public static final int f27558c = 1;

        /* renamed from: d, reason: collision with root package name */
        public static final int f27559d = 0;

        /* renamed from: e, reason: collision with root package name */
        public static final int f27560e = 1;

        public f() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("state", 0);
            int intExtra2 = intent.getIntExtra(PLVPPTAuthentic.PermissionType.MICROPHONE, 0);
            String stringExtra = intent.getStringExtra("name");
            StringBuilder sb = new StringBuilder();
            sb.append("WiredHeadsetReceiver.onReceive");
            sb.append(h.b.a());
            sb.append(": a=");
            sb.append(intent.getAction());
            sb.append(", s=");
            sb.append(intExtra == 0 ? "unplugged" : "plugged");
            sb.append(", m=");
            sb.append(intExtra2 == 1 ? "mic" : "no mic");
            sb.append(", n=");
            sb.append(stringExtra);
            sb.append(", sb=");
            sb.append(isInitialStickyBroadcast());
            h.a(a.f27521s, sb.toString());
            a.this.f27532h = intExtra == 1;
            a.this.i();
        }

        public /* synthetic */ f(a aVar, C0465a c0465a) {
            this();
        }
    }

    public a(Context context) {
        this.f27537m = null;
        h.a(f27521s, "ctor");
        ThreadUtils.checkIsOnMainThread();
        this.f27525a = context;
        this.f27526b = (AudioManager) context.getSystemService("audio");
        this.f27538n = k.b.a(context, this);
        this.f27540p = new f(this, null);
        this.f27528d = e.UNINITIALIZED;
        PreferenceManager.getDefaultSharedPreferences(context);
        this.f27536l = f27523u;
        h.a(f27521s, "useSpeakerphone: " + f27523u);
        this.f27533i = c.SPEAKER_PHONE;
        this.f27537m = b.a.a(context, new Runnable() { // from class: z1.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f28298c.f();
            }
        });
        h.a(f27521s, "defaultAudioDevice: " + this.f27533i);
        h.b.a(f27521s);
    }

    public void c(c cVar) {
        ThreadUtils.checkIsOnMainThread();
        int i2 = b.f27546a[cVar.ordinal()];
        if (i2 == 1) {
            this.f27533i = cVar;
        } else if (i2 != 2) {
            Log.e(f27521s, "Invalid default audio device selection");
        } else if (d()) {
            this.f27533i = cVar;
        } else {
            this.f27533i = c.SPEAKER_PHONE;
        }
        h.a(f27521s, "setDefaultAudioDevice(device=" + this.f27533i + ")");
        i();
    }

    public final boolean d() {
        return this.f27525a.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    @Deprecated
    public final boolean e() {
        for (AudioDeviceInfo audioDeviceInfo : this.f27526b.getDevices(3)) {
            int type = audioDeviceInfo.getType();
            if (type == 3) {
                h.a(f27521s, "hasWiredHeadset: found wired headset");
                return true;
            }
            if (type == 11) {
                h.a(f27521s, "hasWiredHeadset: found USB audio device");
                return true;
            }
        }
        return false;
    }

    public final void f() {
        if (this.f27536l.equals("auto") && this.f27539o.size() == 2) {
            Set<c> set = this.f27539o;
            c cVar = c.EARPIECE;
            if (set.contains(cVar)) {
                Set<c> set2 = this.f27539o;
                c cVar2 = c.SPEAKER_PHONE;
                if (set2.contains(cVar2)) {
                    if (this.f27537m.c()) {
                        b(cVar);
                    } else {
                        b(cVar2);
                    }
                }
            }
        }
    }

    public void g() {
        k.b bVar = this.f27538n;
        if (bVar != null) {
            bVar.e();
        }
    }

    public void h() {
        h.a(f27521s, "stop");
        ThreadUtils.checkIsOnMainThread();
        if (this.f27528d != e.RUNNING) {
            Log.e(f27521s, "Trying to stop AudioManager in incorrect state: " + this.f27528d);
            return;
        }
        this.f27528d = e.UNINITIALIZED;
        a(this.f27540p);
        this.f27538n.h();
        c(this.f27530f);
        a(this.f27531g);
        this.f27526b.setMode(this.f27529e);
        this.f27526b.abandonAudioFocus(this.f27541q);
        this.f27541q = null;
        h.a(f27521s, "Abandoned audio focus for VOICE_CALL streams");
        b.a aVar = this.f27537m;
        if (aVar != null) {
            aVar.e();
            this.f27537m = null;
        }
        this.f27527c = null;
        h.a(f27521s, "AudioManager stopped");
    }

    public void i() {
        c cVar;
        c cVar2;
        c cVar3;
        ThreadUtils.checkIsOnMainThread();
        h.a(f27521s, "--- updateAudioDeviceState: wired headset=" + this.f27532h + ", BT state=" + this.f27538n.c());
        h.a(f27521s, "Device status: available=" + this.f27539o + ", selected=" + this.f27534j + ", user selected=" + this.f27535k);
        b.d dVarC = this.f27538n.c();
        b.d dVar = b.d.HEADSET_AVAILABLE;
        if (dVarC == dVar || this.f27538n.c() == b.d.HEADSET_UNAVAILABLE || this.f27538n.c() == b.d.SCO_DISCONNECTING) {
            this.f27538n.k();
        }
        HashSet hashSet = new HashSet();
        b.d dVarC2 = this.f27538n.c();
        b.d dVar2 = b.d.SCO_CONNECTED;
        if (dVarC2 == dVar2 || this.f27538n.c() == b.d.SCO_CONNECTING || this.f27538n.c() == dVar) {
            hashSet.add(c.BLUETOOTH);
        }
        if (this.f27532h) {
            hashSet.add(c.WIRED_HEADSET);
        } else {
            hashSet.add(c.SPEAKER_PHONE);
            if (d()) {
                hashSet.add(c.EARPIECE);
            }
        }
        boolean z2 = true;
        boolean z3 = !this.f27539o.equals(hashSet);
        this.f27539o = hashSet;
        if (this.f27538n.c() == b.d.HEADSET_UNAVAILABLE && this.f27535k == c.BLUETOOTH) {
            this.f27535k = c.NONE;
        }
        boolean z4 = this.f27532h;
        if (z4 && this.f27535k == c.SPEAKER_PHONE) {
            this.f27535k = c.WIRED_HEADSET;
        }
        if (!z4 && this.f27535k == c.WIRED_HEADSET) {
            this.f27535k = c.SPEAKER_PHONE;
        }
        boolean z5 = false;
        boolean z6 = this.f27538n.c() == dVar && ((cVar3 = this.f27535k) == c.NONE || cVar3 == c.BLUETOOTH);
        if ((this.f27538n.c() == dVar2 || this.f27538n.c() == b.d.SCO_CONNECTING) && (cVar = this.f27535k) != c.NONE && cVar != c.BLUETOOTH) {
            z5 = true;
        }
        if (this.f27538n.c() == dVar || this.f27538n.c() == b.d.SCO_CONNECTING || this.f27538n.c() == dVar2) {
            h.a(f27521s, "Need BT audio: start=" + z6 + ", stop=" + z5 + ", BT state=" + this.f27538n.c());
        }
        if (z5) {
            this.f27538n.i();
            this.f27538n.k();
        }
        if (!z6 || z5 || this.f27526b.getMode() != 3 || this.f27538n.f()) {
            z2 = z3;
        } else {
            this.f27539o.remove(c.BLUETOOTH);
        }
        if (this.f27538n.c() == dVar2 || this.f27538n.c() == dVar) {
            cVar2 = c.BLUETOOTH;
        } else if (this.f27532h) {
            cVar2 = c.WIRED_HEADSET;
        } else if (this.f27542r) {
            cVar2 = this.f27533i;
            h.a(f27521s, "updateAudioDeviceState newAudioDevice to defaultAudioDevice = " + cVar2);
        } else {
            cVar2 = c.EARPIECE;
        }
        if (cVar2 != this.f27534j || z2) {
            b(cVar2);
            h.a(f27521s, "New device status: available=" + this.f27539o + ", selected=" + cVar2 + "audioManagerEvents: " + this.f27527c);
            d dVar3 = this.f27527c;
            if (dVar3 != null) {
                dVar3.a(this.f27534j, this.f27539o);
            }
        }
        h.a(f27521s, "--- updateAudioDeviceState done");
    }

    public final void b(c cVar) {
        h.a(f27521s, "setAudioDeviceInternal(device=" + cVar + ")");
        if (!this.f27539o.contains(cVar)) {
            h.a(f27521s, "audioDevices not contain device type: " + cVar);
            return;
        }
        int i2 = b.f27546a[cVar.ordinal()];
        if (i2 == 1) {
            c(true);
        } else if (i2 == 2 || i2 == 3 || i2 == 4) {
            c(false);
        } else {
            Log.e(f27521s, "Invalid audio device selection");
        }
        this.f27534j = cVar;
    }

    public static a a(Context context) {
        return new a(context);
    }

    public void a(d dVar) {
        h.a(f27521s, "start");
        ThreadUtils.checkIsOnMainThread();
        e eVar = this.f27528d;
        e eVar2 = e.RUNNING;
        if (eVar == eVar2) {
            Log.e(f27521s, "AudioManager is already active");
            return;
        }
        h.a(f27521s, "AudioManager starts...");
        this.f27527c = dVar;
        this.f27528d = eVar2;
        this.f27529e = this.f27526b.getMode();
        this.f27530f = this.f27526b.isSpeakerphoneOn();
        this.f27531g = this.f27526b.isMicrophoneMute();
        this.f27532h = e();
        C0465a c0465a = new C0465a();
        this.f27541q = c0465a;
        if (this.f27526b.requestAudioFocus(c0465a, 0, 2) == 1) {
            h.a(f27521s, "Audio focus request granted for VOICE_CALL streams");
        } else {
            Log.e(f27521s, "Audio focus request failed");
        }
        this.f27526b.setMode(0);
        a(false);
        b(true);
        c cVar = c.NONE;
        this.f27535k = cVar;
        this.f27534j = cVar;
        this.f27539o.clear();
        this.f27538n.e();
        i();
        a(this.f27540p, new IntentFilter("android.intent.action.HEADSET_PLUG"));
        h.a(f27521s, "AudioManager started");
    }

    public void b(boolean z2) {
        h.a(f27521s, "setSpeakerOn status : " + z2);
        this.f27542r = z2;
        this.f27526b.setSpeakerphoneOn(z2);
    }

    public boolean c() {
        boolean zIsSpeakerphoneOn = this.f27526b.isSpeakerphoneOn();
        h.a(f27521s, "getSpeakerOn status : " + zIsSpeakerphoneOn);
        return zIsSpeakerphoneOn;
    }

    public final void c(boolean z2) {
        if (this.f27526b.isSpeakerphoneOn() == z2) {
            return;
        }
        h.a(f27521s, "audioManager.setSpeakerphoneOn is :" + z2);
        this.f27526b.setSpeakerphoneOn(z2);
    }

    public c b() {
        ThreadUtils.checkIsOnMainThread();
        return this.f27534j;
    }

    public void a(c cVar) {
        ThreadUtils.checkIsOnMainThread();
        if (!this.f27539o.contains(cVar)) {
            Log.e(f27521s, "Can not select " + cVar + " from available " + this.f27539o);
        }
        this.f27535k = cVar;
        i();
    }

    public Set<c> a() {
        ThreadUtils.checkIsOnMainThread();
        return Collections.unmodifiableSet(new HashSet(this.f27539o));
    }

    public final void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.f27525a.registerReceiver(broadcastReceiver, intentFilter);
    }

    public final void a(BroadcastReceiver broadcastReceiver) {
        this.f27525a.unregisterReceiver(broadcastReceiver);
    }

    public final void a(boolean z2) {
        if (this.f27526b.isMicrophoneMute() == z2) {
            return;
        }
        this.f27526b.setMicrophoneMute(z2);
    }

    public void a(int i2) {
        h.a(f27521s, "--- setAudioMode called mode is:" + i2);
        AudioManager audioManager = this.f27526b;
        if (audioManager != null) {
            audioManager.setMode(i2);
            if (this.f27538n.c() == b.d.SCO_CONNECTED && i2 == 0) {
                this.f27538n.i();
                return;
            }
            if (this.f27538n.c() == b.d.HEADSET_AVAILABLE && i2 == 3) {
                this.f27538n.f();
                return;
            }
            h.a(f27521s, "bluetoothManager.getState(): " + this.f27538n.c());
        }
    }
}
