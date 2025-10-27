package com.alibaba.sdk.android.tbrest.rest;

import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f2906a;

    /* renamed from: a, reason: collision with other field name */
    private a f65a;

    /* renamed from: a, reason: collision with other field name */
    private b f66a;

    public class a implements Comparator<String> {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2);
        }
    }

    public class b implements Comparator<String> {
        private b() {
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2) * (-1);
        }
    }

    private c() {
        this.f66a = new b();
        this.f65a = new a();
    }

    public static synchronized c a() {
        if (f2906a == null) {
            f2906a = new c();
        }
        return f2906a;
    }

    public String[] a(String[] strArr, boolean z2) {
        Comparator comparator;
        if (z2) {
            comparator = this.f65a;
        } else {
            comparator = this.f66a;
        }
        if (comparator == null || strArr == null || strArr.length <= 0) {
            return null;
        }
        Arrays.sort(strArr, comparator);
        return strArr;
    }
}
