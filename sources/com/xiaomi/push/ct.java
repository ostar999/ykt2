package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ct {

    /* renamed from: a, reason: collision with other field name */
    private Context f276a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f278a;

    /* renamed from: a, reason: collision with other field name */
    private cv f279a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f281a;

    /* renamed from: a, reason: collision with root package name */
    private int f24693a = 0;

    /* renamed from: a, reason: collision with other field name */
    private List<b> f280a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private List<b> f24694b = new ArrayList();

    /* renamed from: a, reason: collision with other field name */
    private final ServiceConnection f277a = new cu(this);

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2 = message.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    ct.this.a();
                    return;
                } else if (i2 != 3) {
                    Log.w("GeoFencingServiceWrapper", "unknown message type ");
                    return;
                } else {
                    ct.this.b();
                    return;
                }
            }
            ct.a(ct.this);
            ct ctVar = ct.this;
            ctVar.a(ctVar.f276a);
            Log.w("GeoFencingServiceWrapper", "Try bindService count=" + ct.this.f24693a + ",mBinded=" + ct.this.f281a);
            if (ct.this.f281a || ct.this.f278a == null || ct.this.f24693a >= 10) {
                return;
            }
            ct.this.f278a.sendEmptyMessageDelayed(1, com.heytap.mcssdk.constant.a.f7153q);
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        public double f24696a;

        /* renamed from: a, reason: collision with other field name */
        public float f282a;

        /* renamed from: a, reason: collision with other field name */
        public long f283a;

        /* renamed from: a, reason: collision with other field name */
        public String f285a;

        /* renamed from: b, reason: collision with root package name */
        public double f24697b;

        /* renamed from: b, reason: collision with other field name */
        public String f286b;

        /* renamed from: c, reason: collision with root package name */
        public String f24698c;

        public b(double d3, double d4, float f2, long j2, String str, String str2, String str3) {
            this.f24696a = d3;
            this.f24697b = d4;
            this.f282a = f2;
            this.f283a = j2;
            this.f285a = str;
            this.f286b = str2;
            this.f24698c = str3;
        }
    }

    public ct(Context context) {
        this.f281a = false;
        this.f276a = context;
        this.f281a = false;
        a(context);
        HandlerThread handlerThread = new HandlerThread("GeoFencingServiceWrapper");
        handlerThread.start();
        a aVar = new a(handlerThread.getLooper());
        this.f278a = aVar;
        if (this.f281a) {
            return;
        }
        aVar.sendEmptyMessageDelayed(1, com.heytap.mcssdk.constant.a.f7153q);
    }

    public static /* synthetic */ int a(ct ctVar) {
        int i2 = ctVar.f24693a;
        ctVar.f24693a = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        cv cvVar;
        List<b> list = this.f280a;
        Log.d("GeoFencingServiceWrapper", "try registerPendingFence size=" + (list == null ? 0 : list.size()));
        for (b bVar : this.f280a) {
            if (bVar != null && (cvVar = this.f279a) != null) {
                try {
                    cvVar.a(bVar.f24696a, bVar.f24697b, bVar.f282a, bVar.f283a, bVar.f285a, bVar.f286b, bVar.f24698c);
                } catch (RemoteException e2) {
                    Log.w("GeoFencingServiceWrapper", "registerPendingFence:" + e2);
                }
            }
        }
        List<b> list2 = this.f280a;
        if (list2 != null) {
            list2.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        cv cvVar;
        List<b> list = this.f24694b;
        Log.d("GeoFencingServiceWrapper", "try unregisterPendingFence size=" + (list == null ? 0 : list.size()));
        for (b bVar : this.f24694b) {
            if (bVar != null && (cvVar = this.f279a) != null) {
                try {
                    cvVar.a(bVar.f285a, bVar.f286b);
                } catch (RemoteException e2) {
                    Log.w("GeoFencingServiceWrapper", "unregisterPendingFence:" + e2);
                }
            }
        }
        List<b> list2 = this.f24694b;
        if (list2 != null) {
            list2.clear();
        }
    }

    public void a(Context context) {
        if (this.f281a || context == null) {
            return;
        }
        if (this.f279a != null) {
            Log.d("GeoFencingServiceWrapper", "GeoFencingService already started");
            return;
        }
        Intent intent = new Intent("com.xiaomi.metoknlp.GeoFencingService");
        intent.setPackage("com.xiaomi.metoknlp");
        try {
            if (context.bindService(intent, this.f277a, 1)) {
                Log.d("GeoFencingServiceWrapper", "GeoFencingService started");
                this.f281a = true;
            } else {
                Log.d("GeoFencingServiceWrapper", "Can't bind GeoFencingService");
                this.f281a = false;
            }
        } catch (SecurityException e2) {
            Log.e("GeoFencingServiceWrapper", "SecurityException:" + e2);
        }
    }

    public void a(Context context, double d3, double d4, float f2, long j2, String str, String str2, String str3) {
        a(context);
        cv cvVar = this.f279a;
        if (cvVar == null) {
            Log.d("GeoFencingServiceWrapper", "registerFenceListener service not ready, add to pending list.");
            this.f280a.add(new b(d3, d4, f2, j2, str, str2, str3));
        } else {
            try {
                cvVar.a(d3, d4, f2, j2, str, str2, str3);
                Log.d("GeoFencingServiceWrapper", "calling registerFenceListener success");
            } catch (RemoteException e2) {
                throw new RuntimeException("GeoFencingService has died", e2);
            }
        }
    }

    public void a(Context context, String str, String str2) {
        a(context);
        cv cvVar = this.f279a;
        if (cvVar == null) {
            Log.d("GeoFencingServiceWrapper", "unregisterFenceListener service not ready, add to pending list.");
            this.f24694b.add(new b(0.0d, 0.0d, 0.0f, -1L, str, str2, ""));
        } else {
            try {
                cvVar.a(str, str2);
                Log.d("GeoFencingServiceWrapper", "calling unregisterFenceListener success");
            } catch (RemoteException e2) {
                throw new RuntimeException("GeoFencingService has died", e2);
            }
        }
    }
}
