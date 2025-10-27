package io.crossbar.autobahn.websocket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import c.e;
import c.h;
import com.yikaobang.yixue.R2;
import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.utils.TLSSocketFactory;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.interfaces.IWebSocket;
import io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.messages.BinaryMessage;
import io.crossbar.autobahn.websocket.messages.CannotConnect;
import io.crossbar.autobahn.websocket.messages.ClientHandshake;
import io.crossbar.autobahn.websocket.messages.Close;
import io.crossbar.autobahn.websocket.messages.ConnectionLost;
import io.crossbar.autobahn.websocket.messages.Error;
import io.crossbar.autobahn.websocket.messages.Ping;
import io.crossbar.autobahn.websocket.messages.Pong;
import io.crossbar.autobahn.websocket.messages.ProtocolViolation;
import io.crossbar.autobahn.websocket.messages.Quit;
import io.crossbar.autobahn.websocket.messages.RawTextMessage;
import io.crossbar.autobahn.websocket.messages.ServerError;
import io.crossbar.autobahn.websocket.messages.ServerHandshake;
import io.crossbar.autobahn.websocket.messages.TextMessage;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;
import io.crossbar.autobahn.websocket.types.WebSocketOptions;
import io.crossbar.autobahn.websocket.utils.IPUtils;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes8.dex */
public class WebSocketConnection implements IWebSocket {

    /* renamed from: a, reason: collision with root package name */
    public Handler f27159a;

    /* renamed from: b, reason: collision with root package name */
    public WebSocketReader f27160b;

    /* renamed from: c, reason: collision with root package name */
    public WebSocketWriter f27161c;

    /* renamed from: d, reason: collision with root package name */
    public HandlerThread f27162d;

    /* renamed from: e, reason: collision with root package name */
    public Socket f27163e;

    /* renamed from: f, reason: collision with root package name */
    public URI f27164f;

    /* renamed from: g, reason: collision with root package name */
    public String f27165g;

    /* renamed from: h, reason: collision with root package name */
    public String f27166h;

    /* renamed from: i, reason: collision with root package name */
    public String f27167i;

    /* renamed from: j, reason: collision with root package name */
    public int f27168j;

    /* renamed from: k, reason: collision with root package name */
    public String f27169k;

    /* renamed from: l, reason: collision with root package name */
    public String f27170l;

    /* renamed from: m, reason: collision with root package name */
    public String[] f27171m;

    /* renamed from: n, reason: collision with root package name */
    public Map<String, String> f27172n;

    /* renamed from: o, reason: collision with root package name */
    public IWebSocketConnectionHandler f27173o;

    /* renamed from: p, reason: collision with root package name */
    public WebSocketOptions f27174p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f27175q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f27176r;

    /* renamed from: s, reason: collision with root package name */
    public boolean f27177s;

    /* renamed from: t, reason: collision with root package name */
    public ScheduledExecutorService f27178t;

    /* renamed from: u, reason: collision with root package name */
    public ScheduledFuture<?> f27179u;

    /* renamed from: v, reason: collision with root package name */
    public final Runnable f27180v = new AnonymousClass1();

    /* renamed from: x, reason: collision with root package name */
    public static final String f27158x = "WebSocketConnection";

    /* renamed from: w, reason: collision with root package name */
    public static final IABLogger f27157w = ABLogger.a(f27158x);

    /* renamed from: io.crossbar.autobahn.websocket.WebSocketConnection$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            if (WebSocketConnection.this.f27160b.b() < WebSocketConnection.this.f27174p.a()) {
                return;
            }
            WebSocketConnection.this.a(new ConnectionLost("AutoPing timed out."));
        }

        @Override // java.lang.Runnable
        public void run() {
            if (WebSocketConnection.this.f27160b == null || WebSocketConnection.this.f27160b.b() < WebSocketConnection.this.f27174p.a() - 1) {
                return;
            }
            WebSocketConnection.this.a();
            WebSocketConnection.this.f27178t.schedule(new Runnable() { // from class: io.crossbar.autobahn.websocket.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f27225c.a();
                }
            }, WebSocketConnection.this.f27174p.b(), TimeUnit.SECONDS);
        }
    }

    public class WebSocketConnector extends Thread {
        public WebSocketConnector() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws NoSuchAlgorithmException, IOException, NumberFormatException, CertificateException {
            Thread.currentThread().setName("WebSocketConnector");
            h.a(WebSocketConnection.f27158x, "connector to connect start");
            try {
                if (WebSocketConnection.this.f27165g.equals("wss")) {
                    WebSocketConnection.this.f27163e = SSLSocketFactory.getDefault().createSocket();
                    KeyStore.getInstance(KeyStore.getDefaultType()).load(null, null);
                    WebSocketConnection.this.f27163e = new TLSSocketFactory().createSocket();
                } else {
                    WebSocketConnection.this.f27163e = SocketFactory.getDefault().createSocket();
                }
                if (WebSocketConnection.this.f27174p.j() != null) {
                    WebSocketConnection webSocketConnection = WebSocketConnection.this;
                    webSocketConnection.a(webSocketConnection.f27163e, WebSocketConnection.this.f27174p.j());
                }
                h.a(WebSocketConnection.f27158x, "socket call connect start host: " + WebSocketConnection.this.f27167i + " ip: " + WebSocketConnection.this.f27166h + " port: " + WebSocketConnection.this.f27168j);
                if (TextUtils.isEmpty(WebSocketConnection.this.f27166h)) {
                    h.a(WebSocketConnection.f27158x, "socket connect with URLHost: " + WebSocketConnection.this.f27167i);
                    WebSocketConnection.this.f27163e.connect(new InetSocketAddress(WebSocketConnection.this.f27167i, WebSocketConnection.this.f27168j), WebSocketConnection.this.f27174p.h());
                } else {
                    h.a(WebSocketConnection.f27158x, "socket connect with ip: " + WebSocketConnection.this.f27166h);
                    Inet4Address inet4AddressA = IPUtils.a(WebSocketConnection.this.f27166h, WebSocketConnection.this.f27167i);
                    h.a(WebSocketConnection.f27158x, "inet4Address: " + inet4AddressA);
                    WebSocketConnection.this.f27163e.connect(new InetSocketAddress(inet4AddressA, WebSocketConnection.this.f27168j), WebSocketConnection.this.f27174p.h());
                }
                h.a(WebSocketConnection.f27158x, "socket call connect finish");
                h.a(WebSocketConnection.f27158x, "socket setSoTimeout");
                WebSocketConnection.this.f27163e.setSoTimeout(WebSocketConnection.this.f27174p.i());
                h.a(WebSocketConnection.f27158x, "socket setTcpNoDelay");
                WebSocketConnection.this.f27163e.setTcpNoDelay(WebSocketConnection.this.f27174p.k());
            } catch (IOException e2) {
                WebSocketConnection.this.a(new CannotConnect(e2.getMessage()));
                return;
            } catch (KeyStoreException e3) {
                e3.printStackTrace();
                h.a(WebSocketConnection.f27158x, e.a(e3.getCause()));
            } catch (NoSuchAlgorithmException e4) {
                e4.printStackTrace();
                h.a(WebSocketConnection.f27158x, e.a(e4.getCause()));
            } catch (CertificateException e5) {
                e5.printStackTrace();
                h.a(WebSocketConnection.f27158x, e.a(e5.getCause()));
            }
            h.a(WebSocketConnection.f27158x, "mExecutor: " + WebSocketConnection.this.f27178t);
            if (WebSocketConnection.this.f27178t == null || WebSocketConnection.this.f27178t.isShutdown()) {
                WebSocketConnection.this.f27178t = Executors.newSingleThreadScheduledExecutor();
                h.a(WebSocketConnection.f27158x, "newSingleThreadScheduledExecutor :" + WebSocketConnection.this.f27178t);
            }
            h.a(WebSocketConnection.f27158x, "check is Connected");
            if (!WebSocketConnection.this.b()) {
                WebSocketConnection.this.a(new CannotConnect("Could not connect to WebSocket server"));
                return;
            }
            h.a(WebSocketConnection.f27158x, "is connected now to create reader,writer");
            try {
                h.a(WebSocketConnection.f27158x, "SSLoutputstream" + WebSocketConnection.this.f27163e.getOutputStream());
                WebSocketConnection.this.k();
                WebSocketConnection.this.j();
                ClientHandshake clientHandshake = new ClientHandshake(WebSocketConnection.this.f27167i + ":" + WebSocketConnection.this.f27168j);
                clientHandshake.f27237b = WebSocketConnection.this.f27169k;
                clientHandshake.f27238c = WebSocketConnection.this.f27170l;
                clientHandshake.f27240e = WebSocketConnection.this.f27171m;
                clientHandshake.f27241f = WebSocketConnection.this.f27172n;
                WebSocketConnection.this.f27161c.a((Object) clientHandshake);
                WebSocketConnection.this.f27176r = true;
                h.a(WebSocketConnection.f27158x, "is create reader,writer finish");
            } catch (Exception e6) {
                WebSocketConnection.this.a(new Error(e6));
            }
        }

        public /* synthetic */ WebSocketConnector(WebSocketConnection webSocketConnection, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public WebSocketConnection() {
        f27157w.b("WebSocketConnection Created");
        i();
        this.f27175q = false;
        this.f27176r = false;
    }

    public final void b(Object obj) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void l() throws IOException {
        if (b()) {
            try {
                this.f27163e.close();
            } catch (IOException e2) {
                e2.printStackTrace();
                h.a(f27158x, e.a(e2.getCause()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void m() {
        f27157w.b("Reconnecting...");
        n();
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void d() {
        a(1000);
    }

    public final void f() throws InterruptedException {
        a(false);
        h();
        if (b()) {
            try {
                g();
            } catch (IOException | InterruptedException e2) {
                f27157w.b(e2.getMessage(), e2);
            }
        }
        a(true);
        this.f27177s = false;
    }

    public final void g() throws InterruptedException, IOException {
        Thread thread = new Thread(new Runnable() { // from class: io.crossbar.autobahn.websocket.a
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f27223c.l();
            }
        });
        thread.start();
        thread.join();
    }

    public final void h() throws InterruptedException {
        WebSocketWriter webSocketWriter = this.f27161c;
        if (webSocketWriter == null) {
            f27157w.b("mWriter already NULL");
            return;
        }
        webSocketWriter.a(new Quit());
        try {
            this.f27162d.join();
        } catch (InterruptedException e2) {
            f27157w.b(e2.getMessage(), e2);
        }
    }

    public final void i() {
        this.f27159a = new Handler(Looper.getMainLooper()) { // from class: io.crossbar.autobahn.websocket.WebSocketConnection.2
            @Override // android.os.Handler
            public void handleMessage(Message message) throws InterruptedException {
                if (WebSocketConnection.this.f27177s) {
                    WebSocketConnection.f27157w.b("onClose called already, ignore message.");
                    return;
                }
                Object obj = message.obj;
                if (obj instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) obj;
                    if (WebSocketConnection.this.f27173o != null) {
                        WebSocketConnection.this.f27173o.a(textMessage.f27255a);
                        return;
                    } else {
                        WebSocketConnection.f27157w.b("could not call onTextMessage() .. handler already NULL");
                        return;
                    }
                }
                if (obj instanceof RawTextMessage) {
                    RawTextMessage rawTextMessage = (RawTextMessage) obj;
                    if (WebSocketConnection.this.f27173o != null) {
                        WebSocketConnection.this.f27173o.a(rawTextMessage.f27250a, false);
                        return;
                    } else {
                        WebSocketConnection.f27157w.b("could not call onRawTextMessage() .. handler already NULL");
                        return;
                    }
                }
                if (obj instanceof BinaryMessage) {
                    BinaryMessage binaryMessage = (BinaryMessage) obj;
                    if (WebSocketConnection.this.f27173o != null) {
                        WebSocketConnection.this.f27173o.a(binaryMessage.f27234a, true);
                        return;
                    } else {
                        WebSocketConnection.f27157w.b("could not call onBinaryMessage() .. handler already NULL");
                        return;
                    }
                }
                if (obj instanceof Ping) {
                    Ping ping = (Ping) obj;
                    WebSocketConnection.f27157w.b("WebSockets Ping received");
                    if (ping.f27247a == null) {
                        WebSocketConnection.this.f27173o.c();
                        return;
                    } else {
                        WebSocketConnection.this.f27173o.a(ping.f27247a);
                        return;
                    }
                }
                if (obj instanceof Pong) {
                    Pong pong = (Pong) obj;
                    if (pong.f27248a == null) {
                        WebSocketConnection.this.f27173o.b();
                    } else {
                        WebSocketConnection.this.f27173o.b(pong.f27248a);
                    }
                    WebSocketConnection.f27157w.b("WebSockets Pong received");
                    return;
                }
                if (obj instanceof Close) {
                    Close close = (Close) obj;
                    int i2 = close.f27242a == 1000 ? 1 : 3;
                    if (close.f27244c) {
                        WebSocketConnection.f27157w.b("WebSockets Close received (" + close.f27242a + " - " + close.f27243b + ")");
                        WebSocketConnection.this.f();
                        WebSocketConnection.this.c(i2, close.f27243b);
                        return;
                    }
                    if (WebSocketConnection.this.f27175q) {
                        WebSocketConnection.this.a(false);
                        WebSocketConnection.this.f27161c.a((Object) new Close(1000, true));
                        WebSocketConnection.this.f27175q = false;
                        return;
                    }
                    WebSocketConnection.f27157w.b("WebSockets Close received (" + close.f27242a + " - " + close.f27243b + ")");
                    WebSocketConnection.this.f();
                    WebSocketConnection.this.c(i2, close.f27243b);
                    return;
                }
                if (obj instanceof ServerHandshake) {
                    ServerHandshake serverHandshake = (ServerHandshake) obj;
                    WebSocketConnection.f27157w.b("opening handshake received");
                    if (serverHandshake.f27253a) {
                        if (WebSocketConnection.this.f27173o == null) {
                            WebSocketConnection.f27157w.b("could not call onOpen() .. handler already NULL");
                            return;
                        }
                        if (WebSocketConnection.this.f27174p.a() > 0) {
                            WebSocketConnection webSocketConnection = WebSocketConnection.this;
                            webSocketConnection.f27179u = webSocketConnection.f27178t.scheduleAtFixedRate(WebSocketConnection.this.f27180v, 0L, WebSocketConnection.this.f27174p.a(), TimeUnit.SECONDS);
                        }
                        String str = (String) WebSocketConnection.this.a(serverHandshake.f27254b, "Sec-WebSocket-Protocol", (String) null);
                        WebSocketConnection.this.f27173o.a(WebSocketConnection.this);
                        WebSocketConnection.this.f27173o.a(new ConnectionResponse(str));
                        WebSocketConnection.this.f27173o.a();
                        WebSocketConnection.f27157w.b("onOpen() called, ready to rock.");
                        return;
                    }
                    return;
                }
                if (obj instanceof CannotConnect) {
                    WebSocketConnection.this.b(2, ((CannotConnect) obj).f27235a);
                    return;
                }
                if (obj instanceof ConnectionLost) {
                    WebSocketConnection.this.b(3, ((ConnectionLost) obj).f27245a);
                    return;
                }
                if (obj instanceof ProtocolViolation) {
                    WebSocketConnection.this.b(4, "WebSockets protocol violation");
                    return;
                }
                if (obj instanceof Error) {
                    WebSocketConnection.this.b(5, "WebSockets internal error (" + ((Error) obj).f27246a.toString() + ")");
                    return;
                }
                if (!(obj instanceof ServerError)) {
                    WebSocketConnection.this.b(obj);
                    return;
                }
                ServerError serverError = (ServerError) obj;
                WebSocketConnection.this.b(6, "Server error " + serverError.f27251a + " (" + serverError.f27252b + ")");
            }
        };
    }

    public final void j() throws IOException {
        IABLogger iABLogger = f27157w;
        iABLogger.b("WS reader created started");
        this.f27160b = new WebSocketReader(this.f27159a, this.f27163e, this.f27174p, WebSocketReader.f27186q);
        iABLogger.b("WS reader created finished");
        this.f27160b.start();
        iABLogger.b("WS reader created and started");
    }

    public final void k() throws IOException {
        IABLogger iABLogger = f27157w;
        iABLogger.b("WS writer start create");
        HandlerThread handlerThread = new HandlerThread(WebSocketWriter.f27215j);
        this.f27162d = handlerThread;
        handlerThread.start();
        this.f27161c = new WebSocketWriter(this.f27162d.getLooper(), this.f27159a, this.f27163e, this.f27174p);
        iABLogger.b("WS writer created and started");
    }

    public boolean n() {
        if (b() || this.f27164f == null) {
            return false;
        }
        this.f27177s = false;
        new WebSocketConnector(this, null).start();
        return true;
    }

    public final boolean o() {
        int iG = this.f27174p.g();
        boolean z2 = this.f27175q && this.f27176r && iG > 0;
        h.a(f27158x, "web socket inner scheduleReconnect " + z2 + "mActive: " + this.f27175q + " mPrevConnected: " + this.f27176r + " interval: " + iG);
        if (z2) {
            f27157w.b("Reconnection scheduled");
            this.f27159a.postDelayed(new Runnable() { // from class: io.crossbar.autobahn.websocket.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f27224c.m();
                }
            }, iG);
        }
        return z2;
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void c() {
        this.f27161c.a((Object) new Pong());
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void b(byte[] bArr) {
        this.f27161c.a((Object) new Pong(bArr));
    }

    public final void c(int i2, String str) {
        boolean zO;
        if (i2 == 2 || i2 == 3) {
            h.a(f27158x, "web socket start reconnect ");
            zO = o();
        } else {
            zO = false;
        }
        ScheduledExecutorService scheduledExecutorService = this.f27178t;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
        IWebSocketConnectionHandler iWebSocketConnectionHandler = this.f27173o;
        if (iWebSocketConnectionHandler != null) {
            try {
                if (zO) {
                    iWebSocketConnectionHandler.a(7, str);
                } else {
                    iWebSocketConnectionHandler.a(i2, str);
                }
            } catch (Exception e2) {
                f27157w.b(e2.getMessage(), e2);
            }
        } else {
            f27157w.b("mWsHandler already NULL");
        }
        this.f27177s = true;
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public boolean b() {
        Socket socket = this.f27163e;
        return (socket == null || !socket.isConnected() || this.f27163e.isClosed()) ? false : true;
    }

    public void b(int i2, String str) throws InterruptedException {
        h.a(f27158x, "fail connection [code = " + i2 + ", reason = " + str);
        IABLogger iABLogger = f27157w;
        iABLogger.b("fail connection [code = " + i2 + ", reason = " + str);
        a(false);
        h();
        if (b()) {
            try {
                g();
            } catch (IOException | InterruptedException e2) {
                f27157w.b(e2.getMessage(), e2);
            }
        } else {
            iABLogger.b("Socket already closed");
        }
        a(true);
        c(i2, str);
        f27157w.b("Worker threads stopped");
    }

    public final void a(Object obj) {
        Message messageObtainMessage = this.f27159a.obtainMessage();
        messageObtainMessage.obj = obj;
        this.f27159a.sendMessage(messageObtainMessage);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str) {
        this.f27161c.a((Object) new TextMessage(str));
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(byte[] bArr, boolean z2) {
        if (z2) {
            this.f27161c.a((Object) new BinaryMessage(bArr));
        } else {
            this.f27161c.a((Object) new RawTextMessage(bArr));
        }
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a() {
        this.f27161c.a((Object) new Ping());
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(byte[] bArr) {
        this.f27161c.a((Object) new Ping(bArr));
    }

    public final void a(boolean z2) throws InterruptedException {
        WebSocketReader webSocketReader = this.f27160b;
        if (webSocketReader != null) {
            webSocketReader.e();
            if (z2) {
                try {
                    this.f27160b.join();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    h.a(f27158x, e.a(e2.getCause()));
                }
            }
            this.f27160b = null;
            return;
        }
        f27157w.b("mReader already NULL");
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str, IWebSocketConnectionHandler iWebSocketConnectionHandler) throws WebSocketException {
        a(str, null, null, iWebSocketConnectionHandler, null, null);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions) throws WebSocketException {
        a(str, null, null, iWebSocketConnectionHandler, webSocketOptions, null);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str, String str2, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions) throws WebSocketException {
        a(str, str2, null, iWebSocketConnectionHandler, webSocketOptions, null);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str, String[] strArr, IWebSocketConnectionHandler iWebSocketConnectionHandler) throws WebSocketException {
        a(str, null, strArr, iWebSocketConnectionHandler, new WebSocketOptions(), null);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(String str, String str2, String[] strArr, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions, Map<String, String> map) throws WebSocketException {
        h.a(f27158x, "connect start");
        if (!b()) {
            try {
                URI uri = new URI(str);
                this.f27164f = uri;
                if (!uri.getScheme().equals("ws") && !this.f27164f.getScheme().equals("wss")) {
                    throw new WebSocketException("unsupported scheme for WebSockets URI");
                }
                this.f27166h = str2;
                this.f27165g = this.f27164f.getScheme();
                if (this.f27164f.getPort() == -1) {
                    if (this.f27165g.equals("ws")) {
                        this.f27168j = 80;
                    } else {
                        this.f27168j = R2.attr.banner_indicator_selected_color;
                    }
                } else {
                    this.f27168j = this.f27164f.getPort();
                }
                if (this.f27164f.getHost() != null) {
                    this.f27167i = this.f27164f.getHost();
                    if (this.f27164f.getRawPath() != null && !this.f27164f.getRawPath().equals("")) {
                        this.f27169k = this.f27164f.getRawPath();
                    } else {
                        this.f27169k = "/";
                    }
                    AnonymousClass1 anonymousClass1 = null;
                    if (this.f27164f.getRawQuery() != null && !this.f27164f.getRawQuery().equals("")) {
                        this.f27170l = this.f27164f.getRawQuery();
                    } else {
                        this.f27170l = null;
                    }
                    this.f27171m = strArr;
                    this.f27172n = map;
                    this.f27173o = iWebSocketConnectionHandler;
                    if (this.f27174p == null) {
                        if (webSocketOptions == null) {
                            this.f27174p = new WebSocketOptions();
                        } else {
                            this.f27174p = new WebSocketOptions(webSocketOptions);
                        }
                    } else if (webSocketOptions != null) {
                        this.f27174p = new WebSocketOptions(webSocketOptions);
                    }
                    this.f27175q = true;
                    this.f27177s = false;
                    h.a(f27158x, "connect method finish ,start");
                    new WebSocketConnector(this, anonymousClass1).start();
                    return;
                }
                throw new WebSocketException("no host specified in WebSockets URI");
            } catch (URISyntaxException unused) {
                throw new WebSocketException("invalid WebSockets URI");
            }
        }
        throw new WebSocketException("already connected");
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(int i2) {
        a(i2, (String) null);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocket
    public void a(int i2, String str) {
        WebSocketWriter webSocketWriter = this.f27161c;
        if (webSocketWriter != null) {
            webSocketWriter.a((Object) new Close(i2, str));
        } else {
            f27157w.b("could not send Close .. writer already NULL");
        }
        this.f27177s = false;
        this.f27175q = false;
        this.f27176r = false;
    }

    public final <T> T a(Map<?, ?> map, Object obj, T t2) {
        return map.containsKey(obj) ? (T) map.get(obj) : t2;
    }

    public void a(WebSocketOptions webSocketOptions) {
        WebSocketOptions webSocketOptions2 = this.f27174p;
        if (webSocketOptions2 == null) {
            this.f27174p = new WebSocketOptions(webSocketOptions);
            return;
        }
        webSocketOptions2.a(webSocketOptions.a());
        this.f27174p.b(webSocketOptions.b());
        ScheduledFuture<?> scheduledFuture = this.f27179u;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        if (this.f27178t == null) {
            this.f27178t = Executors.newSingleThreadScheduledExecutor();
        }
        if (this.f27174p.a() > 0) {
            this.f27179u = this.f27178t.scheduleAtFixedRate(this.f27180v, 0L, this.f27174p.a(), TimeUnit.SECONDS);
        }
    }

    public final void a(Socket socket, String[] strArr) {
        if (socket == null || !(socket instanceof SSLSocket)) {
            return;
        }
        ((SSLSocket) socket).setEnabledProtocols(strArr);
    }
}
