package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.date.DatePattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public abstract class gt {

    /* renamed from: a, reason: collision with root package name */
    private static long f24967a;

    /* renamed from: a, reason: collision with other field name */
    public static final DateFormat f504a;

    /* renamed from: c, reason: collision with root package name */
    private static String f24969c;

    /* renamed from: a, reason: collision with other field name */
    private gx f505a;

    /* renamed from: a, reason: collision with other field name */
    private List<gq> f506a;

    /* renamed from: a, reason: collision with other field name */
    private final Map<String, Object> f507a;

    /* renamed from: d, reason: collision with root package name */
    private String f24970d;

    /* renamed from: e, reason: collision with root package name */
    private String f24971e;

    /* renamed from: f, reason: collision with root package name */
    private String f24972f;

    /* renamed from: g, reason: collision with root package name */
    private String f24973g;

    /* renamed from: h, reason: collision with root package name */
    private String f24974h;

    /* renamed from: i, reason: collision with root package name */
    private String f24975i;

    /* renamed from: a, reason: collision with other field name */
    protected static final String f503a = Locale.getDefault().getLanguage().toLowerCase();

    /* renamed from: b, reason: collision with root package name */
    private static String f24968b = null;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_MS_PATTERN);
        f504a = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        f24969c = he.a(5) + "-";
        f24967a = 0L;
    }

    public gt() {
        this.f24970d = f24968b;
        this.f24971e = null;
        this.f24972f = null;
        this.f24973g = null;
        this.f24974h = null;
        this.f24975i = null;
        this.f506a = new CopyOnWriteArrayList();
        this.f507a = new HashMap();
        this.f505a = null;
    }

    public gt(Bundle bundle) {
        this.f24970d = f24968b;
        this.f24971e = null;
        this.f24972f = null;
        this.f24973g = null;
        this.f24974h = null;
        this.f24975i = null;
        this.f506a = new CopyOnWriteArrayList();
        this.f507a = new HashMap();
        this.f505a = null;
        this.f24972f = bundle.getString("ext_to");
        this.f24973g = bundle.getString("ext_from");
        this.f24974h = bundle.getString("ext_chid");
        this.f24971e = bundle.getString("ext_pkt_id");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.f506a = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                gq gqVarA = gq.a((Bundle) parcelable);
                if (gqVarA != null) {
                    this.f506a.add(gqVarA);
                }
            }
        }
        Bundle bundle2 = bundle.getBundle("ext_ERROR");
        if (bundle2 != null) {
            this.f505a = new gx(bundle2);
        }
    }

    public static synchronized String i() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(f24969c);
        long j2 = f24967a;
        f24967a = 1 + j2;
        sb.append(Long.toString(j2));
        return sb.toString();
    }

    public static String q() {
        return f503a;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(this.f24970d)) {
            bundle.putString("ext_ns", this.f24970d);
        }
        if (!TextUtils.isEmpty(this.f24973g)) {
            bundle.putString("ext_from", this.f24973g);
        }
        if (!TextUtils.isEmpty(this.f24972f)) {
            bundle.putString("ext_to", this.f24972f);
        }
        if (!TextUtils.isEmpty(this.f24971e)) {
            bundle.putString("ext_pkt_id", this.f24971e);
        }
        if (!TextUtils.isEmpty(this.f24974h)) {
            bundle.putString("ext_chid", this.f24974h);
        }
        gx gxVar = this.f505a;
        if (gxVar != null) {
            bundle.putBundle("ext_ERROR", gxVar.a());
        }
        List<gq> list = this.f506a;
        if (list != null) {
            Bundle[] bundleArr = new Bundle[list.size()];
            Iterator<gq> it = this.f506a.iterator();
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

    public gq a(String str) {
        return a(str, null);
    }

    public gq a(String str, String str2) {
        for (gq gqVar : this.f506a) {
            if (str2 == null || str2.equals(gqVar.b())) {
                if (str.equals(gqVar.m465a())) {
                    return gqVar;
                }
            }
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public gx m469a() {
        return this.f505a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Object m470a(String str) {
        Map<String, Object> map = this.f507a;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    /* renamed from: a */
    public abstract String mo468a();

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Collection<gq> m471a() {
        if (this.f506a == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(this.f506a));
    }

    public void a(gq gqVar) {
        this.f506a.add(gqVar);
    }

    public void a(gx gxVar) {
        this.f505a = gxVar;
    }

    public synchronized Collection<String> b() {
        if (this.f507a == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet(this.f507a.keySet()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        gt gtVar = (gt) obj;
        gx gxVar = this.f505a;
        if (gxVar == null ? gtVar.f505a != null : !gxVar.equals(gtVar.f505a)) {
            return false;
        }
        String str = this.f24973g;
        if (str == null ? gtVar.f24973g != null : !str.equals(gtVar.f24973g)) {
            return false;
        }
        if (!this.f506a.equals(gtVar.f506a)) {
            return false;
        }
        String str2 = this.f24971e;
        if (str2 == null ? gtVar.f24971e != null : !str2.equals(gtVar.f24971e)) {
            return false;
        }
        String str3 = this.f24974h;
        if (str3 == null ? gtVar.f24974h != null : !str3.equals(gtVar.f24974h)) {
            return false;
        }
        Map<String, Object> map = this.f507a;
        if (map == null ? gtVar.f507a != null : !map.equals(gtVar.f507a)) {
            return false;
        }
        String str4 = this.f24972f;
        if (str4 == null ? gtVar.f24972f != null : !str4.equals(gtVar.f24972f)) {
            return false;
        }
        String str5 = this.f24970d;
        String str6 = gtVar.f24970d;
        if (str5 != null) {
            if (str5.equals(str6)) {
                return true;
            }
        } else if (str6 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.f24970d;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f24971e;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.f24972f;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.f24973g;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.f24974h;
        int iHashCode5 = (((((iHashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.f506a.hashCode()) * 31) + this.f507a.hashCode()) * 31;
        gx gxVar = this.f505a;
        return iHashCode5 + (gxVar != null ? gxVar.hashCode() : 0);
    }

    public String j() {
        if ("ID_NOT_AVAILABLE".equals(this.f24971e)) {
            return null;
        }
        if (this.f24971e == null) {
            this.f24971e = i();
        }
        return this.f24971e;
    }

    public String k() {
        return this.f24974h;
    }

    public void k(String str) {
        this.f24971e = str;
    }

    public String l() {
        return this.f24972f;
    }

    public void l(String str) {
        this.f24974h = str;
    }

    public String m() {
        return this.f24973g;
    }

    public void m(String str) {
        this.f24972f = str;
    }

    public String n() {
        return this.f24975i;
    }

    public void n(String str) {
        this.f24973g = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x00ef A[EXC_TOP_SPLITTER, PHI: r4
      0x00ef: PHI (r4v7 java.io.ByteArrayOutputStream) = (r4v5 java.io.ByteArrayOutputStream), (r4v8 java.io.ByteArrayOutputStream), (r4v8 java.io.ByteArrayOutputStream) binds: [B:55:0x0109, B:74:0x00ef, B:38:0x00ec] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0106 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String o() {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.gt.o():java.lang.String");
    }

    public void o(String str) {
        this.f24975i = str;
    }

    public String p() {
        return this.f24970d;
    }
}
