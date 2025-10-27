package com.tencent.open.a;

import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private Response f20509a;

    /* renamed from: b, reason: collision with root package name */
    private String f20510b = null;

    /* renamed from: c, reason: collision with root package name */
    private int f20511c;

    /* renamed from: d, reason: collision with root package name */
    private int f20512d;

    /* renamed from: e, reason: collision with root package name */
    private int f20513e;

    public b(Response response, int i2) {
        this.f20509a = response;
        this.f20512d = i2;
        this.f20511c = response.code();
        ResponseBody responseBodyBody = this.f20509a.body();
        if (responseBodyBody != null) {
            this.f20513e = (int) responseBodyBody.get$contentLength();
        } else {
            this.f20513e = 0;
        }
    }

    public String a() throws IOException {
        if (this.f20510b == null) {
            ResponseBody responseBodyBody = this.f20509a.body();
            if (responseBodyBody != null) {
                this.f20510b = responseBodyBody.string();
            }
            if (this.f20510b == null) {
                this.f20510b = "";
            }
        }
        return this.f20510b;
    }

    public int b() {
        return this.f20513e;
    }

    public int c() {
        return this.f20512d;
    }

    public int d() {
        return this.f20511c;
    }
}
