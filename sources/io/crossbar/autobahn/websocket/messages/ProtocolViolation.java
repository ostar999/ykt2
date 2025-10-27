package io.crossbar.autobahn.websocket.messages;

import io.crossbar.autobahn.websocket.exceptions.WebSocketException;

/* loaded from: classes8.dex */
public class ProtocolViolation extends Message {

    /* renamed from: a, reason: collision with root package name */
    public WebSocketException f27249a;

    public ProtocolViolation(WebSocketException webSocketException) {
        this.f27249a = webSocketException;
    }
}
