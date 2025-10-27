package io.crossbar.autobahn.websocket.interfaces;

import io.crossbar.autobahn.websocket.WebSocketConnection;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;

/* loaded from: classes8.dex */
public interface IWebSocketConnectionHandler {

    /* renamed from: a, reason: collision with root package name */
    public static final int f27226a = 1;

    /* renamed from: b, reason: collision with root package name */
    public static final int f27227b = 2;

    /* renamed from: c, reason: collision with root package name */
    public static final int f27228c = 3;

    /* renamed from: d, reason: collision with root package name */
    public static final int f27229d = 4;

    /* renamed from: e, reason: collision with root package name */
    public static final int f27230e = 5;

    /* renamed from: f, reason: collision with root package name */
    public static final int f27231f = 6;

    /* renamed from: g, reason: collision with root package name */
    public static final int f27232g = 7;

    /* renamed from: h, reason: collision with root package name */
    public static final int f27233h = 8;

    void a();

    void a(int i2, String str);

    void a(WebSocketConnection webSocketConnection);

    void a(ConnectionResponse connectionResponse);

    void a(String str);

    void a(byte[] bArr);

    void a(byte[] bArr, boolean z2);

    void b();

    void b(byte[] bArr);

    void c();
}
