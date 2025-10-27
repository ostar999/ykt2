package com.meizu.cloud.pushsdk.c.c;

import com.meizu.cloud.pushsdk.c.a.c;
import com.meizu.cloud.pushsdk.c.c.a;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes4.dex */
public class b extends com.meizu.cloud.pushsdk.c.c.a {

    /* renamed from: d, reason: collision with root package name */
    private String f9348d;

    /* renamed from: e, reason: collision with root package name */
    private String f9349e;

    /* renamed from: f, reason: collision with root package name */
    private String f9350f;

    /* renamed from: g, reason: collision with root package name */
    private String f9351g;

    /* renamed from: h, reason: collision with root package name */
    private String f9352h;

    /* renamed from: i, reason: collision with root package name */
    private String f9353i;

    /* renamed from: j, reason: collision with root package name */
    private String f9354j;

    /* renamed from: k, reason: collision with root package name */
    private String f9355k;

    /* renamed from: l, reason: collision with root package name */
    private int f9356l;

    public static abstract class a<T extends a<T>> extends a.AbstractC0195a<T> {

        /* renamed from: a, reason: collision with root package name */
        private String f9357a;

        /* renamed from: b, reason: collision with root package name */
        private String f9358b;

        /* renamed from: c, reason: collision with root package name */
        private String f9359c;

        /* renamed from: d, reason: collision with root package name */
        private String f9360d;

        /* renamed from: e, reason: collision with root package name */
        private String f9361e;

        /* renamed from: f, reason: collision with root package name */
        private String f9362f;

        /* renamed from: g, reason: collision with root package name */
        private String f9363g;

        /* renamed from: h, reason: collision with root package name */
        private String f9364h;

        /* renamed from: i, reason: collision with root package name */
        private int f9365i = 0;

        public T a(int i2) {
            this.f9365i = i2;
            return a();
        }

        public T a(String str) {
            this.f9357a = str;
            return a();
        }

        public T b(String str) {
            this.f9358b = str;
            return a();
        }

        public b b() {
            return new b(this);
        }

        public T c(String str) {
            this.f9359c = str;
            return a();
        }

        public T d(String str) {
            this.f9360d = str;
            return a();
        }

        public T e(String str) {
            this.f9361e = str;
            return a();
        }

        public T f(String str) {
            this.f9362f = str;
            return a();
        }

        public T g(String str) {
            this.f9363g = str;
            return a();
        }

        public T h(String str) {
            this.f9364h = str;
            return a();
        }
    }

    /* renamed from: com.meizu.cloud.pushsdk.c.c.b$b, reason: collision with other inner class name */
    public static class C0196b extends a<C0196b> {
        private C0196b() {
        }

        @Override // com.meizu.cloud.pushsdk.c.c.a.AbstractC0195a
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public C0196b a() {
            return this;
        }
    }

    public b(a<?> aVar) {
        super(aVar);
        this.f9349e = ((a) aVar).f9358b;
        this.f9350f = ((a) aVar).f9359c;
        this.f9348d = ((a) aVar).f9357a;
        this.f9351g = ((a) aVar).f9360d;
        this.f9352h = ((a) aVar).f9361e;
        this.f9353i = ((a) aVar).f9362f;
        this.f9354j = ((a) aVar).f9363g;
        this.f9355k = ((a) aVar).f9364h;
        this.f9356l = ((a) aVar).f9365i;
    }

    public static a<?> d() {
        return new C0196b();
    }

    public c e() {
        c cVar = new c();
        cVar.a(SocializeProtocolConstants.PROTOCOL_KEY_EN, this.f9348d);
        cVar.a("ti", this.f9349e);
        cVar.a(AppIconSetting.DEFAULT_LARGE_ICON, this.f9350f);
        cVar.a(SocializeProtocolConstants.PROTOCOL_KEY_PV, this.f9351g);
        cVar.a("pn", this.f9352h);
        cVar.a("si", this.f9353i);
        cVar.a("ms", this.f9354j);
        cVar.a("ect", this.f9355k);
        cVar.a("br", Integer.valueOf(this.f9356l));
        return a(cVar);
    }
}
