package io.crossbar.autobahn.websocket.messages;

import java.util.Map;

/* loaded from: classes8.dex */
public class ServerHandshake extends Message {

    /* renamed from: a, reason: collision with root package name */
    public boolean f27253a;

    /* renamed from: b, reason: collision with root package name */
    public Map<String, String> f27254b;

    public ServerHandshake(Map<String, String> map, boolean z2) {
        this.f27253a = z2;
        this.f27254b = map;
    }
}
