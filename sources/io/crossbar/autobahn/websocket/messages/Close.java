package io.crossbar.autobahn.websocket.messages;

/* loaded from: classes8.dex */
public class Close extends Message {

    /* renamed from: a, reason: collision with root package name */
    public int f27242a;

    /* renamed from: b, reason: collision with root package name */
    public String f27243b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f27244c;

    public Close() {
        this.f27242a = -1;
        this.f27243b = null;
    }

    public Close(int i2) {
        this.f27242a = i2;
        this.f27243b = null;
    }

    public Close(int i2, boolean z2) {
        this.f27242a = i2;
        this.f27244c = z2;
    }

    public Close(int i2, String str) {
        this.f27242a = i2;
        this.f27243b = str;
    }

    public Close(int i2, String str, boolean z2) {
        this.f27242a = i2;
        this.f27244c = z2;
        this.f27243b = str;
    }
}
