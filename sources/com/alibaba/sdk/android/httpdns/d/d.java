package com.alibaba.sdk.android.httpdns.d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.just.agentweb.DefaultWebClient;
import com.yikaobang.yixue.R2;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    protected ExecutorService f2712a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f14a;

    /* renamed from: a, reason: collision with other field name */
    private int[] f15a;

    /* renamed from: b, reason: collision with root package name */
    private int f2713b;

    /* renamed from: b, reason: collision with other field name */
    private long f16b;

    /* renamed from: b, reason: collision with other field name */
    private boolean f17b;

    /* renamed from: b, reason: collision with other field name */
    private int[] f18b;

    /* renamed from: c, reason: collision with root package name */
    private int f2714c;

    /* renamed from: c, reason: collision with other field name */
    private boolean f19c;

    /* renamed from: c, reason: collision with other field name */
    private String[] f20c;
    private Context context;

    /* renamed from: d, reason: collision with root package name */
    private int f2715d;

    /* renamed from: d, reason: collision with other field name */
    private String f21d;

    /* renamed from: d, reason: collision with other field name */
    private String[] f22d;

    /* renamed from: e, reason: collision with root package name */
    private int f2716e;

    /* renamed from: e, reason: collision with other field name */
    private String f23e;

    /* renamed from: e, reason: collision with other field name */
    private String[] f24e;
    private boolean enabled = true;

    /* renamed from: f, reason: collision with root package name */
    private String f2717f;

    private d() {
        String[] strArr = com.alibaba.sdk.android.httpdns.a.f2703a;
        this.f20c = strArr;
        this.f22d = com.alibaba.sdk.android.httpdns.a.f2704b;
        this.f15a = null;
        this.f23e = DefaultWebClient.HTTP_SCHEME;
        this.f24e = strArr;
        this.f18b = null;
        this.f2713b = 0;
        this.f2714c = 0;
        this.f2715d = 0;
        this.f2717f = null;
        this.f16b = 0L;
        this.f2716e = 15000;
        this.f17b = false;
        this.f19c = false;
        this.f2712a = com.alibaba.sdk.android.httpdns.j.b.b();
    }

    public d(Context context, String str) {
        String[] strArr = com.alibaba.sdk.android.httpdns.a.f2703a;
        this.f20c = strArr;
        this.f22d = com.alibaba.sdk.android.httpdns.a.f2704b;
        this.f15a = null;
        this.f23e = DefaultWebClient.HTTP_SCHEME;
        this.f24e = strArr;
        this.f18b = null;
        this.f2713b = 0;
        this.f2714c = 0;
        this.f2715d = 0;
        this.f2717f = null;
        this.f16b = 0L;
        this.f2716e = 15000;
        this.f17b = false;
        this.f19c = false;
        this.f2712a = com.alibaba.sdk.android.httpdns.j.b.b();
        this.context = context;
        this.f21d = str;
        a(context, this);
    }

    private static void a(Context context, d dVar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("httpdns_config_" + dVar.getAccountId(), 0);
        dVar.f24e = com.alibaba.sdk.android.httpdns.j.a.m56b(sharedPreferences.getString("serverIps", com.alibaba.sdk.android.httpdns.j.a.a(com.alibaba.sdk.android.httpdns.a.f2703a)));
        dVar.f18b = com.alibaba.sdk.android.httpdns.j.a.m55b(sharedPreferences.getString("ports", null));
        dVar.f2714c = sharedPreferences.getInt("current", 0);
        dVar.f2713b = sharedPreferences.getInt("last", 0);
        dVar.f16b = sharedPreferences.getLong("servers_last_updated_time", 0L);
        dVar.f2717f = sharedPreferences.getString(TtmlNode.TAG_REGION, null);
        dVar.enabled = sharedPreferences.getBoolean("enable", true);
    }

    private boolean a(String[] strArr, int[] iArr, String[] strArr2, int[] iArr2) {
        return Arrays.equals(strArr, strArr2) && Arrays.equals(iArr, iArr2);
    }

    @SuppressLint({"ApplySharedPref"})
    private static void b(Context context, d dVar) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("httpdns_config_" + dVar.getAccountId(), 0).edit();
        editorEdit.putString("serverIps", com.alibaba.sdk.android.httpdns.j.a.a(dVar.f24e));
        editorEdit.putString("ports", com.alibaba.sdk.android.httpdns.j.a.a(dVar.f18b));
        editorEdit.putInt("current", dVar.f2714c);
        editorEdit.putInt("last", dVar.f2713b);
        editorEdit.putLong("servers_last_updated_time", dVar.f16b);
        editorEdit.putString(TtmlNode.TAG_REGION, dVar.f2717f);
        editorEdit.putBoolean("enable", dVar.enabled);
        editorEdit.commit();
    }

    private int getDefaultPort() {
        if (this.f23e.equals(DefaultWebClient.HTTP_SCHEME)) {
            return 80;
        }
        return R2.attr.banner_indicator_selected_color;
    }

    public d a() {
        d dVar = new d();
        dVar.context = this.context;
        dVar.f21d = this.f21d;
        dVar.f23e = this.f23e;
        dVar.f2717f = this.f2717f;
        String[] strArr = this.f24e;
        dVar.f24e = strArr == null ? null : (String[]) Arrays.copyOf(strArr, strArr.length);
        int[] iArr = this.f18b;
        dVar.f18b = iArr != null ? Arrays.copyOf(iArr, iArr.length) : null;
        dVar.f2713b = this.f2713b;
        dVar.f2714c = this.f2714c;
        dVar.f16b = this.f16b;
        dVar.f2716e = this.f2716e;
        dVar.f2712a = this.f2712a;
        dVar.f20c = this.f20c;
        dVar.f15a = this.f15a;
        return dVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ExecutorService m35a() {
        return this.f2712a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m36a() {
        String[] strArr = this.f22d;
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        this.f2715d = (this.f2715d + 1) % strArr.length;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m37a() {
        String[] strArr;
        return System.currentTimeMillis() - this.f16b >= 86400000 && (strArr = this.f24e) != null && strArr.length > 0;
    }

    public boolean a(String str, int i2) {
        String[] strArr = this.f24e;
        if (strArr == null || !str.equals(strArr[this.f2714c])) {
            return false;
        }
        int[] iArr = this.f18b;
        if (iArr != null && iArr[this.f2714c] != i2) {
            return false;
        }
        int i3 = this.f2714c + 1;
        this.f2714c = i3;
        if (i3 >= this.f24e.length) {
            this.f2714c = 0;
        }
        return this.f2714c == this.f2713b;
    }

    public boolean a(String str, String[] strArr, int[] iArr) {
        if (a(this.f24e, this.f18b, strArr, iArr)) {
            return false;
        }
        this.f2717f = str;
        this.f24e = strArr;
        this.f18b = iArr;
        this.f2713b = 0;
        this.f2714c = 0;
        if (!Arrays.equals(strArr, this.f20c)) {
            this.f16b = System.currentTimeMillis();
        }
        b(this.context, this);
        return true;
    }

    public boolean a(String[] strArr, int[] iArr) {
        return a(this.f2717f, strArr, iArr);
    }

    /* renamed from: a, reason: collision with other method in class */
    public String[] m38a() {
        return this.f24e;
    }

    public int b() {
        String[] strArr = this.f20c;
        if (strArr == null) {
            return 0;
        }
        return strArr.length;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m39b() {
        return this.f2717f;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m40b() {
        String[] strArr = this.f20c;
        if (strArr != null) {
            a(strArr, this.f15a);
        }
    }

    public void b(boolean z2) {
        this.f14a = z2;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m41b() {
        return this.f19c;
    }

    public boolean b(String str, int i2) {
        int[] iArr;
        String[] strArr = this.f24e;
        if (strArr == null || !strArr[this.f2714c].equals(str) || ((iArr = this.f18b) != null && iArr[this.f2714c] != i2)) {
            return false;
        }
        this.f2713b = this.f2714c;
        b(this.context, this);
        return true;
    }

    public String c() {
        return this.f23e;
    }

    public void c(boolean z2) {
        this.f17b = z2;
    }

    public String d() {
        int i2;
        String[] strArr = this.f24e;
        if (strArr == null || (i2 = this.f2714c) >= strArr.length || i2 < 0) {
            return null;
        }
        return strArr[i2];
    }

    public void d(boolean z2) {
        this.f19c = z2;
    }

    public String e() {
        int i2;
        String[] strArr = this.f22d;
        if (strArr == null || (i2 = this.f2715d) >= strArr.length) {
            return null;
        }
        return strArr[i2];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        d dVar = (d) obj;
        return this.enabled == dVar.enabled && this.f2713b == dVar.f2713b && this.f2714c == dVar.f2714c && this.f16b == dVar.f16b && this.f2716e == dVar.f2716e && com.alibaba.sdk.android.httpdns.j.a.equals(this.context, dVar.context) && Arrays.equals(this.f20c, dVar.f20c) && Arrays.equals(this.f15a, dVar.f15a) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f21d, dVar.f21d) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f23e, dVar.f23e) && Arrays.equals(this.f24e, dVar.f24e) && Arrays.equals(this.f18b, dVar.f18b) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f2717f, dVar.f2717f) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f2712a, dVar.f2712a);
    }

    public String getAccountId() {
        return this.f21d;
    }

    public Context getContext() {
        return this.context;
    }

    public int getPort() {
        int i2;
        int[] iArr = this.f18b;
        return (iArr == null || (i2 = this.f2714c) >= iArr.length || i2 < 0) ? getDefaultPort() : iArr[i2];
    }

    public int getTimeout() {
        return this.f2716e;
    }

    public int hashCode() {
        return (((((((Arrays.hashCode(new Object[]{this.context, Boolean.valueOf(this.enabled), this.f21d, this.f23e, Integer.valueOf(this.f2713b), Integer.valueOf(this.f2714c), this.f2717f, Long.valueOf(this.f16b), Integer.valueOf(this.f2716e), this.f2712a}) * 31) + Arrays.hashCode(this.f20c)) * 31) + Arrays.hashCode(this.f15a)) * 31) + Arrays.hashCode(this.f24e)) * 31) + Arrays.hashCode(this.f18b);
    }

    public boolean isEnabled() {
        return (!this.enabled || this.f14a || this.f17b) ? false : true;
    }

    public void setEnabled(boolean z2) {
        this.enabled = z2;
        b(this.context, this);
    }

    public void setHTTPSRequestEnabled(boolean z2) {
        this.f23e = z2 ? DefaultWebClient.HTTPS_SCHEME : DefaultWebClient.HTTP_SCHEME;
    }

    public void setTimeout(int i2) {
        this.f2716e = i2;
    }
}
