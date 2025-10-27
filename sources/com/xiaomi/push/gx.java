package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class gx {

    /* renamed from: a, reason: collision with root package name */
    private int f24992a;

    /* renamed from: a, reason: collision with other field name */
    private String f512a;

    /* renamed from: a, reason: collision with other field name */
    private List<gq> f513a;

    /* renamed from: b, reason: collision with root package name */
    private String f24993b;

    /* renamed from: c, reason: collision with root package name */
    private String f24994c;

    /* renamed from: d, reason: collision with root package name */
    private String f24995d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final a f24996a = new a("internal-server-error");

        /* renamed from: b, reason: collision with root package name */
        public static final a f24997b = new a("forbidden");

        /* renamed from: c, reason: collision with root package name */
        public static final a f24998c = new a("bad-request");

        /* renamed from: d, reason: collision with root package name */
        public static final a f24999d = new a("conflict");

        /* renamed from: e, reason: collision with root package name */
        public static final a f25000e = new a("feature-not-implemented");

        /* renamed from: f, reason: collision with root package name */
        public static final a f25001f = new a("gone");

        /* renamed from: g, reason: collision with root package name */
        public static final a f25002g = new a("item-not-found");

        /* renamed from: h, reason: collision with root package name */
        public static final a f25003h = new a("jid-malformed");

        /* renamed from: i, reason: collision with root package name */
        public static final a f25004i = new a("not-acceptable");

        /* renamed from: j, reason: collision with root package name */
        public static final a f25005j = new a("not-allowed");

        /* renamed from: k, reason: collision with root package name */
        public static final a f25006k = new a("not-authorized");

        /* renamed from: l, reason: collision with root package name */
        public static final a f25007l = new a("payment-required");

        /* renamed from: m, reason: collision with root package name */
        public static final a f25008m = new a("recipient-unavailable");

        /* renamed from: n, reason: collision with root package name */
        public static final a f25009n = new a("redirect");

        /* renamed from: o, reason: collision with root package name */
        public static final a f25010o = new a("registration-required");

        /* renamed from: p, reason: collision with root package name */
        public static final a f25011p = new a("remote-server-error");

        /* renamed from: q, reason: collision with root package name */
        public static final a f25012q = new a("remote-server-not-found");

        /* renamed from: r, reason: collision with root package name */
        public static final a f25013r = new a("remote-server-timeout");

        /* renamed from: s, reason: collision with root package name */
        public static final a f25014s = new a("resource-constraint");

        /* renamed from: t, reason: collision with root package name */
        public static final a f25015t = new a("service-unavailable");

        /* renamed from: u, reason: collision with root package name */
        public static final a f25016u = new a("subscription-required");

        /* renamed from: v, reason: collision with root package name */
        public static final a f25017v = new a("undefined-condition");

        /* renamed from: w, reason: collision with root package name */
        public static final a f25018w = new a("unexpected-request");

        /* renamed from: x, reason: collision with root package name */
        public static final a f25019x = new a("request-timeout");

        /* renamed from: a, reason: collision with other field name */
        private String f514a;

        public a(String str) {
            this.f514a = str;
        }

        public String toString() {
            return this.f514a;
        }
    }

    public gx(int i2, String str, String str2, String str3, String str4, List<gq> list) {
        this.f24992a = i2;
        this.f512a = str;
        this.f24994c = str2;
        this.f24993b = str3;
        this.f24995d = str4;
        this.f513a = list;
    }

    public gx(Bundle bundle) {
        this.f513a = null;
        this.f24992a = bundle.getInt("ext_err_code");
        if (bundle.containsKey("ext_err_type")) {
            this.f512a = bundle.getString("ext_err_type");
        }
        this.f24993b = bundle.getString("ext_err_cond");
        this.f24994c = bundle.getString("ext_err_reason");
        this.f24995d = bundle.getString("ext_err_msg");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.f513a = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                gq gqVarA = gq.a((Bundle) parcelable);
                if (gqVarA != null) {
                    this.f513a.add(gqVarA);
                }
            }
        }
    }

    public gx(a aVar) {
        this.f513a = null;
        a(aVar);
        this.f24995d = null;
    }

    private void a(a aVar) {
        this.f24993b = aVar.f514a;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        String str = this.f512a;
        if (str != null) {
            bundle.putString("ext_err_type", str);
        }
        bundle.putInt("ext_err_code", this.f24992a);
        String str2 = this.f24994c;
        if (str2 != null) {
            bundle.putString("ext_err_reason", str2);
        }
        String str3 = this.f24993b;
        if (str3 != null) {
            bundle.putString("ext_err_cond", str3);
        }
        String str4 = this.f24995d;
        if (str4 != null) {
            bundle.putString("ext_err_msg", str4);
        }
        List<gq> list = this.f513a;
        if (list != null) {
            Bundle[] bundleArr = new Bundle[list.size()];
            Iterator<gq> it = this.f513a.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Bundle bundleA = it.next().a();
                if (bundleA != null) {
                    bundleArr[i2] = bundleA;
                    i2++;
                }
            }
            bundle.putParcelableArray("ext_exts", bundleArr);
        }
        return bundle;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m472a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<error code=\"");
        sb.append(this.f24992a);
        sb.append("\"");
        if (this.f512a != null) {
            sb.append(" type=\"");
            sb.append(this.f512a);
            sb.append("\"");
        }
        if (this.f24994c != null) {
            sb.append(" reason=\"");
            sb.append(this.f24994c);
            sb.append("\"");
        }
        sb.append(">");
        if (this.f24993b != null) {
            sb.append("<");
            sb.append(this.f24993b);
            sb.append(" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\"/>");
        }
        if (this.f24995d != null) {
            sb.append("<text xml:lang=\"en\" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\">");
            sb.append(this.f24995d);
            sb.append("</text>");
        }
        Iterator<gq> it = m473a().iterator();
        while (it.hasNext()) {
            sb.append(it.next().d());
        }
        sb.append("</error>");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized List<gq> m473a() {
        List<gq> list = this.f513a;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = this.f24993b;
        if (str != null) {
            sb.append(str);
        }
        sb.append("(");
        sb.append(this.f24992a);
        sb.append(")");
        if (this.f24995d != null) {
            sb.append(" ");
            sb.append(this.f24995d);
        }
        return sb.toString();
    }
}
