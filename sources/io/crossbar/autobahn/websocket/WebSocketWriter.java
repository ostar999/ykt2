package io.crossbar.autobahn.websocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import c.e;
import c.h;
import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.messages.BinaryMessage;
import io.crossbar.autobahn.websocket.messages.ClientHandshake;
import io.crossbar.autobahn.websocket.messages.Close;
import io.crossbar.autobahn.websocket.messages.ConnectionLost;
import io.crossbar.autobahn.websocket.messages.Error;
import io.crossbar.autobahn.websocket.messages.Ping;
import io.crossbar.autobahn.websocket.messages.Pong;
import io.crossbar.autobahn.websocket.messages.Quit;
import io.crossbar.autobahn.websocket.messages.RawTextMessage;
import io.crossbar.autobahn.websocket.messages.TextMessage;
import io.crossbar.autobahn.websocket.types.WebSocketOptions;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.Random;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes8.dex */
class WebSocketWriter extends Handler {

    /* renamed from: i, reason: collision with root package name */
    public static final String f27214i = "\r\n";

    /* renamed from: a, reason: collision with root package name */
    public final Random f27216a;

    /* renamed from: b, reason: collision with root package name */
    public final Handler f27217b;

    /* renamed from: c, reason: collision with root package name */
    public final Looper f27218c;

    /* renamed from: d, reason: collision with root package name */
    public final WebSocketOptions f27219d;

    /* renamed from: e, reason: collision with root package name */
    public OutputStream f27220e;

    /* renamed from: f, reason: collision with root package name */
    public Socket f27221f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f27222g;

    /* renamed from: j, reason: collision with root package name */
    public static final String f27215j = "WebSocketWriter";

    /* renamed from: h, reason: collision with root package name */
    public static final IABLogger f27213h = ABLogger.a(f27215j);

    public WebSocketWriter(Looper looper, Handler handler, Socket socket, WebSocketOptions webSocketOptions) throws IOException {
        super(looper);
        this.f27216a = new Random();
        this.f27218c = looper;
        this.f27217b = handler;
        this.f27219d = webSocketOptions;
        this.f27221f = socket;
        IABLogger iABLogger = f27213h;
        iABLogger.b("WebSocketWriter socket Created");
        iABLogger.b("use BufferedOutputStream stream");
        this.f27220e = new BufferedOutputStream(socket.getOutputStream(), webSocketOptions.d() + 14);
        this.f27222g = true;
        iABLogger.b("WebSocketWriter Created");
    }

    public final void a(String str) throws IOException {
        try {
            this.f27220e.write(str.getBytes("UTF-8"));
        } catch (IOException e2) {
            e2.printStackTrace();
            h.a(f27215j, e.a(e2.getCause()));
        }
    }

    public final void b(Object obj) {
        Message messageObtainMessage = this.f27217b.obtainMessage();
        messageObtainMessage.obj = obj;
        this.f27217b.sendMessage(messageObtainMessage);
    }

    public void c(Object obj) throws WebSocketException, IOException {
        throw new WebSocketException("unknown message received by WebSocketWriter");
    }

    public void d(Object obj) throws WebSocketException, IOException {
        if (obj instanceof TextMessage) {
            a((TextMessage) obj);
            return;
        }
        if (obj instanceof RawTextMessage) {
            a((RawTextMessage) obj);
            return;
        }
        if (obj instanceof BinaryMessage) {
            a((BinaryMessage) obj);
            return;
        }
        if (obj instanceof Ping) {
            a((Ping) obj);
            return;
        }
        if (obj instanceof Pong) {
            a((Pong) obj);
            return;
        }
        if (obj instanceof Close) {
            a((Close) obj);
            return;
        }
        if (obj instanceof ClientHandshake) {
            a((ClientHandshake) obj);
        } else {
            if (!(obj instanceof Quit)) {
                c(obj);
                return;
            }
            this.f27218c.quit();
            this.f27222g = false;
            f27213h.b("Ended");
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws IOException {
        try {
            d(message.obj);
            if (this.f27222g && this.f27221f.isConnected() && !this.f27221f.isClosed()) {
                this.f27220e.flush();
            }
            Object obj = message.obj;
            if (obj instanceof Close) {
                Close close = (Close) obj;
                if (close.f27244c) {
                    b(new Close(close.f27242a, close.f27243b, true));
                }
            }
        } catch (SocketException e2) {
            f27213h.b("run() : SocketException (" + e2.toString() + ")");
            b(new ConnectionLost(null));
        } catch (Exception e3) {
            f27213h.a(e3.getMessage(), e3);
            b(new Error(e3));
        }
    }

    public final void a(byte b3) throws IOException {
        try {
            this.f27220e.write(b3);
        } catch (IOException e2) {
            e2.printStackTrace();
            h.a(f27215j, e.a(e2.getCause()));
        }
    }

    public final String b() {
        byte[] bArr = new byte[16];
        this.f27216a.nextBytes(bArr);
        return Base64.encodeToString(bArr, 2);
    }

    public final void a(byte[] bArr) throws IOException {
        try {
            this.f27220e.write(bArr);
        } catch (IOException e2) {
            e2.printStackTrace();
            h.a(f27215j, e.a(e2.getCause()));
        }
    }

    public void a(Object obj) {
        if (!this.f27222g) {
            f27213h.b("We have already quit, not processing further messages");
            return;
        }
        Message messageObtainMessage = obtainMessage();
        messageObtainMessage.obj = obj;
        sendMessage(messageObtainMessage);
    }

    public final byte[] a() {
        byte[] bArr = new byte[4];
        this.f27216a.nextBytes(bArr);
        return bArr;
    }

    public final void a(ClientHandshake clientHandshake) throws IOException {
        a("GET " + (clientHandshake.f27238c != null ? clientHandshake.f27237b + "?" + clientHandshake.f27238c : clientHandshake.f27237b) + " HTTP/1.1");
        a("\r\n");
        a("Host: " + clientHandshake.f27236a);
        a("\r\n");
        a("Upgrade: WebSocket");
        a("\r\n");
        a("Connection: Upgrade");
        a("\r\n");
        a("Sec-WebSocket-Key: " + b());
        a("\r\n");
        String str = clientHandshake.f27239d;
        if (str != null && !str.equals("")) {
            a("Origin: " + clientHandshake.f27239d);
            a("\r\n");
        }
        String[] strArr = clientHandshake.f27240e;
        if (strArr != null && strArr.length > 0) {
            a("Sec-WebSocket-Protocol: ");
            int i2 = 0;
            while (true) {
                String[] strArr2 = clientHandshake.f27240e;
                if (i2 >= strArr2.length) {
                    break;
                }
                a(strArr2[i2]);
                if (i2 != clientHandshake.f27240e.length - 1) {
                    a(", ");
                }
                i2++;
            }
            a("\r\n");
        }
        a("Sec-WebSocket-Version: 13");
        a("\r\n");
        Map<String, String> map = clientHandshake.f27241f;
        if (map != null) {
            for (String str2 : map.keySet()) {
                a(str2 + ":" + clientHandshake.f27241f.get(str2));
                a("\r\n");
            }
        }
        a("\r\n");
    }

    public final void a(Close close) throws WebSocketException, IOException {
        byte[] bArr;
        if (close.f27242a > 0) {
            String str = close.f27243b;
            if (str == null || str.equals("")) {
                bArr = new byte[2];
            } else {
                byte[] bytes = close.f27243b.getBytes("UTF-8");
                bArr = new byte[bytes.length + 2];
                for (int i2 = 0; i2 < bytes.length; i2++) {
                    bArr[i2 + 2] = bytes[i2];
                }
            }
            if (bArr.length <= 125) {
                int i3 = close.f27242a;
                bArr[0] = (byte) ((i3 >> 8) & 255);
                bArr[1] = (byte) (i3 & 255);
                a(8, true, bArr);
                return;
            }
            throw new WebSocketException("close payload exceeds 125 octets");
        }
        a(8, true, null);
    }

    public final void a(Ping ping) throws WebSocketException, IOException {
        byte[] bArr = ping.f27247a;
        if (bArr != null && bArr.length > 125) {
            throw new WebSocketException("ping payload exceeds 125 octets");
        }
        a(9, true, bArr);
    }

    public final void a(Pong pong) throws WebSocketException, IOException {
        byte[] bArr = pong.f27248a;
        if (bArr != null && bArr.length > 125) {
            throw new WebSocketException("pong payload exceeds 125 octets");
        }
        a(10, true, bArr);
        f27213h.b("WebSockets Pong Sent");
    }

    public final void a(BinaryMessage binaryMessage) throws WebSocketException, IOException {
        if (binaryMessage.f27234a.length <= this.f27219d.e()) {
            a(2, true, binaryMessage.f27234a);
            return;
        }
        throw new WebSocketException("message payload exceeds payload limit");
    }

    public final void a(TextMessage textMessage) throws WebSocketException, IOException {
        byte[] bytes = textMessage.f27255a.getBytes("UTF-8");
        if (bytes.length <= this.f27219d.e()) {
            a(1, true, bytes);
            return;
        }
        throw new WebSocketException("message payload exceeds payload limit");
    }

    public final void a(RawTextMessage rawTextMessage) throws WebSocketException, IOException {
        if (rawTextMessage.f27250a.length <= this.f27219d.e()) {
            a(1, true, rawTextMessage.f27250a);
            return;
        }
        throw new WebSocketException("message payload exceeds payload limit");
    }

    public void a(int i2, boolean z2, byte[] bArr) throws IOException {
        if (bArr != null) {
            a(i2, z2, bArr, 0, bArr.length);
        } else {
            a(i2, z2, null, 0, 0);
        }
    }

    public void a(int i2, boolean z2, byte[] bArr, int i3, int i4) throws IOException {
        int i5;
        byte b3;
        byte[] bArrA;
        if (z2) {
            b3 = (byte) (-128);
            i5 = i2;
        } else {
            i5 = i2;
            b3 = 0;
        }
        a((byte) (b3 | ((byte) i5)));
        byte b4 = this.f27219d.c() ? (byte) -128 : (byte) 0;
        long j2 = i4;
        if (j2 <= 125) {
            a((byte) (b4 | ((byte) j2)));
        } else if (j2 <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            a((byte) (b4 | 126));
            a(new byte[]{(byte) ((j2 >> 8) & 255), (byte) (j2 & 255)});
        } else {
            a((byte) (b4 | 127));
            a(new byte[]{(byte) ((j2 >> 56) & 255), (byte) ((j2 >> 48) & 255), (byte) ((j2 >> 40) & 255), (byte) ((j2 >> 32) & 255), (byte) ((j2 >> 24) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 8) & 255), (byte) (j2 & 255)});
        }
        if (this.f27219d.c()) {
            bArrA = a();
            a(bArrA[0]);
            a(bArrA[1]);
            a(bArrA[2]);
            a(bArrA[3]);
        } else {
            bArrA = null;
        }
        if (j2 > 0) {
            if (this.f27219d.c()) {
                for (int i6 = 0; i6 < j2; i6++) {
                    int i7 = i6 + i3;
                    bArr[i7] = (byte) (bArr[i7] ^ bArrA[i6 % 4]);
                }
            }
            this.f27220e.write(bArr, i3, i4);
        }
    }
}
