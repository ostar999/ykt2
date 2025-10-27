package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes6.dex */
public class gq implements gu {

    /* renamed from: a, reason: collision with root package name */
    private String f24946a;

    /* renamed from: a, reason: collision with other field name */
    private List<gq> f497a;

    /* renamed from: a, reason: collision with other field name */
    private String[] f498a;

    /* renamed from: b, reason: collision with root package name */
    private String f24947b;

    /* renamed from: b, reason: collision with other field name */
    private String[] f499b;

    /* renamed from: c, reason: collision with root package name */
    private String f24948c;

    public gq(String str, String str2, String[] strArr, String[] strArr2) {
        this.f497a = null;
        this.f24946a = str;
        this.f24947b = str2;
        this.f498a = strArr;
        this.f499b = strArr2;
    }

    public gq(String str, String str2, String[] strArr, String[] strArr2, String str3, List<gq> list) {
        this.f24946a = str;
        this.f24947b = str2;
        this.f498a = strArr;
        this.f499b = strArr2;
        this.f24948c = str3;
        this.f497a = list;
    }

    public static gq a(Bundle bundle) {
        ArrayList arrayList;
        String string = bundle.getString("ext_ele_name");
        String string2 = bundle.getString("ext_ns");
        String string3 = bundle.getString("ext_text");
        Bundle bundle2 = bundle.getBundle("attributes");
        Set<String> setKeySet = bundle2.keySet();
        String[] strArr = new String[setKeySet.size()];
        String[] strArr2 = new String[setKeySet.size()];
        int i2 = 0;
        for (String str : setKeySet) {
            strArr[i2] = str;
            strArr2[i2] = bundle2.getString(str);
            i2++;
        }
        if (bundle.containsKey("children")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("children");
            ArrayList arrayList2 = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                arrayList2.add(a((Bundle) parcelable));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return new gq(string, string2, strArr, strArr2, string3, arrayList);
    }

    public static Parcelable[] a(List<gq> list) {
        return a((gq[]) list.toArray(new gq[list.size()]));
    }

    public static Parcelable[] a(gq[] gqVarArr) {
        if (gqVarArr == null) {
            return null;
        }
        Parcelable[] parcelableArr = new Parcelable[gqVarArr.length];
        for (int i2 = 0; i2 < gqVarArr.length; i2++) {
            parcelableArr[i2] = gqVarArr[i2].m464a();
        }
        return parcelableArr;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putString("ext_ele_name", this.f24946a);
        bundle.putString("ext_ns", this.f24947b);
        bundle.putString("ext_text", this.f24948c);
        Bundle bundle2 = new Bundle();
        String[] strArr = this.f498a;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.f498a;
                if (i2 >= strArr2.length) {
                    break;
                }
                bundle2.putString(strArr2[i2], this.f499b[i2]);
                i2++;
            }
        }
        bundle.putBundle("attributes", bundle2);
        List<gq> list = this.f497a;
        if (list != null && list.size() > 0) {
            bundle.putParcelableArray("children", a(this.f497a));
        }
        return bundle;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Parcelable m464a() {
        return a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m465a() {
        return this.f24946a;
    }

    public String a(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (this.f498a == null) {
            return null;
        }
        int i2 = 0;
        while (true) {
            String[] strArr = this.f498a;
            if (i2 >= strArr.length) {
                return null;
            }
            if (str.equals(strArr[i2])) {
                return this.f499b[i2];
            }
            i2++;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m466a(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = he.a(str);
        }
        this.f24948c = str;
    }

    public String b() {
        return this.f24947b;
    }

    public String c() {
        return !TextUtils.isEmpty(this.f24948c) ? he.b(this.f24948c) : this.f24948c;
    }

    @Override // com.xiaomi.push.gu
    public String d() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(this.f24946a);
        if (!TextUtils.isEmpty(this.f24947b)) {
            sb.append(" ");
            sb.append("xmlns=");
            sb.append("\"");
            sb.append(this.f24947b);
            sb.append("\"");
        }
        String[] strArr = this.f498a;
        if (strArr != null && strArr.length > 0) {
            for (int i2 = 0; i2 < this.f498a.length; i2++) {
                if (!TextUtils.isEmpty(this.f499b[i2])) {
                    sb.append(" ");
                    sb.append(this.f498a[i2]);
                    sb.append("=\"");
                    sb.append(he.a(this.f499b[i2]));
                    sb.append("\"");
                }
            }
        }
        if (TextUtils.isEmpty(this.f24948c)) {
            List<gq> list = this.f497a;
            if (list == null || list.size() <= 0) {
                sb.append("/>");
                return sb.toString();
            }
            sb.append(">");
            Iterator<gq> it = this.f497a.iterator();
            while (it.hasNext()) {
                sb.append(it.next().d());
            }
        } else {
            sb.append(">");
            sb.append(this.f24948c);
        }
        sb.append("</");
        sb.append(this.f24946a);
        sb.append(">");
        return sb.toString();
    }

    public String toString() {
        return d();
    }
}
