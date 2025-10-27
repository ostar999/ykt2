package com.tencent.smtt.sdk.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static g f21146a;

    /* renamed from: b, reason: collision with root package name */
    private static f f21147b;

    private g() {
    }

    public static synchronized g a() {
        if (f21146a == null) {
            f21146a = new g();
        }
        return f21146a;
    }

    public static String a(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        if (strArr != null && strArr.length > 0) {
            if (strArr.length > 1) {
                for (int i2 = 0; i2 < strArr.length - 1; i2++) {
                    sb.append(strArr[i2]);
                    sb.append(",");
                }
            }
            sb.append(strArr[strArr.length - 1]);
        }
        return sb.toString();
    }

    public static String[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(",");
    }

    private synchronized SharedPreferences b(Context context) {
        return context.getSharedPreferences("tbs_emergence", 4);
    }

    public List<String> a(Context context, String str) {
        String string = b(context).getString(str, "");
        ArrayList arrayList = new ArrayList();
        String[] strArrSplit = string.split(h.f3376b);
        if (strArrSplit.length > 0) {
            arrayList.addAll(Arrays.asList(strArrSplit));
        }
        return arrayList;
    }

    public void a(Context context) {
        f21147b = f.a(new File(context.getFilesDir(), "prefs.lock"));
    }

    public void a(Context context, String str, long j2) {
        SharedPreferences.Editor editorEdit = b(context).edit();
        editorEdit.putLong(str, j2);
        editorEdit.apply();
    }

    public void a(Context context, String str, String str2) {
        List<String> listA = a(context, str);
        listA.add(str2);
        a(context, str, listA);
    }

    public void a(Context context, String str, List<String> list) {
        SharedPreferences.Editor editorEdit = b(context).edit();
        StringBuilder sb = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            if (list.size() > 1) {
                for (int i2 = 0; i2 < list.size() - 1; i2++) {
                    sb.append(list.get(i2));
                    sb.append(h.f3376b);
                }
            }
            sb.append(list.get(list.size() - 1));
        }
        editorEdit.putString(str, sb.toString());
        editorEdit.apply();
    }

    public long b(Context context, String str) {
        return b(context).getLong(str, -1L);
    }

    public boolean b() {
        return f21147b != null;
    }

    public void c() {
        f fVar = f21147b;
        if (fVar != null) {
            fVar.b();
            f21147b = null;
        }
    }
}
