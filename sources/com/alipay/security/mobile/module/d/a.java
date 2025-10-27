package com.alipay.security.mobile.module.d;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private String f3421a;

    /* renamed from: b, reason: collision with root package name */
    private String f3422b;

    /* renamed from: c, reason: collision with root package name */
    private String f3423c;

    /* renamed from: d, reason: collision with root package name */
    private String f3424d;

    /* renamed from: e, reason: collision with root package name */
    private String f3425e;

    /* renamed from: f, reason: collision with root package name */
    private String f3426f;

    /* renamed from: g, reason: collision with root package name */
    private String f3427g;

    public a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.f3421a = str;
        this.f3422b = str2;
        this.f3423c = str3;
        this.f3424d = str4;
        this.f3425e = str5;
        this.f3426f = str6;
        this.f3427g = str7;
    }

    public final String toString() {
        StringBuilder sb;
        String strSubstring;
        StringBuilder sb2;
        String strSubstring2;
        StringBuilder sb3;
        String strSubstring3;
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        stringBuffer.append("," + this.f3421a);
        stringBuffer.append("," + this.f3422b);
        stringBuffer.append("," + this.f3423c);
        stringBuffer.append("," + this.f3424d);
        if (com.alipay.security.mobile.module.a.a.a(this.f3425e) || this.f3425e.length() < 20) {
            sb = new StringBuilder(",");
            strSubstring = this.f3425e;
        } else {
            sb = new StringBuilder(",");
            strSubstring = this.f3425e.substring(0, 20);
        }
        sb.append(strSubstring);
        stringBuffer.append(sb.toString());
        if (com.alipay.security.mobile.module.a.a.a(this.f3426f) || this.f3426f.length() < 20) {
            sb2 = new StringBuilder(",");
            strSubstring2 = this.f3426f;
        } else {
            sb2 = new StringBuilder(",");
            strSubstring2 = this.f3426f.substring(0, 20);
        }
        sb2.append(strSubstring2);
        stringBuffer.append(sb2.toString());
        if (com.alipay.security.mobile.module.a.a.a(this.f3427g) || this.f3427g.length() < 20) {
            sb3 = new StringBuilder(",");
            strSubstring3 = this.f3427g;
        } else {
            sb3 = new StringBuilder(",");
            strSubstring3 = this.f3427g.substring(0, 20);
        }
        sb3.append(strSubstring3);
        stringBuffer.append(sb3.toString());
        return stringBuffer.toString();
    }
}
