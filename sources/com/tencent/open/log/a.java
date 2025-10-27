package com.tencent.open.log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/* loaded from: classes6.dex */
public class a extends Tracer implements Handler.Callback {

    /* renamed from: a, reason: collision with root package name */
    private b f20598a;

    /* renamed from: b, reason: collision with root package name */
    private FileWriter f20599b;

    /* renamed from: c, reason: collision with root package name */
    private FileWriter f20600c;

    /* renamed from: d, reason: collision with root package name */
    private File f20601d;

    /* renamed from: e, reason: collision with root package name */
    private File f20602e;

    /* renamed from: f, reason: collision with root package name */
    private char[] f20603f;

    /* renamed from: g, reason: collision with root package name */
    private volatile f f20604g;

    /* renamed from: h, reason: collision with root package name */
    private volatile f f20605h;

    /* renamed from: i, reason: collision with root package name */
    private volatile f f20606i;

    /* renamed from: j, reason: collision with root package name */
    private volatile f f20607j;

    /* renamed from: k, reason: collision with root package name */
    private volatile boolean f20608k;

    /* renamed from: l, reason: collision with root package name */
    private HandlerThread f20609l;

    /* renamed from: m, reason: collision with root package name */
    private Handler f20610m;

    public a(b bVar) {
        this(c.f20622b, true, g.f20642a, bVar);
    }

    private void f() {
        if (Thread.currentThread() == this.f20609l && !this.f20608k) {
            this.f20608k = true;
            j();
            try {
                try {
                    this.f20607j.a(g(), this.f20603f);
                } catch (IOException e2) {
                    SLog.e("FileTracer", "flushBuffer exception", e2);
                }
                this.f20608k = false;
            } finally {
                this.f20607j.b();
            }
        }
    }

    private Writer[] g() throws IOException {
        File[] fileArrA = c().a();
        if (fileArrA != null && fileArrA.length >= 2) {
            File file = fileArrA[0];
            if ((file != null && !file.equals(this.f20601d)) || (this.f20599b == null && file != null)) {
                this.f20601d = file;
                h();
                try {
                    this.f20599b = new FileWriter(this.f20601d, true);
                } catch (IOException unused) {
                    this.f20599b = null;
                    SLog.e(SLog.TAG, "-->obtainFileWriter() old log file permission denied");
                }
            }
            File file2 = fileArrA[1];
            if ((file2 != null && !file2.equals(this.f20602e)) || (this.f20600c == null && file2 != null)) {
                this.f20602e = file2;
                i();
                try {
                    this.f20600c = new FileWriter(this.f20602e, true);
                } catch (IOException unused2) {
                    this.f20600c = null;
                    SLog.e(SLog.TAG, "-->obtainFileWriter() app specific file permission denied");
                }
            }
        }
        return new Writer[]{this.f20599b, this.f20600c};
    }

    private void h() throws IOException {
        try {
            FileWriter fileWriter = this.f20599b;
            if (fileWriter != null) {
                fileWriter.flush();
                this.f20599b.close();
            }
        } catch (IOException e2) {
            SLog.e(SLog.TAG, "-->closeFileWriter() exception:", e2);
        }
    }

    private void i() throws IOException {
        try {
            FileWriter fileWriter = this.f20600c;
            if (fileWriter != null) {
                fileWriter.flush();
                this.f20600c.close();
            }
        } catch (IOException e2) {
            SLog.e(SLog.TAG, "-->closeAppSpecificFileWriter() exception:", e2);
        }
    }

    private void j() {
        synchronized (this) {
            if (this.f20606i == this.f20604g) {
                this.f20606i = this.f20605h;
                this.f20607j = this.f20604g;
            } else {
                this.f20606i = this.f20604g;
                this.f20607j = this.f20605h;
            }
        }
    }

    public void a() {
        if (this.f20610m.hasMessages(1024)) {
            this.f20610m.removeMessages(1024);
        }
        this.f20610m.sendEmptyMessage(1024);
    }

    public void b() {
        h();
        i();
        this.f20609l.quit();
    }

    public b c() {
        return this.f20598a;
    }

    @Override // com.tencent.open.log.Tracer
    public void doTrace(int i2, Thread thread, long j2, String str, String str2, Throwable th) {
        a(e().a(i2, thread, j2, str, str2, th));
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 1024) {
            return true;
        }
        f();
        return true;
    }

    public a(int i2, boolean z2, g gVar, b bVar) {
        super(i2, z2, gVar);
        this.f20608k = false;
        a(bVar);
        this.f20604g = new f();
        this.f20605h = new f();
        this.f20606i = this.f20604g;
        this.f20607j = this.f20605h;
        this.f20603f = new char[bVar.d()];
        HandlerThread handlerThread = new HandlerThread(bVar.c(), bVar.f());
        this.f20609l = handlerThread;
        handlerThread.start();
        if (!this.f20609l.isAlive() || this.f20609l.getLooper() == null) {
            return;
        }
        this.f20610m = new Handler(this.f20609l.getLooper(), this);
    }

    private void a(String str) {
        this.f20606i.a(str);
        if (this.f20606i.a() >= c().d()) {
            a();
        }
    }

    public void a(b bVar) {
        this.f20598a = bVar;
    }
}
