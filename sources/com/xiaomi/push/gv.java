package com.xiaomi.push;

import android.os.Bundle;

/* loaded from: classes6.dex */
public class gv extends gt {

    /* renamed from: a, reason: collision with root package name */
    private int f24976a;

    /* renamed from: a, reason: collision with other field name */
    private a f508a;

    /* renamed from: a, reason: collision with other field name */
    private b f509a;

    /* renamed from: b, reason: collision with root package name */
    private String f24977b;

    public enum a {
        chat,
        available,
        away,
        xa,
        dnd
    }

    public enum b {
        available,
        unavailable,
        subscribe,
        subscribed,
        unsubscribe,
        unsubscribed,
        error,
        probe
    }

    public gv(Bundle bundle) {
        super(bundle);
        this.f509a = b.available;
        this.f24977b = null;
        this.f24976a = Integer.MIN_VALUE;
        this.f508a = null;
        if (bundle.containsKey("ext_pres_type")) {
            this.f509a = b.valueOf(bundle.getString("ext_pres_type"));
        }
        if (bundle.containsKey("ext_pres_status")) {
            this.f24977b = bundle.getString("ext_pres_status");
        }
        if (bundle.containsKey("ext_pres_prio")) {
            this.f24976a = bundle.getInt("ext_pres_prio");
        }
        if (bundle.containsKey("ext_pres_mode")) {
            this.f508a = a.valueOf(bundle.getString("ext_pres_mode"));
        }
    }

    public gv(b bVar) {
        this.f509a = b.available;
        this.f24977b = null;
        this.f24976a = Integer.MIN_VALUE;
        this.f508a = null;
        a(bVar);
    }

    @Override // com.xiaomi.push.gt
    public Bundle a() {
        Bundle bundleA = super.a();
        b bVar = this.f509a;
        if (bVar != null) {
            bundleA.putString("ext_pres_type", bVar.toString());
        }
        String str = this.f24977b;
        if (str != null) {
            bundleA.putString("ext_pres_status", str);
        }
        int i2 = this.f24976a;
        if (i2 != Integer.MIN_VALUE) {
            bundleA.putInt("ext_pres_prio", i2);
        }
        a aVar = this.f508a;
        if (aVar != null && aVar != a.available) {
            bundleA.putString("ext_pres_mode", aVar.toString());
        }
        return bundleA;
    }

    @Override // com.xiaomi.push.gt
    /* renamed from: a */
    public String mo468a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<presence");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
            sb.append("\"");
        }
        if (j() != null) {
            sb.append(" id=\"");
            sb.append(j());
            sb.append("\"");
        }
        if (l() != null) {
            sb.append(" to=\"");
            sb.append(he.a(l()));
            sb.append("\"");
        }
        if (m() != null) {
            sb.append(" from=\"");
            sb.append(he.a(m()));
            sb.append("\"");
        }
        if (k() != null) {
            sb.append(" chid=\"");
            sb.append(he.a(k()));
            sb.append("\"");
        }
        if (this.f509a != null) {
            sb.append(" type=\"");
            sb.append(this.f509a);
            sb.append("\"");
        }
        sb.append(">");
        if (this.f24977b != null) {
            sb.append("<status>");
            sb.append(he.a(this.f24977b));
            sb.append("</status>");
        }
        if (this.f24976a != Integer.MIN_VALUE) {
            sb.append("<priority>");
            sb.append(this.f24976a);
            sb.append("</priority>");
        }
        a aVar = this.f508a;
        if (aVar != null && aVar != a.available) {
            sb.append("<show>");
            sb.append(this.f508a);
            sb.append("</show>");
        }
        sb.append(o());
        gx gxVarM469a = m469a();
        if (gxVarM469a != null) {
            sb.append(gxVarM469a.m472a());
        }
        sb.append("</presence>");
        return sb.toString();
    }

    public void a(int i2) {
        if (i2 >= -128 && i2 <= 128) {
            this.f24976a = i2;
            return;
        }
        throw new IllegalArgumentException("Priority value " + i2 + " is not valid. Valid range is -128 through 128.");
    }

    public void a(a aVar) {
        this.f508a = aVar;
    }

    public void a(b bVar) {
        if (bVar == null) {
            throw new NullPointerException("Type cannot be null");
        }
        this.f509a = bVar;
    }

    public void a(String str) {
        this.f24977b = str;
    }
}
