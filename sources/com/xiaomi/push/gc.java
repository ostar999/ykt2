package com.xiaomi.push;

import android.util.Pair;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;
import io.socket.client.Socket;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public abstract class gc {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f24918a = new AtomicInteger(0);

    /* renamed from: a, reason: collision with other field name */
    public static boolean f470a;

    /* renamed from: a, reason: collision with other field name */
    protected gd f473a;

    /* renamed from: a, reason: collision with other field name */
    protected XMPushService f475a;

    /* renamed from: a, reason: collision with other field name */
    protected int f471a = 0;

    /* renamed from: a, reason: collision with other field name */
    protected long f472a = -1;

    /* renamed from: b, reason: collision with other field name */
    protected volatile long f480b = 0;

    /* renamed from: c, reason: collision with other field name */
    protected volatile long f483c = 0;

    /* renamed from: a, reason: collision with other field name */
    private LinkedList<Pair<Integer, Long>> f478a = new LinkedList<>();

    /* renamed from: a, reason: collision with other field name */
    private final Collection<gf> f477a = new CopyOnWriteArrayList();

    /* renamed from: a, reason: collision with other field name */
    protected final Map<gh, a> f479a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with other field name */
    protected final Map<gh, a> f482b = new ConcurrentHashMap();

    /* renamed from: a, reason: collision with other field name */
    protected go f474a = null;

    /* renamed from: a, reason: collision with other field name */
    protected String f476a = "";

    /* renamed from: b, reason: collision with other field name */
    protected String f481b = "";

    /* renamed from: c, reason: collision with root package name */
    private int f24920c = 2;

    /* renamed from: b, reason: collision with root package name */
    protected final int f24919b = f24918a.getAndIncrement();

    /* renamed from: e, reason: collision with root package name */
    private long f24922e = 0;

    /* renamed from: d, reason: collision with root package name */
    protected long f24921d = 0;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private gh f24923a;

        /* renamed from: a, reason: collision with other field name */
        private gp f484a;

        public a(gh ghVar, gp gpVar) {
            this.f24923a = ghVar;
            this.f484a = gpVar;
        }

        public void a(fv fvVar) {
            this.f24923a.a(fvVar);
        }

        public void a(gt gtVar) {
            gp gpVar = this.f484a;
            if (gpVar == null || gpVar.mo234a(gtVar)) {
                this.f24923a.a(gtVar);
            }
        }
    }

    static {
        f470a = false;
        try {
            f470a = Boolean.getBoolean("smack.debugEnabled");
        } catch (Exception unused) {
        }
        gi.m460a();
    }

    public gc(XMPushService xMPushService, gd gdVar) throws ClassNotFoundException {
        this.f473a = gdVar;
        this.f475a = xMPushService;
        c();
    }

    private String a(int i2) {
        return i2 == 1 ? "connected" : i2 == 0 ? Socket.EVENT_CONNECTING : i2 == 2 ? "disconnected" : "unknown";
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m447a(int i2) {
        synchronized (this.f478a) {
            if (i2 == 1) {
                this.f478a.clear();
            } else {
                this.f478a.add(new Pair<>(Integer.valueOf(i2), Long.valueOf(System.currentTimeMillis())));
                if (this.f478a.size() > 6) {
                    this.f478a.remove(0);
                }
            }
        }
    }

    public int a() {
        return this.f471a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m448a() {
        return this.f483c;
    }

    /* renamed from: a, reason: collision with other method in class */
    public gd m449a() {
        return this.f473a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String mo450a() {
        return this.f473a.c();
    }

    public void a(int i2, int i3, Exception exc) {
        int i4 = this.f24920c;
        if (i2 != i4) {
            com.xiaomi.channel.commonutils.logger.b.m117a(String.format("update the connection status. %1$s -> %2$s : %3$s ", a(i4), a(i2), com.xiaomi.push.service.ax.a(i3)));
        }
        if (as.b(this.f475a)) {
            m447a(i2);
        }
        if (i2 == 1) {
            this.f475a.a(10);
            if (this.f24920c != 0) {
                com.xiaomi.channel.commonutils.logger.b.m117a("try set connected while not connecting.");
            }
            this.f24920c = i2;
            Iterator<gf> it = this.f477a.iterator();
            while (it.hasNext()) {
                it.next().a(this);
            }
            return;
        }
        if (i2 == 0) {
            if (this.f24920c != 2) {
                com.xiaomi.channel.commonutils.logger.b.m117a("try set connecting while not disconnected.");
            }
            this.f24920c = i2;
            Iterator<gf> it2 = this.f477a.iterator();
            while (it2.hasNext()) {
                it2.next().b(this);
            }
            return;
        }
        if (i2 == 2) {
            this.f475a.a(10);
            int i5 = this.f24920c;
            if (i5 == 0) {
                Iterator<gf> it3 = this.f477a.iterator();
                while (it3.hasNext()) {
                    it3.next().a(this, exc == null ? new CancellationException("disconnect while connecting") : exc);
                }
            } else if (i5 == 1) {
                Iterator<gf> it4 = this.f477a.iterator();
                while (it4.hasNext()) {
                    it4.next().a(this, i3, exc);
                }
            }
            this.f24920c = i2;
        }
    }

    public void a(gf gfVar) {
        if (gfVar == null || this.f477a.contains(gfVar)) {
            return;
        }
        this.f477a.add(gfVar);
    }

    public void a(gh ghVar, gp gpVar) {
        if (ghVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.f479a.put(ghVar, new a(ghVar, gpVar));
    }

    public abstract void a(gt gtVar);

    public abstract void a(at.b bVar);

    public synchronized void a(String str) {
        if (this.f24920c == 0) {
            com.xiaomi.channel.commonutils.logger.b.m117a("setChallenge hash = " + ax.a(str).substring(0, 8));
            this.f476a = str;
            a(1, 0, null);
        } else {
            com.xiaomi.channel.commonutils.logger.b.m117a("ignore setChallenge because connection was disconnected");
        }
    }

    public abstract void a(String str, String str2);

    public abstract void a(fv[] fvVarArr);

    /* renamed from: a */
    public boolean mo445a() {
        return false;
    }

    public synchronized boolean a(long j2) {
        return this.f24922e >= j2;
    }

    public int b() {
        return this.f24920c;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m451b() {
        return this.f473a.b();
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m452b() {
        b(0, (Exception) null);
    }

    public abstract void b(int i2, Exception exc);

    public abstract void b(fv fvVar);

    public void b(gf gfVar) {
        this.f477a.remove(gfVar);
    }

    public void b(gh ghVar, gp gpVar) {
        if (ghVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.f482b.put(ghVar, new a(ghVar, gpVar));
    }

    public abstract void b(boolean z2);

    /* renamed from: b, reason: collision with other method in class */
    public boolean m453b() {
        return this.f24920c == 0;
    }

    public void c() throws ClassNotFoundException {
        String property;
        if (this.f473a.m458a() && this.f474a == null) {
            Class<?> cls = null;
            try {
                property = System.getProperty("smack.debuggerClass");
            } catch (Throwable unused) {
                property = null;
            }
            if (property != null) {
                try {
                    cls = Class.forName(property);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (cls == null) {
                this.f474a = new bi(this);
                return;
            }
            try {
                this.f474a = (go) cls.getConstructor(gc.class, Writer.class, Reader.class).newInstance(this);
            } catch (Exception e3) {
                throw new IllegalArgumentException("Can't initialize the configured debugger!", e3);
            }
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m454c() {
        return this.f24920c == 1;
    }

    public synchronized void d() {
        this.f24922e = System.currentTimeMillis();
    }

    /* renamed from: d, reason: collision with other method in class */
    public synchronized boolean m455d() {
        return System.currentTimeMillis() - this.f24922e < ((long) gi.a());
    }

    public void e() {
        synchronized (this.f478a) {
            this.f478a.clear();
        }
    }

    /* renamed from: e, reason: collision with other method in class */
    public synchronized boolean m456e() {
        return System.currentTimeMillis() - this.f24921d < ((long) (gi.a() << 1));
    }
}
