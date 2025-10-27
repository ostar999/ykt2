package h;

import cn.hutool.core.text.CharPool;
import java.io.Serializable;

/* loaded from: classes8.dex */
public class h implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public String f27087a;

    /* renamed from: b, reason: collision with root package name */
    public String f27088b;

    /* renamed from: c, reason: collision with root package name */
    public String f27089c;

    /* renamed from: g, reason: collision with root package name */
    public int f27093g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f27094h;

    /* renamed from: i, reason: collision with root package name */
    public final int f27095i = 3;

    /* renamed from: d, reason: collision with root package name */
    public long f27090d = System.currentTimeMillis();

    /* renamed from: e, reason: collision with root package name */
    public long f27091e = 0;

    /* renamed from: f, reason: collision with root package name */
    public int f27092f = 0;

    public h(String str, String str2, String str3, boolean z2) {
        this.f27087a = str;
        this.f27088b = str2;
        this.f27089c = str3;
        this.f27094h = z2;
    }

    public void a(String str) {
        this.f27088b = str;
    }

    public void b(String str) {
        this.f27089c = str;
    }

    public String c() {
        return this.f27088b;
    }

    public String d() {
        return this.f27089c;
    }

    public long e() {
        return this.f27090d;
    }

    public long f() {
        return this.f27091e;
    }

    public boolean g() {
        return this.f27094h;
    }

    public String toString() {
        return "WebSocketMessage{mMsgtype='" + this.f27087a + CharPool.SINGLE_QUOTE + ", mMrpcid='" + this.f27088b + CharPool.SINGLE_QUOTE + ", mMsgdata='" + this.f27089c + CharPool.SINGLE_QUOTE + ", mQuetime=" + this.f27090d + ", mStartsendtime=" + this.f27091e + ", mCurretry=" + this.f27092f + ", mMaxretry=" + this.f27093g + ", mIsretry=" + this.f27094h + ", MAX_RETRY=3}";
    }

    public void a(long j2) {
        this.f27090d = j2;
    }

    public void b(long j2) {
        this.f27091e = j2;
    }

    public int a() {
        return this.f27092f;
    }

    public int b() {
        return this.f27093g;
    }

    public void a(int i2) {
        this.f27092f = i2;
    }

    public void b(int i2) {
        this.f27093g = i2;
    }

    public void a(boolean z2) {
        this.f27094h = z2;
    }
}
