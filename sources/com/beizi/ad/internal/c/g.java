package com.beizi.ad.internal.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
final class g {

    /* renamed from: a, reason: collision with root package name */
    private final AtomicInteger f4093a = new AtomicInteger(0);

    /* renamed from: b, reason: collision with root package name */
    private final String f4094b;

    /* renamed from: c, reason: collision with root package name */
    private volatile e f4095c;

    /* renamed from: d, reason: collision with root package name */
    private final List<b> f4096d;

    /* renamed from: e, reason: collision with root package name */
    private final b f4097e;

    /* renamed from: f, reason: collision with root package name */
    private final c f4098f;

    public static final class a extends Handler implements b {

        /* renamed from: a, reason: collision with root package name */
        private final String f4099a;

        /* renamed from: b, reason: collision with root package name */
        private final List<b> f4100b;

        public a(String str, List<b> list) {
            super(Looper.getMainLooper());
            this.f4099a = str;
            this.f4100b = list;
        }

        @Override // com.beizi.ad.internal.c.b
        public void a(File file, String str, int i2) {
            Message messageObtainMessage = obtainMessage();
            messageObtainMessage.arg1 = i2;
            messageObtainMessage.obj = file;
            sendMessage(messageObtainMessage);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Iterator<b> it = this.f4100b.iterator();
            while (it.hasNext()) {
                it.next().a((File) message.obj, this.f4099a, message.arg1);
            }
        }
    }

    public g(String str, c cVar) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.f4096d = copyOnWriteArrayList;
        this.f4094b = (String) k.a(str);
        this.f4098f = (c) k.a(cVar);
        this.f4097e = new a(str, copyOnWriteArrayList);
    }

    private synchronized void b() throws m {
        this.f4095c = this.f4095c == null ? d() : this.f4095c;
    }

    private synchronized void c() {
        if (this.f4093a.decrementAndGet() <= 0) {
            this.f4095c.a();
            this.f4095c = null;
        }
    }

    private e d() throws m {
        e eVar = new e(new h(this.f4094b, this.f4098f.f4064d), new com.beizi.ad.internal.c.a.b(this.f4098f.a(this.f4094b), this.f4098f.f4063c));
        eVar.a(this.f4097e);
        return eVar;
    }

    public void a(d dVar, Socket socket) throws IOException, m {
        b();
        try {
            this.f4093a.incrementAndGet();
            this.f4095c.a(dVar, socket);
        } finally {
            c();
        }
    }

    public int a() {
        return this.f4093a.get();
    }
}
