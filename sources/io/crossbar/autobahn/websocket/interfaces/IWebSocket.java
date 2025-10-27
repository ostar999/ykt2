package io.crossbar.autobahn.websocket.interfaces;

import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.types.WebSocketOptions;
import java.util.Map;

/* loaded from: classes8.dex */
public interface IWebSocket {
    void a();

    void a(int i2);

    void a(int i2, String str);

    void a(String str);

    void a(String str, IWebSocketConnectionHandler iWebSocketConnectionHandler) throws WebSocketException;

    void a(String str, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions) throws WebSocketException;

    void a(String str, String str2, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions) throws WebSocketException;

    void a(String str, String str2, String[] strArr, IWebSocketConnectionHandler iWebSocketConnectionHandler, WebSocketOptions webSocketOptions, Map<String, String> map) throws WebSocketException;

    void a(String str, String[] strArr, IWebSocketConnectionHandler iWebSocketConnectionHandler) throws WebSocketException;

    void a(byte[] bArr);

    void a(byte[] bArr, boolean z2);

    void b(byte[] bArr);

    boolean b();

    void c();

    void d();
}
