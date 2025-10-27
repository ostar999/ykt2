package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f18195a = new a();

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<Integer, WeakReference<b>> f18196b = new ConcurrentHashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private PhoneStateListener f18197c = null;

    /* renamed from: d, reason: collision with root package name */
    private Context f18198d;

    private a() {
    }

    public void finalize() throws Throwable {
        super.finalize();
        if (this.f18197c == null || this.f18198d == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.a.2
            @Override // java.lang.Runnable
            public void run() {
                if (a.this.f18197c != null && a.this.f18198d != null) {
                    try {
                        ((TelephonyManager) a.this.f18198d.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).listen(a.this.f18197c, 0);
                    } catch (Exception e2) {
                        TXCLog.e("AudioCenter:TXCTelephonyMgr", "TelephonyManager listen error ", e2);
                    }
                }
                a.this.f18197c = null;
            }
        });
    }

    public static a a() {
        return f18195a;
    }

    public synchronized void a(b bVar) {
        if (bVar == null) {
            return;
        }
        this.f18196b.put(Integer.valueOf(bVar.hashCode()), new WeakReference<>(bVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(int i2) {
        Iterator<Map.Entry<Integer, WeakReference<b>>> it = this.f18196b.entrySet().iterator();
        while (it.hasNext()) {
            b bVar = it.next().getValue().get();
            if (bVar != null) {
                bVar.onCallStateChanged(i2);
            } else {
                it.remove();
            }
        }
    }

    public void a(Context context) {
        if (this.f18197c != null) {
            return;
        }
        this.f18198d = context.getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.a.1
            @Override // java.lang.Runnable
            public void run() {
                if (a.this.f18197c != null) {
                    return;
                }
                a.this.f18197c = new PhoneStateListener() { // from class: com.tencent.liteav.audio.impl.a.1.1
                    @Override // android.telephony.PhoneStateListener
                    public void onCallStateChanged(int i2, String str) {
                        super.onCallStateChanged(i2, str);
                        TXCLog.i("AudioCenter:TXCTelephonyMgr", "onCallStateChanged:" + i2);
                        a.this.a(i2);
                    }
                };
                try {
                    ((TelephonyManager) a.this.f18198d.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).listen(a.this.f18197c, 32);
                } catch (Exception e2) {
                    TXCLog.e("AudioCenter:TXCTelephonyMgr", "TelephonyManager listen error ", e2);
                }
            }
        });
    }
}
