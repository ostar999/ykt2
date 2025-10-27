package com.beizi.ad.internal.c;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private final Object f4077a;

    /* renamed from: b, reason: collision with root package name */
    private final ExecutorService f4078b;

    /* renamed from: c, reason: collision with root package name */
    private final Map<String, g> f4079c;

    /* renamed from: d, reason: collision with root package name */
    private final ServerSocket f4080d;

    /* renamed from: e, reason: collision with root package name */
    private final int f4081e;

    /* renamed from: f, reason: collision with root package name */
    private final Thread f4082f;

    /* renamed from: g, reason: collision with root package name */
    private final com.beizi.ad.internal.c.c f4083g;

    /* renamed from: h, reason: collision with root package name */
    private final j f4084h;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private File f4085a;

        /* renamed from: d, reason: collision with root package name */
        private com.beizi.ad.internal.c.b.c f4088d;

        /* renamed from: c, reason: collision with root package name */
        private com.beizi.ad.internal.c.a.a f4087c = new com.beizi.ad.internal.c.a.g(IjkMediaMeta.AV_CH_STEREO_LEFT);

        /* renamed from: b, reason: collision with root package name */
        private com.beizi.ad.internal.c.a.c f4086b = new com.beizi.ad.internal.c.a.f();

        public a(Context context) {
            this.f4088d = com.beizi.ad.internal.c.b.d.a(context);
            this.f4085a = q.a(context);
        }

        private com.beizi.ad.internal.c.c b() {
            return new com.beizi.ad.internal.c.c(this.f4085a, this.f4086b, this.f4087c, this.f4088d);
        }

        public a a(long j2) {
            this.f4087c = new com.beizi.ad.internal.c.a.g(j2);
            return this;
        }

        public f a() {
            return new f(b());
        }
    }

    public final class b implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private final Socket f4090b;

        public b(Socket socket) {
            this.f4090b = socket;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            f.this.a(this.f4090b);
        }
    }

    public final class c implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private final CountDownLatch f4092b;

        public c(CountDownLatch countDownLatch) {
            this.f4092b = countDownLatch;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            this.f4092b.countDown();
            f.this.b();
        }
    }

    private String c(String str) {
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovLyVzOiVkLyVz");
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        return String.format(Locale.US, strA, "127.0.0.1", Integer.valueOf(this.f4081e), n.b(str));
    }

    private File d(String str) {
        com.beizi.ad.internal.c.c cVar = this.f4083g;
        return new File(cVar.f4061a, cVar.f4062b.a(str));
    }

    private g e(String str) throws m {
        g gVar;
        synchronized (this.f4077a) {
            gVar = this.f4079c.get(str);
            if (gVar == null) {
                gVar = new g(str, this.f4083g);
                this.f4079c.put(str, gVar);
            }
        }
        return gVar;
    }

    public boolean b(String str) {
        k.a(str, "Url can't be null!");
        return d(str).exists();
    }

    private f(com.beizi.ad.internal.c.c cVar) throws InterruptedException {
        this.f4077a = new Object();
        this.f4078b = Executors.newFixedThreadPool(8);
        this.f4079c = new ConcurrentHashMap();
        this.f4083g = (com.beizi.ad.internal.c.c) k.a(cVar);
        try {
            ServerSocket serverSocket = new ServerSocket(0, 8, InetAddress.getByName("127.0.0.1"));
            this.f4080d = serverSocket;
            int localPort = serverSocket.getLocalPort();
            this.f4081e = localPort;
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Thread thread = new Thread(new c(countDownLatch));
            this.f4082f = thread;
            thread.start();
            countDownLatch.await();
            this.f4084h = new j("127.0.0.1", localPort);
            HaoboLog.i(HaoboLog.httpProxyCacheServerLogTag, "Proxy cache server started. Is it alive? " + a());
        } catch (IOException | InterruptedException e2) {
            this.f4078b.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socketAccept = this.f4080d.accept();
                HaoboLog.d(HaoboLog.httpProxyCacheServerLogTag, "Accept new socket " + socketAccept);
                this.f4078b.submit(new b(socketAccept));
            } catch (IOException e2) {
                a(new m("Error during waiting connection", e2));
                return;
            }
        }
    }

    public String a(String str) {
        return a(str, true);
    }

    private int c() {
        int iA;
        synchronized (this.f4077a) {
            Iterator<g> it = this.f4079c.values().iterator();
            iA = 0;
            while (it.hasNext()) {
                iA += it.next().a();
            }
        }
        return iA;
    }

    private void d(Socket socket) throws IOException {
        try {
            if (socket.isOutputShutdown()) {
                return;
            }
            socket.shutdownOutput();
        } catch (IOException e2) {
            HaoboLog.w(HaoboLog.httpProxyCacheServerLogTag, "Failed to close socket on proxy side: {}. It seems client have already closed connection.", e2);
        }
    }

    public String a(String str, boolean z2) {
        if (!z2 || !b(str)) {
            return a() ? c(str) : str;
        }
        File fileD = d(str);
        a(fileD);
        return Uri.fromFile(fileD).toString();
    }

    private void e(Socket socket) throws IOException {
        try {
            if (socket.isClosed()) {
                return;
            }
            socket.close();
        } catch (IOException e2) {
            a(new m("Error closing socket", e2));
        }
    }

    private void b(Socket socket) throws IOException {
        c(socket);
        d(socket);
        e(socket);
    }

    private boolean a() {
        return this.f4084h.a(3, 70);
    }

    private void c(Socket socket) throws IOException {
        try {
            if (socket.isInputShutdown()) {
                return;
            }
            socket.shutdownInput();
        } catch (SocketException unused) {
        } catch (IOException e2) {
            a(new m("Error closing socket input stream", e2));
        }
    }

    private void a(File file) {
        try {
            this.f4083g.f4063c.a(file);
        } catch (IOException e2) {
            HaoboLog.e(HaoboLog.httpProxyCacheServerLogTag, "Error touching file " + file, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Socket socket) throws IOException {
        String str;
        StringBuilder sb;
        try {
            try {
                d dVarA = d.a(socket.getInputStream());
                HaoboLog.d(HaoboLog.httpProxyCacheServerLogTag, "Request to cache proxy:" + dVarA);
                String strC = n.c(dVarA.f4071a);
                if (this.f4084h.a(strC)) {
                    this.f4084h.a(socket);
                } else {
                    e(strC).a(dVarA, socket);
                }
                b(socket);
                str = HaoboLog.httpProxyCacheServerLogTag;
                sb = new StringBuilder();
            } catch (m e2) {
                e = e2;
                a(new m("Error processing request", e));
                b(socket);
                str = HaoboLog.httpProxyCacheServerLogTag;
                sb = new StringBuilder();
            } catch (SocketException unused) {
                b(socket);
                str = HaoboLog.httpProxyCacheServerLogTag;
                sb = new StringBuilder();
            } catch (IOException e3) {
                e = e3;
                a(new m("Error processing request", e));
                b(socket);
                str = HaoboLog.httpProxyCacheServerLogTag;
                sb = new StringBuilder();
            }
            sb.append("Opened connections: ");
            sb.append(c());
            HaoboLog.d(str, sb.toString());
        } catch (Throwable th) {
            b(socket);
            HaoboLog.d(HaoboLog.httpProxyCacheServerLogTag, "Opened connections: " + c());
            throw th;
        }
    }

    private void a(Throwable th) {
        HaoboLog.e(HaoboLog.httpProxyCacheServerLogTag, "HttpProxyCacheServer error", th);
    }
}
