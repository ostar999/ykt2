package com.xiaomi.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class bv {

    /* renamed from: a, reason: collision with root package name */
    private static bv f24652a;

    /* renamed from: a, reason: collision with other field name */
    private Context f232a;

    /* renamed from: a, reason: collision with other field name */
    private List f234a = new ArrayList();

    /* renamed from: a, reason: collision with other field name */
    private Handler f233a = new bw(this, bk.a().m236a().getLooper());

    /* renamed from: a, reason: collision with other field name */
    private BroadcastReceiver f231a = new bx(this);

    private bv(Context context) {
        this.f232a = context;
        this.f232a.registerReceiver(this.f231a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public static bv a() {
        return f24652a;
    }

    public static void a(Context context) {
        if (f24652a == null) {
            f24652a = new bv(context);
        }
    }

    public void a(bt btVar) {
        synchronized (this.f234a) {
            this.f234a.add(btVar);
        }
    }
}
