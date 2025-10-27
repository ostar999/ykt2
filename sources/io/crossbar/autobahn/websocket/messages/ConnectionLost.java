package io.crossbar.autobahn.websocket.messages;

/* loaded from: classes8.dex */
public class ConnectionLost extends Message {

    /* renamed from: a, reason: collision with root package name */
    public final String f27245a;

    public ConnectionLost(String str) {
        if (str == null) {
            this.f27245a = "WebSockets connection lost";
        } else {
            this.f27245a = str;
        }
    }
}
