package io.crossbar.autobahn.websocket.messages;

import java.util.Map;

/* loaded from: classes8.dex */
public class ClientHandshake extends Message {

    /* renamed from: a, reason: collision with root package name */
    public String f27236a;

    /* renamed from: b, reason: collision with root package name */
    public String f27237b;

    /* renamed from: c, reason: collision with root package name */
    public String f27238c;

    /* renamed from: d, reason: collision with root package name */
    public String f27239d;

    /* renamed from: e, reason: collision with root package name */
    public String[] f27240e;

    /* renamed from: f, reason: collision with root package name */
    public Map<String, String> f27241f;

    public ClientHandshake(String str) {
        this.f27236a = str;
        this.f27237b = "/";
        this.f27239d = null;
        this.f27240e = null;
        this.f27241f = null;
    }

    public ClientHandshake(String str, String str2, String str3) {
        this.f27236a = str;
        this.f27237b = str2;
        this.f27239d = str3;
        this.f27240e = null;
    }

    public ClientHandshake(String str, String str2, String str3, String[] strArr) {
        this.f27236a = str;
        this.f27237b = str2;
        this.f27239d = str3;
        this.f27240e = strArr;
    }
}
