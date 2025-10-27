package com.tencent.liteav.audio.impl.route;

import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.sharp.jni.TraeAudioManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private final HashMap<String, a> f18303a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private String f18304b = TraeAudioManager.DEVICE_NONE;

    /* renamed from: c, reason: collision with root package name */
    private String f18305c = TraeAudioManager.DEVICE_NONE;

    /* renamed from: d, reason: collision with root package name */
    private String f18306d = TraeAudioManager.DEVICE_NONE;

    /* renamed from: e, reason: collision with root package name */
    private String f18307e = "unknown";

    /* renamed from: f, reason: collision with root package name */
    private boolean f18308f = false;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private String f18309a = TraeAudioManager.DEVICE_NONE;

        /* renamed from: b, reason: collision with root package name */
        private boolean f18310b = false;

        /* renamed from: c, reason: collision with root package name */
        private int f18311c = 0;

        public boolean a(String str, int i2) {
            if (str == null || str.length() <= 0 || !h.f(str)) {
                return false;
            }
            this.f18309a = str;
            this.f18311c = i2;
            return true;
        }

        public boolean b() {
            return this.f18310b;
        }

        public int c() {
            return this.f18311c;
        }

        public String a() {
            return this.f18309a;
        }

        public void a(boolean z2) {
            this.f18310b = z2;
        }
    }

    private void l() {
        TXCLog.i("TXCDeviceConfigManager", "ConnectedDevice:" + h() + ", ConnectingDevice:" + g() + ", prevConnectedDevice:" + i() + ", available hightest priority device:" + f() + "device count:" + e());
        Iterator<Map.Entry<String, a>> it = this.f18303a.entrySet().iterator();
        while (it.hasNext()) {
            a value = it.next().getValue();
            TXCLog.i("TXCDeviceConfigManager", "name: %s, visible: %b, priority: %d", value.f18309a, Boolean.valueOf(value.f18310b), Integer.valueOf(value.f18311c));
        }
    }

    public synchronized boolean a(String str) {
        TXCAudioNativeInterface.LogTraceEntry(" strConfigs:" + str);
        if (str != null && str.length() > 0) {
            String strReplace = str.replace("\n", "").replace(StrPool.CR, "");
            if (strReplace.length() <= 0) {
                return false;
            }
            if (!strReplace.contains(com.alipay.sdk.util.h.f3376b)) {
                strReplace = strReplace + com.alipay.sdk.util.h.f3376b;
            }
            String[] strArrSplit = strReplace.split(com.alipay.sdk.util.h.f3376b);
            if (1 > strArrSplit.length) {
                return false;
            }
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                a(strArrSplit[i2], i2);
            }
            l();
            return true;
        }
        return false;
    }

    public synchronized boolean b() {
        return this.f18308f;
    }

    public synchronized void c() {
        this.f18308f = false;
    }

    public String d() {
        return this.f18307e;
    }

    public synchronized int e() {
        return this.f18303a.size();
    }

    public synchronized String f() {
        a aVar;
        Iterator<Map.Entry<String, a>> it = this.f18303a.entrySet().iterator();
        aVar = null;
        while (it.hasNext()) {
            a value = it.next().getValue();
            if (value != null && value.b() && (aVar == null || value.c() >= aVar.c())) {
                aVar = value;
            }
        }
        return aVar != null ? aVar.a() : TraeAudioManager.DEVICE_SPEAKERPHONE;
    }

    public synchronized String g() {
        a aVar;
        aVar = this.f18303a.get(this.f18306d);
        return (aVar == null || !aVar.b()) ? null : this.f18306d;
    }

    public synchronized String h() {
        a aVar = this.f18303a.get(this.f18305c);
        if (aVar == null || !aVar.b()) {
            return TraeAudioManager.DEVICE_NONE;
        }
        return this.f18305c;
    }

    public synchronized String i() {
        String str;
        str = TraeAudioManager.DEVICE_NONE;
        a aVar = this.f18303a.get(this.f18304b);
        if (aVar != null && aVar.b()) {
            str = this.f18304b;
        }
        return str;
    }

    public synchronized void j() {
        this.f18306d = "";
    }

    public synchronized ArrayList<String> k() {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        Iterator<Map.Entry<String, a>> it = this.f18303a.entrySet().iterator();
        while (it.hasNext()) {
            a value = it.next().getValue();
            if (value != null && value.b()) {
                arrayList.add(value.a());
            }
        }
        return arrayList;
    }

    public void b(String str) {
        if (str == null) {
            this.f18307e = "unknown";
        } else if (str.isEmpty()) {
            this.f18307e = "unknown";
        } else {
            this.f18307e = str;
        }
    }

    public synchronized void d(String str) {
        a aVar = this.f18303a.get(str);
        if (aVar != null && aVar.b()) {
            this.f18306d = str;
        }
    }

    public synchronized void e(String str) {
        a aVar = this.f18303a.get(str);
        if (aVar != null && aVar.b()) {
            String str2 = this.f18305c;
            if (str2 != null && !str2.equals(str)) {
                this.f18304b = this.f18305c;
            }
            this.f18305c = str;
            this.f18306d = "";
        }
    }

    public synchronized boolean c(String str) {
        a aVar = this.f18303a.get(str);
        if (aVar == null) {
            return false;
        }
        return aVar.b();
    }

    public static boolean f(String str) {
        return TraeAudioManager.DEVICE_SPEAKERPHONE.equals(str) || TraeAudioManager.DEVICE_EARPHONE.equals(str) || TraeAudioManager.DEVICE_WIREDHEADSET.equals(str) || TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(str);
    }

    private void a(String str, int i2) {
        TXCAudioNativeInterface.LogTraceEntry(" devName:" + str + " priority:" + i2);
        a aVar = new a();
        if (!aVar.a(str, i2)) {
            TXCLog.e("TXCDeviceConfigManager", " err dev init!");
            return;
        }
        if (this.f18303a.containsKey(str)) {
            TXCLog.e("TXCDeviceConfigManager", "err dev exist!");
            return;
        }
        this.f18303a.put(str, aVar);
        this.f18308f = true;
        TXCLog.i("TXCDeviceConfigManager", "add device, name: %s", str);
        TXCAudioNativeInterface.LogTraceExit();
    }

    public synchronized void a() {
        this.f18303a.clear();
        this.f18304b = TraeAudioManager.DEVICE_NONE;
        this.f18305c = TraeAudioManager.DEVICE_NONE;
        this.f18306d = TraeAudioManager.DEVICE_NONE;
    }

    public synchronized boolean a(String str, boolean z2) {
        boolean z3;
        a aVar = this.f18303a.get(str);
        z3 = false;
        if (aVar != null && aVar.b() != z2) {
            aVar.a(z2);
            this.f18308f = true;
            TXCLog.i("TXCDeviceConfigManager", "update device visibility, device: %s, visible: %s", str, Boolean.valueOf(z2));
            z3 = true;
        }
        return z3;
    }
}
