package io.crossbar.autobahn.websocket;

import io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;

/* loaded from: classes8.dex */
public class WebSocketConnectionHandler implements IWebSocketConnectionHandler {

    /* renamed from: i, reason: collision with root package name */
    public WebSocketConnection f27184i;

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a() {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(int i2, String str) {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(ConnectionResponse connectionResponse) {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(String str) {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(byte[] bArr) {
        this.f27184i.b(bArr);
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(byte[] bArr, boolean z2) {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void b() {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void b(byte[] bArr) {
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void c() {
        this.f27184i.c();
    }

    @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
    public void a(WebSocketConnection webSocketConnection) {
        this.f27184i = webSocketConnection;
    }
}
