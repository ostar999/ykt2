package com.xiaomi.push;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class gr extends gt {

    /* renamed from: a, reason: collision with root package name */
    private a f24949a;

    /* renamed from: a, reason: collision with other field name */
    private final Map<String, String> f500a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final a f24950a = new a("get");

        /* renamed from: b, reason: collision with root package name */
        public static final a f24951b = new a("set");

        /* renamed from: c, reason: collision with root package name */
        public static final a f24952c = new a("result");

        /* renamed from: d, reason: collision with root package name */
        public static final a f24953d = new a("error");

        /* renamed from: e, reason: collision with root package name */
        public static final a f24954e = new a(com.heytap.mcssdk.constant.b.f7200y);

        /* renamed from: a, reason: collision with other field name */
        private String f501a;

        private a(String str) {
            this.f501a = str;
        }

        public static a a(String str) {
            if (str == null) {
                return null;
            }
            String lowerCase = str.toLowerCase();
            a aVar = f24950a;
            if (aVar.toString().equals(lowerCase)) {
                return aVar;
            }
            a aVar2 = f24951b;
            if (aVar2.toString().equals(lowerCase)) {
                return aVar2;
            }
            a aVar3 = f24953d;
            if (aVar3.toString().equals(lowerCase)) {
                return aVar3;
            }
            a aVar4 = f24952c;
            if (aVar4.toString().equals(lowerCase)) {
                return aVar4;
            }
            a aVar5 = f24954e;
            if (aVar5.toString().equals(lowerCase)) {
                return aVar5;
            }
            return null;
        }

        public String toString() {
            return this.f501a;
        }
    }

    public gr() {
        this.f24949a = a.f24950a;
        this.f500a = new HashMap();
    }

    public gr(Bundle bundle) {
        super(bundle);
        this.f24949a = a.f24950a;
        this.f500a = new HashMap();
        if (bundle.containsKey("ext_iq_type")) {
            this.f24949a = a.a(bundle.getString("ext_iq_type"));
        }
    }

    @Override // com.xiaomi.push.gt
    public Bundle a() {
        Bundle bundleA = super.a();
        a aVar = this.f24949a;
        if (aVar != null) {
            bundleA.putString("ext_iq_type", aVar.toString());
        }
        return bundleA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public a m467a() {
        return this.f24949a;
    }

    @Override // com.xiaomi.push.gt
    /* renamed from: a, reason: collision with other method in class */
    public String mo468a() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("<iq ");
        if (j() != null) {
            sb.append("id=\"" + j() + "\" ");
        }
        if (l() != null) {
            sb.append("to=\"");
            sb.append(he.a(l()));
            sb.append("\" ");
        }
        if (m() != null) {
            sb.append("from=\"");
            sb.append(he.a(m()));
            sb.append("\" ");
        }
        if (k() != null) {
            sb.append("chid=\"");
            sb.append(he.a(k()));
            sb.append("\" ");
        }
        for (Map.Entry<String, String> entry : this.f500a.entrySet()) {
            sb.append(he.a(entry.getKey()));
            sb.append("=\"");
            sb.append(he.a(entry.getValue()));
            sb.append("\" ");
        }
        if (this.f24949a == null) {
            str = "type=\"get\">";
        } else {
            sb.append("type=\"");
            sb.append(m467a());
            str = "\">";
        }
        sb.append(str);
        String strB = b();
        if (strB != null) {
            sb.append(strB);
        }
        sb.append(o());
        gx gxVarM469a = m469a();
        if (gxVarM469a != null) {
            sb.append(gxVarM469a.m472a());
        }
        sb.append("</iq>");
        return sb.toString();
    }

    public void a(a aVar) {
        if (aVar == null) {
            aVar = a.f24950a;
        }
        this.f24949a = aVar;
    }

    public synchronized void a(Map<String, String> map) {
        this.f500a.putAll(map);
    }

    public String b() {
        return null;
    }
}
