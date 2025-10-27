package com.heytap.mcssdk.c;

import cn.hutool.core.text.CharPool;
import com.heytap.msp.push.mode.BaseMode;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class b extends BaseMode {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7128a = "&";

    /* renamed from: b, reason: collision with root package name */
    private String f7129b;

    /* renamed from: c, reason: collision with root package name */
    private String f7130c;

    /* renamed from: d, reason: collision with root package name */
    private String f7131d;

    /* renamed from: e, reason: collision with root package name */
    private String f7132e;

    /* renamed from: f, reason: collision with root package name */
    private int f7133f;

    /* renamed from: g, reason: collision with root package name */
    private String f7134g;

    /* renamed from: h, reason: collision with root package name */
    private int f7135h = -2;

    /* renamed from: i, reason: collision with root package name */
    private String f7136i;

    public static <T> String a(List<T> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("&");
        }
        return sb.toString();
    }

    public String a() {
        return this.f7129b;
    }

    public void a(int i2) {
        this.f7133f = i2;
    }

    public void a(String str) {
        this.f7129b = str;
    }

    public String b() {
        return this.f7130c;
    }

    public void b(int i2) {
        this.f7135h = i2;
    }

    public void b(String str) {
        this.f7130c = str;
    }

    public String c() {
        return this.f7131d;
    }

    public void c(String str) {
        this.f7131d = str;
    }

    public String d() {
        return this.f7132e;
    }

    public void d(String str) {
        this.f7132e = str;
    }

    public int e() {
        return this.f7133f;
    }

    public void e(String str) {
        this.f7134g = str;
    }

    public String f() {
        return this.f7134g;
    }

    public void f(String str) {
        this.f7136i = str;
    }

    public int g() {
        return this.f7135h;
    }

    @Override // com.heytap.msp.push.mode.BaseMode
    public int getType() {
        return 4105;
    }

    public String h() {
        return this.f7136i;
    }

    public String toString() {
        return "CallBackResult{, mRegisterID='" + this.f7131d + CharPool.SINGLE_QUOTE + ", mSdkVersion='" + this.f7132e + CharPool.SINGLE_QUOTE + ", mCommand=" + this.f7133f + CharPool.SINGLE_QUOTE + ", mContent='" + this.f7134g + CharPool.SINGLE_QUOTE + ", mAppPackage=" + this.f7136i + CharPool.SINGLE_QUOTE + ", mResponseCode=" + this.f7135h + '}';
    }
}
