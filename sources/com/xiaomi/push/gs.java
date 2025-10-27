package com.xiaomi.push;

import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class gs extends gt {

    /* renamed from: a, reason: collision with root package name */
    private boolean f24955a;

    /* renamed from: b, reason: collision with root package name */
    private String f24956b;

    /* renamed from: b, reason: collision with other field name */
    private boolean f502b;

    /* renamed from: c, reason: collision with root package name */
    private String f24957c;

    /* renamed from: d, reason: collision with root package name */
    private String f24958d;

    /* renamed from: e, reason: collision with root package name */
    private String f24959e;

    /* renamed from: f, reason: collision with root package name */
    private String f24960f;

    /* renamed from: g, reason: collision with root package name */
    private String f24961g;

    /* renamed from: h, reason: collision with root package name */
    private String f24962h;

    /* renamed from: i, reason: collision with root package name */
    private String f24963i;

    /* renamed from: j, reason: collision with root package name */
    private String f24964j;

    /* renamed from: k, reason: collision with root package name */
    private String f24965k;

    /* renamed from: l, reason: collision with root package name */
    private String f24966l;

    public gs() {
        this.f24956b = null;
        this.f24957c = null;
        this.f24955a = false;
        this.f24963i = "";
        this.f24964j = "";
        this.f24965k = "";
        this.f24966l = "";
        this.f502b = false;
    }

    public gs(Bundle bundle) {
        super(bundle);
        this.f24956b = null;
        this.f24957c = null;
        this.f24955a = false;
        this.f24963i = "";
        this.f24964j = "";
        this.f24965k = "";
        this.f24966l = "";
        this.f502b = false;
        this.f24956b = bundle.getString("ext_msg_type");
        this.f24958d = bundle.getString("ext_msg_lang");
        this.f24957c = bundle.getString("ext_msg_thread");
        this.f24959e = bundle.getString("ext_msg_sub");
        this.f24960f = bundle.getString("ext_msg_body");
        this.f24961g = bundle.getString("ext_body_encode");
        this.f24962h = bundle.getString("ext_msg_appid");
        this.f24955a = bundle.getBoolean("ext_msg_trans", false);
        this.f502b = bundle.getBoolean("ext_msg_encrypt", false);
        this.f24963i = bundle.getString("ext_msg_seq");
        this.f24964j = bundle.getString("ext_msg_mseq");
        this.f24965k = bundle.getString("ext_msg_fseq");
        this.f24966l = bundle.getString("ext_msg_status");
    }

    @Override // com.xiaomi.push.gt
    public Bundle a() {
        Bundle bundleA = super.a();
        if (!TextUtils.isEmpty(this.f24956b)) {
            bundleA.putString("ext_msg_type", this.f24956b);
        }
        String str = this.f24958d;
        if (str != null) {
            bundleA.putString("ext_msg_lang", str);
        }
        String str2 = this.f24959e;
        if (str2 != null) {
            bundleA.putString("ext_msg_sub", str2);
        }
        String str3 = this.f24960f;
        if (str3 != null) {
            bundleA.putString("ext_msg_body", str3);
        }
        if (!TextUtils.isEmpty(this.f24961g)) {
            bundleA.putString("ext_body_encode", this.f24961g);
        }
        String str4 = this.f24957c;
        if (str4 != null) {
            bundleA.putString("ext_msg_thread", str4);
        }
        String str5 = this.f24962h;
        if (str5 != null) {
            bundleA.putString("ext_msg_appid", str5);
        }
        if (this.f24955a) {
            bundleA.putBoolean("ext_msg_trans", true);
        }
        if (!TextUtils.isEmpty(this.f24963i)) {
            bundleA.putString("ext_msg_seq", this.f24963i);
        }
        if (!TextUtils.isEmpty(this.f24964j)) {
            bundleA.putString("ext_msg_mseq", this.f24964j);
        }
        if (!TextUtils.isEmpty(this.f24965k)) {
            bundleA.putString("ext_msg_fseq", this.f24965k);
        }
        if (this.f502b) {
            bundleA.putBoolean("ext_msg_encrypt", true);
        }
        if (!TextUtils.isEmpty(this.f24966l)) {
            bundleA.putString("ext_msg_status", this.f24966l);
        }
        return bundleA;
    }

    @Override // com.xiaomi.push.gt
    /* renamed from: a */
    public String mo468a() {
        gx gxVarM469a;
        StringBuilder sb = new StringBuilder();
        sb.append("<message");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
            sb.append("\"");
        }
        if (this.f24958d != null) {
            sb.append(" xml:lang=\"");
            sb.append(h());
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
        if (!TextUtils.isEmpty(d())) {
            sb.append(" seq=\"");
            sb.append(d());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(e())) {
            sb.append(" mseq=\"");
            sb.append(e());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(f())) {
            sb.append(" fseq=\"");
            sb.append(f());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(g())) {
            sb.append(" status=\"");
            sb.append(g());
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
        if (this.f24955a) {
            sb.append(" transient=\"true\"");
        }
        if (!TextUtils.isEmpty(this.f24962h)) {
            sb.append(" appid=\"");
            sb.append(c());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(this.f24956b)) {
            sb.append(" type=\"");
            sb.append(this.f24956b);
            sb.append("\"");
        }
        if (this.f502b) {
            sb.append(" s=\"1\"");
        }
        sb.append(">");
        if (this.f24959e != null) {
            sb.append("<subject>");
            sb.append(he.a(this.f24959e));
            sb.append("</subject>");
        }
        if (this.f24960f != null) {
            sb.append("<body");
            if (!TextUtils.isEmpty(this.f24961g)) {
                sb.append(" encode=\"");
                sb.append(this.f24961g);
                sb.append("\"");
            }
            sb.append(">");
            sb.append(he.a(this.f24960f));
            sb.append("</body>");
        }
        if (this.f24957c != null) {
            sb.append("<thread>");
            sb.append(this.f24957c);
            sb.append("</thread>");
        }
        if ("error".equalsIgnoreCase(this.f24956b) && (gxVarM469a = m469a()) != null) {
            sb.append(gxVarM469a.m472a());
        }
        sb.append(o());
        sb.append("</message>");
        return sb.toString();
    }

    public void a(String str) {
        this.f24962h = str;
    }

    public void a(String str, String str2) {
        this.f24960f = str;
        this.f24961g = str2;
    }

    public void a(boolean z2) {
        this.f24955a = z2;
    }

    public String b() {
        return this.f24956b;
    }

    public void b(String str) {
        this.f24963i = str;
    }

    public void b(boolean z2) {
        this.f502b = z2;
    }

    public String c() {
        return this.f24962h;
    }

    public void c(String str) {
        this.f24964j = str;
    }

    public String d() {
        return this.f24963i;
    }

    public void d(String str) {
        this.f24965k = str;
    }

    public String e() {
        return this.f24964j;
    }

    public void e(String str) {
        this.f24966l = str;
    }

    @Override // com.xiaomi.push.gt
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        gs gsVar = (gs) obj;
        if (!super.equals(gsVar)) {
            return false;
        }
        String str = this.f24960f;
        if (str == null ? gsVar.f24960f != null : !str.equals(gsVar.f24960f)) {
            return false;
        }
        String str2 = this.f24958d;
        if (str2 == null ? gsVar.f24958d != null : !str2.equals(gsVar.f24958d)) {
            return false;
        }
        String str3 = this.f24959e;
        if (str3 == null ? gsVar.f24959e != null : !str3.equals(gsVar.f24959e)) {
            return false;
        }
        String str4 = this.f24957c;
        if (str4 == null ? gsVar.f24957c == null : str4.equals(gsVar.f24957c)) {
            return this.f24956b == gsVar.f24956b;
        }
        return false;
    }

    public String f() {
        return this.f24965k;
    }

    public void f(String str) {
        this.f24956b = str;
    }

    public String g() {
        return this.f24966l;
    }

    public void g(String str) {
        this.f24959e = str;
    }

    public String h() {
        return this.f24958d;
    }

    public void h(String str) {
        this.f24960f = str;
    }

    @Override // com.xiaomi.push.gt
    public int hashCode() {
        String str = this.f24956b;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f24960f;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.f24957c;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.f24958d;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.f24959e;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    public void i(String str) {
        this.f24957c = str;
    }

    public void j(String str) {
        this.f24958d = str;
    }
}
