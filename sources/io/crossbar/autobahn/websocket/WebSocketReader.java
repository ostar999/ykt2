package io.crossbar.autobahn.websocket;

import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import c.h;
import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.messages.BinaryMessage;
import io.crossbar.autobahn.websocket.messages.Close;
import io.crossbar.autobahn.websocket.messages.ConnectionLost;
import io.crossbar.autobahn.websocket.messages.Error;
import io.crossbar.autobahn.websocket.messages.Ping;
import io.crossbar.autobahn.websocket.messages.Pong;
import io.crossbar.autobahn.websocket.messages.ProtocolViolation;
import io.crossbar.autobahn.websocket.messages.RawTextMessage;
import io.crossbar.autobahn.websocket.messages.ServerHandshake;
import io.crossbar.autobahn.websocket.messages.TextMessage;
import io.crossbar.autobahn.websocket.types.WebSocketOptions;
import io.crossbar.autobahn.websocket.utils.Utf8Validator;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes8.dex */
class WebSocketReader extends Thread {

    /* renamed from: r, reason: collision with root package name */
    public static final int f27187r = 0;

    /* renamed from: s, reason: collision with root package name */
    public static final int f27188s = 1;

    /* renamed from: t, reason: collision with root package name */
    public static final int f27189t = 2;

    /* renamed from: u, reason: collision with root package name */
    public static final int f27190u = 3;

    /* renamed from: a, reason: collision with root package name */
    public final Handler f27191a;

    /* renamed from: b, reason: collision with root package name */
    public final WebSocketOptions f27192b;

    /* renamed from: c, reason: collision with root package name */
    public BufferedInputStream f27193c;

    /* renamed from: d, reason: collision with root package name */
    public Socket f27194d;

    /* renamed from: e, reason: collision with root package name */
    public int f27195e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f27196f;

    /* renamed from: g, reason: collision with root package name */
    public ByteBuffer f27197g;

    /* renamed from: h, reason: collision with root package name */
    public ByteArrayOutputStream f27198h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f27199i;

    /* renamed from: j, reason: collision with root package name */
    public int f27200j;

    /* renamed from: k, reason: collision with root package name */
    public long f27201k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f27202l;

    /* renamed from: m, reason: collision with root package name */
    public int f27203m;

    /* renamed from: n, reason: collision with root package name */
    public FrameHeader f27204n;

    /* renamed from: o, reason: collision with root package name */
    public Utf8Validator f27205o;

    /* renamed from: q, reason: collision with root package name */
    public static final String f27186q = "WebSocketReader";

    /* renamed from: p, reason: collision with root package name */
    public static final IABLogger f27185p = ABLogger.a(f27186q);

    public static class FrameHeader {

        /* renamed from: a, reason: collision with root package name */
        public int f27206a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f27207b;

        /* renamed from: c, reason: collision with root package name */
        public int f27208c;

        /* renamed from: d, reason: collision with root package name */
        public int f27209d;

        /* renamed from: e, reason: collision with root package name */
        public int f27210e;

        /* renamed from: f, reason: collision with root package name */
        public int f27211f;

        /* renamed from: g, reason: collision with root package name */
        public byte[] f27212g;

        private FrameHeader() {
        }
    }

    public WebSocketReader(Handler handler, Socket socket, WebSocketOptions webSocketOptions, String str) throws IOException {
        super(str);
        this.f27199i = false;
        this.f27202l = false;
        this.f27205o = new Utf8Validator();
        this.f27191a = handler;
        this.f27192b = webSocketOptions;
        this.f27194d = socket;
        h.a(f27186q, "WebSocketReader allocate messagedata " + (webSocketOptions.d() + 14));
        this.f27196f = new byte[webSocketOptions.d() + 14];
        h.a(f27186q, "WebSocketReader allocate BufferedInputStream " + (webSocketOptions.d() + 14));
        this.f27193c = new BufferedInputStream(this.f27194d.getInputStream(), webSocketOptions.d() + 14);
        h.a(f27186q, "WebSocketReader allocate payload " + (webSocketOptions.e() + 14));
        this.f27198h = new ByteArrayOutputStream(webSocketOptions.e());
        h.a(f27186q, "WebSocketReader allocate finish");
        this.f27204n = null;
        this.f27200j = 1;
        h.a(f27186q, "WebSocketReader Created");
    }

    public void a(Object obj) {
        Message messageObtainMessage = this.f27191a.obtainMessage();
        messageObtainMessage.obj = obj;
        this.f27191a.sendMessage(messageObtainMessage);
    }

    public double b() {
        return (System.currentTimeMillis() - this.f27201k) / 1000.0d;
    }

    public final boolean c() throws Exception {
        byte[] bArr;
        boolean z2;
        FrameHeader frameHeader;
        int i2;
        String str;
        int i3;
        boolean z3;
        long j2;
        int i4;
        FrameHeader frameHeader2 = this.f27204n;
        if (frameHeader2 == null) {
            int i5 = this.f27195e;
            if (i5 < 2) {
                return false;
            }
            byte[] bArr2 = this.f27196f;
            byte b3 = bArr2[0];
            boolean z4 = (b3 & 128) != 0;
            int i6 = (b3 & 112) >> 4;
            int i7 = b3 & 15;
            byte b4 = bArr2[1];
            boolean z5 = (b4 & 128) != 0;
            int i8 = b4 & 127;
            if (i6 != 0) {
                throw new WebSocketException("RSV != 0 and no extension negotiated");
            }
            if (z5) {
                throw new WebSocketException("masked server frame");
            }
            if (i7 > 7) {
                if (!z4) {
                    throw new WebSocketException("fragmented control frame");
                }
                if (i8 > 125) {
                    throw new WebSocketException("control frame with payload length > 125 octets");
                }
                if (i7 != 8 && i7 != 9 && i7 != 10) {
                    throw new WebSocketException("control frame using reserved opcode " + i7);
                }
                if (i7 == 8 && i8 == 1) {
                    throw new WebSocketException("received close control frame with payload len 1");
                }
            } else {
                if (i7 != 0 && i7 != 1 && i7 != 2) {
                    throw new WebSocketException("data frame using reserved opcode " + i7);
                }
                boolean z6 = this.f27202l;
                if (!z6 && i7 == 0) {
                    throw new WebSocketException("received continuation data frame outside fragmented message");
                }
                if (z6 && i7 != 0) {
                    throw new WebSocketException("received non-continuation data frame while inside fragmented message");
                }
            }
            int i9 = z5 ? 4 : 0;
            if (i8 < 126) {
                i3 = i9 + 2;
            } else if (i8 == 126) {
                i3 = i9 + 4;
            } else {
                if (i8 != 127) {
                    throw new Exception("logic error");
                }
                i3 = i9 + 10;
            }
            if (i5 < i3) {
                return false;
            }
            if (i8 == 126) {
                j2 = (bArr2[3] & 255) | ((bArr2[2] & 255) << 8);
                if (j2 < 126) {
                    throw new WebSocketException("invalid data frame length (not using minimal length encoding)");
                }
                z3 = z5;
                i4 = 4;
            } else if (i8 == 127) {
                if ((bArr2[2] & 128) != 0) {
                    throw new WebSocketException("invalid data frame length (> 2^63)");
                }
                z3 = z5;
                j2 = (bArr2[9] & 255) | ((bArr2[3] & 255) << 48) | ((r8 & 255) << 56) | ((bArr2[4] & 255) << 40) | ((bArr2[5] & 255) << 32) | ((bArr2[6] & 255) << 24) | ((bArr2[7] & 255) << 16) | ((bArr2[8] & 255) << 8);
                if (j2 < 65536) {
                    throw new WebSocketException("invalid data frame length (not using minimal length encoding)");
                }
                i4 = 10;
            } else {
                z3 = z5;
                j2 = i8;
                i4 = 2;
            }
            if (j2 > this.f27192b.d()) {
                throw new WebSocketException("frame payload too large");
            }
            FrameHeader frameHeader3 = new FrameHeader();
            this.f27204n = frameHeader3;
            frameHeader3.f27206a = i7;
            frameHeader3.f27207b = z4;
            frameHeader3.f27208c = i6;
            int i10 = (int) j2;
            frameHeader3.f27210e = i10;
            frameHeader3.f27209d = i3;
            frameHeader3.f27211f = i3 + i10;
            if (z3) {
                frameHeader3.f27212g = new byte[4];
                for (int i11 = 0; i11 < 4; i11++) {
                    this.f27204n.f27212g[i4] = (byte) (this.f27196f[i4 + i11] & 255);
                }
            } else {
                frameHeader3.f27212g = null;
            }
            FrameHeader frameHeader4 = this.f27204n;
            return frameHeader4.f27210e == 0 || this.f27195e >= frameHeader4.f27211f;
        }
        if (this.f27195e < frameHeader2.f27211f) {
            return false;
        }
        int i12 = frameHeader2.f27210e;
        if (i12 > 0) {
            bArr = new byte[i12];
            System.arraycopy(this.f27196f, frameHeader2.f27209d, bArr, 0, i12);
        } else {
            bArr = null;
        }
        byte[] bArr3 = this.f27196f;
        int i13 = this.f27204n.f27211f;
        this.f27196f = Arrays.copyOfRange(bArr3, i13, bArr3.length + i13);
        int i14 = this.f27195e;
        FrameHeader frameHeader5 = this.f27204n;
        this.f27195e = i14 - frameHeader5.f27211f;
        int i15 = frameHeader5.f27206a;
        if (i15 > 7) {
            if (i15 == 8) {
                int i16 = frameHeader5.f27210e;
                if (i16 >= 2) {
                    i2 = ((bArr[0] & 255) * 256) + (bArr[1] & 255);
                    if (i2 < 1000 || (!(i2 < 1000 || i2 > 2999 || i2 == 1000 || i2 == 1001 || i2 == 1002 || i2 == 1003 || i2 == 1007 || i2 == 1008 || i2 == 1009 || i2 == 1010 || i2 == 1011) || i2 >= 5000)) {
                        throw new WebSocketException("invalid close code " + i2);
                    }
                    if (i16 > 2) {
                        int i17 = i16 - 2;
                        byte[] bArr4 = new byte[i17];
                        System.arraycopy(bArr, 2, bArr4, 0, i17);
                        Utf8Validator utf8Validator = new Utf8Validator();
                        utf8Validator.a(bArr4);
                        if (!utf8Validator.a()) {
                            throw new WebSocketException("invalid close reasons (not UTF-8)");
                        }
                        str = new String(bArr4, "UTF-8");
                    }
                    a(i2, str);
                    this.f27200j = 0;
                } else {
                    i2 = 1005;
                }
                str = null;
                a(i2, str);
                this.f27200j = 0;
            } else if (i15 == 9) {
                b(bArr);
            } else {
                if (i15 != 10) {
                    throw new Exception("logic error");
                }
                c(bArr);
            }
            frameHeader = null;
            z2 = true;
        } else {
            if (!this.f27202l) {
                this.f27202l = true;
                this.f27203m = i15;
                if (i15 == 1 && this.f27192b.l()) {
                    this.f27205o.c();
                }
            }
            if (bArr != null) {
                if (this.f27198h.size() + bArr.length > this.f27192b.e()) {
                    throw new WebSocketException("message payload too large");
                }
                if (this.f27203m == 1 && this.f27192b.l() && !this.f27205o.a(bArr)) {
                    throw new WebSocketException("invalid UTF-8 in text message payload");
                }
                this.f27198h.write(bArr);
            }
            if (this.f27204n.f27207b) {
                int i18 = this.f27203m;
                z2 = true;
                if (i18 == 1) {
                    if (this.f27192b.l() && !this.f27205o.a()) {
                        throw new WebSocketException("UTF-8 text message payload ended within Unicode code point");
                    }
                    if (this.f27192b.f()) {
                        d(this.f27198h.toByteArray());
                    } else {
                        a(new String(this.f27198h.toByteArray(), "UTF-8"));
                    }
                } else {
                    if (i18 != 2) {
                        throw new Exception("logic error");
                    }
                    a(this.f27198h.toByteArray());
                }
                this.f27202l = false;
                this.f27198h.reset();
            } else {
                z2 = true;
            }
            frameHeader = null;
        }
        this.f27204n = frameHeader;
        if (this.f27195e > 0) {
            return z2;
        }
        return false;
    }

    public void d(byte[] bArr) {
        a(new RawTextMessage(bArr));
    }

    public void e() {
        this.f27200j = 0;
        f27185p.b("Quit");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        do {
            try {
                try {
                    try {
                        try {
                            BufferedInputStream bufferedInputStream = this.f27193c;
                            byte[] bArr = this.f27196f;
                            int i2 = this.f27195e;
                            int i3 = bufferedInputStream.read(bArr, i2, bArr.length - i2);
                            this.f27195e += i3;
                            if (i3 > 0) {
                                this.f27201k = System.currentTimeMillis();
                                while (a()) {
                                }
                            } else if (this.f27200j == 0) {
                                this.f27199i = true;
                            } else if (i3 < 0) {
                                f27185p.b("run() : ConnectionLost");
                                a(new ConnectionLost(null));
                                this.f27199i = true;
                            }
                        } catch (WebSocketException e2) {
                            f27185p.b("run() : WebSocketException (" + e2.toString() + ")");
                            a(new ProtocolViolation(e2));
                            f27185p.b("Ended");
                        }
                    } catch (Exception e3) {
                        f27185p.b("run() : Exception (" + e3.toString() + ")");
                        a(new Error(e3));
                        f27185p.b("Ended");
                    }
                } catch (SocketException e4) {
                    if (this.f27200j != 0 && !this.f27194d.isClosed()) {
                        f27185p.b("run() : SocketException (" + e4.toString() + ")");
                        a(new ConnectionLost(null));
                    }
                }
            } finally {
                this.f27199i = true;
            }
        } while (!this.f27199i);
        this.f27199i = true;
        f27185p.b("Ended");
    }

    public void b(byte[] bArr) {
        a(new Ping(bArr));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean d() throws java.io.UnsupportedEncodingException {
        /*
            r8 = this;
            int r0 = r8.f27195e
            int r0 = r0 + (-4)
        L4:
            r1 = 0
            if (r0 < 0) goto L9e
            byte[] r2 = r8.f27196f
            r3 = r2[r0]
            r4 = 13
            if (r3 != r4) goto L9a
            int r3 = r0 + 1
            r3 = r2[r3]
            r5 = 10
            if (r3 != r5) goto L9a
            int r3 = r0 + 2
            r3 = r2[r3]
            if (r3 != r4) goto L9a
            int r3 = r0 + 3
            r3 = r2[r3]
            if (r3 != r5) goto L9a
            java.lang.String r3 = new java.lang.String
            int r4 = r0 + 4
            byte[] r2 = java.util.Arrays.copyOf(r2, r4)
            java.lang.String r5 = "UTF-8"
            r3.<init>(r2, r5)
            java.lang.String r2 = "\r\n"
            java.lang.String[] r2 = r3.split(r2)
            r3 = r2[r1]
            java.lang.String r5 = "HTTP"
            boolean r3 = r3.startsWith(r5)
            r5 = 1
            if (r3 == 0) goto L69
            r3 = r2[r1]
            android.util.Pair r3 = r8.b(r3)
            java.lang.Object r6 = r3.first
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r7 = 300(0x12c, float:4.2E-43)
            if (r6 < r7) goto L69
            io.crossbar.autobahn.websocket.messages.ServerError r6 = new io.crossbar.autobahn.websocket.messages.ServerError
            java.lang.Object r7 = r3.first
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            java.lang.Object r3 = r3.second
            java.lang.String r3 = (java.lang.String) r3
            r6.<init>(r7, r3)
            r8.a(r6)
            r3 = r5
            goto L6a
        L69:
            r3 = r1
        L6a:
            int r6 = r2.length
            java.lang.Object[] r2 = java.util.Arrays.copyOfRange(r2, r5, r6)
            java.lang.String[] r2 = (java.lang.String[]) r2
            java.util.Map r2 = r8.a(r2)
            byte[] r6 = r8.f27196f
            int r7 = r6.length
            int r7 = r7 + r0
            int r7 = r7 + 4
            byte[] r0 = java.util.Arrays.copyOfRange(r6, r4, r7)
            r8.f27196f = r0
            int r0 = r8.f27195e
            int r0 = r0 - r4
            r8.f27195e = r0
            if (r3 != 0) goto L8f
            if (r0 <= 0) goto L8b
            r1 = r5
        L8b:
            r0 = 3
            r8.f27200j = r0
            goto L94
        L8f:
            r8.f27200j = r1
            r8.f27199i = r5
            r1 = r5
        L94:
            r0 = r3 ^ 1
            r8.a(r2, r0)
            goto L9e
        L9a:
            int r0 = r0 + (-1)
            goto L4
        L9e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.crossbar.autobahn.websocket.WebSocketReader.d():boolean");
    }

    public final Pair<Integer, String> b(String str) throws UnsupportedEncodingException {
        String[] strArrSplit = str.split(" ");
        int iIntValue = Integer.valueOf(strArrSplit[1]).intValue();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 2; i2 < strArrSplit.length; i2++) {
            sb.append(strArrSplit[i2]);
            sb.append(" ");
        }
        String strTrim = sb.toString().trim();
        f27185p.b(String.format("Status: %d (%s)", Integer.valueOf(iIntValue), strTrim));
        return new Pair<>(Integer.valueOf(iIntValue), strTrim);
    }

    public void a(Map<String, String> map, boolean z2) {
        a(new ServerHandshake(map, z2));
    }

    public void a(int i2, String str) {
        a(new Close(i2, str));
    }

    public void a(String str) {
        a(new TextMessage(str));
    }

    public void a(byte[] bArr) {
        a(new BinaryMessage(bArr));
    }

    public final Map<String, String> a(String[] strArr) throws UnsupportedEncodingException {
        HashMap map = new HashMap();
        for (String str : strArr) {
            if (str.length() > 0) {
                String[] strArrSplit = str.split(": ");
                if (strArrSplit.length == 2) {
                    map.put(strArrSplit[0], strArrSplit[1]);
                    f27185p.b(String.format("'%s'='%s'", strArrSplit[0], strArrSplit[1]));
                }
            }
        }
        return map;
    }

    public final boolean a() throws Exception {
        int i2 = this.f27200j;
        if (i2 == 3 || i2 == 2) {
            return c();
        }
        if (i2 == 1) {
            return d();
        }
        return false;
    }

    public void c(byte[] bArr) {
        a(new Pong(bArr));
    }
}
