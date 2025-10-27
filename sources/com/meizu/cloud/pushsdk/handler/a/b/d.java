package com.meizu.cloud.pushsdk.handler.a.b;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    String f9452a;

    /* renamed from: b, reason: collision with root package name */
    String f9453b;

    /* renamed from: c, reason: collision with root package name */
    String f9454c;

    /* renamed from: d, reason: collision with root package name */
    String f9455d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private String f9456a;

        /* renamed from: b, reason: collision with root package name */
        private String f9457b;

        /* renamed from: c, reason: collision with root package name */
        private String f9458c;

        /* renamed from: d, reason: collision with root package name */
        private String f9459d;

        public a a(String str) {
            this.f9456a = str;
            return this;
        }

        public d a() {
            return new d(this);
        }

        public a b(String str) {
            this.f9457b = str;
            return this;
        }

        public a c(String str) {
            this.f9458c = str;
            return this;
        }

        public a d(String str) {
            this.f9459d = str;
            return this;
        }
    }

    public d() {
    }

    public d(a aVar) {
        this.f9452a = !TextUtils.isEmpty(aVar.f9456a) ? aVar.f9456a : "";
        this.f9453b = !TextUtils.isEmpty(aVar.f9457b) ? aVar.f9457b : "";
        this.f9454c = !TextUtils.isEmpty(aVar.f9458c) ? aVar.f9458c : "";
        this.f9455d = TextUtils.isEmpty(aVar.f9459d) ? "" : aVar.f9459d;
    }

    public static a a() {
        return new a();
    }

    public String b() {
        com.meizu.cloud.pushsdk.c.a.c cVar = new com.meizu.cloud.pushsdk.c.a.c();
        cVar.a(PushConstants.TASK_ID, this.f9452a);
        cVar.a("seq_id", this.f9453b);
        cVar.a("push_timestamp", this.f9454c);
        cVar.a("device_id", this.f9455d);
        return cVar.toString();
    }

    public String c() {
        return this.f9452a;
    }

    public String d() {
        return this.f9453b;
    }

    public String e() {
        return this.f9454c;
    }

    public String f() {
        return this.f9455d;
    }
}
